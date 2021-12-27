package com.example.car_spec.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.car_spec.R
import com.example.car_spec.databinding.FragmentAddCarBinding
import com.example.car_spec.model.CarModel
import com.example.car_spec.view.viewmodel.CarsViewModel
import java.util.*


class AddCarFragment : Fragment() {
    private lateinit var binding: FragmentAddCarBinding
    val carViewModel: CarsViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddCarBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addImageImageView.setOnClickListener {


        }

        binding.cancelAddButton.setOnClickListener {

        }


        binding.saveAddButton.setOnClickListener() {

            val brandMake  = binding.makeAddEditText.text.toString()

            val brandModel =  binding.modelAddEdittext.text.toString()

            val brandColor =  binding.colorAddEdittext.text.toString()
            val brandYear  =  binding.yearAddEdittext.text.toString()
            val addTitle   = binding.titleAddEditTextText.text.toString()
            val addLocation= binding.locationAddEdittext.text.toString()
            val addPrice  = binding.priceEditTextText.text.toString().toDouble()


                carViewModel.save(
                    CarModel(
                        brandMake,
                        brandModel,
                        brandColor,
                        brandYear,
                        addTitle,
                        addLocation,
                        0,
                        Date(),
                        addPrice,
                        true,
                        ""
                    )
                )


        }


    }
}