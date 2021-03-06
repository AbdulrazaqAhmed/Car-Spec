package com.example.car_spec.view.main

import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.car_spec.R
import com.example.car_spec.databinding.FragmentAddCarBinding
import com.example.car_spec.model.CarModel
import com.example.car_spec.view.viewmodel.CarsViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.protobuf.Empty
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.math.log
import kotlin.streams.asSequence

const val STRING_LENGTH = 10;
const val ALPHANUMERIC_REGEX = "[a-zA-Z0-9]+"

class AddCarFragment : Fragment() {
    private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    private lateinit var imagePath: String
    private lateinit var binding: FragmentAddCarBinding
    private val carViewModel: CarsViewModel by activityViewModels()
    private val image_Picker = 1
    private lateinit var image: Uri


    //    val uploadImageLiveData = MutableLiveData<String>()
    private lateinit var progressDialog: ProgressDialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        progressDialog = ProgressDialog(requireActivity())
        progressDialog.setTitle("Uploading...")
        progressDialog.setCancelable(false)


        // Inflate the layout for this fragment
        binding = FragmentAddCarBinding.inflate(inflater, container, false)
        return binding.root


    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addImageImageView.setOnClickListener {

            showImagePicker()

            observer()
            bindSelectedImage()


//binding.addImageImageView.setOnClickListener(new View.OnClickListener() {}
        }
//        val intent = Intent()


        binding.cancelAddButton.setOnClickListener {
            findNavController().navigate(R.id.carFragment)

        }

//        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
//        val mDate = formatter.parse(dateStr)


        binding.saveAddButton.setOnClickListener() {

            val current = LocalDateTime.now()

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
            val formatted = current.format(formatter)

            val brandMake = binding.makeAddEditText.text.toString()
            val brandModel = binding.modelAddEdittext.text.toString()
            val brandDescription = binding.addDescriptionEditTextTextMultiLine.text.toString()
            val brandColor = binding.colorAddEdittext.text.toString()
            val brandYear = binding.yearAddEdittext.text.toString()
            val addTitle = binding.titleAddEditTextText.text.toString()
            val addLocation = binding.locationAddEdittext.text.toString()
            val addPrice = binding.priceEditTextText.text.toString().toDouble()
            val userIdProfile = FirebaseAuth.getInstance().uid
            val uri: Uri = "null".toUri()
            val randomString = ThreadLocalRandom.current()
                .ints(STRING_LENGTH.toLong(), 0, charPool.size)
                .asSequence()
                .map(charPool::get)
                .joinToString("")
            if (brandMake.isNotEmpty() && brandModel.isNotEmpty() && brandDescription.isNotEmpty() && brandColor.isNotEmpty() && brandYear.isNotEmpty() && addTitle.isNotEmpty() &&
                addLocation.isNotEmpty()
            ) {

                carViewModel.save(
                    CarModel(
                        brandMake,
                        brandModel,
                        brandColor,
                        brandYear,
                        addTitle,
                        addLocation,
                        0, // auto genrate id
                        "${Date()}",
                        addPrice,
                        true,
                        "$randomString $formatted",
                        brandDescription,
                        userIdProfile!!


                    ), image
                )

                observer()
                bindSelectedImage()
                findNavController().navigate(R.id.carFragment)
                Toast.makeText(context, "Information Added Successfully", Toast.LENGTH_SHORT).show()

//                try {
//                    Log.d(TAG, "add price successfull")
//                }catch (e:Exception){
//                    Log.d(TAG, "add price Error")
//                }
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == image_Picker && resultCode == RESULT_OK) {
//            var imageUri = data!!.data
            progressDialog.show()
            image = Matisse.obtainResult(data)[0]

            Log.d(TAG, "onActivityResult: image uri ")
//            val imageName = FirebaseAuth.getInstance().uid

            Glide.with(this).load(image)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .placeholder(R.drawable.uploadimages).into(binding.addImageImageView)
            if (progressDialog.isShowing) progressDialog.dismiss()

//            carViewModel.save(CarModel(), image)


        }

//        val imagePath = Matisse.obtainResult(data)[0]
//
        //  carViewModel.uploadPhoto("") //----changed from Uri to File in uploadPhoto in carViewModel---

    }

    fun observer() {

        carViewModel.carsLiveData.observe(viewLifecycleOwner, {
            Log.d("mainAc", it.toString())

            Glide.with(this).load("").into(binding.addImageImageView)


        })
        carViewModel.uploadImageLiveData.observe(viewLifecycleOwner, {
            bindSelectedImage()
            if (progressDialog.isShowing) progressDialog.dismiss()
        })

    }


//    fun selectImage(){
//        val intent = Intent()
//        intent.type="image/"
//        intent.action = Intent.ACTION_GET_CONTENT
//
//    }

    fun bindSelectedImage() {
        imagePath =
            "https://firebasestorage.googleapis.com/v0/b/car-spec-9231b.appspot.com/o/image%2F${FirebaseAuth.getInstance().uid}?alt=media&token=09720aeb-7236-4349-b356-81119ca99571"
        Glide.with(this).load(imagePath)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .placeholder(R.drawable.uploadimages).into(binding.addImageImageView)
    }

    fun showImagePicker() {
        Matisse.from(this)
            .choose(MimeType.ofImage(), false)
            .capture(true)
            .captureStrategy(CaptureStrategy(true, "com.example.car_spec"))
            .forResult(image_Picker)

    }

    fun getDate(dateStr: String) {
        try {
            /** DEBUG dateStr = '2006-04-16T04:00:00Z' **/
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
            val mDate = formatter.parse(dateStr) // this never ends while debugging
            Log.e("mDate", mDate.toString())
        } catch (e: Exception) {
            Log.e("mDate", e.toString()) // this never gets called either
        }
    }

}