package com.example.forever.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.forever.R
import com.example.forever.presentation.ui.components.HeartBackground

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    HeartBackground {
        Box(
            modifier = modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Название приложения
                Text(
                    text = "Forever", // Замените на ваше название
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.text_color)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Индикатор загрузки
                LinearProgressIndicator(
                    modifier = Modifier
                        .width(200.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    color = colorResource(id = R.color.pink_heart),
                    trackColor = colorResource(id = R.color.pink_background)
                )
            }
        }
    }
}