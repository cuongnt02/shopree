package com.ntc.shopree.core.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ntc.shopree.core.ui.icons.Icons
import com.ntc.shopree.core.ui.theme.ColorGrey100
import com.ntc.shopree.core.ui.theme.ColorGrey400
import com.ntc.shopree.core.ui.theme.ColorGrey500
import com.ntc.shopree.core.ui.theme.Outfit
import com.ntc.shopree.core.ui.theme.fontSize4
import com.ntc.shopree.core.ui.theme.radius1
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(
    query: String,
    onQueryChanged: (String) -> Unit,
    onSearch: (String) -> Unit,
    searchResults: List<String>,
    onResultClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: @Composable () -> Unit = { Text("Search") },
    leadingIcon: (@Composable () -> Unit)? = {
        Icon(
            Icons.Outlined.Search, contentDescription = "search icon"
        )
    },
    trailingIcon: (@Composable () -> Unit)? = null,
    supportContent: (@Composable (String) -> Unit)? = null,
    leadingContent: (@Composable () -> Unit)? = null
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Box(modifier = modifier
        .fillMaxWidth()
        .semantics {
            isTraversalGroup = true
        }) {
        SearchBar(modifier = Modifier
            .align(Alignment.TopCenter)
            .semantics {
                traversalIndex = 0f
            }, inputField = {
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = onQueryChanged,
                onSearch = {
                    onSearch(query)
                    expanded = false
                },
                expanded = expanded,
                onExpandedChange = { expanded = it },
                placeholder = placeholder,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon
            )
        }, expanded = expanded, onExpandedChange = { expanded = it }) {
            LazyColumn {
                items(count = searchResults.size) { index ->
                    val resultText = searchResults[index]
                    ListItem(
                        headlineContent = { Text(resultText) },
                        supportingContent = supportContent?.let { { it(resultText) } },
                        leadingContent = leadingContent,
                        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                        modifier = Modifier
                            .clickable {
                                onResultClick(resultText)
                                expanded = false
                            }
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp))
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleSearchBar(
    textFieldState: TextFieldState,
    onSearch: (String) -> Unit,
    searchResults: List<String>,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    var isFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    val inputBorderColor by animateColorAsState(
        targetValue = when {
            isFocused -> ColorGrey500
            else -> ColorGrey400
        }, animationSpec = tween(
            durationMillis = 100
        )
    )

    Box(
        modifier
            .fillMaxWidth()
            .semantics { isTraversalGroup = true }) {
        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .semantics { traversalIndex = 0f },

            inputField = {
                SearchBarDefaults.InputField(
                    query = textFieldState.text.toString(),
                    onQueryChange = {
                        textFieldState.edit { replace(0, length, it) }
                        scope.launch {
                            onSearch(it)
                        }
                    },
                    onSearch = {
                        onSearch(textFieldState.text.toString())

                    },
                    expanded = false,
                    onExpandedChange = { },
                    placeholder = { Text("Search", fontSize = fontSize4, fontFamily = Outfit) },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = ColorGrey100, focusedContainerColor = ColorGrey100
                    ),
                    modifier = Modifier
                        .background(ColorGrey100, RoundedCornerShape(radius1))
                        .border(2.dp, inputBorderColor, RoundedCornerShape(radius1))
                        .onFocusChanged {
                            isFocused = it.isFocused
                            if (!isFocused) focusManager.clearFocus()
                        },
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Search, contentDescription = "search icon"
                        )

                    })
            },
            expanded = false,
            onExpandedChange = { },
            shape = RoundedCornerShape(radius1),

//            colors = SearchBarDefaults.colors(
//                containerColor = ColorGrey200, // your desired background color
//                dividerColor = Color.Transparent, inputFieldColors = TextFieldDefaults.colors(
//                    focusedTextColor = ColorGrey700,
//                    unfocusedTextColor = ColorGrey700,
//                    cursorColor = ColorGrey700,
//                    focusedPlaceholderColor = ColorGrey400,
//                    unfocusedPlaceholderColor = ColorGrey400,
//                )
//            ),
        ) {
            // Display search results in a scrollable column
            Column(Modifier.verticalScroll(rememberScrollState())) {
                searchResults.forEach { result ->
                    ListItem(headlineContent = { Text(result) }, modifier = Modifier
                        .clickable {
                            textFieldState.edit { replace(0, length, result) }
                            onSearch(result)
                        }
                        .fillMaxWidth())
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SimpleSearchBarPreview() {
    val textFieldState = rememberTextFieldState()
    SimpleSearchBar(
        textFieldState = textFieldState, onSearch = {}, searchResults = emptyList()
    )
}