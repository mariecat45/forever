package com.example.forever.presentation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.forever.R
import com.example.forever.presentation.ui.components.HeartBackground

@Composable
fun NameInputScreen(
    onNameSaved: (String) -> Unit
) {
    var name by remember { mutableStateOf("") }

    HeartBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Введи своё имя:",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Твоё имя") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    textAlign = TextAlign.Center,
                    color = Color.Black
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF2F0F1),
                    unfocusedContainerColor = Color(0xFFF2F0F1),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(36.dp))

            Text(
                text = "Готово!",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .clickable(enabled = name.isNotBlank()) {
                        onNameSaved(name)
                    }
                    .padding(8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNameInputScreen() {
    NameInputScreen(onNameSaved = {})
}