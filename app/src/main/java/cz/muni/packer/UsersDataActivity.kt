package cz.muni.packer

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import cz.muni.packer.data.Item
import cz.muni.packer.data.PackerList
import cz.muni.packer.databinding.ActivityUsersDataBinding

class UsersDataActivity : AppCompatActivity(), AppNavigator {

    private lateinit var binding: ActivityUsersDataBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsersDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null) {
            // User is not logged in
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        else {
            // User is already Logged in
            // Get user info
            // TODO: userProfile
        }
    }

    override fun navigateToItemList(packerList: PackerList) {
        if (packerList.id != null) {
            val action = ListListFragmentDirections.actionListListFragmentToItemListFragment(packerList.id)
            findNavController(R.id.nav_host_fragment).navigate(action)
        }
    }

    override fun navigateToItemDetails(item: Item) {
        val action = ItemListFragmentDirections.actionItemListFragmentToItemAddEditFragment(item, item.packerListId ?: "")
        findNavController(R.id.nav_host_fragment).navigate(action)
    }

    override fun signOut() {
        firebaseAuth.signOut()
        GoogleSignIn.getClient(this, GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()).signOut()
        checkUser()
    }
}