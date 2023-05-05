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
import cz.muni.packer.data.Item
import cz.muni.packer.databinding.FragmentItemListBinding
import cz.muni.packer.repository.ItemRepository

/**
 * A simple [Fragment] subclass.
 */
class ItemListFragment : Fragment() {

    private lateinit var binding: FragmentItemListBinding
    private lateinit var adapter: ItemAdapter
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

        adapter = ItemAdapter(
            onItemClick = { item ->
                showItemDetails(item)
            },
            onCountUpdate = { item ->
                updateItemCount(item)
            }
        )
        binding.rvItems.layoutManager = LinearLayoutManager(requireContext())
        binding.rvItems.adapter = adapter
        adapter.submitList(itemRepository.getItemsForPackerList(args.packerListId))

        binding.fab.setOnClickListener {
            val action = ItemListFragmentDirections.actionItemListFragmentToItemAddEditFragment(packerListId = args.packerListId)
            findNavController().navigate(action)
        }
        // TODO: change title to list name
        // activity?.findViewById<Toolbar>(R.id.toolbar)?.title = args.packerListId.toString()

        return binding.root
    }

    private fun showItemDetails(item: Item) {
        appNavigator.navigateToItemDetails(item)
    }

    private fun updateItemCount(item: Item) {
//        itemRepository.updateItem(item)
        itemRepository.updateCount(item.id, item.currentCount)
    }

    private fun refreshList() {
        adapter.submitList(itemRepository.getItemsForPackerList(args.packerListId))
    }
    override fun onResume() {
        super.onResume()
        refreshList()
    }

}