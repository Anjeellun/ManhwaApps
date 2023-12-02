package com.dicoding.manhwaapps.ui.screen.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.dicoding.manhwaapps.R
import com.dicoding.manhwaapps.di.Injection
import com.dicoding.manhwaapps.model.ManhwaList
import com.dicoding.manhwaapps.ui.common.UiState
import com.dicoding.manhwaapps.viewmodel.DetailVM
import com.dicoding.manhwaapps.viewmodel.VMFactory

@Composable
fun DetailScreen(
    manhwaId: String,
    viewModel: DetailVM = viewModel(factory = VMFactory(Injection.provideRepository())),
    navigateBack: () -> Unit,
) {
    val isFavorite = remember { mutableStateOf(false) }

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getManhwaListById(manhwaId)
            }

            is UiState.Success<*> -> {
                val data = uiState.data as ManhwaList

                viewModel.isFavManhwa(manhwaId) { isManhwaFavorite ->
                    isFavorite.value = isManhwaFavorite
                }

                DetailContent(
                    coverUrl = data.list.coverUrl,
                    manhwaName = data.list.manhwaName,
                    synopsis = data.list.synopsis,
                    author = data.list.author,
                    artist = data.list.artist,
                    genre = data.list.genre,
                    publisher = data.list.publisher,
                    status = data.list.status,
                    serialization = data.list.serialization,
                    readUrl = data.list.readUrl,
                    readUrlKor = data.list.reaUrlKor,
                    onBackClick = navigateBack,
                    isFavorite = isFavorite.value,
                    onToggleFavorite = {
                        if (isFavorite.value) {
                            viewModel.removeManhwaFav(manhwaId)
                            isFavorite.value = false
                        } else {
                            viewModel.addManhwaToFav(manhwaId)
                            isFavorite.value = true
                        }
                    }
                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    coverUrl: String,
    manhwaName: String,
    synopsis: String,
    author: String,
    artist: String,
    genre: String,
    publisher: String,
    status: String,
    serialization: String,
    readUrl: String,
    readUrlKor: String,
    onBackClick: () -> Unit,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier

) {

    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = { onToggleFavorite() },
                    modifier = modifier.padding(top = 5.dp, end = 5.dp)
                ) {
                    val icon =
                        if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder
                    Icon(
                        imageVector = icon,
                        tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                        contentDescription = stringResource(R.string.addfav),
                        modifier = Modifier.size(26.dp)
                    )
                }

            }

            AsyncImage(
                model = coverUrl,
                contentDescription = "Cover",
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .height(650.dp)
                    .width(550.dp)
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = 30.dp,
                            topEnd = 30.dp,
                            bottomStart = 30.dp,
                            bottomEnd = 30.dp
                        )
                    )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = manhwaName,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                modifier = modifier.align(Alignment.CenterHorizontally)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp)
            ) {
                Text(
                    text = "Author",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    modifier = modifier
                        .padding(top = 16.dp, bottom = 8.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Text(
                    text = author,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = modifier.padding(top = 5.dp)
                )

                Text(
                    text = "Artist",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    modifier = modifier
                        .padding(top = 16.dp, bottom = 8.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Text(
                    text = artist,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = modifier.padding(top = 5.dp)
                )
                Text(
                    text = "Genre",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    modifier = modifier
                        .padding(top = 16.dp, bottom = 8.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Text(
                    text = genre,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = modifier.padding(top = 5.dp)
                )

                Text(
                    text = "Publisher",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    modifier = modifier
                        .padding(top = 16.dp, bottom = 8.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Text(
                    text = publisher,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = modifier.padding(top = 5.dp)
                )

                Text(
                    text = "Status",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    modifier = modifier
                        .padding(top = 16.dp, bottom = 8.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Text(
                    text = status,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = modifier.padding(top = 5.dp)
                )

                Text(
                    text = "Serialization",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    modifier = modifier
                        .padding(top = 16.dp, bottom = 8.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Text(
                    text = serialization,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = modifier.padding(top = 5.dp)
                )

                Text(
                    text = "English Platform",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    modifier = modifier
                        .padding(top = 16.dp, bottom = 8.dp)
                        .align(Alignment.CenterHorizontally)

                )

                Text(
                    text = readUrl,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = modifier.padding(top = 5.dp)

                )

                Text(
                    text = "Korea Platform",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    modifier = modifier
                        .padding(top = 16.dp, bottom = 8.dp)
                        .align(Alignment.CenterHorizontally)

                )

                Text(
                    text = readUrlKor,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = modifier.padding(top = 5.dp)

                )

                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    modifier = modifier.align(Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically,

                ) {
                    Column {
                        Text(
                            text = "Synopsis",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Left,
                            modifier = modifier
                                .padding(top = 16.dp, bottom = 8.dp)
                                .align(Alignment.Start)
                        )
                        Text(
                            text = synopsis,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Justify,
                            modifier = modifier.padding(top = 5.dp)
                        )

                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(5.dp)
                                .padding(top = 16.dp)
                        )
                    }
                }
            }
        }
    }
}
