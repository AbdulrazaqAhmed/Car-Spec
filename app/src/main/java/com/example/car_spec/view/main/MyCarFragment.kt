package com.example.car_spec.view.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.car_spec.databinding.FragmentMyCarBinding
import com.example.car_spec.model.CarModel
import com.example.car_spec.view.adapters.MyCarsadapter
import com.example.car_spec.view.viewmodel.MyCarsViewModel

private const val TAG = "MyCarFragment"

class MyCarFragment : Fragment() {
    private var allCars = listOf<CarModel>()
    private lateinit var myCarAdapter: MyCarsadapter
    private val myCarsViewModel: MyCarsViewModel by activityViewModels()
    private lateinit var binding: FragmentMyCarBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyCarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myCarAdapter = MyCarsadapter(myCarsViewModel, requireActivity())
        binding.mycarsItemRecyclerView.adapter = myCarAdapter

        setHasOptionsMenu(true)
        myCarsViewModel.getMyCars()
        observer()

    }

    fun observer() {

        myCarsViewModel.mycarsLiveData.observe(viewLifecycleOwner, {
            Log.d("obserLivedata", it.toString())
            binding.progressBar4.animate().alpha(0F)
            binding.progressBar4.animate().alpha(0F).setDuration(1000)
            allCars = it
            myCarAdapter.submitList(it)
            binding.mycarsItemRecyclerView.animate().alpha(1F)

        })

//        myCarsViewModel.mycarsLiveData.observe(viewLifecycleOwner, {
//            Log.d(TAG, "observer: my car live data")
//        })

//        myCarsViewModel.mycarsErrorLiveData.observe(viewLifecycleOwner, {
//            Log.d("observer Error", it.toString())
//        })

    }

    //--------------------- update My Car Info ----------------------------------------


}