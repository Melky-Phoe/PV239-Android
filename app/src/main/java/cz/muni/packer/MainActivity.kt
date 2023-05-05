package cz.muni.packer

import android.os.Bundle
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import cz.muni.packer.data.Item
import cz.muni.packer.data.PackerList
import cz.muni.packer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), AppNavigator {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    override fun navigateToItemList(packerList: PackerList) {
        val action = ListListFragmentDirections.actionListListFragmentToItemListFragment(packerList.id)
        findNavController(R.id.nav_host_fragment).navigate(action)
    }

    override fun navigateToItemDetails(item: Item) {
        val action = ItemListFragmentDirections.actionItemListFragmentToItemAddEditFragment(item)
        findNavController(R.id.nav_host_fragment).navigate(action)
    }
}