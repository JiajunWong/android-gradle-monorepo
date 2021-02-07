package com.sopherwang.mall.app_root

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.IdRes
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sopherwang.libaries.ui.base.reduceDragSensitivity
import com.sopherwang.libraries.data_layer.product.ProductViewModel
import com.sopherwang.mall.feature.authorization.OnBoardingFragment
import com.sopherwang.mall.features.cart_list.CartListFragment
import com.sopherwang.mall.features.product_details.ProductDetailsFragment
import com.sopherwang.mall.libraries.data_layer.session.SessionViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import kotlin.math.abs

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val productViewModel: ProductViewModel by viewModels()
    private val sessionViewModel: SessionViewModel by viewModels()

    private lateinit var root: MotionLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var onBoardingFragment: OnBoardingFragment
    private lateinit var productDetailFragment: ProductDetailsFragment
    private lateinit var cartListFragment: CartListFragment

    private var lastProgress = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupFragment()
        setupRoot()
        setupBottomNavView()
        setupViewPager()
        subscribeProductClick()
        subscribeSessionChange()
    }

    override fun onBackPressed() {
        if (onBoardingFragment.isAdded) {
            if (!onBoardingFragment.onBackPressed()) {
                root.transitionToStart()
                return
            } else {
                return
            }
        }

        if (productDetailFragment.isAdded) {
            detachFragment(productDetailFragment)
            return
        }

        super.onBackPressed()
    }

    private fun setupFragment() {
        onBoardingFragment = OnBoardingFragment.newInstance()
        productDetailFragment = ProductDetailsFragment.newInstance()
        cartListFragment = CartListFragment.newInstance()
    }

    private fun setupRoot() {
        root = findViewById(R.id.activity_main_root)
        root.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
                if (p3 - lastProgress > 0) {
                    // from start to end
                    val atEnd = abs(p3 - 1f) < 0.1f
                    if (atEnd) {
                        val fragment =
                            if (true != sessionViewModel.hasAuthenticatedLiveData.value) onBoardingFragment else cartListFragment
                        attachFragment(fragment, R.id.main_page_cart_container)
                    }
                } else {
                    // from end to start
                    val atStart = p3 < 0.9f
                    if (atStart) {
                        val fragment =
                            if (true != sessionViewModel.hasAuthenticatedLiveData.value) onBoardingFragment else cartListFragment
                        detachFragment(fragment)
                    }
                }
                lastProgress = p3
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
            }

        })
    }

    private fun setupBottomNavView() {
        bottomNavigationView = findViewById(R.id.main_page_bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_nav_tab_main -> {
                    viewPager.currentItem = 0
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.bottom_nav_tab_category -> {
                    viewPager.currentItem = 1
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.bottom_nav_tab_subject -> {
                    viewPager.currentItem = 2
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.bottom_nav_tab_me -> {
                    viewPager.currentItem = 3
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {
                    return@setOnNavigationItemSelectedListener false
                }
            }
        }
    }

    private fun setupViewPager() {
        viewPager = findViewById(R.id.main_page_view_pager)
        viewPager.reduceDragSensitivity()
        with(viewPager) {
            adapter = MainPageViewPagerAdapter(supportFragmentManager, lifecycle)
            registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    bottomNavigationView.menu.getItem(position).isChecked = true
                }
            })
        }
    }

    private fun subscribeProductClick() {
        productViewModel.productLiveData.observe(this, Observer { product ->
            Timber.tag(javaClass.simpleName).d("Product ${product.name} has clicked")
            attachFragment(productDetailFragment, R.id.main_page_product_details_container)
        })
    }


    private fun subscribeSessionChange() {
        sessionViewModel.hasAuthenticatedLiveData.observe(this, Observer { hasAuthed ->
            if (hasAuthed && onBoardingFragment.isAdded) {
                attachFragment(cartListFragment, R.id.main_page_cart_container)
            }
            if (!hasAuthed && cartListFragment.isAdded) {
                attachFragment(onBoardingFragment, R.id.main_page_cart_container)
            }
        })
    }

    private fun attachFragment(fragment: Fragment, @IdRes id: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction
            .setCustomAnimations(R.animator.show, 0)
            .replace(id, fragment)
            .commitNow()
    }

    private fun detachFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction
            .setCustomAnimations(0, R.animator.hide)
        transaction
            .remove(fragment).commitNow()
    }
}
