package com.example.forever

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.res.colorResource
import com.example.forever.ui.theme.AppTypography
import com.example.forever.ui.theme.ForeverTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ForeverTheme {
                Surface(modifier = androidx.compose.ui.Modifier.fillMaxSize()) {
                    WelcomeScreen()
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun WelcomeScreen() {
    ForeverTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = colorResource(id = R.color.pink_background) // Розовый фон
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                // Большое сердце справа
                Image(
                    painter = painterResource(id = R.drawable.ic_heart),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(colorResource(id = R.color.pink_heart)),
                    modifier = Modifier
                        .size(400.dp)
                        .align(Alignment.CenterEnd)
                        .offset(x = (100).dp, y = (-20).dp)
                )

                // Сердце слева сверху
                Image(
                    painter = painterResource(id = R.drawable.ic_heart),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(colorResource(id = R.color.pink_heart)),
                    modifier = Modifier
                        .size(150.dp)
                        .align(Alignment.TopStart)
                        .offset(x = (-30).dp, y = 50.dp)
                )

                // Сердце снизу
                Image(
                    painter = painterResource(id = R.drawable.ic_heart),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(colorResource(id = R.color.pink_heart)),
                    modifier = Modifier
                        .size(250.dp)
                        .align(Alignment.BottomStart)
                        .offset(x = (-50).dp, y = (-50).dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Текст "Приветствие"
                    Text(
                        text = "Добро пожаловать в Forever!",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    // Текст объяснения
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
    }
}