package com.sopherwang.mall.app_root

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupBottomNavView()
    }

    private fun setupBottomNavView() {
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.main_page_bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_nav_tab_main -> {
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.bottom_nav_tab_category -> {
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.bottom_nav_tab_subject -> {
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.bottom_nav_tab_me -> {
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {
                    return@setOnNavigationItemSelectedListener false
                }
            }
        }
    }
}