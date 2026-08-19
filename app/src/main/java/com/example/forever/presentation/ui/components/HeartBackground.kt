package com.example.forever.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.WindowInsets
import com.example.forever.R

@Composable
fun HeartBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier.then(Modifier.fillMaxSize()),
        color = colorResource(id = R.color.pink_background),
        contentWindowInsets = WindowInsets(0)
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
                    .offset(x = 100.dp, y = (-20).dp)
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
                    .size(500.dp)
                    .align(Alignment.BottomStart)
                    .offset(x = (-100).dp, y = 170.dp)
            )

            // Содержимое экрана
            content()
        }
    }
}