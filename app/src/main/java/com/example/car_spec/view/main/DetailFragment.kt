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
import com.example.car_spec.databinding.FragmentAddCarBinding
import com.example.car_spec.databinding.FragmentDetailBinding
import com.example.car_spec.view.viewmodel.CarsViewModel
import com.zhihu.matisse.engine.impl.PicassoEngine


class DetailFragment : Fragment() {
    private val carViewModel: CarsViewModel by activityViewModels()
    private lateinit var binding: FragmentDetailBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        carViewModel.selectedItemMutableLiveData.observe(viewLifecycleOwner, {
            Log.d("FragmentDetailLog", it.toString())
            it?.let { Car ->
                binding.titleDetailTextView.text = Car.title
                Glide.with(this).load(Car.image).into(binding.photoDetailsImageView)


            binding.detailDescriptionTextView.text = Car.description
            binding.detailCarPriceTextview.text = Car.price.toString()


//                val specificItem = carViewModel.selectedCarItemFireStore
//                specificItem?.let {
//                    binding.
//                }
        }
    })
}
}
