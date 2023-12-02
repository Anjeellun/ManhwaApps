package com.dicoding.manhwaapps.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.manhwaapps.data.ManhwaRepost
import com.dicoding.manhwaapps.model.ManhwaList
import com.dicoding.manhwaapps.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeVM(private val repository: ManhwaRepost) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<Map<Char, List<ManhwaList>>>> =
        MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<Map<Char, List<ManhwaList>>>> get() = _uiState

    private val _searchResult = MutableStateFlow<List<ManhwaList>>(emptyList())
    val searchResult: StateFlow<List<ManhwaList>> get() = _searchResult

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> get() = _query

    fun getAllManhwa() {
        viewModelScope.launch {
            repository.getSortedGrouped()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { groupedCodeItems ->
                    _uiState.value = UiState.Success(groupedCodeItems)
                }
        }
    }

    fun searchManhwa() {
        val currentQuery = _query.value
        viewModelScope.launch {
            repository.searchManhwa(currentQuery)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { searchResult ->
                    _searchResult.value = searchResult
                }
        }
    }

    fun setQuery(newQuery: String) {
        _query.value = newQuery
    }
}