package com.tanyareznikova.gifapptinkoff.view.fragments


import androidx.lifecycle.Observer
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.tanyareznikova.gifapptinkoff.R
import com.tanyareznikova.gifapptinkoff.databinding.FragmentLatestGifBinding
import com.tanyareznikova.gifapptinkoff.utils.APP_ACTIVITY
import com.tanyareznikova.gifapptinkoff.utils.restartActivity
import com.tanyareznikova.gifapptinkoff.utils.showToast
import com.tanyareznikova.gifapptinkoff.viewmodel.MainViewModel
import com.weather.openweatherapp.database.PostModel
import com.weather.openweatherapp.database.SQLiteHelper
import kotlinx.android.synthetic.main.fragment_latest_gif.*

class LatestGifFragment : Fragment(R.layout.fragment_latest_gif) {

    private val viewModel by viewModels<MainViewModel>()

    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor

    private lateinit var binding: FragmentLatestGifBinding
    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var newPost: PostModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLatestGifBinding.bind(view)

        GET = APP_ACTIVITY.getSharedPreferences(APP_ACTIVITY.packageName, AppCompatActivity.MODE_PRIVATE)
        SET = GET.edit()

        sqLiteHelper = SQLiteHelper(APP_ACTIVITY)

        val gif = GET.getString("gif", "0")
        //latest_tv_author_gif.setText(cName)
        latest_tv_author_gif.text = gif.toString()
        viewModel.refreshRandomData(gif!!)
        //viewModel.refreshLatestData(gif!!)

        getRandomLiveData()
        //getLatestLiveData()

        latest_ib_back.setOnClickListener {

            getPost()

        }

        latest_ib_next.setOnClickListener {

            viewModel.refreshRandomData(gif!!)
            //viewModel.refreshLatestData(gif!!)
            getRandomLiveData()
            //getLatestLiveData()
            sqLiteHelper.insert(newPost)
            //showToast(newPost.toString())

            latest_ib_back.visibility = View.VISIBLE

        }
    }

    fun updatePost(items: MutableList<PostModel>){

        postList = items
        //notifyItemInserted(postList.size)

    }//updateCities

    //display all city names from DB
    private fun getPost(){
        val pList = sqLiteHelper.getAll()
        updatePost(pList)

        Glide.with(this)
            .load(pList[0].gifURL)
            .into(latest_imageview)
        latest_tv_author_gif.text = pList[0].author
        latest_tv_date_gif.text = pList[0].date
        latest_tv_desc_gif.text = pList[0].description
    }//getCity

    //random
    private fun getRandomLiveData() {

        viewModel.random_data.observe(viewLifecycleOwner, Observer { data ->
            data?.let {
                cl_with_img.visibility = View.VISIBLE

                newPost = PostModel(id = data.id, gifURL = data.gifURL, author = data.author,
                    date = data.date, description = data.description)

                Glide.with(this)
                    .load(data.gifURL)
                    .into(latest_imageview)
                latest_tv_author_gif.text = data.author
                latest_tv_date_gif.text = data.date
                latest_tv_desc_gif.text = data.description

            }
        })

        viewModel.random_error.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if (error) {
                    tv_error.visibility = View.VISIBLE
                    pb_loading.visibility = View.GONE
                    cl_with_img.visibility = View.GONE
                } else {
                    tv_error.visibility = View.GONE
                }
            }
        })

        viewModel.random_loading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (loading) {
                    pb_loading.visibility = View.VISIBLE
                    tv_error.visibility = View.GONE
                    cl_with_img.visibility = View.GONE
                } else {
                    pb_loading.visibility = View.GONE
                }
            }
        })

    }

    //latest
    private fun getLatestLiveData() {

        viewModel.latest_data.observe(viewLifecycleOwner, Observer { data ->
            data?.let {
                cl_with_img.visibility = View.VISIBLE

                Glide.with(this)
                    //.load("https://developerslife.ru/" + data.result[0].previewURL + "@2x.png")
                    .load(data.result[0].gifURL)
                    .into(latest_imageview)
                latest_tv_author_gif.text = data.result[0].author.toString()
                latest_tv_date_gif.text = data.result[0].date.toString()
                latest_tv_desc_gif.text = data.result[0].description.toString()

            }
        })

        viewModel.latest_error.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if (error) {
                    tv_error.visibility = View.VISIBLE
                    pb_loading.visibility = View.GONE
                    cl_with_img.visibility = View.GONE
                } else {
                    tv_error.visibility = View.GONE
                }
            }
        })

        viewModel.latest_loading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (loading) {
                    pb_loading.visibility = View.VISIBLE
                    tv_error.visibility = View.GONE
                    cl_with_img.visibility = View.GONE
                } else {
                    pb_loading.visibility = View.GONE
                }
            }
        })

    }

    companion object {
        var postList = mutableListOf<PostModel>()
    }

}