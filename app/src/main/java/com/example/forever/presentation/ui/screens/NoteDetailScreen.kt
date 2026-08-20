package com.example.forever.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.forever.R
import com.example.forever.presentation.ui.components.HeartBackground
import com.example.forever.presentation.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun NoteDetailScreen(
    viewModel: MainViewModel,
    noteId: Long,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val notes by viewModel.notes.collectAsState()
    val note = notes.find { it.id == noteId }

    // ← Редактируемый текст, изначально = текст заметки
    var text by remember(noteId) { mutableStateOf(note?.text ?: "") }

    HeartBackground {
        Column(
            modifier = modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 16.dp)
        ) {
            // Шапка: назад + дата + галочка сохранения
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Назад",
                        tint = colorResource(id = R.color.text_color)
                    )
                }

                Text(
                    text = "Запись от ${note?.let { formatNoteDate(it.createdAt) } ?: "..."}",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Medium,
                    color = colorResource(id = R.color.text_color),
                    modifier = Modifier.weight(1f)
                )

                // ← Галочка: сохранить изменения и вернуться
                IconButton(onClick = {
                    note?.let { viewModel.updateNote(it, text) }
                    onBackClick()
                }) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Сохранить",
                        tint = colorResource(id = R.color.pink_heart)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Карточка с редактируемым текстом
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(id = R.color.white)
                ),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier.fillMaxSize(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    textStyle = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

private fun formatNoteDate(timestamp: Long): String {
    val formatter = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
    return formatter.format(Date(timestamp))
}