package com.example.forever.presentation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.forever.presentation.ui.components.NoteItem
import com.example.forever.presentation.ui.components.HeartBackground
import com.example.forever.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import com.example.forever.R
import com.example.forever.domain.model.Note

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier,
    onSettingsClick: () -> Unit
) {
    // Данные из ViewModel
    val userName by viewModel.userName.collectAsState()
    val notes by viewModel.notes.collectAsState()

    // Локальное состояние поля ввода
    var inputText by remember { mutableStateOf("") }
    var editingNote by remember { mutableStateOf<Note?>(null) }

    // Состояние панели
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // Функция сохранения (добавление ИЛИ обновление)
    fun save() {
        if (inputText.isBlank()) return
        val noteToEdit = editingNote
        if (noteToEdit != null) {
            viewModel.updateNote(noteToEdit, inputText)
            editingNote = null
        } else {
            viewModel.addNote(inputText)
        }
        inputText = ""
    }
    Box(modifier = Modifier.fillMaxSize()) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(
                    drawerContainerColor = colorResource(id = R.color.pink_background)
                ) {
                    Spacer(modifier = Modifier.height(32.dp))

                    // Шапка панели
                    Text(
                        text = "Forever",
                        style = MaterialTheme.typography.headlineSmall,
                        color = colorResource(id = R.color.text_color),
                        modifier = Modifier.padding(horizontal = 28.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Пункт "Настройки"
                    NavigationDrawerItem(
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = null,
                                tint = colorResource(id = R.color.pink_heart)
                            )
                        },
                        label = {
                            Text(
                                "Настройки",
                                color = colorResource(id = R.color.text_color)
                            )
                        },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() } // закрываем панель
                            onSettingsClick()                    // открываем настройки
                        },
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )

                    // ПОЗЖЕ: добавить "Темы оформления", "О приложении" и т.д.
                }
            }
        )
        {
            // Оборачиваем всё в фон с сердечками
            HeartBackground {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .padding(horizontal = 24.dp)
                ) {
                    // Меню (гамбургер) слева сверху
                    IconButton(
                        onClick = { scope.launch { drawerState.open() } },
                        modifier = Modifier.align(Alignment.Start)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Меню",
                            tint = Color(0xFFB98A96)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Приветствие
                    Text(
                        text = "Привет, $userName!",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color(0xFF3E2A32),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Подзаголовок
                    Text(
                        text = "Запиши что-нибудь приятное для себя\nДай себе повод улыбнуться :)",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF5C4451),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Поле ввода с галочкой в углу (как в макете)
                    Box {
                        OutlinedTextField(
                            value = inputText,
                            onValueChange = { inputText = it },
                            placeholder = {
                                Text(
                                    if (editingNote != null) "Редактирование..."
                                    else "Начните писать..."
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 4,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedContainerColor = Color(0xFFFFFFFF),
                                focusedContainerColor = Color(0xFFFFFFFF),
                                unfocusedBorderColor = Color.Transparent,
                                focusedBorderColor = Color(0xFFD9A5B3),
                                unfocusedPlaceholderColor = Color(0xFFB98A96),
                                focusedPlaceholderColor = Color(0xFFB98A96)
                            )
                        )

                        // Галочка сохранения в правом нижнем углу поля
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Сохранить",
                            tint = Color(0xFFD9A5B3),
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(10.dp)
                                .size(24.dp)
                                .clickable { save() }
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Заголовок списка
                    Text(
                        text = "Все комплименты:",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF3E2A32)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Список комплиментов
                    if (notes.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Пока нет комплиментов...\nДобавь первый! 💗",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color(0xFF5C4451),
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(bottom = 24.dp)
                        ) {
                            items(notes, key = { it.id }) { note ->
                                NoteItem(
                                    text = note.text ?: "Без текста",
                                    onClick = { viewModel.onNoteClicked(note.id) },
                                    onEditClick = { viewModel.onNoteClicked(note.id) },
                                    onDeleteClick = {
                                        viewModel.deleteNote(note.id)
                                        scope.launch {
                                            val result = snackbarHostState.showSnackbar(
                                                message = "Комплимент удалён",
                                                actionLabel = "Вернуть"
                                            )
                                            if (result == SnackbarResult.ActionPerformed) {
                                                viewModel.restoreNote(note)
                                            }
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }

        }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(8.dp)
        )
    }
}
