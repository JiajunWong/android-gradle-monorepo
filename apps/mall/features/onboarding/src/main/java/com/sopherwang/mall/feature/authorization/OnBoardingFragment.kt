package com.sopherwang.mall.feature.authorization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.textfield.TextInputEditText
import com.sopherwang.libaries.ui.base.hideKeyboard
import com.sopherwang.libraries.network.common.models.Status
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class OnBoardingFragment(private val authSuccessListener: AuthSuccessListener) : Fragment() {
    private val onBoardingViewModel: OnBoardingViewModel by viewModels()

    lateinit var root: MotionLayout
    lateinit var signUpButton: Button
    lateinit var loginButton: Button
    lateinit var signUpSubmitButton: Button
    lateinit var loginSubmitButton: Button
    lateinit var signUpUsername: TextInputEditText
    lateinit var signUpPassword: TextInputEditText
    lateinit var signUpAuthCode: TextInputEditText
    lateinit var loginUsername: TextInputEditText
    lateinit var loginPassword: TextInputEditText

    companion object {
        fun newInstance(authSuccessListener: AuthSuccessListener) = OnBoardingFragment(authSuccessListener)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_onboarding, container, false)
        root = view.findViewById(R.id.fragment_onboarding_root)
        signUpButton = view.findViewById(R.id.fragment_onboarding_button_sign_up)
        loginButton = view.findViewById(R.id.fragment_onboarding_button_login)
        signUpSubmitButton = view.findViewById(R.id.fragment_onboarding_button_sign_up_submit)
        loginSubmitButton = view.findViewById(R.id.fragment_onboarding_button_login_submit)
        signUpUsername = view.findViewById(R.id.fragment_onboarding_sign_up_username)
        signUpPassword = view.findViewById(R.id.fragment_onboarding_sign_up_password)
        signUpAuthCode = view.findViewById(R.id.fragment_onboarding_sign_up_auth_code)
        loginUsername = view.findViewById(R.id.fragment_onboarding_login_username)
        loginPassword = view.findViewById(R.id.fragment_onboarding_login_password)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSignUpButton()
        setupLoginButton()
        setupSignUpSubmitButton()
        sutupLoginSubmitButton()
        subscribeSignUp()
        subscribeLogin()
    }

    fun onBackPressed(): Boolean {
        if (!isAdded) {
            return false
        }
        if (root.progress > 0.5f) {
            root.transitionToStart()
            return true
        }
        return false
    }

    private fun setupSignUpButton() {
        signUpButton.setOnClickListener {
            root.setTransition(
                R.id.fragment_onborading_root_start,
                R.id.fragment_onborading_root_sign_up
            )
            root.transitionToEnd()
        }
    }

    private fun setupLoginButton() {
        loginButton.setOnClickListener {
            root.setTransition(
                R.id.fragment_onborading_root_start,
                R.id.fragment_onborading_root_login
            )
            root.transitionToEnd()
        }
    }

    private fun setupSignUpSubmitButton() {
        signUpSubmitButton.setOnClickListener {
            val username = signUpUsername.text.toString()
            val password = signUpPassword.text.toString()
            val authCode = signUpAuthCode.text.toString()

            onBoardingViewModel.updateSignUpRequest(username, password, authCode)
        }
    }

    private fun sutupLoginSubmitButton() {
        loginSubmitButton.setOnClickListener {
            val username = loginUsername.text.toString()
            val password = loginPassword.text.toString()

            onBoardingViewModel.updateLoginRequest(username, password)
        }
    }

    private fun subscribeSignUp() {
        onBoardingViewModel.signUpTokenLiveData.observe(
            viewLifecycleOwner,
            Observer {
                when (it.status) {
                    Status.LOADING -> {
                        Timber.tag(javaClass.simpleName).d("subscribeSignUp() loading.")
                    }
                    Status.SUCCESS -> {
                        Timber.tag(javaClass.simpleName).d("subscribeSignUp() success.")
                        root.hideKeyboard()
                        authSuccessListener.onAuthSuccess()
                    }
                    Status.ERROR -> {
                        Timber.tag(javaClass.simpleName).d("subscribeSignUp() error = ${it.message}.")
                    }
                }
            })
    }

    private fun subscribeLogin() {
        onBoardingViewModel.loginTokenLiveData.observe(
            viewLifecycleOwner,
            Observer {
                when (it.status) {
                    Status.LOADING -> {
                        Timber.tag(javaClass.simpleName).d("subscribeLogin() loading.")
                    }
                    Status.SUCCESS -> {
                        Timber.tag(javaClass.simpleName).d("subscribeLogin() success.")
                        root.hideKeyboard()
                        authSuccessListener.onAuthSuccess()
                    }
                    Status.ERROR -> {
                        Timber.tag(javaClass.simpleName).d("subscribeLogin() error = ${it.message}.")
                    }
                }
            })
    }

    interface AuthSuccessListener {
        fun onAuthSuccess()
    }
}
