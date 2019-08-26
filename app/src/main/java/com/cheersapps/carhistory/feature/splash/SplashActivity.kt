package com.cheersapps.carhistory.feature.splash

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import com.cheersapps.carhistory.R
import com.cheersapps.carhistory.core.activity.BaseActivity
import com.cheersapps.carhistory.feature.home.HomeActivity
import com.cheersapps.carhistory.feature.home.HomePageActivity
import com.cheersapps.carhistory.feature.login.LoginActivity
import com.cheersapps.carhistory.feature.login.LoginViewModel
import com.cheersapps.carhistory.feature.onBoarding.OnBoardingActivity
import com.cheersapps.carhistory.utils.NavigationUtils
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {

    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this)[LoginViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splash_animation.addAnimatorUpdateListener {
            Log.d("animation", " fractions: ${it.animatedFraction}")
            Log.d("animation", " vals: ${it.animatedValue}")
            if (it.animatedFraction == 1f) {
                if (loginViewModel.getStayLoggedIn())
                    NavigationUtils.navigateTo(context = this, finish = true, activity = HomePageActivity::class.java)
                else
                    NavigationUtils.navigateTo(context = this, finish = true, activity = OnBoardingActivity::class.java)
            }
        }

        splash_animation.playAnimation()
    }
}
