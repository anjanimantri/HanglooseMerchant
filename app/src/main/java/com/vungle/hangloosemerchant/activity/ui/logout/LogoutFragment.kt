package com.vungle.hangloosemerchant.activity.ui.logout

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.vungle.hangloosemerchant.R
import com.vungle.hangloosemerchant.activity.DrawerActivity
import com.vungle.hangloosemerchant.activity.LoginActivity
import com.vungle.hangloosemerchant.utils.PreferenceHelper

class LogoutFragment: Fragment() {

    private var mPreference: SharedPreferences? = null
    private var mContext: DrawerActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DrawerActivity) {
            mContext = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_logout, container, false)
        mPreference = PreferenceHelper.defaultPrefs(mContext!!)
        mPreference!!.edit().clear().apply()
        val intent = Intent(mContext, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        Toast.makeText(mContext, "Logout Successfully", Toast.LENGTH_LONG).show()
        return root
    }
}