package com.vungle.hangloosemerchant.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.vungle.hangloosemerchant.R
import com.vungle.hangloosemerchant.utils.MERCHANT_EMAIL
import com.vungle.hangloosemerchant.utils.PreferenceHelper
import com.vungle.hangloosemerchant.utils.PreferenceHelper.get
import kotlinx.android.synthetic.main.nav_header_drawer.*

class DrawerActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var mPreference: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer)
        mPreference = PreferenceHelper.defaultPrefs(this)
        val merchantEmail: String? = mPreference!![MERCHANT_EMAIL]
        Log.d("11111", " 00000 " + merchantEmail)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_logout
            ), drawerLayout
        )
        navView.setNavigationItemSelectedListener(this)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val haederView = navView.getHeaderView(0)
        var emailAddress = haederView.findViewById<TextView>(R.id.textEmailAddress)
        emailAddress.text = merchantEmail
       /* textBottomLogout.setOnClickListener {
            mPreference!!.edit().clear().apply()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            Toast.makeText(applicationContext, "Logout Successfully", Toast.LENGTH_LONG).show()
        }*/
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.d("11111", " 00000 " + item.itemId)
        when (item.itemId) {
            R.id.nav_logout -> {
                mPreference!!.edit().clear().apply()
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                Toast.makeText(applicationContext, "Logout Successfully", Toast.LENGTH_LONG).show()
            }
        }
        return false
    }
}
