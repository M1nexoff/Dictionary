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
import com.example.dictionary.databinding.ItemFoundBinding
import com.example.dictionary.domain.AppRepository
import com.example.dictionary.domain.AppRepositoryImpl
import java.util.*

class FavouriteAdapter(private val context: Context) : RecyclerView.Adapter<FavouriteAdapter.WordViewHolder>(),
    TextToSpeech.OnInitListener {
    private val appRepository: AppRepository = AppRepositoryImpl.getAppRepository()
    private var mCursor: Cursor = appRepository.getAllStared()
    private var isDataValid = true
    var isUzbek = false
    var search = ""
        set(value) {
            field = value
            if (value == "") {
                mCursor = appRepository.getAllStared()
            } else if (isUzbek) {
                mCursor = appRepository.getStaredFromUzbek(value)
            } else {
                mCursor = appRepository.getStaredFromEnglish(value)
            }
            notifyDataSetChanged()
        }

    private var tts: TextToSpeech = TextToSpeech(context, this)

    inner class WordViewHolder(private val binding: ItemFoundBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(id: Long, english: String, uzbek: String) {
            val text = SpannableString(english)
            text.setSpan(
                ForegroundColorSpan(android.graphics.Color.BLUE),
                0,
                if (search == "") 0 else search.length,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )

            binding.found.text = text
            binding.translation.text = uzbek

            val starIcon = R.drawable.star_checked
            binding.star.setImageResource(starIcon)

            binding.speaker.setOnClickListener {
                speakOut(uzbek)
            }

            binding.star.setOnClickListener {
                val newFavoriteStatus = Stared(id,false)
                appRepository.insertStared(newFavoriteStatus)
                search = search
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder(
            ItemFoundBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = mCursor.count ?: 0

    @SuppressLint("Range")
    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        if (!isDataValid) return
        if (isUzbek) {
            mCursor.let {
                if (it.moveToPosition(position)) {
                    val id = it.getLong(it.getColumnIndex("id"))
                    val english = it.getString(it.getColumnIndex("english"))
                    val uzbek = it.getString(it.getColumnIndex("uzbek"))
                    holder.bind(id, uzbek, english)
                }
            }
        } else {
            mCursor.let {
                if (it.moveToPosition(position)) {
                    val id = it.getLong(it.getColumnIndex("id"))
                    val english = it.getString(it.getColumnIndex("english"))
                    val uzbek = it.getString(it.getColumnIndex("uzbek"))
                    holder.bind(id,english,uzbek)
                }
            }
        }
    }

    
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA ||
                result == TextToSpeech.LANG_NOT_SUPPORTED
            ) {
                Toast.makeText(context,"Fail", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context,"Fail", Toast.LENGTH_SHORT).show()
        }
    }

    private fun speakOut(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    fun onDestroy() {
        tts.stop()
        tts.shutdown()
    }
}