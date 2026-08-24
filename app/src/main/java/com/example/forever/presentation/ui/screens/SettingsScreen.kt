package com.example.forever.presentation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.forever.R
import com.example.forever.presentation.ui.components.HeartBackground
import com.example.forever.presentation.viewmodel.MainViewModel

@Composable
fun SettingsScreen(
    viewModel: MainViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val userName by viewModel.userName.collectAsState()
    var editingName by remember { mutableStateOf(userName) }

    HeartBackground {
        Column(
            modifier = modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 24.dp)
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Назад",
                    tint = colorResource(id = R.color.pink_heart)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Настройки",
                style = MaterialTheme.typography.headlineMedium,
                color = colorResource(id = R.color.text_color),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Ваше имя:",
                style = MaterialTheme.typography.bodyLarge,
                color = colorResource(id = R.color.text_color)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = editingName,
                onValueChange = { editingName = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = colorResource(id = R.color.pink_background),
                    focusedContainerColor = colorResource(id = R.color.pink_background),
                    unfocusedBorderColor = colorResource(id = R.color.pink_heart),
                    focusedBorderColor = colorResource(id = R.color.pink_heart)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Сохранить",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .clickable {
                        viewModel.updateUserName(editingName)
                        onBackClick()
                    }
                    .padding(8.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}