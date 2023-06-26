package pastukh.vova.appcompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pastukh.vova.appcompose.ui.components.theme.AppDarkTheme
import pastukh.vova.appcompose.ui.components.theme.AppLightTheme
import pastukh.vova.appcompose.ui.components.theme.TextStyles

@Composable
fun ErrorView(
    modifier: Modifier = Modifier,
    message: String,
    actionPrimary: @Composable (Modifier) -> Unit,
    actionSecondary: @Composable (Modifier) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Text(
            text = message,
            style = TextStyles.Body2,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.error,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(top = 16.dp, end = 16.dp, start = 16.dp)
        ) {
            actionPrimary(Modifier.weight(1f))
            actionSecondary(
                Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            )
        }
    }
}

@Preview
@Composable
fun ErrorViewPreviewLight() {
    AppLightTheme {
        ErrorView(
            message = "Error message",
            actionPrimary = { mod ->
                Action(
                    title = "Action 1",
                    elevation = 8.dp,
                    modifier = mod
                ) {}
            },
            actionSecondary = { mod ->
                Action(
                    title = "Action2",
                    colorBackground = MaterialTheme.colorScheme.secondaryContainer,
                    colorBorder = MaterialTheme.colorScheme.secondary,
                    modifier = mod
                ) {}
            }
        )
    }
}

@Preview
@Composable
fun ErrorViewPreviewDark() {
    AppDarkTheme {
        ErrorView(
            message = "Error message",
            actionPrimary = { mod ->
                Action(
                    title = "Action 1",
                    elevation = 8.dp,
                    modifier = mod
                ) {}
            },
            actionSecondary = { mod ->
                Action(
                    title = "Action2",
                    colorBackground = MaterialTheme.colorScheme.secondaryContainer,
                    colorBorder = MaterialTheme.colorScheme.secondary,
                    modifier = mod
                ) {}
            }
        )
    }
}