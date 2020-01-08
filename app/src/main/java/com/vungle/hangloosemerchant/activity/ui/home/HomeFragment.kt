package com.vungle.hangloosemerchant.activity.ui.home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.vungle.hangloosemerchant.App
import com.vungle.hangloosemerchant.R
import com.vungle.hangloosemerchant.activity.DrawerActivity
import com.vungle.hangloosemerchant.model.RestaurantList
import com.vungle.hangloosemerchant.utils.PreferenceHelper
import com.vungle.hangloosemerchant.utils.PreferenceHelper.get
import com.vungle.hangloosemerchant.utils.RESTAURANT_ID
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private var mRestaurantId: String? = null
    private var mPreference: SharedPreferences? = null
    private var mContext: DrawerActivity? = null
    private var mSwipeRefreshFragment: SwipeRefreshLayout? = null
    private var mTextLogout: TextView? = null
    private var mTextLikes: TextView? = null
    private var mTextDislikes: TextView? = null
    private var mTextTotalViews: TextView? = null
    private var mTextRestaurantName: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        mSwipeRefreshFragment = root.findViewById(R.id.swipeRefreshFragment)
        mTextLikes = root.findViewById(R.id.textLikes)
        mTextDislikes = root.findViewById(R.id.textDislikes)
        mTextTotalViews = root.findViewById(R.id.textTotalViews)
        mTextRestaurantName = root.findViewById(R.id.textRestaurantName)

        mSwipeRefreshFragment!!.setOnRefreshListener(this)
        mPreference = PreferenceHelper.defaultPrefs(mContext!!)
        mRestaurantId = mPreference!![RESTAURANT_ID]
        doApiCallForLikeDisLike(mRestaurantId)
        return root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DrawerActivity) {
            mContext = context
        }
    }

    override fun onRefresh() {
        mRestaurantId = mPreference!![RESTAURANT_ID]
        doApiCallForLikeDisLike(mRestaurantId)
        Handler().postDelayed({ mSwipeRefreshFragment!!.isRefreshing = false }, 2000)
    }

    private fun doApiCallForLikeDisLike(restaurantId: String?) {
        val likeDislike: Call<RestaurantList> =
            App.getApiService()!!.getRestaurantById(restaurantId!!)
        likeDislike.enqueue(object : Callback<RestaurantList> {
            override fun onFailure(call: Call<RestaurantList>, t: Throwable) {
                call.cancel()
                Toast.makeText(mContext, "Failure", Toast.LENGTH_LONG).show()
                swipeRefreshFragment.isRefreshing = false
            }

            override fun onResponse(
                call: Call<RestaurantList>,
                response: Response<RestaurantList>
            ) {
                val likeDislikeResponse = response.body()
                Log.i("Hangloose", likeDislikeResponse!!.likes!!)
                mTextRestaurantName!!.text = likeDislikeResponse.name
                mTextLikes!!.text = likeDislikeResponse.likes
                mTextDislikes!!.text = likeDislikeResponse.disLikes
                val total = likeDislikeResponse.likes!!.toInt() + likeDislikeResponse.disLikes!!.toInt()
                mTextTotalViews!!.text = total.toString()
                mSwipeRefreshFragment!!.isRefreshing = false
            }
        })
    }
}