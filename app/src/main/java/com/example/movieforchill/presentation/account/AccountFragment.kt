package com.example.movieforchill.presentation.account

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.movieforchill.R
import com.example.movieforchill.databinding.FragmentAccountBinding
import com.squareup.picasso.Picasso

//аккаунт с фото

class AccountFragment : Fragment(R.layout.fragment_account) {
    private lateinit var binding: FragmentAccountBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAccountBinding.bind(view)
        Picasso.get().load(R.drawable.empty_avatar).into(binding.imageAccount)
        // проверяем есть ли разрешение
        binding.buttonCamera.setOnClickListener {
            getCameraPermission()
        }
        binding.buttonGallery.setOnClickListener {
            getGalleryPermission()
        }
    }

    private fun getCameraPermission(){
        if (ContextCompat.checkSelfPermission(
                this.requireActivity(), Manifest.permission.CAMERA) ==
            PackageManager.PERMISSION_GRANTED){
            takePicture()
        }else{
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                PERMISSION_FOR_CAMERA
            )
        }
    }

    private fun getGalleryPermission(){
        if (ContextCompat.checkSelfPermission(
                this.requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) ==
            PackageManager.PERMISSION_GRANTED){
            pickImage()
            //запрашиваем разрешение если его нет
        }else{
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                PERMISSION_FOR_GALLERY
            )
        }
    }


    //функция позволяющая сделать фотографию
    private fun takePicture() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(takePictureIntent, PERMISSION_FOR_CAMERA)
    }

    //берем фотографии с галлереи
    private fun pickImage() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, PERMISSION_FOR_GALLERY)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            PERMISSION_FOR_CAMERA ->{
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // можно делать что - то с камерой
                    takePicture()
                }

            }
            PERMISSION_FOR_GALLERY ->{
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickImage()
                }

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK) {
            when (requestCode) {
                PERMISSION_FOR_CAMERA -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    binding.imageAccount.setImageBitmap(imageBitmap)
                }
                PERMISSION_FOR_GALLERY -> {
                    binding.imageAccount.setImageURI(data?.data)
                }
            }
        }
    }



    companion object{
        private const val PERMISSION_FOR_CAMERA = 1
        private const val PERMISSION_FOR_GALLERY = 2
    }
}