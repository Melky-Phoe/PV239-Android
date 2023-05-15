package cz.muni.packer

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import cz.muni.packer.data.Item
import cz.muni.packer.data.Categories
import cz.muni.packer.data.loadImageFromFirebase
import cz.muni.packer.databinding.FragmentItemAddEditBinding
import cz.muni.packer.repository.ItemRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemAddEditFragment : Fragment() {
    private lateinit var binding: FragmentItemAddEditBinding
    private val args: ItemAddEditFragmentArgs by navArgs()
    private val itemRepository = ItemRepository()

    private var _currentCount = 0

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
        // binding.itemNameTextInputLayout.hint = args.item.name
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.countEditText.setText("1")
        // val item = args.item
         setInitialValues()
        binding.plusButton.setOnClickListener {
            binding.countEditText.setText(
                (binding.countEditText.text.toString().toInt() + 1).toString()
            )
        }
        binding.minusButton.setOnClickListener {
            if (binding.countEditText.text.toString().toInt() > 0) {
                binding.countEditText.setText(
                    (binding.countEditText.text.toString().toInt() - 1).toString()
                )
            }
        }
        binding.cameraButton.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            takePhotoLauncher.launch(intent)
        }

        binding.galleryButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            fromGalleryLauncher.launch(intent)
        }

        binding.saveButton.setOnClickListener {
            val name = binding.itemNameEditText.text.toString()
            val totalCount = binding.countEditText.text.toString().toInt()
            val picture = (binding.imageView.drawable as? BitmapDrawable)?.bitmap

            if (name.isEmpty()) {
                binding.itemNameEditText.error = "Field must be filled"
            } else if (totalCount <= 0) {
                binding.countEditText.error = "Must be higher than 0"
            } else if (_currentCount > totalCount) {
                _currentCount = totalCount
            } else {
                val category = when (binding.categoryChipGroup.checkedChipId) {
                    R.id.clothing_chip -> Categories.CLOTHING
                    R.id.food_chip -> Categories.FOOD
                    R.id.sleeping_chip -> Categories.SLEEPING
                    R.id.electronics_chip -> Categories.ELECTRONICS
                    R.id.hygiene_chip -> Categories.HYGIENE
                    else -> Categories.OTHER
                }

                if (picture != null) {
                    itemRepository.uploadImageToFirebaseStorage(picture,
                        onSuccess = { imageUrl ->
                            val item = Item(
                                id = args.item?.id ?: "",
                                name = name,
                                category = category,
                                picture = imageUrl,
                                currentCount = _currentCount,
                                totalCount = totalCount,
                                packerListId = args.packerListId
                            )
                            if (args.item != null) {
                                itemRepository.updateItem(item)
                            } else {
                                itemRepository.addItem(item)
                            }
                        },
                        onFailure = { exception ->
                            Log.e("ItemAddEditFragment", "Error uploading image", exception)
                        }
                    )
                }
                else {
                    val item = Item(
                        id = args.item?.id ?: "",
                        name = name,
                        category = category,
                        currentCount = _currentCount,
                        totalCount = totalCount,
                        packerListId = args.packerListId
                    )

                    if (args.item != null) {
                        itemRepository.updateItem(item)
                    } else {
                        itemRepository.addItem(item)
                    }
                }
                findNavController().navigateUp()
            }
        }
        binding.deleteButton.setOnClickListener {
            val builder = context?.let { it1 -> AlertDialog.Builder(it1) }
            if (builder != null) {
                builder.setTitle("Are you sure?")
                builder.setMessage("Do you want to delete this item?")
                builder.setPositiveButton("Yes") { _, _ ->
                    args.item?.id?.let { it1 -> itemRepository.deleteItem(it1) }
                    findNavController().navigateUp()
                }
                builder.setNegativeButton("No") { _, _ ->
                    // Do nothing
                }
                val dialog = builder.create()
                dialog.show()
            }

        }
    }

    private fun setInitialValues() {
        val item: Item? = args.item

        if (item != null) {
            _currentCount = item.currentCount!!
            binding.itemNameEditText.setText(item.name)
            binding.countEditText.setText(item.totalCount.toString())
            item.picture?.let {
                CoroutineScope(Dispatchers.IO).launch {
                    val bitmap = loadImageFromFirebase(it)
                    bitmap?.let { bmp ->
                        CoroutineScope(Dispatchers.Main).launch {
                            binding.imageView.setImageBitmap(bmp)
                        }
                    }
                }
            }
            when (item.category) {
                Categories.CLOTHING -> binding.categoryChipGroup.check(R.id.clothing_chip)
                Categories.HYGIENE -> binding.categoryChipGroup.check(R.id.hygiene_chip)
                Categories.SLEEPING -> binding.categoryChipGroup.check(R.id.sleeping_chip)
                Categories.FOOD -> binding.categoryChipGroup.check(R.id.food_chip)
                Categories.ELECTRONICS -> binding.categoryChipGroup.check(R.id.electronics_chip)
                Categories.OTHER -> binding.categoryChipGroup.check(R.id.other_chip)
                else -> {}
            }
        }
    }

    private fun processPhotoResult(activityResult: ActivityResult) {
        if (activityResult.resultCode == AppCompatActivity.RESULT_OK) {
            val image = activityResult.data?.extras?.get("data") as? Bitmap
            binding.imageView.setImageBitmap(image)
        }
    }

    private fun processFromGalleryResult(activityResult: ActivityResult) {
        if (activityResult.resultCode == AppCompatActivity.RESULT_OK) {
            val uri = activityResult.data?.data
            val image = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
            binding.imageView.setImageBitmap(image)
        }
    }
}