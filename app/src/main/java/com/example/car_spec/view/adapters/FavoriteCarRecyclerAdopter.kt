package com.example.car_spec.view.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.car_spec.R
import com.example.car_spec.databinding.FavoriteCarLayoutBinding
import com.example.car_spec.model.CarModel


class FavoriteCarRecyclerAdopter(private val list: List<CarModel>) :
    RecyclerView.Adapter<FavoriteCarRecyclerAdopter.FavoriteViewHolder>() {
    val DIF_CAL_BACK = object : DiffUtil.ItemCallback<CarModel>() {
        override fun areItemsTheSame(oldItem: CarModel, newItem: CarModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CarModel, newItem: CarModel): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIF_CAL_BACK)

    fun submitList(list: List<CarModel>) {
        differ.submitList(list)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteCarRecyclerAdopter.FavoriteViewHolder {
        val binding =
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val item = list[position]
//
//        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/car-spec-9231b.appspot.com/o/image%2F${item.image}?alt=media&token=787746ff-b858-49c6-91d4-218d775195b3")
//            .centerCrop()
//            .into(holder.image)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class FavoriteViewHolder(val binding: FavoriteCarLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CarModel) {
            binding.titleFavoriteTextView.text = item.title
            binding.detailFavoriteTextView.text= item.description
            binding.FavoritePriceTextview.text = "${item.price} SAR"
//            binding.photoFavoriteImageView.setImageIcon(R.id.)




        }
    }
}