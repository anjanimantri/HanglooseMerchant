package com.vungle.hangloosemerchant.activity

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vungle.hangloosemerchant.App
import com.vungle.hangloosemerchant.R
import com.vungle.hangloosemerchant.model.LoginRequest
import com.vungle.hangloosemerchant.model.LoginResponse
import com.vungle.hangloosemerchant.utils.*
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.vungle.hangloosemerchant.utils.PreferenceHelper.set
import java.util.regex.Pattern.compile

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private var mPreference: SharedPreferences? = null
    private var mHeader: String? = null

    override fun onClick(view: View?) {
        /**
         * method to call API to verify signIn credentials
         */
        hideSoftKeyboard()
        pbLayout.visibility = View.VISIBLE
        mPreference = PreferenceHelper.defaultPrefs(this)
        val mail = editEmail.text.toString()
        val number = editPassword.text.toString()
        if(mail.isEmpty() || number.isEmpty() || !mail.isEmail()) {
            pbLayout.visibility = View.GONE
            Toast.makeText(applicationContext, "Enter valid Credential", Toast.LENGTH_LONG).show()
        } else {

            val callLogin: Call<LoginResponse> =
                App.getApiService()!!.login(LoginRequest(mail, number))
            callLogin.enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    call.cancel()
                    pbLayout.visibility = View.GONE
                    Toast.makeText(applicationContext, "Enter valid Credential", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    val loginResponse = response.body()
                    val headers = response.headers()
                    if (loginResponse != null) {
                        mHeader = headers.get("X-AUTH-TOKEN").toString()
                        Log.i("Hangloose", loginResponse.restaurantId)
                        mPreference!![X_AUTH_TOKEN] = headers.get("X-AUTH-TOKEN").toString()

                        Toast.makeText(applicationContext, "Success", Toast.LENGTH_LONG).show()
                        mPreference!![RESTAURANT_ID] = loginResponse.restaurantId
                        mPreference!![MERCHANT_EMAIL] = loginResponse.email
                        val intent =
                            Intent(this@LoginActivity, DrawerActivity::class.java)
                        startActivity(intent)
                    }
                    pbLayout.visibility = View.GONE
                }
            })
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        buttonSignIn.setOnClickListener(this)
    }

    private fun hideSoftKeyboard() {
        val inputMethodManager = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (this.currentFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(this.currentFocus!!.windowToken, 0)
        }
    }

    private val emailRegex = compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    private fun String.isEmail() : Boolean {
        return emailRegex.matcher(this).matches()
    }
}
