package com.example.db

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        // 初始化 Repository 和 ViewModel
        val driver = DatabaseDriverFactory(this).createDriver()
        val repository = NoteRepository(driver)
        
        setContent {
            val viewModel = remember { AppViewModel(repository) }
            App(viewModel)
        }
    }
}
