package com.dicoding.manhwaapps.ui.screen.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.manhwaapps.R
import com.dicoding.manhwaapps.di.Injection
import com.dicoding.manhwaapps.model.ManhwaList
import com.dicoding.manhwaapps.ui.common.UiState
import com.dicoding.manhwaapps.ui.component.ManhwaList
import com.dicoding.manhwaapps.ui.theme.ManhwaAppsTheme
import com.dicoding.manhwaapps.viewmodel.FavoriteVM
import com.dicoding.manhwaapps.viewmodel.VMFactory

@Composable
fun FavoriteScreen(
    viewModel: FavoriteVM = viewModel(
        factory = VMFactory(Injection.provideRepository())
    ),
    navigateBack: () -> Unit,
    navigateToDetail: (String) -> Unit
) {
    val favsManhwa by viewModel.favsManhwa.collectAsState(emptyList())

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllFavsManhwa()
            }

            is UiState.Success -> {
                FavoriteContent(
                    favsManhwa = favsManhwa,
                    onBackClick = navigateBack,
                    navigateToDetail = navigateToDetail
                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun FavoriteContent(
    favsManhwa: List<ManhwaList>,
    onBackClick: () -> Unit,
    navigateToDetail: (String) -> Unit
) {

    Column(modifier = Modifier.padding(25.dp)) {

        Column {
            Text(
                text = "Your Fav Manhwa",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            if (favsManhwa.isEmpty()) {
                Text(
                    text = stringResource(R.string.emptyfav),
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxSize()
                        .testTag("Empty")
                        .wrapContentSize(Alignment.Center),
                    color = Color.LightGray,
                    textAlign = TextAlign.Center
                )
            } else {

                LazyVerticalGrid(
                    columns = GridCells.Fixed(1),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {

                    items(favsManhwa.size) { index ->
                        val manhwaList = favsManhwa[index]
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
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFavoriteContent() {
    ManhwaAppsTheme {
        FavoriteContent(
            favsManhwa = listOf(),
            onBackClick = {},
            navigateToDetail = {}
        )
    }
}