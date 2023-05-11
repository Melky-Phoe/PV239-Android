package cz.muni.packer

import android.content.Context
import android.os.Bundle
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

        binding.logoutButton.setOnClickListener {
            val builder = context?.let { it1 -> androidx.appcompat.app.AlertDialog.Builder(it1) }
            if (builder != null) {
                builder.setTitle("Are you sure?")
                builder.setMessage("Do you want to LogOut?")
                builder.setPositiveButton("Yes") { _, _ ->
                    appNavigator.signOut()
                }
                builder.setNegativeButton("No") { _, _ ->
                    // Do nothing
                }
                val dialog = builder.create()
                dialog.show()
            }
        }

        // TODO: change title to list name
        // activity?.findViewById<Toolbar>(R.id.toolbar)?.title = args.packerListId.toString()

        return binding.root
    }

}