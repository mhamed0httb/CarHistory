package com.cheersapps.carhistory.feature.onBoarding

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.cheersapps.carhistory.R
import com.cheersapps.carhistory.core.activity.BaseActivity
import com.cheersapps.carhistory.feature.login.LoginActivity
import com.cheersapps.carhistory.utils.NavigationUtils
import kotlinx.android.synthetic.main.activity_on_boarding.*

class OnBoardingActivity : BaseActivity() {

    private val pagerAdapter: PagerAdapter by lazy {
        PagerAdapter()
    }

    private var isLasPage = false
    private var currentPage: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        initViewPager()
        initPagerListener()
        initClicks()

        onboarding_tabs.setupWithViewPager(onboarding_pager)
    }

    private fun initClicks() {
        onboarding_txv_skip.setOnClickListener {
            NavigationUtils.navigateTo(context = this, finish = true, activity = LoginActivity::class.java)
        }

        onboarding_img_next.setOnClickListener {
            if (isLasPage) {
                NavigationUtils.navigateTo(context = this, finish = true, activity = LoginActivity::class.java)
            } else {
                onboarding_pager.setCurrentItem(currentPage + 1, true)
            }
        }
    }

    private fun initPagerListener() {
        onboarding_pager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if (position == pagerAdapter.count - 1)
                    onboarding_img_next.setImageResource(R.drawable.ic_success)
                else
                    onboarding_img_next.setImageResource(R.drawable.ic_onboarding_next)

                isLasPage = position == pagerAdapter.count - 1
                currentPage = position
            }
        })

    }

    private fun initViewPager() {
        onboarding_pager.adapter = pagerAdapter

        val items = arrayOf(R.mipmap.bg_profile, R.mipmap.bg_onboarding)
        //pagerAdapter.changeAll(items = items.toList())
    }
}
