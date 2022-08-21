package org.aos.shareroof

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import org.aos.shareroof.base.BaseActivity
import org.aos.shareroof.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var navController: NavController
    lateinit var navGraph: NavGraph

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initNavigation()
        setListeners()
    }

    private fun initNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container_main) as NavHostFragment
        navController = navHostFragment.navController
        navGraph = navController.navInflater.inflate(R.navigation.nav_main)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.homeFragment) {
                binding.ivList.isVisible = true
                binding.ivList.setImageResource(R.drawable.menu)
            } else if ( destination.id == R.id.listFragment) {
                binding.ivList.setImageResource(R.drawable.map)
            } else{
                binding.ivList.isVisible = false
            }
        }
    }

    private fun setListeners() {
        binding.ivList.setOnClickListener {
            if (mainViewModel.changeView.value == true){
                navController.navigate(R.id.action_listFragment_to_homeFragment)
                mainViewModel.changeView.value = false
            }else{
                /*navGraph.setStartDestination(R.id.listFragment)
                navController.graph = navGraph*/
                navController.navigate(R.id.action_homeFragment_to_listFragment)
                mainViewModel.changeView.value = true
            }

        }
    }
}