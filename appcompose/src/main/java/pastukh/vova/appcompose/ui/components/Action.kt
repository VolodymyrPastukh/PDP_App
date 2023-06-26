package pastukh.vova.appcompose.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pastukh.vova.appcompose.ui.components.theme.Colors
import pastukh.vova.appcompose.ui.components.theme.TextStyles
import pastukh.vova.appcompose.utils.conditional
import pastukh.vova.baseui.R

@Composable
fun Action(
    modifier: Modifier = Modifier,
    title: String? = null,
    icon: Painter? = null,
    colorBackground: Color = MaterialTheme.colorScheme.primaryContainer,
    colorContent: Color = MaterialTheme.colorScheme.primary,
    colorBorder: Color = Colors.transparent,
    elevation: Dp = 0.dp,
    loading: Boolean = false,
    onClick: () -> Unit,
) {
    return Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorBackground,
            contentColor = colorContent,
        ),
        contentPadding = when {
            icon != null && title != null -> PaddingValues(
                start = 12.dp,
                top = 12.dp,
                bottom = 12.dp,
                end = 24.dp
            )

            title != null -> PaddingValues(horizontal = 24.dp, vertical = 12.dp)
            else -> PaddingValues(12.dp)
        },
        shape = RoundedCornerShape(32.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = elevation),
        modifier = modifier
            .border(width = 1.dp, color = colorBorder, CircleShape)
//            .conditional(icon != null && title == null) { aspectRatio(1f) }
    ) {
        if (loading)
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
        else {
            icon?.let { painter -> Icon(painter, null, Modifier.size(32.dp)) }
            title?.let {
                Text(
                    text = title,
                    style = TextStyles.Sub1,
                    modifier = Modifier.conditional(icon != null) { padding(start = 8.dp) }
                )
            }
        }
    }
}

@Preview
@Composable
fun ActionTextPreview() {
    Action(title = "Action") {}
}

@Preview
@Composable
fun ActionIconPreview() {
    Action(
        icon = painterResource(id = R.drawable.ic_download),
        colorBorder = MaterialTheme.colorScheme.secondary,
        elevation = 8.dp,
        modifier = Modifier.size(50.dp)
    ) {}
}

@Preview
@Composable
fun ActionTextIconPreview() {
    Action(
        title = "Action",
        icon = painterResource(id = R.drawable.ic_download),
        colorBackground =  MaterialTheme.colorScheme.secondaryContainer,
        modifier = Modifier.size(160.dp, 60.dp)
    ) {}
}
