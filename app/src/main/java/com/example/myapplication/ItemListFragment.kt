package com.example.myapplication

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentItemListBinding

/**
 * A simple [Fragment] subclass.
 */
class ItemListFragment : Fragment() {

    private lateinit var binding: FragmentItemListBinding
    private lateinit var adapter: ItemAdapter
    private lateinit var appNavigator: AppNavigator
    private val args: ItemListFragmentArgs by navArgs()

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
            //updateItem(it)
        }
        binding.rvItems.layoutManager = LinearLayoutManager(requireContext())
        binding.rvItems.adapter = adapter
        adapter.submitList(args.packerList.items)

        return binding.root
    }

    fun showItemDetails(item: Item) {
        appNavigator.navigateToItemDetails(item)
    }

    fun updateItem(item: Item) {
        Toast.makeText(context, "changed count", Toast.LENGTH_SHORT).show()
    }

}