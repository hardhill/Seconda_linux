import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.reflect.KProperty

@Composable
fun ClockDisplay() {
    var tmp = Time.getLatest()
    var time  = remember { mutableStateOf(tmp) }

    LaunchedEffect(true) {
        while (true) {
            time.value = Time.getLatest()
            delay(1000)
        }
    }

    val style = LedMatrixStyle(
        ledShape = LedShape.Round,
        ledWidth = 5.dp,
        ledHeight = 5.dp,
        ledSpacing = 0.dp,
        onColor = Color.Black,
        offColor = Color(0xFFEDEDED)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        //Hour
        LedMatrixDisplay(number = time.value.hour / 10, style = style)
        Spacer(modifier = Modifier.width(4.dp))
        LedMatrixDisplay(number = time.value.hour % 10, style)
        Spacer(modifier = Modifier.width(16.dp))

        //Minutes
        LedMatrixDisplay(number = time.value.minutes / 10, style = style)
        Spacer(modifier = Modifier.width(4.dp))
        LedMatrixDisplay(number = time.value.minutes % 10, style)
        Spacer(modifier = Modifier.width(16.dp))

        //Seconds
        LedMatrixDisplay(number = time.value.seconds / 10, style = style)
        Spacer(modifier = Modifier.width(4.dp))
        LedMatrixDisplay(number = time.value.seconds % 10, style)
        Spacer(modifier = Modifier.width(16.dp))
    }
}






