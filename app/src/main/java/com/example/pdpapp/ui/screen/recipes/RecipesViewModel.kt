package com.example.pdpapp.ui.screen.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pdpapp.data.server.entity.base.getOrNull
import com.example.pdpapp.data.server.repository.RecipesRepository
import com.example.pdpapp.ui.screen.entity.RecipeEntity
import com.example.pdpapp.ui.screen.entity.base.ViewState
import com.example.pdpapp.ui.screen.entity.base.mapTo
import com.example.pdpapp.ui.screen.entity.base.onDataState
import com.example.pdpapp.ui.screen.entity.base.toViewState
import com.example.pdpapp.ui.screen.entity.downloaded
import com.example.pdpapp.ui.screen.entity.downloading
import com.example.pdpapp.ui.utils.toEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipesViewModel(private val _repository: RecipesRepository) : ViewModel() {

    private val _state = MutableStateFlow<ViewState<List<RecipeEntity>>>(ViewState.Loading)
    val state: StateFlow<ViewState<List<RecipeEntity>>>
        get() = _state

    init {
        viewModelScope.launch {
            _repository.storeRecipesFlow.collect { id ->
                state.value.onDataState { data ->
                    _state.emit(ViewState.Data(data.map { event -> if (event.id == id) event.downloaded() else event }))
                }
            }
        }
    }

    fun getRecipes() {
        viewModelScope.launch {
            val storedIds = _repository.getStoredRecipes().getOrNull()?.map { it.id } ?: emptyList()

            _state.emit(
                _repository.getRecipes()
                    .mapTo { recipes -> recipes.map { it.toEntity(isStored = it.id in storedIds) } }
                    .toViewState()
            )
        }
    }

    fun storeRecipe(id: String) {
        viewModelScope.launch {
            state.value.onDataState { data ->
                _state.emit(
                    ViewState.Data(data.map { event -> if (event.id == id) event.downloading() else event })
                )
            }
        }
    }
}