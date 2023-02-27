import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.themes.BgColor
import ui.themes.BtnBGColor
import ui.themes.ContentColor

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Logout") }

    MaterialTheme {
        TopAppBar(modifier = Modifier.fillMaxWidth(), backgroundColor = BgColor){(Modifier.padding(Dp(5.0F)))
            Button( colors = ButtonDefaults.buttonColors(
                BtnBGColor,
                ContentColor
            ), onClick = {
                text = "Logout"
            }) {
                Text(text)
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, transparent = false) {
        App()
    }
}
