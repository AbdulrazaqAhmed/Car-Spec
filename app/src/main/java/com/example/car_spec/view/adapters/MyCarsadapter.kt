package com.example.car_spec.view.adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.car_spec.R
import com.example.car_spec.model.CarModel
import com.example.car_spec.view.viewmodel.CarsViewModel
import com.example.car_spec.view.viewmodel.MyCarsViewModel


class MyCarsadapter(val viewModel: MyCarsViewModel, val context: Context) :
    RecyclerView.Adapter<MyCarsadapter.MyCarsViewHolder>() {


    val DIFF_CALL_BACK = object : DiffUtil.ItemCallback<CarModel>() {
        override fun areItemsTheSame(oldItem: CarModel, newItem: CarModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CarModel, newItem: CarModel): Boolean {
            return oldItem == newItem
        }


    }
    private val differ = AsyncListDiffer(this, DIFF_CALL_BACK)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyCarsadapter.MyCarsViewHolder {
        return MyCarsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.mycars_item_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyCarsViewHolder, position: Int) {
        val item = differ.currentList[position]


        holder.myCarstitle   .text = item.title
        holder.myCarslocation.text = item.location
        holder.myCarsdate    .text = item.date.toString()
        holder.myCarsprice   .text = "${item.price} SAR"
//        holder.favoriteIcon.isChecked = item.favorite
        Glide.with(context)
            .load("https://firebasestorage.googleapis.com/v0/b/car-spec-9231b.appspot.com/o/image%2F${item.image}?alt=media&token=2e3a534c-22d3-48b0-8f0e-ee5a5d41897c")
            .centerCrop()
            .into(holder.myCarsimage)

                holder.itemView.setOnClickListener(){ view ->
                    viewModel.selectedItemMutableLiveData.postValue(item)
                    view.findNavController().navigate(R.id.action_myCarFragment2_to_myCarDetailFragment)
                }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size

    }
    fun submitList(list: List<CarModel>) {
        differ.submitList(list)
    }

    class MyCarsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val myCarstitle   : TextView = itemView.findViewById(R.id.myCarstitel_textview)
        val myCarslocation: TextView = itemView.findViewById(R.id.myCarslocation_textview)
        val myCarsdate    : TextView = itemView.findViewById(R.id.myCarsdateCreated_textView)
        val myCarsprice   : TextView = itemView.findViewById(R.id.myCarsprice_textView)
        val myCarsimage   : ImageView = itemView.findViewById(R.id.myCarsImage_imageview)

    }
}