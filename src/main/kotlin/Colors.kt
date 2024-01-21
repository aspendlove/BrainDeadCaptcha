import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlin.random.Random

class Colors : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val correctRed = Random.nextInt(256)
        val correctGreen = Random.nextInt(256)
        val correctBlue = Random.nextInt(256)
        val correctColor = Color(correctRed, correctGreen, correctBlue)

        fun getRandomColor(r: Int, g: Int, b: Int): Color {
            val rAdjustment = Random.nextInt(2, 8)
            val rAdjDirection = Random.nextBoolean()
            val gAdjustment = Random.nextInt(2, 8)
            val gAdjDirection = Random.nextBoolean()
            val bAdjustment = Random.nextInt(2, 8)
            val bAdjDirection = Random.nextBoolean()

            val adjR = r + rAdjustment * if(rAdjDirection) {1} else {-1}
            val adjG = g + gAdjustment * if(gAdjDirection) {1} else {-1}
            val adjB = b + bAdjustment * if(bAdjDirection) {1} else {-1}
            return Color(adjR, adjG, adjB)
        }

        val colors: List<Color> = listOf(
            getRandomColor(correctRed, correctGreen, correctBlue),
            getRandomColor(correctRed, correctGreen, correctBlue),
            getRandomColor(correctRed, correctGreen, correctBlue),
            getRandomColor(correctRed, correctGreen, correctBlue),
            correctColor
        ).shuffled()

        // bard made this function because I'm lazy
        fun rgbToHex(red: Int, green: Int, blue: Int): String {
            // Ensure each component is within the valid range (0-255)
            val clampedRed = red.coerceIn(0, 255)
            val clampedGreen = green.coerceIn(0, 255)
            val clampedBlue = blue.coerceIn(0, 255)

            // Format each component as a two-digit hex string with leading zeros
            val hexRed = clampedRed.toString(16).padStart(2, '0').toUpperCase(Locale.current)
            val hexGreen = clampedGreen.toString(16).padStart(2, '0').toUpperCase(Locale.current)
            val hexBlue = clampedBlue.toString(16).padStart(2, '0').toUpperCase(Locale.current)

            // Combine the hex components and prepend the hash symbol
            return "#$hexRed$hexGreen$hexBlue"
        }


        Row(Modifier.fillMaxSize()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f).fillMaxHeight()
            ) {
                Text(
                    """Choose the box with color ${rgbToHex(correctRed, correctGreen, correctBlue)}
                       |(if you fail you should go back to kindergarten)""".trimMargin()
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f).fillMaxHeight()
            ) {
                for (i in colors.indices) {
                    if(colors[i] == correctColor) {
                        println(i + 1)
                    }
                    Button(
                        colors = ButtonDefaults.buttonColors(contentColor = colors[i], backgroundColor = colors[i]),
                        modifier = Modifier.width(150.dp),
                        onClick = {
                            if (colors[i] == correctColor) {
                                navigator.push(Win())
                            } else {
                                navigator.push(Lose("It's just colors bro"))
                            }
                        }) {}
                }
            }
        }
    }
}