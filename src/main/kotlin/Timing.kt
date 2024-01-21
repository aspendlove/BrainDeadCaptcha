import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.delay
import java.time.Instant
import kotlin.random.Random

class Timing : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        var boxTime = 0L
        var spaceTime = 0L
        val requester = remember { FocusRequester() }
        val showBox = remember { mutableStateOf(false) }
        val textMeasurer = rememberTextMeasurer()


        Box(modifier = Modifier.onKeyEvent { event: KeyEvent ->
            if (event.key == Key.Spacebar && event.type == KeyEventType.KeyDown) {
                spaceTime = Instant.now().toEpochMilli()
                val reactionTime: Long = spaceTime - boxTime
                if (reactionTime in 0..300) {
                    navigator.push(Notes())
                } else {
                    navigator.push(Lose("You have terrible reaction times"))
                }
                return@onKeyEvent true
            }
            return@onKeyEvent false
        }.focusRequester(requester).focusable()) {
            Canvas(modifier = Modifier.background(Color.Black).fillMaxSize()) {
                val measuredText =
                    textMeasurer.measure(
                        AnnotatedString("SPACE"),
                        constraints = Constraints.fixedWidth((size.width * 2f / 3f).toInt()),
                        style = TextStyle(fontSize = 150.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    )


                if (showBox.value) {
                    drawText(
                        measuredText,
                        topLeft = Offset(
                            center.x - (measuredText.size.width / 2),
                            center.y - (measuredText.size.height / 2)
                        )
                    )
                }
            }
        }
        LaunchedEffect(Unit) {
            requester.requestFocus()
            delay(Random.nextLong(1000, 5000))
            showBox.value = true
            boxTime = Instant.now().toEpochMilli()
            delay(20)
            showBox.value = false
            delay(1000)
            navigator.push(Lose("Just hit space, smh"))
        }
    }
}