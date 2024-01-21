import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import cafe.adriel.voyager.navigator.Navigator

@Composable
@Preview
fun App() {
    Navigator(
        screen = Start()
    )
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Captcha from hell") {
        App()
    }
}
