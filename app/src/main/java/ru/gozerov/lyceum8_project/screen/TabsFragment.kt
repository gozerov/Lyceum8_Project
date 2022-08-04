package ru.gozerov.lyceum8_project.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import ru.gozerov.lyceum8_project.R
import ru.gozerov.lyceum8_project.databinding.FragmentTabsBinding

class TabsFragment : Fragment(R.layout.fragment_tabs) {

   /* private lateinit var childNavController: NavController

    private val args: TabsFragmentArgs by navArgs()

    private lateinit var binding: FragmentTabsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTabsBinding.inflate(inflater, container, false)

        childNavController = findChildNavController()
        childNavController.currentBackStackEntry?.arguments?.putSerializable(NewsRVFragment.ARG_NEWS_LIST, args.newsList)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.navigationView.setupWithNavController(childNavController)

        binding.tabsToolbar.setNavigationOnClickListener {
            binding.drawer.openDrawer(GravityCompat.START)
        }

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.libraryFragment -> childNavController.navigate(R.id.libraryFragment)

                R.id.profileFragment -> childNavController.navigate(R.id.profileFragment)

                R.id.schoolStructureFragment -> childNavController.navigate(R.id.schoolStructureFragment)

                R.id.lyceumNewsNestedGraph -> {
                    val selectableNewsFragmentId = childNavController.backQueue.firstOrNull {
                        it.destination.id == R.id.selectableNewsFragment
                    }?.destination?.id
                    findChildNavController().popBackStack(
                        destinationId = selectableNewsFragmentId ?: R.id.newsFragment,
                        inclusive = false
                    )
                }
            }
            binding.drawer.closeDrawer(binding.navigationView)
            return@setNavigationItemSelectedListener true
        }

        childNavController.addOnDestinationChangedListener(destinationListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        childNavController.removeOnDestinationChangedListener(destinationListener)
    }

    private fun findChildNavController(): NavController {
        return (childFragmentManager.findFragmentById(R.id.fragmentTabsContainer) as NavHostFragment)
            .navController
    }

    private val destinationListener: (NavController, NavDestination, Bundle?) -> Unit = { _, destination, _ ->
        binding.titleToolbar.text = destination.label
    }
*/
}