package com.example.forever.presentation.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun HeartProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = Color(0xFFD9A5B3),
    trackColor: Color = Color(0x40D9A5B3),
    strokeWidth: Dp = 5.dp
) {
    // Бесконечная анимация: progress бежит от 0 до 1
    val transition = rememberInfiniteTransition(label = "heart")
    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1600, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "progress"
    )

    Canvas(modifier = modifier) {
        // Контур сердца (кривые Безье), масштабируется под размер
        val scale = size.minDimension / 100f
        val heart = Path().apply {
            moveTo(50f * scale, 92f * scale)                      // нижний кончик
            cubicTo(20f * scale, 66f * scale, 4f * scale, 46f * scale, 4f * scale, 28f * scale)
            cubicTo(4f * scale, 12f * scale, 16f * scale, 4f * scale, 28f * scale, 4f * scale)
            cubicTo(38f * scale, 4f * scale, 46f * scale, 10f * scale, 50f * scale, 20f * scale)
            cubicTo(54f * scale, 10f * scale, 62f * scale, 4f * scale, 72f * scale, 4f * scale)
            cubicTo(84f * scale, 4f * scale, 96f * scale, 12f * scale, 96f * scale, 28f * scale)
            cubicTo(96f * scale, 46f * scale, 80f * scale, 66f * scale, 50f * scale, 92f * scale)
        }

        val stroke = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)

        // 1. Бледный контур — "дорожка" (как трек у кругового индикатора)
        drawPath(path = heart, color = trackColor, style = stroke)

        // 2. Яркий бегущий сегмент по контуру
        val measure = PathMeasure()
        measure.setPath(heart, forceClosed = false)
        val length = measure.length
        val runnerLength = length * 0.3f          // длина "бегунка" (30% контура)
        val start = progress * length
        val end = start + runnerLength

        if (end <= length) {
            val segment = Path()
            measure.getSegment(start, end, segment, true)
            drawPath(path = segment, color = color, style = stroke)
        } else {
            // Сегмент переходит через начало контура — рисуем двумя частями
            val part1 = Path()
            val part2 = Path()
            measure.getSegment(start, length, part1, true)
            measure.getSegment(0f, end - length, part2, true)
            drawPath(path = part1, color = color, style = stroke)
            drawPath(path = part2, color = color, style = stroke)
        }
    }
}