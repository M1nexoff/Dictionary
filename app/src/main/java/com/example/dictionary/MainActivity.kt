package com.example.dictionary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionary.databinding.ActivityMainBinding
import com.example.dictionary.presenter.MainScreen
import com.example.dictionary.presenter.adapters.DictionaryAdapter

class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::bind)
    lateinit var adapter: DictionaryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(R.id.main, MainScreen())
            .commit()
    }
}