package com.indie.apps.newyorktimestories.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.indie.apps.newyorktimestories.R
import com.indie.apps.newyorktimestories.util.ErrorMessage

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