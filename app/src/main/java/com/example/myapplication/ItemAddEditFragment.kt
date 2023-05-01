package com.example.myapplication

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.myapplication.databinding.FragmentItemAddEditBinding

class ItemAddEditFragment : Fragment() {
    private lateinit var binding: FragmentItemAddEditBinding
    private val args: ItemAddEditFragmentArgs by navArgs()

    private val takePhotoLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            processPhotoResult(it)
        }

    private val fromGalleryLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            processFromGalleryResult(it)
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentItemAddEditBinding.inflate(layoutInflater, container, false)
        binding.itemNameTextInputLayout.hint = args.item.name
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // val item = args.item
        // setInitialValues()
        // do something with the item
        binding.cameraButton.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            takePhotoLauncher.launch(intent)
        }

        binding.galleryButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            fromGalleryLauncher.launch(intent)
        }
    }

    private fun processPhotoResult(activityResult: ActivityResult) {
        if (activityResult.resultCode == AppCompatActivity.RESULT_OK) {
            val image = activityResult.data?.extras?.get("data") as? Bitmap
            // item.picture = image
        }
    }

    private fun processFromGalleryResult(activityResult: ActivityResult) {
        if (activityResult.resultCode == AppCompatActivity.RESULT_OK) {
            val uri = activityResult.data?.data
            val image = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
            // item.picture = image
        }
    }
}