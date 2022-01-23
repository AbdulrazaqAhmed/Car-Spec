package com.example.car_spec.view.main

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.car_spec.R
import com.example.car_spec.accessablity.Login
import com.example.car_spec.accessablity.SHARED_PREF_FILE
import com.example.car_spec.databinding.FragmentProfileBinding
import com.example.car_spec.model.UsersModel
import com.example.car_spec.view.viewmodel.UsersViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy
import okhttp3.internal.cache2.Relay.Companion.edit
import java.io.File

private lateinit var currentUser : UsersModel
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private var updateuser = UsersModel()
    private val userViewModel: UsersViewModel by activityViewModels()
    private lateinit var progressDialog: ProgressDialog
    private val image_Picker = 1
    private val TAG = "Profile Fragment"
    private lateinit var sharedPrefEditor: SharedPreferences.Editor
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = requireActivity().getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
        sharedPrefEditor = sharedPref.edit()
    }

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
//        updateUser()
        userViewModel.deleteuser()



        binding.profilePicImageView.setOnClickListener {

            showUserImagePicker()


//binding.addImageImageView.setOnClickListener(new View.OnClickListener() {}
        }

        binding.updateProfileButton.setOnClickListener() {
//            updateUser()
//            Toast.makeText(context, "Update saved successfully", Toast.LENGTH_SHORT).show()
            updateUser()
            Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.carFragment)

        }

        binding.deleteProTextView.setOnClickListener() {
            val alertDialog = android.app.AlertDialog.Builder(context).setTitle("Delete Profile")
                .setMessage(
                    "Are you sure? All files will be deleted and no data recovery after this ste"
                )
            alertDialog.setPositiveButton("Delete") { _, _ ->
                Log.i(TAG, "Delete")
                userViewModel.deleteuser()
                val profileUser = Firebase.auth.currentUser!!

                profileUser.delete()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "User account deleted.")
                        }
                    }





                FirebaseAuth.getInstance().signOut()
                this?.let {
                    val intent = Intent(it.requireActivity(), Login::class.java)
                    it.startActivity(intent)
                }

                requireActivity().getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
                sharedPrefEditor = sharedPref.edit()
                sharedPrefEditor.remove("isUserLogin")
                sharedPrefEditor.commit()
                requireActivity().finish()
                Log.d(TAG, "sharedPref delete")
            }
            alertDialog.setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            alertDialog.show()
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
            binding.firstNameEditTex.setText(it.firstname)
            binding.lastNameEditText.setText(it.lastname)
            binding.emailTextView.text = it.email
             currentUser = it

        })
        userViewModel.uploadUsersImageLiveData.observe(viewLifecycleOwner, {
            showPic()

            if (progressDialog.isShowing) progressDialog.dismiss()
        })

        userViewModel.usersLiveData.observe(viewLifecycleOwner, {
        })
        userViewModel.deleteProLiveData.observe(viewLifecycleOwner, {
        })

    }


    fun showPic() {
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

    //--------------------------------------- update user ---------------------------------------
    fun updateUser() {
        currentUser.apply {
            firstname = binding.firstNameEditTex.text.toString()
            lastname = binding.lastNameEditText.text.toString()



            userViewModel.updateUsers(currentUser)
        }

    }

}

