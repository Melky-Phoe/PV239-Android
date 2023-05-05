package cz.muni.packer

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import cz.muni.packer.data.Category
import cz.muni.packer.data.Item
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
        ItemRepository(requireContext())
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

        val packerListItems = itemRepository.getItemsForPackerList(args.packerListId)
        val itemMap = packerListItems.groupBy { it.category }
        val categoryItemList = itemMap.map { (category, itemList) ->
            Category(category.name, itemList)
        }

        adapter = CategoryAdapter(categoryItemList, appNavigator, itemRepository)
        binding.rvItems.layoutManager = LinearLayoutManager(requireContext())
        binding.rvItems.adapter = adapter

        binding.fab.setOnClickListener {
            val action = ItemListFragmentDirections.actionItemListFragmentToItemAddEditFragment(packerListId = args.packerListId)
            findNavController().navigate(action)
        }
        // TODO: change title to list name
        // activity?.findViewById<Toolbar>(R.id.toolbar)?.title = args.packerListId.toString()

        return binding.root
    }

}