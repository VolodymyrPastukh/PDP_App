package pastukh.vova.appcompose.utils

import androidx.compose.ui.Modifier

fun Modifier.conditional(condition: Boolean, then: Modifier.() -> Modifier): Modifier {
    return if (condition) then(this) else this
}