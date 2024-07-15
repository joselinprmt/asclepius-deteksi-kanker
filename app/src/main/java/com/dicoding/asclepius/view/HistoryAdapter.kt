package com.dicoding.asclepius.view

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.entity.History
import com.dicoding.asclepius.databinding.ItemHistoryBinding

class HistoryAdapter: ListAdapter<History, HistoryAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class MyViewHolder(private val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: History){
            item.imageByteArray.let {
                val bitmap = BitmapFactory.decodeByteArray(item.imageByteArray, 0, item.imageByteArray.size)
                try {
                    binding.resultImage.setImageBitmap(bitmap)
                } catch (e: Exception) {
                    binding.resultImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            binding.root.context,
                            R.drawable.ic_place_holder
                        )
                    )
                }
            }
            binding.tvLabel.text = item.result
            binding.tvScore.text = binding.root.context.getString(R.string.score, item.score)
            binding.tvTime.text = binding.root.context.getString(R.string.time, item.inferenceTime)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<History>() {
            override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
                return oldItem == newItem
            }
        }
    }
}