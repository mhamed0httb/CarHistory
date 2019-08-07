package com.cheersapps.carhistory.feature.create

import android.os.Bundle
import com.cheersapps.carhistory.R
import com.cheersapps.carhistory.core.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_create.*

class CreateActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        //create_etx_desc.clearFocus()
    }
}
