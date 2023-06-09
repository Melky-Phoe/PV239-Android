package cz.muni.packer

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import cz.muni.packer.data.Category
import cz.muni.packer.databinding.FragmentItemListBinding
import cz.muni.packer.repository.ItemRepository

/**
 * A simple [Fragment] subclass.
 */
class ItemListFragment : Fragment() {

    private lateinit var binding: FragmentItemListBinding
    private lateinit var adapter: CategoryAdapter
    private lateinit var appNavigator: AppNavigator
    private val args: ItemListFragmentArgs by navArgs()

    private val itemRepository: ItemRepository by lazy {
        ItemRepository()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appNavigator = context as AppNavigator
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemListBinding.inflate(layoutInflater, container, false)

        itemRepository.getListName(args.packerListId) { name ->
            appNavigator.setToolbarTitle(name)
            Log.d(ContentValues.TAG, "PackerList name is $name")
        }

        itemRepository.getItems(args.packerListId) { packerListItems ->
            // This is added because the app has failed here due to "attempting to access the
            // context of the ItemListFragment when the fragment is not attached to a context"
            if (!isAdded) { // Check if the fragment is still added to the activity
                return@getItems
            }

            val itemMap = packerListItems.groupBy { it.category }
            val categoryItemList = itemMap.map { (category, itemList) ->
                Category(category?.name ?: "", itemList)
            }

            adapter = CategoryAdapter(categoryItemList, appNavigator, itemRepository)
            binding.rvItems.layoutManager = LinearLayoutManager(requireContext())
            binding.rvItems.adapter = adapter
        }

        binding.addItemButton.setOnClickListener {
            val action = ItemListFragmentDirections.actionItemListFragmentToItemAddEditFragment(packerListId = args.packerListId)
            findNavController().navigate(action)
        }

        return binding.root
    }

}