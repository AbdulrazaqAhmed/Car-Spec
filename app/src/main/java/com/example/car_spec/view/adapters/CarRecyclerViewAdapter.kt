package com.example.car_spec.view.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ToggleButton
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.example.car_spec.R
import com.example.car_spec.model.CarModel
import com.example.car_spec.view.viewmodel.CarsViewModel


class CarRecyclerViewAdapter(val viewModel: CarsViewModel) :
    RecyclerView.Adapter<CarRecyclerViewAdapter.CarViewHolder>() {
    val DIFF_CALL_BACK = object : DiffUtil.ItemCallback<CarModel>() {
        override fun areItemsTheSame(oldItem: CarModel, newItem: CarModel): Boolean {
            return oldItem.Id == newItem.Id
        }

        override fun areContentsTheSame(oldItem: CarModel, newItem: CarModel): Boolean {
            return oldItem == newItem
        }


    }


    private val differ = AsyncListDiffer(this, DIFF_CALL_BACK)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CarRecyclerViewAdapter.CarViewHolder {
        return CarViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.car_item_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val item = differ.currentList[position]

        holder.title.text = item.Title
        holder.location.text = item.Location
        holder.date.text = item.Date.toString()
        holder.price.text = "${item.Price} SAR"
        holder.favoriteIcon.isChecked = item.isFavorite

//        holder.favoriteIcon.setOnClickListener() {
//           if (holder.favoriteIcon.isChecked) {
//               viewModel.
//           }
//        }


    }

    override fun getItemCount(): Int {
        return differ.currentList.size

    }

    fun submitList(list: List<CarModel>) {
        differ.submitList(list)
    }

    class CarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title: TextView = itemView.findViewById(R.id.titel_textview)
        val location: TextView = itemView.findViewById(R.id.location_textview)
        val date: TextView = itemView.findViewById(R.id.dateCreated_textView)
        val price: TextView = itemView.findViewById(R.id.price_textView)
        val favoriteIcon: ToggleButton = itemView.findViewById(R.id.favorite_toggle_button)

    }
}