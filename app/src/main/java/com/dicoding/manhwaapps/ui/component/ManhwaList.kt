package com.dicoding.manhwaapps.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ManhwaList(
    manhwaName: String,
    synopsis: String,
    coverUrl: String,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 9.dp
        ),
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = coverUrl,
                contentDescription = "Cover",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(105.dp)
                    .clip(RoundedCornerShape(15.dp))
            )

            Column(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .fillMaxWidth()
            ) {

                Text(
                    text = manhwaName,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onBackground,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    )

                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = synopsis,
                    maxLines = 3,
                    color = MaterialTheme.colorScheme.onBackground,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium

                )

            }

        }
    }
}

@Composable
@Preview(showBackground = true)
fun ManhwaListPreview() {
    MaterialTheme {
        ManhwaList(
            manhwaName = "Who Made Me a Princess",
            synopsis = "Athanasia De Alger Obelia, that's this body name",
            coverUrl = "",
            modifier = Modifier.padding(8.dp)
        )
    }
}