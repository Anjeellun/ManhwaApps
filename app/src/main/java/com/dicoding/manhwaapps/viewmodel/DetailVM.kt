package com.dicoding.manhwaapps.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.manhwaapps.data.ManhwaRepost
import com.dicoding.manhwaapps.model.ManhwaList
import com.dicoding.manhwaapps.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailVM(private val repository: ManhwaRepost) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<ManhwaList>> = MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<ManhwaList>> get() = _uiState

    fun getManhwaListById(manhwaId: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getManhwaListById(manhwaId))
        }
    }

    fun addManhwaToFav(manhwaId: String) {
        viewModelScope.launch {
            repository.addManwhaToFav(manhwaId)
        }
    }

    fun removeManhwaFav(manhwaId: String) {
        viewModelScope.launch {
            repository.removeManhwaFav(manhwaId)
        }
    }

    fun isFavManhwa(manhwaId: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val isFavorite = repository.isFavManhwa(manhwaId)
            onResult(isFavorite)
        }
    }
}