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
import com.example.myapplication.databinding.FragmentListListBinding
import java.util.jar.Pack200.Packer

/**
 * A simple [Fragment] subclass.
 */
class ListListFragment : Fragment() {
    private lateinit var binding: FragmentListListBinding
    private lateinit var adapter: PackerListAdapter
    private val listListRepository = ListListRepository()
    private lateinit var appNavigator: AppNavigator

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appNavigator = context as AppNavigator
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListListBinding.inflate(layoutInflater, container, false)
        adapter = PackerListAdapter() {
            showListItems(it)
        }
        binding.rvItems.layoutManager = LinearLayoutManager(requireContext())
        binding.rvItems.adapter = adapter

        val listsObserver = Observer<List<PackerList>> { items ->
            // Update list adapter
            adapter.submitList(items)
        }
        listListRepository.lists.observe(this, listsObserver)

        listListRepository.loadItems()
        return binding.root
    }

    fun showListItems(packerList: PackerList) {
        appNavigator.navigateToItemList(packerList)
    }

}