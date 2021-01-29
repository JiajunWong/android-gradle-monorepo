package com.sopherwang.mall.feature.authorization

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingActivity : AppCompatActivity() {
    private lateinit var signUpButton: Button
    private lateinit var loginButton: Button

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, OnBoardingActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        signUpButton = findViewById(R.id.onboarding_button_sign_up)
        loginButton = findViewById(R.id.onboarding_button_login)
    }
}
