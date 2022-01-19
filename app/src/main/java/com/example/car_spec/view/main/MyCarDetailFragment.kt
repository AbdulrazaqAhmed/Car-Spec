package com.example.car_spec.view.main

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
import com.example.car_spec.databinding.FragmentMyCarDetailBinding
import com.example.car_spec.model.CarModel
import com.example.car_spec.view.adapters.CarRecyclerViewAdapter
import com.example.car_spec.view.viewmodel.MyCarsViewModel

private const val TAG = "MyCarDetailFragment"

class MyCarDetailFragment : Fragment() {
    private val myCarsViewModel: MyCarsViewModel by activityViewModels()
    private lateinit var binding: FragmentMyCarDetailBinding
    private lateinit var myCarInfoM : CarModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentMyCarDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()


        binding.updateMyCarButton.setOnClickListener() {
            updateMycar()

            Toast.makeText( context,"Updated successfully", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.myCarFragment2)

        }


        binding.deleteMycarsButton.setOnClickListener {
            val alertD = android.app.AlertDialog.Builder(context).setTitle("Delete This Post")
                .setMessage(
                    "Are you sure? All files will be deleted and no data recovery after this step"
                )
            alertD.setPositiveButton("Delete My Car") { _, _ ->
                Log.i(TAG, "Delete")


                myCarsViewModel.deleteMyCar(myCarInfoM)
                Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.myCarFragment2)
            }
                alertD.setNegativeButton("Cancel") { dialog, _ ->
                    dialog.cancel()
                }
                alertD.show()




//            Toast.makeText(, "Deleted successfully", Toast.LENGTH_SHORT).show()
//                findNavController().navigate(R.id.myCarFragment2)


        }
    }
    fun observer() {

        myCarsViewModel.selectedItemMutableLiveData.observe(viewLifecycleOwner, {
            Log.d(TAG, "selectedItemLiveData ")
            it?.let { myCar ->
                var myCarImagPath =
                    "https://firebasestorage.googleapis.com/v0/b/car-spec-9231b.appspot.com/o/image%2F${myCar.image}?alt=media&token=2e3a534c-22d3-48b0-8f0e-ee5a5d41897c"
                Glide.with(this)
                    .load(myCarImagPath)
                    .into(binding.myCarphotoDetailsImageView)


                binding.myCartitleDetailTextView.setText(myCar.title)
                binding.myCardetailCarPriceTextview.setText(myCar.price.toString())
                binding.myCardetailColorTextview.setText(myCar.color)
                binding.myCardetailDescriptionTextView.setText(myCar.description)
                binding.myCardetailMakeTextView.setText(myCar.make)
                binding.myCardetailModelTextview.setText(myCar.model)
                binding.myCardetailYearTextView.setText(myCar.year)
                binding.myCardetailLocationTextView.setText(myCar.location)

                myCarInfoM = myCar


            }

        })

        myCarsViewModel.mycarsLiveData.observe(viewLifecycleOwner, {
            Log.d(TAG, "observer: live Data")

        })

        myCarsViewModel.mycarsErrorLiveData.observe(viewLifecycleOwner, {
            Log.d(TAG, "observer: error live data")
        })


    }
    fun updateMycar(){
        myCarInfoM.title = binding.myCartitleDetailTextView.text.toString()
        myCarInfoM.make    = binding.myCardetailMakeTextView.text.toString()
        myCarInfoM.model   = binding.myCardetailModelTextview.text.toString()
        myCarInfoM.color   = binding.myCardetailColorTextview.text.toString()
        myCarInfoM.year    = binding.myCardetailYearTextView.text.toString()
        myCarInfoM.location= binding.myCardetailLocationTextView.text.toString()
        myCarInfoM.description= binding.myCardetailDescriptionTextView.text.toString()

        myCarsViewModel.editMycarInfo(myCarInfoM)

    }




}