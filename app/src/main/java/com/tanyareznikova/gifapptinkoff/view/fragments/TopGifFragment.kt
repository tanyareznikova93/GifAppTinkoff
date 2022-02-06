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
import com.tanyareznikova.gifapptinkoff.databinding.FragmentLatestGifBinding
import com.tanyareznikova.gifapptinkoff.databinding.FragmentTopGifBinding
import com.tanyareznikova.gifapptinkoff.utils.APP_ACTIVITY
import com.tanyareznikova.gifapptinkoff.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_latest_gif.*
import kotlinx.android.synthetic.main.fragment_latest_gif.pb_loading
import kotlinx.android.synthetic.main.fragment_latest_gif.tv_error
import kotlinx.android.synthetic.main.fragment_top_gif.*

class TopGifFragment : Fragment(R.layout.fragment_top_gif) {

    private val viewModel by viewModels<MainViewModel>()

    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor

    private lateinit var binding: FragmentTopGifBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTopGifBinding.bind(view)

        GET = APP_ACTIVITY.getSharedPreferences(APP_ACTIVITY.packageName, AppCompatActivity.MODE_PRIVATE)
        SET = GET.edit()

        val name = GET.getString("gif", "0")
        top_tv_author_gif.text = name.toString()
        viewModel.refreshTopData(name!!)
        getTopLiveData()

        top_ib_back.setOnClickListener {
            getTopLiveData()
        }

        top_ib_next.setOnClickListener {
            val gif = top_tv_author_gif.text.toString()
            SET.putString("gif", gif)
            SET.apply()

            viewModel.refreshTopData(gif!!)
            getTopLiveData()
        }
    }

    //top
    private fun getTopLiveData() {

        viewModel.top_data.observe(viewLifecycleOwner, Observer { data ->
            data?.let {
                top_cl_with_img.visibility = View.VISIBLE

                Glide.with(this)
                    .load(data.result[0].gifURL)
                    .into(top_imageview)
                top_tv_author_gif.text = data.result[0].author
                top_tv_date_gif.text = data.result[0].date
                top_tv_desc_gif.text = data.result[0].description

            }
        })

        viewModel.top_error.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if (error) {
                    tv_error.visibility = View.VISIBLE
                    pb_loading.visibility = View.GONE
                    top_cl_with_img.visibility = View.GONE
                } else {
                    tv_error.visibility = View.GONE
                }
            }
        })

        viewModel.top_loading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (loading) {
                    pb_loading.visibility = View.VISIBLE
                    tv_error.visibility = View.GONE
                    top_cl_with_img.visibility = View.GONE
                } else {
                    pb_loading.visibility = View.GONE
                }
            }
        })

    }

}