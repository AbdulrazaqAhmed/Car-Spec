package com.example.car_spec.view.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.car_spec.R
import com.example.car_spec.accessablity.SHARED_PREF_FILE
import com.example.car_spec.databinding.ActivityMainBinding
import com.example.car_spec.databinding.FragmentCarBinding
import com.example.car_spec.model.CarModel
import com.example.car_spec.repository.ApiServiceRepo
import com.example.car_spec.view.adapters.CarRecyclerViewAdapter
import com.example.car_spec.view.viewmodel.CarsViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import okhttp3.internal.userAgent

class CarFragment : Fragment() {
    private var allCars = listOf<CarModel>()
    private lateinit var carAdapter: CarRecyclerViewAdapter
    private val carViewModel: CarsViewModel by activityViewModels()
    private lateinit var binding: FragmentCarBinding
    private lateinit var sharedpreff: SharedPreferences
    private lateinit var sharedPreffEditor: SharedPreferences.Editor
    private lateinit var logoutItem: MenuItem
    private lateinit var carRepo : ApiServiceRepo



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedpreff = requireActivity().getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
        sharedPreffEditor = sharedpreff.edit()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentCarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        carAdapter = CarRecyclerViewAdapter(CarsViewModel())
        binding.carItemRecyclerView.adapter = carAdapter

        //
        observer()


    }


    // carViewModel.save("")     //calling function "save"  continue here


    fun observer() {

        carViewModel.carsLiveData.observe(viewLifecycleOwner, {
            binding.progressBar4.animate().alpha(0F)
            binding.progressBar4.animate().alpha(0F).setDuration(1000)
            allCars = it
            carAdapter.submitList(it)
            binding.carItemRecyclerView.animate().alpha(1F)
            binding.carItemRecyclerView

        })

        carViewModel.carsErrorLiveData.postValue(null)


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().menuInflater.inflate(R.menu.main_menu, menu)
        val searchItem = menu.findItem(R.id.app_bar_search)
        logoutItem = menu.findItem(R.id.logout_item)


        val searchView =
            searchItem.actionView as androidx.appcompat.widget.SearchView  // SearchView "androidx"

        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                carAdapter.submitList(
                    allCars.filter {
                        it.Title.lowercase().contains(query!!.lowercase())
                    }
                )

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true

            }

        })
        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                carAdapter.submitList(allCars)
                return true

            }

        })

       // carViewModel.fitch("")



    }


}
