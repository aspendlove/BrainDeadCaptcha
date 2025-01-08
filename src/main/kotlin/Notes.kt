import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.delay
import kotlin.random.Random

class Notes : Screen {
    @Composable
    override fun Content() {

        val note = Random.nextInt(60, 65)
        val navigator = LocalNavigator.currentOrThrow

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text("Choose the correct musical note", fontSize = 20.sp)
            for (i in 0..4) {
                if (note - 60 == i + 1) {
                    println(i + 1)
                }
                Button({
                    if (note - 60 == i + 1) {
                        navigator.push(Colors())
                    } else {
                        navigator.push(Lose("Guess you're tone deaf, I'll keep that in mind"))
                    }
                }) {
                    Text(('C' + i).toString())
                }
            }
        }
        LaunchedEffect(Unit) {
            delay(500)
            val noteMap = hashMapOf(
                60 to 60,
                61 to 62,
                62 to 64,
                63 to 65,
                64 to 67,
            )
            playNote(noteMap[note]!!)
        }
    }
}