package com.example.dictionary.presenter.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.speech.tts.TextToSpeech
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.R
import com.example.dictionary.data.model.Stared
import com.example.dictionary.databinding.ItemWordBinding
import com.example.dictionary.domain.AppRepository
import com.example.dictionary.domain.AppRepositoryImpl
import java.util.*

class DictionaryAdapter(private val context: Context) : RecyclerView.Adapter<DictionaryAdapter.WordViewHolder>()
//    , TextToSpeech.OnInitListener
{

    var onClick: ((data: com.example.dictionary.data.model.Dictionary)->Unit)? = null
    var onUpdate: ((data: Boolean)->Unit)? = null
    private val appRepository: AppRepository = AppRepositoryImpl.getAppRepository()
    private var mCursor: Cursor? = null
    private var isDataValid = true
    var isUzbek = false

    var search = ""
        set(value) {
            field = value
            if (value.isEmpty()) {
                appRepository.getAll { cursor ->
                    updateCursor(cursor)
                }
            } else if (isUzbek) {
                appRepository.getFromUzbek(value) { cursor ->
                    updateCursor(cursor)
                }
            } else {
                appRepository.getFromEnglish(value) { cursor ->
                    updateCursor(cursor)
                }
            }
        }

//    private var tts: TextToSpeech = TextToSpeech(context, this)

    inner class WordViewHolder(private val binding: ItemWordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(id: Long, english: String, uzbek: String, isF: Int) {
            var isFavorite = isF
            val text = SpannableString(english)
            text.setSpan(
                ForegroundColorSpan(android.graphics.Color.BLUE),
                0,
                if (search.isEmpty()) 0 else search.length,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            binding.found.text = text

            val starIcon = if (isFavorite == 1) R.drawable.bookmark else R.drawable.bookmark_unused
            binding.star.setImageResource(starIcon)

            binding.star.setOnClickListener {
                val newFav = if (isFavorite == 0) 1 else 0
                val newFavoriteStatus = Stared(id, newFav)
                appRepository.insertStared(newFavoriteStatus) {
                    search = search
//                    binding.star.setImageResource(if (isFavorite == 1) R.drawable.bookmark else R.drawable.bookmark_unused)
                }
            }
            binding.root.setOnClickListener {
                appRepository.getWordById(id) { dictionary ->
                    onClick?.invoke(dictionary)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder(
            ItemWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = mCursor?.count ?: 0

    @SuppressLint("Range")
    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        if (!isDataValid) return
        mCursor?.let {
            if (it.moveToPosition(position)) {
                val id = it.getLong(it.getColumnIndex("id"))
                val isFavourite = it.getInt(it.getColumnIndex("isStared"))
                val english = it.getString(it.getColumnIndex("english"))
                val uzbek = it.getString(it.getColumnIndex("uzbek"))
                    if (isUzbek) {
                        holder.bind(id, uzbek, english, isFavourite)
                    } else {
                        holder.bind(id, english, uzbek, isFavourite)
                    }
            }
        }
    }

    private fun updateCursor(newCursor: Cursor) {
        mCursor?.close() // Close the previous cursor if needed
        mCursor = newCursor
        if (mCursor?.count == 0) {
            onUpdate?.invoke(true)
        }else{
            onUpdate?.invoke(false)
        }
        notifyDataSetChanged()
    }
//
//    override fun onInit(status: Int) {
//        if (status == TextToSpeech.SUCCESS) {
//            val result = tts.setLanguage(Locale.US)
//            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                Toast.makeText(context, "Text-to-Speech initialization failed", Toast.LENGTH_SHORT).show()
//            }
//        } else {
//            Toast.makeText(context, "Text-to-Speech initialization failed", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun speakOut(text: String) {
//        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
//    }
//
//    fun onDestroy() {
//        tts.stop()
//        tts.shutdown()
//    }
}
