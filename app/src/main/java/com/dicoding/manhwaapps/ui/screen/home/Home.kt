package com.dicoding.manhwaapps.ui.screen.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.manhwaapps.di.Injection
import com.dicoding.manhwaapps.model.ManhwaList
import com.dicoding.manhwaapps.ui.common.UiState
import com.dicoding.manhwaapps.ui.component.ManhwaList
import com.dicoding.manhwaapps.ui.component.Search
import com.dicoding.manhwaapps.viewmodel.HomeVM
import com.dicoding.manhwaapps.viewmodel.VMFactory


@Composable
fun Home(
    modifier: Modifier = Modifier,
    viewModel: HomeVM = viewModel(
        factory = VMFactory(Injection.provideRepository())
    ),
    navigateToDetail: (String) -> Unit,
) {
    val searchResult by viewModel.searchResult.collectAsState(initial = emptyList())
    val query by viewModel.query.collectAsState(initial = "")

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllManhwa()
            }

            is UiState.Success -> {
                Column {
                    Search(
                        query = query,
                        onQueryChange = { newQuery ->
                            viewModel.setQuery(newQuery)
                            viewModel.searchManhwa()
                        },
                    )
                    HomeContent(
                        groupedManhwaList = if (query.isEmpty()) uiState.data else emptyMap(),
                        searchResult = searchResult,
                        modifier = modifier,
                        navigateToDetail = navigateToDetail,
                    )
                }
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    groupedManhwaList: Map<Char, List<ManhwaList>>,
    searchResult: List<ManhwaList>,
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = modifier.testTag("List")
    ) {
        if (searchResult.isNotEmpty()) {
            items(searchResult.size) { index ->
                val manhwaList = searchResult[index]
                val manhwa = manhwaList.list

                ManhwaList(
                    manhwaName = manhwa.manhwaName,
                    synopsis = manhwa.synopsis,
                    coverUrl = manhwa.coverUrl,
                    modifier = Modifier.clickable {
                        navigateToDetail(manhwa.id)
                    }
                )
            }
        } else {
            groupedManhwaList.entries.forEach { (_, manhwaList) ->
                items(manhwaList.size) { index ->
                    val data = manhwaList[index]
                    val manhwa = data.list

                    ManhwaList(
                        manhwaName = manhwa.manhwaName,
                        synopsis = manhwa.synopsis,
                        coverUrl = manhwa.coverUrl,
                        modifier = Modifier.clickable {
                            navigateToDetail(manhwa.id)
                        }
                    )
                }
            }
        }
    }
}
