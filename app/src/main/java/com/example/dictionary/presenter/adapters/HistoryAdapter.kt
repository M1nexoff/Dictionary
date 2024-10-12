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

class HistoryAdapter(private val context: Context) : RecyclerView.Adapter<HistoryAdapter.WordViewHolder>(),
    TextToSpeech.OnInitListener {
    var onUpdate: ((Boolean) -> Unit)? = null
    var onClick: ((data: com.example.dictionary.data.model.Dictionary)->Unit)? = null
    private val appRepository: AppRepository = AppRepositoryImpl.getAppRepository()
    private var mCursor: Cursor? = null
    private var isDataValid = true
    var isUzbek = false
        set(value) {
            if (value){
                appRepository.getRecentHistory{
                    updateCursor(it)
                    field = value
                }
            }else{
                appRepository.getRecentHistory{
                    updateCursor(it)
                    field = value
                }
            }
        }

    private fun updateCursor(cursor: Cursor) {
        mCursor?.close()
        mCursor = cursor
        if (mCursor?.count == 0) {
            onUpdate?.invoke(true)
        }else{
            onUpdate?.invoke(false)
        }
        notifyDataSetChanged()
    }

    private var tts: TextToSpeech = TextToSpeech(context, this)

    inner class WordViewHolder(private val binding: ItemWordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int,id: Long, english: String, uzbek: String) {
            val text = SpannableString(english)
            binding.found.text = text
            val starIcon = R.drawable.cancel
            binding.star.setImageResource(starIcon)
            binding.star.setOnClickListener {
                appRepository.clearHistoryForWord(id){
                    isUzbek = isUzbek
                }
            }
            binding.root.setOnClickListener {
                appRepository.getWordById(id) {
                    onClick?.invoke(it)
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
        if (isUzbek) {
            mCursor.let {
                if (it!!.moveToPosition(position)) {
                    val id = it.getLong(it.getColumnIndex("id"))
                    val english = it.getString(it.getColumnIndex("english"))
                    val uzbek = it.getString(it.getColumnIndex("uzbek"))
                    holder.bind(position,id, uzbek, english)
                }
            }
        } else {
            mCursor.let {
                if (it!!.moveToPosition(position)) {
                    val id = it.getLong(it.getColumnIndex("id"))
                    val english = it.getString(it.getColumnIndex("english"))
                    val uzbek = it.getString(it.getColumnIndex("uzbek"))
                    holder.bind(position,id,english,uzbek)
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