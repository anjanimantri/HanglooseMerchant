package com.vungle.hangloosemerchant.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.vungle.hangloosemerchant.App
import com.vungle.hangloosemerchant.R
import com.vungle.hangloosemerchant.model.RestaurantList
import com.vungle.hangloosemerchant.utils.PreferenceHelper
import com.vungle.hangloosemerchant.utils.PreferenceHelper.get
import com.vungle.hangloosemerchant.utils.PreferenceHelper.set
import com.vungle.hangloosemerchant.utils.RESTAURANT_ID
import com.vungle.hangloosemerchant.utils.X_AUTH_TOKEN
import kotlinx.android.synthetic.main.activity_like_dislike.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LikeDisLikeRestaurantActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private var mRestaurantId: String? = null
    private var mPreference: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_like_dislike)
        swipeRefresh.setOnRefreshListener(this)
        mPreference = PreferenceHelper.defaultPrefs(this)
        mRestaurantId = mPreference!![RESTAURANT_ID]
        doApiCallForLikeDisLike(mRestaurantId)

        textLogout.setOnClickListener {
            mPreference!!.edit().clear().apply()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            Toast.makeText(applicationContext, "Logout Successfully", Toast.LENGTH_LONG).show()
        }
    }

    override fun onRefresh() {
        mRestaurantId = mPreference!![RESTAURANT_ID]
        doApiCallForLikeDisLike(mRestaurantId)
        Handler().postDelayed({ swipeRefresh.isRefreshing = false }, 2000)
    }

    private fun doApiCallForLikeDisLike(restaurantId: String?) {
        val likeDislike: Call<RestaurantList> =
            App.getApiService()!!.getRestaurantById(restaurantId!!)
            likeDislike.enqueue(object : Callback<RestaurantList> {
            override fun onFailure(call: Call<RestaurantList>, t: Throwable) {
                call.cancel()
                Toast.makeText(applicationContext, "Failure", Toast.LENGTH_LONG).show()
                swipeRefresh.isRefreshing = false
            }

            override fun onResponse(
                call: Call<RestaurantList>,
                response: Response<RestaurantList>
            ) {
                val likeDislikeResponse = response.body()
                Log.i("Anjani", likeDislikeResponse!!.likes!!)
                textLikes.text = likeDislikeResponse.likes
                textDislikes.text = likeDislikeResponse.disLikes
                val total = likeDislikeResponse.likes!!.toInt() + likeDislikeResponse.disLikes!!.toInt()
                textTotalViews.text = total.toString()
                swipeRefresh.isRefreshing = false
            }
        })
    }
}