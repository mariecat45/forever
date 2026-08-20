package com.example.forever.presentation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.forever.R
import com.example.forever.presentation.ui.components.HeartBackground

@Composable
fun WelcomeScreen(
    onStartClick: () -> Unit
) {
    HeartBackground(
        modifier = Modifier.clickable { onStartClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Добро пожаловать в Forever!",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Это приложение — твой личный дневник для комплиментов, тёплых слов и приятных воспоминаний. Оно создано, чтобы помочь тебе замечать хорошее, собирать в копилку приятные моменты, искренние комплименты и личные победы, а затем возвращаться к ним, когда нужна поддержка.",
                fontSize = 18.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )

        }
    }
}

// Preview для быстрого просмотра
@Preview(showBackground = true)
@Composable
fun PreviewWelcomeScreen() {
    WelcomeScreen(onStartClick = {})
}