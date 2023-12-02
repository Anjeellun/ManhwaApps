package com.dicoding.manhwaapps.ui.screen.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dicoding.manhwaapps.R
import com.dicoding.manhwaapps.ui.theme.ManhwaAppsTheme

@Composable
fun AboutMe(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit
) {
    AboutContent(
        modifier = modifier,
        onBackClick = navigateBack
    )
}

@Composable
fun AboutContent(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    Column(
        modifier = modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "About Developer",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp),
                textAlign = TextAlign.Center,
                modifier = modifier
                    .padding(start = 8.dp)
            )

            Spacer(modifier = Modifier.height(15.dp))

            AsyncImage(
                model = stringResource(R.string.profpic),
                contentDescription = "profile picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(8.dp)
                    .size(150.dp)
                    .clip(RoundedCornerShape(15.dp))
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = stringResource(R.string.myname),
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = stringResource(R.string.myemail),
                style =
                TextStyle(
                    fontSize = 12.sp
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAboutContent() {
    ManhwaAppsTheme {
        AboutContent {}
    }
}