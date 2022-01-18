package com.example.car_spec.view.main

import android.annotation.SuppressLint
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
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
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

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val bottomNav: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
//        bottomNav.visibility = View.GONE

        carViewModel.selectedItemMutableLiveData.observe(viewLifecycleOwner, {
            Log.d("FragmentDetailLog", it.toString())
            it?.let { Car ->
                //
                var imagePath =
                    "https://firebasestorage.googleapis.com/v0/b/car-spec-9231b.appspot.com/o/image%2F${Car.image}?alt=media&token=2e3a534c-22d3-48b0-8f0e-ee5a5d41897c"
                binding.titleDetailTextView.text = Car.title
                Glide.with(this)
                    .load(imagePath)
                    .into(binding.photoDetailsImageView)

                binding.detailDescriptionTextView.text = "Description: ${Car.description}"
                binding.detailCarPriceTextview.text = "Price: ${Car.price}"
                binding.detailMakeTextView.text = "Make: ${Car.make}"
                binding.detailModelTextview.text= "Model: ${Car.model}"
                binding.detailLocationTextView.text = "Location: ${Car.location}"
                binding.detailColorTextview.text= "Color: ${Car.color}"
                binding.detailYearTextView.text = "Year: ${Car.year}"

//                binding.favoriteToggleButton.setOnClickListener(){
//                    if (binding.favoriteToggleButton.isChecked) {
//                        carViewModel.add(item.id)
//                    }else{
//                        viewModel.removeFavorite(item.id)
//                    }
//
//
//                }



//                val specificItem = carViewModel.selectedCarItemFireStore
//                specificItem?.let {
//                    binding.
//                }
            }
        })
    }
}
