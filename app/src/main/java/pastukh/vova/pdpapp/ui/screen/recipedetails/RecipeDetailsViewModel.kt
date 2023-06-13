package pastukh.vova.pdpapp.ui.screen.recipedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pastukh.vova.data.server.entity.base.getOrNull
import pastukh.vova.pdpapp.ui.screen.entity.RecipeEntity
import pastukh.vova.pdpapp.ui.screen.entity.base.ViewState
import pastukh.vova.pdpapp.ui.screen.entity.base.mapTo
import pastukh.vova.pdpapp.ui.screen.entity.base.onDataState
import pastukh.vova.pdpapp.ui.screen.entity.base.toViewState
import pastukh.vova.pdpapp.ui.screen.entity.downloaded
import pastukh.vova.pdpapp.ui.screen.entity.downloading
import pastukh.vova.pdpapp.ui.utils.toEntity

class RecipeDetailsViewModel(private val _repository: pastukh.vova.data.server.repository.RecipesRepository) :
    ViewModel() {

    private val _state = MutableStateFlow<ViewState<RecipeEntity>>(ViewState.Loading)
    val state: StateFlow<ViewState<RecipeEntity>>
        get() = _state

    init {
        viewModelScope.launch {
            _repository.storeRecipesFlow.collect { id ->
                state.value.onDataState { data ->
                    if (data.id == id) _state.emit(ViewState.Data(data.downloaded()))
                }
            }
        }
    }

    fun getRecipe(id: String) {
        viewModelScope.launch {
            val storedIds = _repository.getStoredRecipes().getOrNull()?.map { it.id } ?: emptyList()

            _state.emit(
                _repository.getRecipe(id)
                    .mapTo { recipe -> recipe.toEntity(isStored = recipe.id in storedIds) }
                    .toViewState()
            )
        }
    }

    fun storeRecipe() {
        viewModelScope.launch {
            state.value.onDataState { data -> _state.emit(ViewState.Data(data.downloading())) }
        }
    }
}