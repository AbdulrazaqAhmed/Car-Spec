package com.example.car_spec.view.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.car_spec.R
import com.example.car_spec.databinding.FragmentAddCarBinding
import com.example.car_spec.databinding.FragmentDetailBinding
import com.example.car_spec.model.CarModel
import com.example.car_spec.model.FavoriteModel
import com.example.car_spec.view.viewmodel.CarsViewModel
import com.example.car_spec.view.viewmodel.FavoriteViewModel
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.zhihu.matisse.engine.impl.PicassoEngine

private lateinit var car : CarModel
private  lateinit var favModel : FavoriteModel
class DetailFragment : Fragment() {
    private val carViewModel: CarsViewModel by activityViewModels()
    private lateinit var binding: FragmentDetailBinding
    private val favoriteViewModel: FavoriteViewModel by activityViewModels()



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
//        favModel = FavoriteModel(favModel.documentId, favModel.userId)


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

                binding.detailDescriptionTextView.text = Car.description
                binding.detailCarPriceTextview.text = " ${Car.price} SAR"
                binding.detailMakeTextView.text = Car.make
                binding.detailModelTextview.text= Car.model
                binding.detailLocationTextView.text = Car.location
                binding.detailColorTextview.text= Car.color
                binding.detailYearTextView.text = Car.year

                car = Car





//                val specificItem = carViewModel.selectedCarItemFireStore
//                specificItem?.let {
//                    binding.
//                }
            }
            binding.favoriteToggleButton.setOnClickListener(){

                if (binding.favoriteToggleButton.isChecked) {
                    favModel = FavoriteModel()
                    favModel.make = car.make
                    favModel.model= car.model
                    favModel.color = car.color
                    favModel.year = car.year
                    favModel.title = car.title
                    favModel.location = car.location
                    favModel.date = car.date
                    favModel.price = car.price
                    favModel.image = car.image
                    favModel.description = car.description
                    favModel.documentId = car.documentId
                    favModel.userId = FirebaseAuth.getInstance().uid.toString()
                    favoriteViewModel.addFavorites(favModel)
                    Toast.makeText(context, "Liked", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.carFragment)
                }
                else{

                    favoriteViewModel.removeFavorite(favModel)
                }


            }
        })




    }
}
