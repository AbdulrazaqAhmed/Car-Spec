package com.example.car_spec.view.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.car_spec.R
import com.example.car_spec.databinding.FragmentMyCarDetailBinding
import com.example.car_spec.view.viewmodel.MyCarsViewModel

private const val TAG = "MyCarDetailFragment"
class MyCarDetailFragment : Fragment() {
private val  myCarsViewModel : MyCarsViewModel by activityViewModels()
  private lateinit var  binding : FragmentMyCarDetailBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
binding = FragmentMyCarDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myCarsViewModel.selectedItemMutableLiveData.observe(viewLifecycleOwner,{
            Log.d(TAG, "selectedItemLiveData ")
            it?.let { myCar ->
                var myCarImagPath = "https://firebasestorage.googleapis.com/v0/b/car-spec-9231b.appspot.com/o/image%2F${myCar.image}?alt=media&token=2e3a534c-22d3-48b0-8f0e-ee5a5d41897c"
                Glide.with(this)
                    .load(myCarImagPath)
                    .into(binding.myCarphotoDetailsImageView)


                binding.myCartitleDetailTextView.setText(myCar.title)
                binding.myCardetailCarPriceTextview.setText(myCar.price.toString())
                binding.myCardetailColorTextview.setText(myCar.color)
                binding.myCardetailDescriptionTextView.setText(myCar.description)
                binding.myCardetailMakeTextView.setText(myCar.make)
                binding.myCardetailModelTextview.setText(myCar.model)
                binding.myCardetailYearTextView.setText(myCar.year)



            }

        })

    }
}