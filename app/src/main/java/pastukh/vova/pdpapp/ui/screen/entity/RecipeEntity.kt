package pastukh.vova.pdpapp.ui.screen.entity

data class RecipeEntity(
    val id: String,
    val title: String,
    val image: String,
    val country: String,
    val description: String,
    val steps: List<RecipeStepEntity>,
    val state: RecipeState = RecipeState.DEFAULT
) {
    val stepsAmount: Int
        get() = steps.size
    val durationInSec: Double
        get() = steps.sumOf { it.durationInSec }
}

fun RecipeEntity.downloading() =
    RecipeEntity(id, title, image, country, description, steps, RecipeState.DOWNLOADING)
fun RecipeEntity.downloaded() =
    RecipeEntity(id, title, image, country, description, steps, RecipeState.DOWNLOADED)


enum class RecipeState{
    DOWNLOADING,
    DOWNLOADED,
    DEFAULT;
}