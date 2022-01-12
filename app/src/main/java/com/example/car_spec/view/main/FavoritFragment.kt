package com.example.car_spec.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.car_spec.R
import com.example.car_spec.databinding.FragmentFavoritBinding
import com.example.car_spec.view.viewmodel.FavoriteViewModel


class FavoritFragment : Fragment() {
    private lateinit var binding: FragmentFavoritBinding
    private val favoriteViewModel: FavoriteViewModel by activityViewModels()
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

    }

    fun observer(){

    }

}