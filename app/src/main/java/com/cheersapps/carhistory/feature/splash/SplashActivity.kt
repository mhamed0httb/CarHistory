package com.cheersapps.carhistory.feature.splash

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.lifecycle.ViewModelProviders
import com.cheersapps.carhistory.R
import com.cheersapps.carhistory.core.activity.BaseActivity
import com.cheersapps.carhistory.feature.home.HomePageActivity
import com.cheersapps.carhistory.feature.login.LoginViewModel
import com.cheersapps.carhistory.feature.onBoarding.OnBoardingActivity
import com.cheersapps.carhistory.utils.LocaleHelper
import com.cheersapps.carhistory.utils.NavigationUtils
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {

    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this)[LoginViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
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

        LocaleHelper.setLocale(this, loginViewModel.getAppLanguage().code)

        splash_animation.playAnimation()
    }
}
