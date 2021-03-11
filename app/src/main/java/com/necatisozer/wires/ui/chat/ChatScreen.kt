/*
 * Copyright 2021 Necati Sozer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.necatisozer.wires.ui.chat

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.IconToggleButton
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.WbIncandescent
import androidx.compose.material.icons.outlined.WbIncandescent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState.Active
import androidx.compose.ui.focus.FocusState.Inactive
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.transform.CircleCropTransformation
import com.necatisozer.wires.R
import com.necatisozer.wires.domain.model.Message
import com.necatisozer.wires.domain.model.Theme.DARK
import com.necatisozer.wires.domain.model.Theme.LIGHT
import com.necatisozer.wires.domain.model.User
import com.necatisozer.wires.domain.model.isDarkTheme
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.insets.navigationBarsWithImePadding
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.launch

@Composable
fun ChatScreen(
    chatViewModel: ChatViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val viewState: State<ChatViewState> = chatViewModel.viewState.collectAsState()
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    Box(modifier) {
        Column(Modifier.fillMaxSize()) {
            ChatAppBar(
                title = viewState.value.user?.nickname.orEmpty(),
                onBackClick = {
                    chatViewModel.deleteUser()
                    navController.popBackStack()
                },
                isDarkTheme = viewState.value.theme.isDarkTheme(),
                onThemeChange = { isDarkTheme ->
                    val theme = if (isDarkTheme) DARK else LIGHT
                    chatViewModel.setTheme(theme)
                },
                modifier = Modifier.statusBarsPadding()
            )
            Messages(
                messages = viewState.value.messages,
                user = viewState.value.user,
                lazyListState = listState,
                modifier = Modifier.weight(1f)
            )
            MessageInput(
                onSendClick = {
                    chatViewModel.sendMessage(it)
                    scope.launch {
                        listState.animateScrollToItem(viewState.value.messages.lastIndex)
                    }
                },
                modifier = Modifier.navigationBarsWithImePadding(),
            )
        }
    }
}

@Composable
fun ChatAppBar(
    title: String,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    isDarkTheme: Boolean,
    onThemeChange: (isDarkTheme: Boolean) -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h6,
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.chat_back_content_description),
                )
            }
        },
        actions = {
            IconToggleButton(checked = isDarkTheme, onCheckedChange = onThemeChange) {
                Crossfade(targetState = isDarkTheme) { isDarkTheme ->
                    if (isDarkTheme) {
                        Icon(
                            Icons.Outlined.WbIncandescent,
                            contentDescription = stringResource(R.string.chat_dark_theme_content_description),
                        )
                    } else {
                        Icon(
                            Icons.Filled.WbIncandescent,
                            contentDescription = stringResource(R.string.chat_light_theme_content_description),
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun Messages(
    messages: List<Message>,
    user: User?,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        state = lazyListState,
    ) {
        itemsIndexed(messages) { index, message ->
            val userOfPreviousMessage = messages.getOrNull(index - 1)?.user
            val userOfNextMessage = messages.getOrNull(index + 1)?.user

            Message(
                message = message,
                isUserMe = message.user.id == user?.id,
                isFirstMessageOfBlock = userOfPreviousMessage != message.user,
                isLastMessageOfBlock = userOfNextMessage != message.user,
            )
        }
    }
}

@Composable
fun Message(
    message: Message,
    isUserMe: Boolean,
    isFirstMessageOfBlock: Boolean,
    isLastMessageOfBlock: Boolean,
) {
    Row(
        modifier = if (isFirstMessageOfBlock) Modifier.padding(top = 8.dp) else Modifier,
    ) {
        if (isFirstMessageOfBlock) {
            Avatar(
                avatarUrl = message.user.avatarURL,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .size(42.dp)
                    .align(Alignment.Top),
            )
        } else {
            Spacer(Modifier.width(74.dp))
        }

        Column(
            modifier = Modifier
                .padding(end = 16.dp)
                .weight(1f),
        ) {
            if (isFirstMessageOfBlock) {
                Text(
                    text = message.user.nickname,
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier
                        .paddingFrom(LastBaseline, after = 8.dp)
                )
            }
            MessageBubble(
                text = message.text,
                isUserMe = isUserMe,
                isFirstMessageOfBlock = isFirstMessageOfBlock,
                isLastMessageOfBlock = isLastMessageOfBlock,
            )
            if (isLastMessageOfBlock) {
                Spacer(Modifier.height(8.dp))
            } else {
                Spacer(Modifier.height(4.dp))
            }
        }
    }
}

@Composable
fun Avatar(
    avatarUrl: String,
    modifier: Modifier = Modifier,
) {
    CoilImage(
        data = avatarUrl,
        contentDescription = stringResource(R.string.chat_avatar_content_description),
        fadeIn = true,
        requestBuilder = {
            transformations(CircleCropTransformation())
        },
        modifier = modifier,
    )
}

@Composable
fun MessageBubble(
    text: String,
    isUserMe: Boolean,
    isFirstMessageOfBlock: Boolean,
    isLastMessageOfBlock: Boolean,
) {
    val cardShape = RoundedCornerShape(
        topStart = if (isFirstMessageOfBlock) 8.dp else 0.dp,
        topEnd = 8.dp,
        bottomEnd = 8.dp,
        bottomStart = if (isLastMessageOfBlock) 8.dp else 0.dp,
    )

    val cardBackgroundColor = when {
        isUserMe -> MaterialTheme.colors.primary
        else -> MaterialTheme.colors.surface
    }

    Card(
        shape = cardShape,
        backgroundColor = cardBackgroundColor,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.body1.copy(color = LocalContentColor.current),
            modifier = Modifier.padding(8.dp),
        )
    }
}

@Composable
fun MessageInput(
    onSendClick: (message: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var textState by remember { mutableStateOf(TextFieldValue()) }
    var textFieldFocusState by remember { mutableStateOf(Inactive) }

    Box(modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BasicTextField(
                value = textState,
                onValueChange = { textState = it },
                cursorBrush = SolidColor(LocalContentColor.current),
                textStyle = LocalTextStyle.current.copy(color = LocalContentColor.current),
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .background(
                        color = MaterialTheme.colors.surface,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(16.dp)
                    .onFocusChanged { textFieldFocusState = it }
            )

            AnimatedVisibility(visible = textState.text.isNotBlank()) {
                IconButton(
                    onClick = {
                        onSendClick(textState.text)
                        textState = TextFieldValue()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Send,
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary,
                    )
                }
            }
        }

        val disableContentColor = MaterialTheme.colors.onSurface
            .copy(alpha = ContentAlpha.disabled)
        if (textState.text.isEmpty() && textFieldFocusState != Active) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 24.dp),
                text = stringResource(R.string.chat_type_a_message_placeholder),
                style = MaterialTheme.typography.body1.copy(color = disableContentColor)
            )
        }
    }
}
