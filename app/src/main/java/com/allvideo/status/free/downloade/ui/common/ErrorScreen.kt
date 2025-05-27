package com.allvideo.status.free.downloade.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.allvideo.status.free.downloade.util.ErrorMessage

@Composable
fun ErrorScreen(
    errorText: String?,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(text = errorText ?: ErrorMessage.NO_MESSAGE.message)
    }
}