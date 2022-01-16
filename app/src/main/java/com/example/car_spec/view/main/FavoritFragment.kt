package com.example.car_spec.view.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.car_spec.R
import com.example.car_spec.databinding.FragmentFavoritBinding
import com.example.car_spec.model.CarModel
import com.example.car_spec.view.adapters.FavoriteCarRecyclerAdopter
import com.example.car_spec.view.viewmodel.FavoriteViewModel

private val TAG = "FavoriteFragment"

class FavoritFragment : Fragment() {
    private lateinit var binding: FragmentFavoritBinding
    private val favoriteViewModel: FavoriteViewModel by activityViewModels()
    private lateinit var favCarAdapter: FavoriteCarRecyclerAdopter
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
        observer()
        favoriteViewModel.callFavorites()

    }

    fun observer() {
        favoriteViewModel.favoriteLiveData.observe(viewLifecycleOwner, {
            Log.d("observer: ", it.toString())
            binding.progressBar2.animate().alpha(0f)
            favCarAdapter.submitList(it)

        })
        favoriteViewModel.favoriteErrorData.observe(viewLifecycleOwner, {
            it?.let {
                Toast.makeText(requireActivity(), it , Toast.LENGTH_SHORT).show()
            }
        })

    }

}