package com.cheersapps.carhistory.feature.login

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import com.cheersapps.carhistory.MainActivity
import com.cheersapps.carhistory.R
import com.cheersapps.carhistory.core.activity.BaseActivity
import com.cheersapps.carhistory.feature.register.RegisterActivity
import com.cheersapps.carhistory.utils.NavigationUtils
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import java.util.concurrent.TimeUnit

class LoginActivity : BaseActivity() {


    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this)[LoginViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //App.appComponent.inject(this)
        setContentView(R.layout.activity_login)

        //initFocusListener()
    }

    private fun initFocusListener() {
        login_etx_username.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus)
                v.background = ContextCompat.getDrawable(this, R.drawable.bg_round_etx_focused)
            else
                v.background = ContextCompat.getDrawable(this, R.drawable.bg_round_etx)
        }

        login_etx_password.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus)
                v.background = ContextCompat.getDrawable(this, R.drawable.bg_round_etx_focused)
            else
                v.background = ContextCompat.getDrawable(this, R.drawable.bg_round_etx)
        }
    }


    /** CLICK Events */
    fun onNoAccountClick(v: View) {
        startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
    }

    fun onSubmitClick(v: View) {
        //NavigationUtils.navigateTo(context = this@LoginActivity, finish = true, activity = MainActivity::class.java)


        val username = login_etx_username.text
        val password = login_etx_password.text
        var isErrors = Pair(first = false, second = false)
        if (username.isNullOrEmpty()) {
            isErrors = isErrors.copy(first = true)
            login_txv_error_username.text = getString(R.string.empty_field)
            login_txv_error_username.visibility = View.VISIBLE
            login_etx_username.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake))
        } else {
            login_txv_error_username.visibility = View.GONE
        }

        if (password.isNullOrEmpty()) {
            isErrors = isErrors.copy(second = true)
            login_txv_error_password.text = getString(R.string.empty_field)
            login_txv_error_password.visibility = View.VISIBLE
            login_etx_password.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake))
        } else {
            login_txv_error_password.visibility = View.GONE
        }

        if (isErrors.first || isErrors.second) return

        // TODO: Check credentials first
        NavigationUtils.navigateTo(context = this@LoginActivity, finish = true, activity = MainActivity::class.java)

    }

    fun createFakeSingle(): Disposable {
        val d: Disposable = Single.just("Hello World")
                .delay(5, TimeUnit.SECONDS, Schedulers.io())
                .subscribeWith(object : DisposableSingleObserver<String>() {

                    override fun onStart() {
                        Log.d("Single", "Started")
                    }

                    override fun onSuccess(t: String) {
                        Log.d("Single", "success: $t")
                    }

                    override fun onError(e: Throwable) {

                        e.printStackTrace()
                    }

                })

        return d


    }
}
