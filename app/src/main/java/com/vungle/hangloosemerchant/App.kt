package com.vungle.hangloosemerchant

import android.app.Application
import com.vungle.hangloosemerchant.network.ApiInf
import com.vungle.hangloosemerchant.network.RetrofitHelper

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        api = RetrofitHelper(this).create()
    }

    companion object {
        private var api: ApiInf? = null

        fun getApiService(): ApiInf? {
            return api!!
        }
    }
}