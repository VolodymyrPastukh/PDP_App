package com.example.pdpapp.ui.screen.recipedetails

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.pdpapp.R
import com.example.pdpapp.databinding.FragmentRecipeDetailsBinding
import com.example.pdpapp.services.ServiceConstants
import com.example.pdpapp.services.loading.LoadingService
import com.example.pdpapp.services.media.LiveRecipeService
import com.example.pdpapp.ui.screen.entity.RecipeEntity
import com.example.pdpapp.ui.screen.entity.RecipeState
import com.example.pdpapp.ui.screen.entity.base.ViewState
import com.example.pdpapp.ui.utils.toast
import com.example.pdpapp.ui.utils.visibility
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecipeDetailsFragment : Fragment() {
    private var _binding: FragmentRecipeDetailsBinding? = null
    private val binding: FragmentRecipeDetailsBinding
        get() = checkNotNull(_binding)

    private val args: RecipeDetailsFragmentArgs by navArgs()
    private val viewModel: RecipeDetailsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentRecipeDetailsBinding.inflate(inflater).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() = with(binding) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { viewModel.state.collect { processState(it) } }
            }
        }
        viewModel.getRecipe(args.id)
    }

    private fun processState(state: ViewState<RecipeEntity>) = with(binding) {
        when (state) {
            is ViewState.Data<RecipeEntity> -> {
                progress.hide()
                with(state.data) {
                    progressDownloading.hide()
                    when (this.state) {
                        RecipeState.DOWNLOADED -> iconBtn.iconButtonPlay()
                        RecipeState.DOWNLOADING -> {
                            iconBtn.visibility(false)
                            progressDownloading.show()
                        }
                        RecipeState.DEFAULT -> iconBtn.iconButtonDownload()
                    }
                    Picasso.get().load(image).into(imageIv)
                    titleTv.text = title
                    countryTv.text = requireContext().getString(R.string.recipe_country, country)
                    descriptionTv.text =
                        requireContext().getString(R.string.recipe_description, description)
                    stepsAmountTv.text =
                        requireContext().getString(R.string.recipe_steps_amount, "$stepsAmount")
                    durationTv.text =
                        requireContext().getString(R.string.recipe_duration, "$durationInSec")
                }
            }
            is ViewState.Error -> requireContext().toast(state.message)
            is ViewState.Loading -> progress.show()
        }
    }

    private fun ImageView.iconButtonPlay() {
        visibility(true)
        setImageResource(R.drawable.ic_play)
        setOnClickListener { startLiveRecipe(args.id) }
    }

    private fun ImageView.iconButtonDownload() {
        visibility(true)
        setImageResource(R.drawable.ic_download)
        setOnClickListener {
            storeRecipeBackground(args.id)
            viewModel.storeRecipe()
        }
    }

    private fun startLiveRecipe(id: String) {
        requireActivity().startService(
            Intent(requireActivity(), LiveRecipeService::class.java).apply {
                putExtra(ServiceConstants.EXTRA_RECIPE_URL, id)
            }
        )
    }

    private fun storeRecipeBackground(id: String) {
        requireActivity().startService(
            Intent(requireActivity(), LoadingService::class.java).apply {
                putExtra(ServiceConstants.EXTRA_RECIPE_URL, id)
            }
        )
    }
}