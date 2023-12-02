package com.dicoding.manhwaapps.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.manhwaapps.data.ManhwaRepost
import com.dicoding.manhwaapps.model.ManhwaList
import com.dicoding.manhwaapps.ui.common.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteVM(private val repository: ManhwaRepost) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<ManhwaList>>> =
        MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<List<ManhwaList>>> get() = _uiState

    val favsManhwa: Flow<List<ManhwaList>> = repository.getFavManhwa()

    fun getAllFavsManhwa() {
        viewModelScope.launch {
            repository.getFavManhwa()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { favoriteAnimeItems ->
                    _uiState.value = UiState.Success(favoriteAnimeItems)
                }
        }
    }
}