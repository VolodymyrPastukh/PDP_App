package pastukh.vova.baseui.viewModel.recipeDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pastukh.vova.baseui.entity.base.mapTo
import pastukh.vova.baseui.entity.downloaded
import pastukh.vova.baseui.entity.downloading
import pastukh.vova.baseui.utils.toEntity
import pastukh.vova.data.server.entity.base.ResponseResult
import pastukh.vova.data.server.entity.base.getOrNull

class RecipeDetailsViewModel(private val _repository: pastukh.vova.data.server.repository.RecipesRepository) :
    ViewModel() {

    private val _state = MutableStateFlow<RecipeDetailsViewState>(RecipeDetailsViewState.Loading)
    val state: StateFlow<RecipeDetailsViewState>
        get() = _state

    init {
        viewModelScope.launch {
            _repository.storeRecipesFlow.collect { id ->
                state.value.onState(
                    onData = { recipe ->
                        if (recipe.id == id) _state.emit(RecipeDetailsViewState.Data(recipe.downloaded()))
                    }
                )
            }
        }
    }

    fun getRecipe(id: String?) {
        viewModelScope.launch {
            if(id == null){
                _state.emit(RecipeDetailsViewState.NotFound)
                return@launch
            }
            val storedIds = _repository.getStoredRecipes().getOrNull()?.map { it.id } ?: emptyList()

            _state.emit(
                when (val result = _repository.getRecipe(id)
                    .mapTo { recipe -> recipe.toEntity(isStored = recipe.id in storedIds) }) {
                    is ResponseResult.Success -> RecipeDetailsViewState.Data(result.data)
                    is ResponseResult.Error -> RecipeDetailsViewState.Error(result.error.message)
                    else -> RecipeDetailsViewState.Error(null)
                }
            )
        }
    }

    fun storeRecipe() {
        viewModelScope.launch {
            state.value.onState(
                onData = { recipe ->
                    _state.emit(RecipeDetailsViewState.Data(recipe.downloading()))
                }
            )
        }
    }
}