package com.example.myapplication

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentItemListBinding

/**
 * A simple [Fragment] subclass.
 */
class ItemListFragment : Fragment() {

    private lateinit var binding: FragmentItemListBinding
    private lateinit var adapter: ItemAdapter
    private val itemListRepository = ItemListRepository()
    private lateinit var appNavigator: AppNavigator

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appNavigator = context as AppNavigator
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemListBinding.inflate(layoutInflater, container, false)

        adapter = ItemAdapter() {
            showItemDetails(it)
        }
        binding.rvItems.layoutManager = LinearLayoutManager(requireContext())
        binding.rvItems.adapter = adapter

        // val test = requireArguments().getString(KEY_TEST) ?: ""
        // Toast.makeText(requireContext(), test, Toast.LENGTH_SHORT).show()

        val itemsObserver = Observer<List<Item>> { items ->
            // Update list adapter
            adapter.submitList(items)
        }
        itemListRepository.items.observe(this, itemsObserver)

        itemListRepository.loadItems()
        return binding.root
    }

    fun showItemDetails(item: Item) {
        appNavigator.navigateToItemDetails(item)
    }

    companion object {
        const val KEY_TEST = "key_test"

        fun newInstance(test: String) : ItemListFragment {
            val fragment = ItemListFragment()

            val args = Bundle()
            args.putString(KEY_TEST, test)
            fragment.arguments = args

            return fragment
        }
    }

}