package com.indie.apps.newyorktimestories.ui.story_list.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.outlined.ViewCozy
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.indie.apps.newyorktimestories.R
import com.indie.apps.newyorktimestories.ui.model.UIArticle
import com.indie.apps.newyorktimestories.ui.theme.NewYorkTimeStoriesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoryListTopBar(
    onTextChange: (String) -> Unit,
    enabledList: Boolean = true,
    selectedFilterText: String,
    onFilterSelect: (String) -> Unit,
    onListChange: () -> Unit,
    options: List<String>,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    var textState by remember {
        mutableStateOf("")
    }


    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
            disabledIndicatorColor = MaterialTheme.colorScheme.primary,
            focusedContainerColor = MaterialTheme.colorScheme.primary,
            unfocusedContainerColor = MaterialTheme.colorScheme.primary
        )

        TextField(
            value = textState,
            onValueChange = {
                textState = it
                onTextChange(it)
            },
            placeholder = {
                Text(
                    text = "Search",
                    color = MaterialTheme.colorScheme.background
                )
            },
            textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.background),
            modifier = Modifier
                .weight(1f)
                .background(MaterialTheme.colorScheme.onTertiary),
            colors = colors
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            Row(
                modifier = Modifier.width(120.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.FilterAlt,
                    contentDescription = "list",
                    tint = MaterialTheme.colorScheme.background
                )
                Text(
                    text = selectedFilterText,
                    modifier = Modifier
                        .width(70.dp)
                        .menuAnchor(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.background
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.background
                )
            }

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.width(150.dp)
            ) {
                options.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            //selectedFilterText = item
                            textState = ""
                            expanded = false
                            onFilterSelect(item)
                        }
                    )
                }
            }
        }

        IconButton(onClick = onListChange) {
            Icon(
                imageVector = if (enabledList) Icons.AutoMirrored.Filled.List else Icons.Outlined.ViewCozy,
                contentDescription = "list",
                tint = MaterialTheme.colorScheme.background
            )
        }

    }
}

@Composable
fun StoryListItem(
    uiArticle: UIArticle,
    onItemSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        onClick = onItemSelect
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {

            val url = uiArticle.images?.get(2)?.url

            Image(
                painter = rememberImagePainter(data = url),
                contentDescription = "image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .fillMaxHeight()
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .height(100.dp)
                    .padding(dimensionResource(id = R.dimen.inner_padding))
            ) {
                Text(
                    text = uiArticle.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = uiArticle.author,
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Gray,
                    textAlign = TextAlign.Right,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

}

@Composable
fun StoryCardListItem(
    uiArticle: UIArticle,
    onItemSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(200.dp),
        onClick = onItemSelect
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val url = uiArticle.images?.get(1)?.url

            Image(
                painter = rememberImagePainter(data = url),
                contentDescription = "image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
            )

            Column(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.inner_padding))
            ) {
                Text(
                    text = uiArticle.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = uiArticle.author,
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Gray,
                    textAlign = TextAlign.Right,
                    modifier = Modifier.fillMaxWidth()
                )
            }


        }
    }

}

@Preview(showBackground = true)
@Composable
fun StoryListTopBarPriview() {
    NewYorkTimeStoriesTheme {
        /* StoryListTopBar(
             onTextChange = {},
             options = emptyList()
         )*/
    }
}

@Preview(showBackground = true)
@Composable
fun StoryListItemPriview() {
    NewYorkTimeStoriesTheme {
        //StoryListItem({})
    }
}