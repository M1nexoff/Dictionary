package com.example.dictionary

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
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
            .add(R.id.container, MainScreen())
            .commit()
    }
}
fun <T> runOnMainThread(callback: () -> T) {
    Handler(Looper.getMainLooper()).post {
        callback()
    }
}
