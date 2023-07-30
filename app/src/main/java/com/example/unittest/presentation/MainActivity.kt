package com.example.unittest.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.unittest.BuildConfig
import com.example.unittest.R
import com.example.unittest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btn.setOnClickListener{
            //Todo
        }

    }
}