package com.stechoq.quiz3

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.stechoq.quiz3.databinding.ActivityMainBinding
import com.stechoq.quiz3.network.PhotoApi

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MainAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = MainAdapter()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )
        binding.recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getData().observe(this) { photos ->
            adapter.updateData(photos)
        }
        viewModel.getStatus().observe(this) { status ->
            updateProgress(status)
        }
    }

    private fun updateProgress(status: PhotoApi.ApiStatus) {
        when (status) {
            PhotoApi.ApiStatus.LOADING -> {
                binding.progressBar.visibility = View.VISIBLE
                binding.networkError.visibility = View.GONE
            }

            PhotoApi.ApiStatus.SUCCESS -> {
                binding.progressBar.visibility = View.GONE
                binding.networkError.visibility = View.GONE
            }

            PhotoApi.ApiStatus.FAILED -> {
                binding.progressBar.visibility = View.GONE
                binding.networkError.visibility = View.VISIBLE
            }
        }
    }
}
