package com.stechoq.quiz3

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.stechoq.quiz3.databinding.ActivityMainBinding
import com.stechoq.quiz3.network.PhotoApi
import com.stechoq.quiz3.ui.LoginActivity

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
        binding.logoutButton.setOnClickListener {
            logout()
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

    private fun logout() {
        binding.progressBar.visibility = View.VISIBLE
        FirebaseAuth.getInstance().signOut()
        binding.progressBar.visibility = View.GONE
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
