package com.dicoding.asclepius.view

import android.os.Bundle
import android.view.View
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.entity.History
import com.dicoding.asclepius.databinding.ActivityHistoryBinding
import com.dicoding.asclepius.viewmodel.HistoryViewModel
import com.dicoding.asclepius.viewmodel.ViewModelFactory

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val historyViewModel = obtainViewModel(this@HistoryActivity)
        historyViewModel.getAllHistory().observe(this) { history ->
            checkSize(history)
            val items = mutableListOf<History>()
            history.map {
                val item = History(
                    result = it.result,
                    score = it.score,
                    imageByteArray = it.imageByteArray,
                    inferenceTime = it.inferenceTime
                )
                items.add(item)
            }
            setHistoryData(items)
        }
        historyViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        initToolbar()
        initRecycleView()
    }

    private fun initToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = getString(R.string.history)
        toolbar.navigationIcon = applicationContext.getDrawable(R.drawable.ic_arrow_back_ios_24)

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initRecycleView() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvHistory.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvHistory.addItemDecoration(itemDecoration)
    }

    private fun setHistoryData(histories: List<History?>?) {
        val adapter = HistoryAdapter()
        adapter.submitList(histories)
        binding.rvHistory.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.tvNoData.visibility = View.GONE
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun checkSize(list: List<History>) {
        if (list.isEmpty()) {
            binding.tvNoData.visibility = View.VISIBLE
        } else {
            binding.tvNoData.visibility = View.GONE
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): HistoryViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[HistoryViewModel::class.java]
    }
}