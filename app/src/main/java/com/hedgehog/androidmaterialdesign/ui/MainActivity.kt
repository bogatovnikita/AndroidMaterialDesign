package com.hedgehog.androidmaterialdesign.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hedgehog.androidmaterialdesign.R
import com.hedgehog.androidmaterialdesign.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        supportFragmentManager.beginTransaction().replace(R.id.main_container, MainFragment())
            .commit()
        initMenu()
    }

    private fun initMenu() {
        binding.toolbar.setOnClickListener {
            when (it.id) {
                R.id.choose_theme ->
                    true
            }
        }
    }


}