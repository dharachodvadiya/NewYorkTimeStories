package com.allvideo.status.free.downloade.ui.story_detail.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.allvideo.status.free.downloade.R
import com.allvideo.status.free.downloade.ui.model.UIArticle
import com.allvideo.status.free.downloade.ui.theme.NewYorkTimeStoriesTheme
import com.allvideo.status.free.downloade.util.ErrorMessage

@Composable
fun StoryDetailTopBar(
    onNavigationBack: () -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onNavigationBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.background
            )
        }

        Text(
            text = stringResource(id = R.string.article),
            color = MaterialTheme.colorScheme.background,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(40.dp))
    }
}


@Composable
fun StoryDetailData(
    uiArticle: UIArticle,
    onSeeMoreClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val url = uiArticle.images?.get(0)?.url
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.inner_padding))
    ) {
        StoryDetailImage(
            url,
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = uiArticle.title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = uiArticle.details,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = if (uiArticle.author.isNotBlank()) "Author : ${uiArticle.author}" else "",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "See More >>",
            style = MaterialTheme.typography.labelMedium,
            color = Color.Blue,
            textAlign = TextAlign.Right,
            modifier = Modifier
                .clickable(role = Role.Button) { onSeeMoreClick(uiArticle.id) }
                .fillMaxWidth()
        )
    }

}

@Composable
fun StoryDetailImage(url: String?, modifier: Modifier = Modifier) {

    if (url.isNullOrEmpty()) {

        loadableImage(
            null,
            ErrorMessage.IMAGE_LOADING.message,
            modifier
        )
    } else {
        val painter = rememberImagePainter(
            data = url
        )

        if (painter.state is ImagePainter.State.Loading) {
            loadableImage(null, null, modifier)
        } else {
            loadableImage(painter, null, modifier)
        }
    }
}

@Composable
fun loadableImage(painter: ImagePainter?, errorMessage: String?, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        if (painter != null) {
            Image(
                painter = painter,
                contentDescription = "image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            if (errorMessage.isNullOrEmpty())
                CircularProgressIndicator()
            else
                Text(text = errorMessage)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun StoryDetailTopBarPriview() {
    NewYorkTimeStoriesTheme {
        StoryDetailTopBar(onNavigationBack = {})
    }
}

@Preview(showBackground = true)
@Composable
fun StoryDetailDataPriview() {
    NewYorkTimeStoriesTheme {
        //StoryDetailData()
    }
}