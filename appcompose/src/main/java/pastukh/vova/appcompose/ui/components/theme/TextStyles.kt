package pastukh.vova.appcompose.ui.components.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import pastukh.vova.pdpapp.R


val poppinsFamily = FontFamily(
    Font(pastukh.vova.baseui.R.font.poppins, FontWeight.Normal),
)

object TextStyles {
    val Header1 =
        TextStyle.Default.copy(
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.W500,
            fontSize = 56.sp,
            lineHeight = 64.sp,
            letterSpacing = 0.sp,
        )
    val Header2 =
        TextStyle.Default.copy(
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.W500,
            fontSize = 44.sp,
            lineHeight = 52.sp,
            letterSpacing = 0.1.sp,
        )
    val Header3 =
        TextStyle.Default.copy(
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.W500,
            fontSize = 36.sp,
            lineHeight = 44.sp,
            letterSpacing = 0.2.sp,
        )

    val Body1 =
        TextStyle.Default.copy(
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.W500,
            fontSize = 32.sp,
            lineHeight = 40.sp,
            letterSpacing = 0.3.sp,
        )
    val Body2 =
        TextStyle.Default.copy(
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.W500,
            fontSize = 28.sp,
            lineHeight = 36.sp,
            letterSpacing = 0.3.sp,
        )
    val Body3 =
        TextStyle.Default.copy(
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.W500,
            fontSize = 24.sp,
            lineHeight = 36.sp,
            letterSpacing = 0.6.sp,
        )

    val Sub1 =
        TextStyle.Default.copy(
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.W500,
            fontSize = 22.sp,
            lineHeight = 28.sp,
            letterSpacing = 1.1.sp,
        )
    val Sub2 =
        TextStyle.Default.copy(
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.W500,
            fontSize = 20.sp,
            lineHeight = 26.sp,
            letterSpacing = 1.2.sp,
        )
    val Sub3 =
        TextStyle.Default.copy(
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.W500,
            fontSize = 18.sp,
            lineHeight = 24.sp,
            letterSpacing = 1.2.sp,
        )
}