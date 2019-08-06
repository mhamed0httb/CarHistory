package com.cheersapps.carhistory.feature.splash

import android.os.Bundle
import android.util.Log
import com.cheersapps.carhistory.R
import com.cheersapps.carhistory.core.activity.BaseActivity
import com.cheersapps.carhistory.feature.login.LoginActivity
import com.cheersapps.carhistory.utils.NavigationUtils
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splash_animation.addAnimatorUpdateListener {
            Log.d("animation", " fractions: ${it.animatedFraction}")
            Log.d("animation", " vals: ${it.animatedValue}")
            if (it.animatedFraction == 1f) {
                NavigationUtils.navigateTo(context = this, finish = true, activity = LoginActivity::class.java)
            }
        }

        splash_animation.playAnimation()
    }
}
