package com.example.car_spec.view.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.car_spec.R
import com.example.car_spec.accessablity.Login
import com.example.car_spec.accessablity.SHARED_PREF_FILE
import com.example.car_spec.databinding.FragmentFavoritBinding
import com.example.car_spec.model.CarModel
import com.example.car_spec.model.FavoriteModel
import com.example.car_spec.view.adapters.FavoriteCarRecyclerAdopter
import com.example.car_spec.view.viewmodel.FavoriteViewModel

private val TAG = "FavoriteFragment"

class FavoritFragment : Fragment() {
    private var allFavoriteCars = listOf<FavoriteModel>()
    private lateinit var binding: FragmentFavoritBinding
    private val favoriteViewModel: FavoriteViewModel by activityViewModels()
    private lateinit var favCarAdapter: FavoriteCarRecyclerAdopter
    private lateinit var sharedpreff: SharedPreferences
    private lateinit var sharedPreffEditor: SharedPreferences.Editor
    private lateinit var logoutItem: MenuItem
    private lateinit var userProfileItem: MenuItem
//private lateinit var favoriteRecyclerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favCarAdapter = FavoriteCarRecyclerAdopter(favoriteViewModel,requireActivity())
        binding.recyclerView.adapter = favCarAdapter

        setHasOptionsMenu(true)
        observer()
        favoriteViewModel.callFavorites()

    }

    fun observer() {
        favoriteViewModel.favoriteLiveData.observe(viewLifecycleOwner, {
            Log.d("observer: ", it.toString())
            binding.progressBar.animate().alpha(0f)
            binding.progressBar.animate().alpha(0F).setDuration(1000)
//            allFavoriteCars = it
            favCarAdapter.submitList(it)
//            binding.progressBar.animate().alpha(1F)

        })
        favoriteViewModel.favoriteErrorData.observe(viewLifecycleOwner, {
            it?.let {
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
            }
            favoriteViewModel.favoriteErrorData.postValue(null)

        })

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.logout_item -> {
                sharedpreff = requireActivity().getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
                sharedPreffEditor = sharedpreff.edit()
                sharedPreffEditor.putBoolean("state", false)
                sharedPreffEditor.commit()
                logoutItem.isVisible = true




                this?.let {
                    val intent = Intent(it.requireActivity(), Login::class.java)
                    it.startActivity(intent)
                }


            }
            R.id.profile_item -> {
                findNavController().navigate(R.id.action_favoritFragment2_to_profileFragment)

            }
            R.id.addFragment -> {
                findNavController().navigate(R.id.action_favoritFragment2_to_addCarFragment)

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
                favCarAdapter.submitList(
                    allFavoriteCars.filter {
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
                favCarAdapter.submitList(allFavoriteCars)
                return true

            }

        })

        // carViewModel.fitch("")


    }

}