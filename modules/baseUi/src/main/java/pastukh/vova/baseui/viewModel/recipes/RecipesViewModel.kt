package pastukh.vova.baseui.viewModel.recipes

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
import pastukh.vova.data.server.repository.RecipesRepository

class RecipesViewModel(private val _repository: RecipesRepository) : ViewModel() {

    private val _state = MutableStateFlow<RecipesViewState>(RecipesViewState.Loading)
    val state: StateFlow<RecipesViewState>
        get() = _state

    init {
        viewModelScope.launch {
            _repository.storeRecipesFlow.collect { id ->
                state.value.onState(
                    onData = { recipes ->
                        _state.emit(
                            RecipesViewState.Data(
                                recipes.map { event -> if (event.id == id) event.downloaded() else event }
                            )
                        )
                    }
                )
            }
        }
    }

    fun getRecipes() {
        viewModelScope.launch {
            val storedIds = _repository.getStoredRecipes().getOrNull()?.map { it.id } ?: emptyList()

            _state.emit(
                when (val result = _repository.getRecipes()
                    .mapTo { recipes -> recipes.map { it.toEntity(isStored = it.id in storedIds) } }) {
                    is ResponseResult.Success -> RecipesViewState.Data(result.data)
                    is ResponseResult.Error -> RecipesViewState.Error(result.error.message)
                    else -> RecipesViewState.Error(null)
                }
            )
        }
    }

    fun storeRecipe(id: String) {
        viewModelScope.launch {
            state.value.onState(
                onData = { recipes ->
                    _state.emit(
                        RecipesViewState.Data(recipes.map { event -> if (event.id == id) event.downloading() else event })
                    )
                }
            )
        }
    }
}