package com.example.car_spec.view.main

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.car_spec.R
import com.example.car_spec.databinding.FragmentProfileBinding
import com.example.car_spec.model.UsersModel
import com.example.car_spec.view.viewmodel.UsersViewModel
import com.google.firebase.auth.FirebaseAuth
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy
import java.io.File


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val userViewModel: UsersViewModel by activityViewModels()
    private lateinit var progressDialog: ProgressDialog
    private val image_Picker = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        progressDialog = ProgressDialog(requireActivity())
        progressDialog.setTitle("Uploading...")
        progressDialog.setCancelable(false)
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()
        userViewModel.fetchUsers()
        showPic()


        binding.profilePicImageView.setOnClickListener {

            showUserImagePicker()

//binding.addImageImageView.setOnClickListener(new View.OnClickListener() {}
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == image_Picker && resultCode == Activity.RESULT_OK) {

            progressDialog.show()
            var imagePath = Matisse.obtainResult(data)[0]
            userViewModel.uploadUsersPhoto(imagePath)

            Log.d(ContentValues.TAG, "onActivityResult: image uri ")
            //  userViewModel.uploadUsersPhoto(imagePath)


        }
    }


    fun observer() {

        userViewModel.usersLiveData.observe(viewLifecycleOwner, {
            Log.d("profileObserver", it.toString())

            //            binding.profileProgressBar.animate().alpha(0f)
            binding.userFirstNameTextView.text = it.firstname
            binding.userLastNameTextView.text = it.lastname
            binding.userEmail.text = it.email

        })
        userViewModel.uploadUsersImageLiveData.observe(viewLifecycleOwner, {
            showPic()

            if (progressDialog.isShowing) progressDialog.dismiss()
        })

    }

    fun showPic()
    {
        var imagePath =
            "https://firebasestorage.googleapis.com/v0/b/car-spec-9231b.appspot.com/o/profile%20images%2F${FirebaseAuth.getInstance().uid}?alt=media&token=787746ff-b858-49c6-91d4-218d775195b3"

        Glide.with(this).load(imagePath)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .placeholder(R.drawable.img).into(binding.profilePicImageView)
    }

    fun showUserImagePicker() {
        Matisse.from(this)
            .choose(MimeType.ofImage(), false)
            .capture(true)
            .captureStrategy(CaptureStrategy(true, "com.example.car_spec"))
            .forResult(image_Picker)

    }

}

