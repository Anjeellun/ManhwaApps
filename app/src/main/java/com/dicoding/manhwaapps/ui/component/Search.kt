package com.dicoding.manhwaapps.ui.component

import com.dicoding.manhwaapps.ui.theme.ManhwaAppsTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dicoding.manhwaapps.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    androidx.compose.material3.SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = {},
        active = false,
        onActiveChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "search_icon",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        placeholder = {
            Text(
                text = stringResource(R.string.search),
                fontSize = 15.sp
            )
        },
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth()
            .heightIn(min = 20.dp)
    ) {
    }
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    ManhwaAppsTheme {
        Search(query = "Enter the title of the manhwa you are looking for...", onQueryChange = {})
    }
}