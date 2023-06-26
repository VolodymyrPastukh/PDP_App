package pastukh.vova.appcompose.ui.components.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

object Colors {
    val primaryL = Color(0xFFF56416)
    val secondaryL = Color(0xFFE28413)
    val primaryD = Color(0xFFFFE69C)
    val secondaryD = Color(0xFF2B4141)
    val primaryContainerL = Color(0xFFEFF9F0)
    val primaryContainerD = Color(0xFFC9B371)
    val secondaryContainerL = Color(0xFFA29B8F)
    val secondaryContainerD = Color(0xFF979294)
    val backgroundL = Color(0xFFFFFFFF)
    val backgroundD = Color(0xFF000000)

    val error = Color(0xFFF44336)
    val transparent = Color(0x00000000)
}

val LightColorScheme = lightColorScheme(
    background = Colors.backgroundL,
    primary = Colors.primaryL,
    secondary = Colors.secondaryL,
    primaryContainer = Colors.primaryContainerL,
    secondaryContainer = Colors.secondaryContainerL,
    error = Colors.error
)

val DarkColorScheme = darkColorScheme(
    background = Colors.backgroundD,
    primary = Colors.primaryD,
    secondary = Colors.secondaryD,
    primaryContainer = Colors.primaryContainerD,
    secondaryContainer = Colors.secondaryContainerD,
    error = Colors.error
)
