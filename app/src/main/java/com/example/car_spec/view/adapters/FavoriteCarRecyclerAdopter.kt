package com.example.car_spec.view.adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.car_spec.R
import com.example.car_spec.model.FavoriteModel
import com.example.car_spec.view.viewmodel.FavoriteViewModel

private lateinit var myfavo: FavoriteModel

class FavoriteCarRecyclerAdopter(val viewModel: FavoriteViewModel, val context: Context) :
    RecyclerView.Adapter<FavoriteCarRecyclerAdopter.FavoriteViewHolder>() {


    val FavDIF_CAL_BACK = object : DiffUtil.ItemCallback<FavoriteModel>() {
        override fun areItemsTheSame(oldItem: FavoriteModel, newItem: FavoriteModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FavoriteModel, newItem: FavoriteModel): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, FavDIF_CAL_BACK)


    override fun onCreateViewHolder(

        parent: ViewGroup,
        viewType: Int
    ): FavoriteCarRecyclerAdopter.FavoriteViewHolder {
        return FavoriteViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.favorite_car_layout,
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val item = differ.currentList[position]

            holder.title.text = item.title
            holder.description.text = item.location
            holder.make.text = item.date.toString()
            holder.price.text = item.price.toString()



            Glide.with(context)
                .load("https://firebasestorage.googleapis.com/v0/b/car-spec-9231b.appspot.com/o/image%2F${item.image}?alt=media&token=2e3a534c-22d3-48b0-8f0e-ee5a5d41897c")
                .centerCrop()
                .into(holder.image)
            myfavo = item



        holder.favoriteToggelButon.setOnClickListener {

            val instanceFavMod = mutableListOf<FavoriteModel>()

            if (holder.favoriteToggelButon.isChecked == false){
                viewModel.removeFavorite(myfavo)
                Toast.makeText(context, "Un Liked", Toast.LENGTH_SHORT).show()

            }
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<FavoriteModel>) {
        differ.submitList(list)

    }


    class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val title: TextView = itemView.findViewById(R.id.titleFavorite_TextView)
        val description: TextView = itemView.findViewById(R.id.descriptionFavorite_textView)
        val make: TextView = itemView.findViewById(R.id.favoriteMake_textView)
        val price: TextView = itemView.findViewById(R.id.FavoritePrice_textview)
        val favoriteToggelButon: ToggleButton = itemView.findViewById(R.id.favorited_toggle_button)
        val image: ImageView = itemView.findViewById(R.id.photoFavorite_imageView)
//    Picasso.get().load(item.image).into(binding.photoFavoriteImageView)


    }

}