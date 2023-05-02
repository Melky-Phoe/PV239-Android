package com.example.myapplication

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentListListBinding
import com.example.myapplication.repository.ListListRepository

/**
 * A simple [Fragment] subclass.
 */
class ListListFragment : Fragment() {
    private lateinit var binding: FragmentListListBinding
    private lateinit var adapter: PackerListAdapter
    private val listListRepository: ListListRepository by lazy {
        ListListRepository(requireContext())
    }
    private lateinit var appNavigator: AppNavigator

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appNavigator = context as AppNavigator
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListListBinding.inflate(layoutInflater, container, false)
        adapter = PackerListAdapter {
            showListItems(it)
        }
        binding.rvItems.layoutManager = LinearLayoutManager(requireContext())
        binding.rvItems.adapter = adapter

        binding.addListButton.setOnClickListener {
            showAddPackerListDialog()
        }

        val listsObserver = Observer<List<PackerList>> { items ->
            // Update list adapter
            adapter.submitList(items)
        }
        listListRepository.lists.observe(viewLifecycleOwner, listsObserver)

        listListRepository.loadItems()
        return binding.root
    }

    private fun showListItems(packerList: PackerList) {
        appNavigator.navigateToItemList(packerList)
    }

    private fun showAddPackerListDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.new_packer_list_dialog, null)
        val listNameEditText = dialogView.findViewById<EditText>(R.id.et_list_name)

        AlertDialog.Builder(requireActivity())
            .setTitle("Add New Packer List")
            .setView(dialogView)
            .setPositiveButton("Create") { _, _ ->
                val listName = listNameEditText.text.toString().trim()
                if (listName.isNotEmpty()) {
                    createNewPackerList(listName)
                }
            }
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }

    private fun createNewPackerList(listName: String) {
        val newPackerList = PackerList(name = listName, items = emptyList())

        // Add the new PackerList to your Firebase Realtime Database
        // You may need to modify this code to match your existing database structure
        //val database = FirebaseDatabase.getInstance()
        //val packerListReference = database.getReference("packerLists").push()

        //packerListReference.setValue(newPackerList)

        // temporary solution - for testing
        val newList = adapter.currentList.toMutableList()
        newList.add(newPackerList)

        adapter.submitList(newList)
    }

}