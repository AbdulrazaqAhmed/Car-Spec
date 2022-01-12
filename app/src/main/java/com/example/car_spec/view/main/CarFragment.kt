package com.example.car_spec.view.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.car_spec.MainActivity
import com.example.car_spec.R
import com.example.car_spec.accessablity.Login
import com.example.car_spec.accessablity.SHARED_PREF_FILE
import com.example.car_spec.databinding.FragmentCarBinding
import com.example.car_spec.model.CarModel
import com.example.car_spec.repository.ApiServiceRepo
import com.example.car_spec.repository.USER_ID
import com.example.car_spec.view.adapters.CarRecyclerViewAdapter
import com.example.car_spec.view.viewmodel.CarsViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CarFragment : Fragment() {
    private var allCars = listOf<CarModel>()
    private lateinit var carAdapter: CarRecyclerViewAdapter
    private val carViewModel: CarsViewModel by activityViewModels()
    private lateinit var binding: FragmentCarBinding
    private lateinit var sharedpreff: SharedPreferences
    private lateinit var sharedPreffEditor: SharedPreferences.Editor
    private lateinit var logoutItem: MenuItem
    private lateinit var userProfileItem: MenuItem

    private lateinit var carRepo: ApiServiceRepo


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

        carAdapter = CarRecyclerViewAdapter(carViewModel, requireActivity())
        binding.carItemRecyclerView.adapter = carAdapter

        setHasOptionsMenu(true)
        //
        observer()
        carViewModel.fitch()

    }

    // carViewModel.save("")     //calling function "save"  continue here


    fun observer() {

        carViewModel.carsLiveData.observe(viewLifecycleOwner, {
            Log.d("mainAc", it.toString())
            binding.progressBar4.animate().alpha(0F)
            binding.progressBar4.animate().alpha(0F).setDuration(1000)
            allCars = it
            carAdapter.submitList(it)
            binding.carItemRecyclerView.animate().alpha(1F)


        })


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.logout_item -> {
                sharedPreffEditor.putBoolean("state", false)
                sharedPreffEditor.commit()
                logoutItem.isVisible = true
                binding.progressBar4.animate().alpha(1f)
                binding.carItemRecyclerView.animate().alpha(0f)
                carViewModel.fitch()



                this?.let {
                    val intent = Intent(it.requireActivity(), Login::class.java)
                    it.startActivity(intent)
                }


            }
            R.id.profile_item -> {
                findNavController().navigate(R.id.action_carFragment_to_profileFragment)

            }
            R.id.addFragment -> {
                findNavController().navigate(R.id.action_carFragment_to_addCarFragment)

            }


        }

        return super.onOptionsItemSelected(item)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().menuInflater.inflate(R.menu.main_menu, menu)
        val searchItem = menu.findItem(R.id.app_bar_search)
        logoutItem = menu.findItem(R.id.logout_item)
        userProfileItem = menu.findItem(R.id.profile_item)


        val searchView =
            searchItem.actionView as androidx.appcompat.widget.SearchView  // SearchView "androidx"

        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                carAdapter.submitList(
                    allCars.filter {
                        it.title.lowercase().contains(query!!.lowercase())
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
