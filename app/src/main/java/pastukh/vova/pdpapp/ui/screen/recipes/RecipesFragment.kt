package pastukh.vova.pdpapp.ui.screen.recipes

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import pastukh.vova.components.services.ServiceConstants
import pastukh.vova.components.services.loading.LoadingService
import pastukh.vova.pdpapp.databinding.FragmentRecipesBinding
import pastukh.vova.pdpapp.ui.screen.entity.RecipeEntity
import pastukh.vova.pdpapp.ui.screen.entity.base.ViewState
import pastukh.vova.pdpapp.ui.screen.recipes.adapter.RecipesAdapter
import pastukh.vova.pdpapp.ui.utils.toast

class RecipesFragment : Fragment() {

    private var _binding: FragmentRecipesBinding? = null
    private val binding: FragmentRecipesBinding
        get() = checkNotNull(_binding)

    private val args: RecipesFragmentArgs by navArgs()
    private val viewModel: RecipesViewModel by viewModel()
    private val adapter = RecipesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentRecipesBinding.inflate(inflater).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.id?.let { navigateToDetailed(it) }
        initView()
    }

    private fun initView() = with(binding) {
        recipesRv.adapter = adapter
        adapter.setClickListener { navigateToDetailed(it) }
        adapter.setDownloadClickListener {
            storeRecipeBackground(it)
            viewModel.storeRecipe(it)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { viewModel.state.collect { processState(it) } }
            }
        }
        viewModel.getRecipes()
    }

    private fun processState(state: ViewState<List<RecipeEntity>>) = with(binding) {
        when (state) {
            is ViewState.Data<List<RecipeEntity>> -> {
                progress.hide()
                adapter.submitList(state.data)
            }

            is ViewState.Error -> requireContext().toast(state.message)
            is ViewState.Loading -> progress.show()
        }
    }

    private fun storeRecipeBackground(id: String) {
        requireActivity().startService(
            Intent(requireActivity(), LoadingService::class.java).apply {
                putExtra(ServiceConstants.EXTRA_RECIPE_URL, id)
            }
        )
    }

    private fun navigateToDetailed(id: String) {
        findNavController().navigate(
            RecipesFragmentDirections.actionRecipesFragmentToRecipeDetailsFragment(id)
        )
    }
}