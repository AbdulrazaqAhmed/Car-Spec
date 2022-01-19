package com.example.car_spec.view.adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.car_spec.R
import com.example.car_spec.databinding.FavoriteCarLayoutBinding
import com.example.car_spec.model.CarModel
import com.example.car_spec.view.viewmodel.FavoriteViewModel
import com.squareup.picasso.Picasso


class FavoriteCarRecyclerAdopter (val viewModel: FavoriteViewModel, val context: Context):
    RecyclerView.Adapter<FavoriteCarRecyclerAdopter.FavoriteViewHolder>() {


    val FavDIF_CAL_BACK = object : DiffUtil.ItemCallback<CarModel>() {
        override fun areItemsTheSame(oldItem: CarModel, newItem: CarModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CarModel, newItem: CarModel): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, FavDIF_CAL_BACK)

    fun submitList(list: List<CarModel>) {
        differ.submitList(list)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteCarRecyclerAdopter.FavoriteViewHolder {
        val binding =
            FavoriteCarLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)
        holder.binding.titleFavoriteTextView.text = item.title
        holder.binding.descriptionFavoriteTextView.text = item.description
        holder.binding.FavoritePriceTextview.text = item.price.toString()
        holder.binding.favoriteMakeTextView.text = item.make

//        Glide.with(context)
//            .load("https://firebasestorage.googleapis.com/v0/b/car-spec-9231b.appspot.com/o/image%2F${item.image}?alt=media&token=2e3a534c-22d3-48b0-8f0e-ee5a5d41897c")
//            .centerCrop()
//            .into(holder.myCarsimage)

//        holder.itemView.setOnClickListener(){ view ->
//            viewModel.selectedItemMutableLiveData.postValue(item)
//            view.findNavController().navigate(R.id.action_myCarFragment2_to_myCarDetailFragment)
//        }
        holder.binding.favoritedToggleButton.setOnClickListener {
            if (holder.binding.favoritedToggleButton.isChecked)
                viewModel.removeFavorite()
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class FavoriteViewHolder(val binding: FavoriteCarLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CarModel) {
            binding.titleFavoriteTextView.text = item.title
            binding.descriptionFavoriteTextView.text = item.description
            binding.FavoritePriceTextview.text = "${item.price} SAR"
            binding.favoriteMakeTextView.text = item.make
            Picasso.get().load(item.image).into(binding.photoFavoriteImageView)



        }
    }
}