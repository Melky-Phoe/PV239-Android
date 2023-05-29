package cz.muni.packer

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import cz.muni.packer.data.Category
import cz.muni.packer.data.Item
import cz.muni.packer.data.PackerList
import cz.muni.packer.databinding.FragmentListListBinding
import cz.muni.packer.repository.ItemRepository
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
        adapter = PackerListAdapter (
            onItemClick = { packerList ->
                showListItems(packerList)
            },
            onLongItemClick = {packerList ->
                onLongItemClick(packerList)
            }
        )
        binding.rvItems.layoutManager = LinearLayoutManager(requireContext())
        binding.rvItems.adapter = adapter

        // Add divider
        val dividerItemDecoration = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        binding.rvItems.addItemDecoration(dividerItemDecoration)

        binding.addListButton.setOnClickListener {
            showAddEditPackerListDialog("Add New Packer List", "Create")
        }

        // Load packer lists and submit them to the adapter
        packerListRepository.getPackerLists { packerLists ->
            var counter = 0
            for (list in packerLists) {
                list.items = mutableListOf()
                packerListRepository.getItems(list.id!!) { items ->
                    for (item in items) {
                        list.items!!.add(item)
                    }

                    // Increment the counter
                    counter++

                    // Check if all items have been fetched for each packer list
                    if (counter == packerLists.size) {
                        // All items have been fetched, submit the packer lists to the adapter
                        adapter.submitList(packerLists)
                    }
                }
            }
        }

        return binding.root
    }

    private fun showListItems(packerList: PackerList) {
        appNavigator.navigateToItemList(packerList)
    }

    private fun onLongItemClick(packerList: PackerList) {
        showAddEditPackerListDialog("Edit Packer List", "Edit", packerList)
    }

    private fun showAddEditPackerListDialog(actionString: String, buttonString: String, packerList: PackerList? = null) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.new_packer_list_dialog, null)

        val listNameEditText = dialogView.findViewById<EditText>(R.id.et_list_name)
        listNameEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES

        if (packerList?.name != null) {
            listNameEditText.setText(packerList.name)
        }

        AlertDialog.Builder(requireActivity())
            .setTitle(actionString)
            .setView(dialogView)
            .setPositiveButton(buttonString) { _, _ ->
                val listName = listNameEditText.text.toString().trim()
                if (listName.isNotEmpty()) {
                    if (packerList == null) {
                        createNewPackerList(listName)
                    }
                    else {
                        editPackerList(packerList, listName)
                    }
                }
                else {
                    Toast.makeText(this.context, "Name cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Delete") { _, _ ->
                if (packerList != null) {
                    val builder = context?.let { AlertDialog.Builder(it) }
                    if (builder != null) {
                        builder.setTitle("Are you sure?")
                        builder.setMessage("Do you want to delete this Packer List? " +
                                            "It will delete all items under this list.")
                        builder.setPositiveButton("Yes") { _, _ ->
                            packerList.id?.let { packerListRepository.deletePackerList(packerListId = it) }
                            findNavController().navigateUp()
                        }
                        builder.setNegativeButton("No") { _, _ ->
                            // Do nothing
                        }
                        val dialog = builder.create()
                        dialog.show()
                    }
                }
            }
            .setNeutralButton("Cancel", null)
            .create()
            .show()
    }

    private fun createNewPackerList(listName: String) {
        val newPackerList = PackerList(id = "", name = listName, items = mutableListOf())

        // Save the new PackerList to the database
        packerListRepository.addPackerList(newPackerList)

        // Fetch updated packer lists and submit them to the adapter
        packerListRepository.getPackerLists { packerLists ->
            for (list in packerLists) {
                list.items = mutableListOf()
                packerListRepository.getItems(list.id!!) { items ->
                    for (item in items) {
                        list.items!!.add(item)
                    }

                }
            }
            adapter.submitList(packerLists)
        }
    }

    private fun editPackerList(packerList: PackerList, listName: String) {
        val newPackerList = packerList.copy(name = listName)

        // Update the PackerList in the database
        packerListRepository.updatePackerList(newPackerList)

        // Fetch updated packer lists and submit them to the adapter
        packerListRepository.getPackerLists { packerLists ->
            for (list in packerLists) {
                list.items = mutableListOf()
                packerListRepository.getItems(list.id!!) { items ->
                    for (item in items) {
                        list.items!!.add(item)
                    }

                }
            }
            adapter.submitList(packerLists)
        }
    }
}
