package cz.muni.packer

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

        adapter = ItemAdapter() {
            showItemDetails(it)
            //updateItem(it)
        }
        binding.rvItems.layoutManager = LinearLayoutManager(requireContext())
        binding.rvItems.adapter = adapter
        adapter.submitList(itemRepository.getItemsForPackerList(args.packerListId))

        binding.fab.setOnClickListener {
            val action = ItemListFragmentDirections.actionItemListFragmentToItemAddEditFragment(packerListId = args.packerListId)
            findNavController().navigate(action)
        }
        return binding.root
    }

    fun showItemDetails(item: Item) {
        appNavigator.navigateToItemDetails(item)
    }

    fun updateItem(item: Item) {
        Toast.makeText(context, "changed count", Toast.LENGTH_SHORT).show()
    }

    private fun refreshList() {
        adapter.submitList(itemRepository.getItemsForPackerList(args.packerListId))
    }
    override fun onResume() {
        super.onResume()
        refreshList()
    }

}