package cz.muni.packer

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import cz.muni.packer.data.PackerList
import cz.muni.packer.databinding.FragmentListListBinding
import cz.muni.packer.repository.PackerListRepository

/**
 * A simple [Fragment] subclass.
 */
class ListListFragment : Fragment() {
    private lateinit var binding: FragmentListListBinding
    private lateinit var adapter: PackerListAdapter
    private val packerListRepository: PackerListRepository by lazy {
        PackerListRepository()
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

        // Load packer lists and submit them to the adapter
        packerListRepository.getPackerLists { packerLists ->
            adapter.submitList(packerLists)
        }

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
        val newPackerList = PackerList(id = "", name = listName, items = emptyList())

        // Save the new PackerList to the database
        packerListRepository.addPackerList(newPackerList)

        // Fetch updated packer lists and submit them to the adapter
        packerListRepository.getPackerLists { packerLists ->
            adapter.submitList(packerLists)
        }
    }
}
