package studio.inprogress.yetanothertestapi.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_app_bar.*
import studio.inprogress.yetanothertestapi.R
import studio.inprogress.yetanothertestapi.databinding.DrawerNavHeaderBinding
import studio.inprogress.yetanothertestapi.utils.binding

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var binding: DrawerNavHeaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawer_layout)

        nav_view.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding = nav_view.getHeaderView(0).binding() as DrawerNavHeaderBinding
        nav_view.menu.findItem(R.id.logout).setOnMenuItemClickListener {
            viewModel.logout()
            navController.navigate(R.id.loginFragment)
            true
        }
        viewModel.getUser().observe(this, Observer {
            binding.user = it
            nav_view.menu.setGroupVisible(R.id.mainMenu, it != null)
            nav_view.menu.findItem(R.id.loginFragment).isVisible = it == null
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(nav_view)) {
            drawer_layout.closeDrawers()
        } else {
            super.onBackPressed()
        }
    }
}