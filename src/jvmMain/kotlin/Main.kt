import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.themes.BgColor
import ui.themes.BtnBGColor
import ui.themes.ContentColor

@OptIn(ExperimentalUnitApi::class)
@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Logout") }
    val title by remember { mutableStateOf("Home") }

    MaterialTheme {
        //
        TopAppBar(modifier = Modifier.fillMaxWidth(), backgroundColor = BgColor){
            Row (modifier = Modifier.fillMaxWidth().padding(start = Dp(20.0F), end = Dp(20.0F)), horizontalArrangement = Arrangement.SpaceEvenly,verticalAlignment = Alignment.CenterVertically){
                ///padding(start = Dp(10.0F))
                Box(modifier = Modifier.fillMaxWidth().weight(1.0F)){
                    Text(title,color = ContentColor,
                        fontSize = TextUnit(value = 28F, type = TextUnitType.Sp), textAlign = TextAlign.Center)
                }
                Box( modifier = Modifier.fillMaxWidth().weight(1.0F), contentAlignment = Alignment.CenterEnd){
                    Button(colors = ButtonDefaults.buttonColors(
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
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, transparent = false, title = "Local Chat App") {
        App()
    }
}
