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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val bottomNav: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
//        bottomNav.visibility = View.GONE

        carViewModel.selectedItemMutableLiveData.observe(viewLifecycleOwner, {
            Log.d("FragmentDetailLog", it.toString())
            it?.let { Car ->
                //
                var imagePath =
                    "https://firebasestorage.googleapis.com/v0/b/car-spec-9231b.appspot.com/o/image%2F${FirebaseAuth.getInstance().uid}?alt=media&token=787746ff-b858-49c6-91d4-218d775195b3"
                binding.titleDetailTextView.text = Car.title
                Glide.with(this)
                    .load(imagePath)
                    .into(binding.photoDetailsImageView)

                binding.detailDescriptionTextView.text = Car.description
                binding.detailCarPriceTextview.text = Car.price.toString()
                binding.detailMakeTextView.text = Car.make
                binding.detailModelTextview.text= Car.model
                binding.detailLocationTextView.text = Car.location
                binding.detailColorTextview.text= Car.color
                binding.detailYearTextView.text = Car.year



//                val specificItem = carViewModel.selectedCarItemFireStore
//                specificItem?.let {
//                    binding.
//                }
            }
        })
    }
}
