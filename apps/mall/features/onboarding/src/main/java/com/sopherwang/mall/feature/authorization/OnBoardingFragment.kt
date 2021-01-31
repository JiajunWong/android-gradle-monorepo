package com.sopherwang.mall.feature.authorization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment

class OnBoardingFragment : Fragment() {
    lateinit var root: MotionLayout
    lateinit var authButtonContainer: LinearLayout
    lateinit var signUpFlowContainer: LinearLayout
    lateinit var signUpButton: Button
    lateinit var loginButton: Button

    companion object {
        fun newInstance() = OnBoardingFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_onboarding, container, false)
        root = view.findViewById(R.id.fragment_onboarding_root)
        authButtonContainer = view.findViewById(R.id.fragment_onboarding_button_containers)
        signUpFlowContainer = view.findViewById(R.id.fragment_onboarding_sign_up_containers)
        signUpButton = view.findViewById(R.id.fragment_onboarding_button_sign_up)
        loginButton = view.findViewById(R.id.fragment_onboarding_button_login)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSignUpButton()
        setupLoginButton()
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
        loginButton.setOnClickListener{
            root.setTransition(
                R.id.fragment_onborading_root_start,
                R.id.fragment_onborading_root_login
            )
            root.transitionToEnd()
        }
    }
}
