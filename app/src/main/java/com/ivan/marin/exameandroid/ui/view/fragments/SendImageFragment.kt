package com.ivan.marin.exameandroid.ui.view.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.ivan.marin.exameandroid.R
import com.ivan.marin.exameandroid.databinding.FragmentSendImageBinding
import com.ivan.marin.exameandroid.ui.view.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class SendImageFragment : Fragment() {
    private val PATH_IMAGENES = "photos"

    private lateinit var binding: FragmentSendImageBinding
    private var mActivity: MainActivity? = null
    private var mPhotoSelectedUri: Uri? = null

    private lateinit var mStorageReference: StorageReference

    private val mActionPickLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
            mPhotoSelectedUri = it.data?.data
            binding.imgPhoto.setImageURI(mPhotoSelectedUri)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSendImageBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mStorageReference = FirebaseStorage.getInstance().reference
        mActivity = activity as MainActivity
        binding.btnSelect.setOnClickListener { openGallery() }
        binding.fab.setOnClickListener { postPhoto() }
    }


    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }
        mActionPickLauncher.launch(intent)
    }

    private fun postPhoto(){
        binding.progressBar.visibility = View.VISIBLE
        val sdf = SimpleDateFormat("ddMMyyyy_hhmmss")
        val currentDate = sdf.format(Date())
        val storageReference = mStorageReference.child(PATH_IMAGENES).child(currentDate.toString())
        if (mPhotoSelectedUri!=null){
            storageReference.putFile(mPhotoSelectedUri!!)
                .addOnProgressListener {
                    val progress = (100 * it.bytesTransferred/it.totalByteCount).toDouble()
                    binding.progressBar.progress = progress.toInt()
                }
                .addOnCompleteListener{
                    binding.progressBar.visibility = View.INVISIBLE
                }
                .addOnSuccessListener {
                    Snackbar.make(binding.root,getString(R.string.message_succes_send_image),
                        Snackbar.LENGTH_SHORT).setAnchorView(binding.fab).show()
                }
                .addOnFailureListener{
                    Snackbar.make(binding.root,getString(R.string.message_failure_send_image),
                        Snackbar.LENGTH_SHORT).setAnchorView(binding.fab).show()
                }
        }
    }

}