package com.example.car_spec.view.main

import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.car_spec.R
import com.example.car_spec.databinding.FragmentAddCarBinding
import com.example.car_spec.model.CarModel
import com.example.car_spec.view.viewmodel.CarsViewModel
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy
import java.io.File
import java.util.*


class AddCarFragment : Fragment() {
    private lateinit var binding: FragmentAddCarBinding
    val carViewModel: CarsViewModel by activityViewModels()
    private val image_Picker = 1
    private lateinit var progressDialog: ProgressDialog



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        progressDialog = ProgressDialog(requireActivity())
        progressDialog.setTitle("Loading...")
        progressDialog.setCancelable(false)

        // Inflate the layout for this fragment
        binding = FragmentAddCarBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addImageImageView.setOnClickListener {
            showImagePicker()
            selectImage()




//binding.addImageImageView.setOnClickListener(new View.OnClickListener() {}
        }
//        val intent = Intent()




        binding.cancelAddButton.setOnClickListener {
            findNavController().navigate(R.id.CarFragment)
        }


        binding.saveAddButton.setOnClickListener() {


            val brandMake = binding.makeAddEditText.text.toString()
            val brandModel = binding.modelAddEdittext.text.toString()
            val brandColor = binding.colorAddEdittext.text.toString()
            val brandYear = binding.yearAddEdittext.text.toString()
            val addTitle = binding.titleAddEditTextText.text.toString()
            val addLocation = binding.locationAddEdittext.text.toString()
            val addPrice = binding.priceEditTextText.text.toString().toDouble()


            carViewModel.save(
                CarModel(
                    brandMake,
                    brandModel,
                    brandColor,
                    brandYear,
                    addTitle,
                    addLocation,
                    0,
                    "${Date()}",
                    addPrice,
                    true,
                    ""
                )
            )


        }


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == image_Picker && resultCode == RESULT_OK)
            progressDialog.show()

        val imagePath = Matisse.obtainResult(data)[0]

        carViewModel.uploadPhoto(imagePath) //----changed from Uri to File in uploadPhoto in carViewModel---

    }
    fun selectImage(){
        val intent = Intent()
        intent.type="image/"
        intent.action = Intent.ACTION_GET_CONTENT

    }

    fun showImagePicker() {
        Matisse.from(this)
            .choose(MimeType.ofImage(), false)
            .capture(true)
            .captureStrategy(CaptureStrategy(true, "com.example.car_spec"))
            .forResult(image_Picker)
    }

}