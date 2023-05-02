package cz.muni.packer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import cz.muni.packer.ItemListFragmentDirections
import cz.muni.packer.ListListFragmentDirections
import cz.muni.packer.data.Item
import cz.muni.packer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), AppNavigator {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun navigateToItemList(packerList: PackerList) {
        val action = ListListFragmentDirections.actionListListFragmentToItemListFragment(packerList)
        findNavController(R.id.nav_host_fragment).navigate(action)
    }

    override fun navigateToItemDetails(item: Item) {
        val action = ItemListFragmentDirections.actionItemListFragmentToItemAddEditFragment(item)
        findNavController(R.id.nav_host_fragment).navigate(action)
    }
}