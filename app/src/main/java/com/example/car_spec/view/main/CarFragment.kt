package com.example.car_spec.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.car_spec.R
import com.example.car_spec.databinding.ActivityMainBinding
import com.example.car_spec.databinding.FragmentCarBinding

class CarFragment : Fragment() {
    private lateinit var binding: FragmentCarBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_car, container, false)








    }


}