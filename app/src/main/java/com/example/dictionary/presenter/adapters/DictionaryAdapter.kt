package com.example.dictionary.presenter.adapters

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.DataSetObserver
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.data.source.AppDatabase
import com.example.dictionary.databinding.ItemFoundBinding

class DictionaryAdapter : RecyclerView.Adapter<DictionaryAdapter.WordViewHolder>() {
    val dao = AppDatabase.init().dictionaryDao()
    private var mCursor: Cursor = dao.getAll()
    private var isDataValid = true
    var isUzbek = false
    var search = ""
            set(value){
                field = value
                if(value == ""){ mCursor = dao.getAll() }
                else if (isUzbek){ mCursor = dao.getFromUzbek(value) }
                else {mCursor = dao.getFromEnglish(value)}
                notifyDataSetChanged()
            }

    inner class WordViewHolder(private val binding: ItemFoundBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(english: String,uzbek: String){
            val text = SpannableString(english)
            text.setSpan(
                ForegroundColorSpan(Color.BLUE),
                0,
                if (search == "") 0 else search.length,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                )

            binding.found.text = text
            binding.translation.text = uzbek
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder(
            ItemFoundBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }


    override fun getItemCount(): Int = mCursor?.count ?: 0

    @SuppressLint("Range")
    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        if (!isDataValid) return
        if (isUzbek){
            mCursor.let {
                if(it.moveToPosition(position)){
                    val id = it.getLong(it.getColumnIndex("id"))
                    val english = it.getString(it.getColumnIndex("english"))
                    val uzbek = it.getString(it.getColumnIndex("uzbek"))
                    holder.bind(uzbek,english)
                }
            }
        }else{
            mCursor.let {
                if(it.moveToPosition(position)){
                    val id = it.getLong(it.getColumnIndex("id"))
                    val english = it.getString(it.getColumnIndex("english"))
                    val uzbek = it.getString(it.getColumnIndex("uzbek"))
                    holder.bind(english,uzbek)
                }
            }
        }
    }
}