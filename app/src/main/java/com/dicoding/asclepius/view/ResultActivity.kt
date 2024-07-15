package com.dicoding.asclepius.view

import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.entity.History
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.viewmodel.ResultViewModel
import com.dicoding.asclepius.viewmodel.ViewModelFactory
import java.io.ByteArrayOutputStream
import java.io.InputStream

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private lateinit var resultViewModel: ResultViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Menampilkan hasil gambar, prediksi, dan confidence score.
        resultViewModel = obtainViewModel(this@ResultActivity)

        initToolbar()
        setResult()
    }

    private fun initToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = getString(R.string.analysis)
        toolbar.navigationIcon = applicationContext.getDrawable(R.drawable.ic_arrow_back_ios_24)

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setResult() {
        val result = intent.getStringExtra(EXTRA_RESULT)
        val score = intent.getStringExtra(EXTRA_SCORE)
        val uri = intent.getStringExtra(EXTRA_URI)
        val time = intent.getStringExtra(EXTRA_TIME)

        val imageByteArray = imageUriToByteArray(Uri.parse(uri), applicationContext.contentResolver)
        try {
            binding.resultImage.setImageURI(Uri.parse(uri))
        } catch (e: Exception) {
            binding.resultImage.setImageDrawable(
                ContextCompat.getDrawable(
                    binding.root.context,
                    R.drawable.ic_place_holder
                )
            )
            Toast.makeText(
                binding.root.context, "Failed to load image", Toast.LENGTH_SHORT
            ).show()
        }

        val history = History(result, score, imageByteArray, uri, time)
        resultViewModel.insertHistory(history)

        binding.tvResult.text = binding.root.context.getString(R.string.result, result, score)
        binding.tvTime.text = binding.root.context.getString(R.string.time, time)
        if (!result.equals("CANCER")) {
            binding.tvDesc.text = binding.root.context.getString(R.string.non_cancer_desc)
        }
    }

    private fun imageUriToByteArray(uri: Uri, contentResolver: ContentResolver): ByteArray {
        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        inputStream?.use {
            val byteArrayOutputStream = ByteArrayOutputStream()
            it.copyTo(byteArrayOutputStream)
            return byteArrayOutputStream.toByteArray()
        }
        return byteArrayOf() // Return empty byte array if inputStream is null
    }

    private fun obtainViewModel(activity: AppCompatActivity): ResultViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[ResultViewModel::class.java]
    }

    companion object {
        const val EXTRA_RESULT = "extra_id"
        const val EXTRA_SCORE = "extra_score"
        const val EXTRA_URI = "extra_uri"
        const val EXTRA_TIME = "extra_time"
    }
}