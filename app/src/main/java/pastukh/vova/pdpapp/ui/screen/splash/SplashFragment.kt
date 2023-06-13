package pastukh.vova.pdpapp.ui.screen.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pastukh.vova.pdpapp.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSplashBinding.inflate(inflater).root

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            delay(2000)
            findNavController().navigate(
                SplashFragmentDirections.actionSplashFragmentToRecipesFragment(
                    null
                )
            )
        }
    }
}