package com.carlosu.androidappbundleexample.ondemand

import android.os.Bundle
import com.carlosu.androidappbundleexample.BaseSplitActivity
import com.carlosu.androidappbundleexample.ondemand.login_administrativos.R

class AdministrativosActivity : BaseSplitActivity()   {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_administrativos)
    }
}
