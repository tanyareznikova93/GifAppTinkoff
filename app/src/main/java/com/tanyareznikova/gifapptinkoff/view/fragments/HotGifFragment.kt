package com.tanyareznikova.gifapptinkoff.view.fragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.tanyareznikova.gifapptinkoff.R
import com.tanyareznikova.gifapptinkoff.databinding.FragmentHotGifBinding
import com.tanyareznikova.gifapptinkoff.databinding.FragmentTopGifBinding
import com.tanyareznikova.gifapptinkoff.utils.APP_ACTIVITY
import com.tanyareznikova.gifapptinkoff.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_hot_gif.*
import kotlinx.android.synthetic.main.fragment_latest_gif.*
import kotlinx.android.synthetic.main.fragment_top_gif.*
import kotlinx.android.synthetic.main.fragment_top_gif.pb_loading
import kotlinx.android.synthetic.main.fragment_top_gif.tv_error

class HotGifFragment : Fragment(R.layout.fragment_hot_gif) {

    private val viewModel by viewModels<MainViewModel>()

    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor

    private lateinit var binding: FragmentHotGifBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHotGifBinding.bind(view)

        GET = APP_ACTIVITY.getSharedPreferences(APP_ACTIVITY.packageName, AppCompatActivity.MODE_PRIVATE)
        SET = GET.edit()

        //viewModel2 = ViewModelProviders.of(this).get(MainViewModel::class.java)

        val name = GET.getString("gif", "0")
        //latest_tv_author_gif.setText(cName)
        hot_tv_author_gif.text = name.toString()
        viewModel.refreshHotData(name!!)
        getHotLiveData()

        hot_ib_back.setOnClickListener {
            getHotLiveData()
        }

        hot_ib_next.setOnClickListener {
            val gif = hot_tv_author_gif.text.toString()
            SET.putString("gif", gif)
            SET.apply()

            viewModel.refreshHotData(gif!!)
            getHotLiveData()
        }
    }

    //hot
    private fun getHotLiveData() {

        viewModel.hot_data.observe(viewLifecycleOwner, Observer { data ->
            data?.let {
                hot_cl_with_img.visibility = View.VISIBLE

                Glide.with(this)
                    //.load("https://developerslife.ru/" + data.result[0].previewURL + "@2x.png")
                    .load(data.result)
                    .into(hot_imageview)
                hot_tv_author_gif.text = data.result.toString()
                hot_tv_date_gif.text = data.result.toString()
                hot_tv_desc_gif.text = data.result.toString()

            }
        })

        viewModel.hot_error.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if (error) {
                    tv_error.visibility = View.VISIBLE
                    pb_loading.visibility = View.GONE
                    hot_cl_with_img.visibility = View.GONE
                } else {
                    tv_error.visibility = View.GONE
                }
            }
        })

        viewModel.hot_loading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (loading) {
                    pb_loading.visibility = View.VISIBLE
                    tv_error.visibility = View.GONE
                    hot_cl_with_img.visibility = View.GONE
                } else {
                    pb_loading.visibility = View.GONE
                }
            }
        })

    }

}