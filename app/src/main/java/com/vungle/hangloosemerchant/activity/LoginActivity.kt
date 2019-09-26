package com.vungle.hangloosemerchant.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.vungle.hangloosemerchant.App
import com.vungle.hangloosemerchant.R
import com.vungle.hangloosemerchant.model.LoginRequest
import com.vungle.hangloosemerchant.model.LoginResponse
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(view: View?) {
        /**
         * method to call API to verify signIn credentials
         */

        val callLogin: Call<LoginResponse> =
            App.getApiService()!!.login(LoginRequest("123@gmail.com", "Admin@1234"))
        callLogin.enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                call.cancel()
                Toast.makeText(applicationContext, "Failure", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val loginResponse = response.body()
                if (loginResponse != null) {
                    Log.i("Anjani", loginResponse.email)
                    Toast.makeText(applicationContext, "Success", Toast.LENGTH_LONG).show()
                }
            }
        })

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        buttonSignIn.setOnClickListener(this)
    }
}
