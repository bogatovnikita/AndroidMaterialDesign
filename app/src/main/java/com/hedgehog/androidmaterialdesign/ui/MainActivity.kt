package com.hedgehog.androidmaterialdesign.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.hedgehog.androidmaterialdesign.R
import com.hedgehog.androidmaterialdesign.databinding.ActivityMainBinding
import com.hedgehog.androidmaterialdesign.ui.fragments.Cat
import com.hedgehog.androidmaterialdesign.ui.fragments.WikiSearchFragment
import com.hedgehog.androidmaterialdesign.ui.settings.ChooseThemeFragment
import com.hedgehog.androidmaterialdesign.ui.view_pager.ViewPager

const val ThemeDefault = 0
const val ThemeGreen = 1
const val ThemePink = 2

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val KEY_SP = "sp"
    private val KEY_CURRENT_THEME = "current_theme"

    private val binding: ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(getRealStyle(getCurrentTheme()))
        if (savedInstanceState == null) initFragment()
        initCLickMenu()
        initClickBottomNavigation()
    }

    private fun initClickBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nasa_api_picture -> {
                    initFragment()
                    true
                }
                R.id.about -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, Cat())
                        .addToBackStack("")
                        .commit()
                    true
                }
                R.id.wiki -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, WikiSearchFragment())
                        .addToBackStack("")
                        .commit()
                    true
                }
                else -> false
            }
        }
    }

    private fun initCLickMenu() {
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.choose_theme_item_menu -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, ChooseThemeFragment())
                        .addToBackStack("")
                        .commit()
                    true
                }
                else -> false
            }
        }
    }

    private fun initFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.main_container, ViewPager())
            .commit()
    }

    fun setCurrentTheme(currentTheme: Int) {
        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_CURRENT_THEME, currentTheme)
        editor.apply()
    }

    fun getCurrentTheme(): Int {
        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        return sharedPreferences.getInt(KEY_CURRENT_THEME, -1)
    }

    private fun getRealStyle(currentTheme: Int): Int {
        return when (currentTheme) {
            ThemeDefault -> R.style.Theme_AndroidMaterialDesign
            ThemeGreen -> R.style.GreenTheme
            ThemePink -> R.style.PinkTheme
            else -> -1
        }
    }
}