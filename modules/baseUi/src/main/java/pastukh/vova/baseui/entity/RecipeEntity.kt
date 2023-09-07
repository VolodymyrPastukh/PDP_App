package pastukh.vova.baseui.entity

data class RecipeEntity(
    val id: Int,
    val title: String,
    val image: String?,
    val country: String,
    val description: String,
    val steps: List<RecipeStepEntity>,
    val state: RecipeState = RecipeState.DEFAULT
) {
    companion object {
        val TEST = RecipeEntity(
            id = 0,
            title = "Holodec",
            image = "",
            country = "Europe",
            description = "Cherepovec, Cherepovec, Rosiya eto Holodec, pizdec...",
            steps = RecipeStepEntity.TEST_LIST,
            state = RecipeState.DEFAULT,
        )
        val TEST_LIST = listOf(
            TEST,
            TEST.copy(state = RecipeState.DOWNLOADED),
            TEST.copy(state = RecipeState.DOWNLOADING)
        )
    }

    val stepsAmount: Int
        get() = steps.size
    val durationInSec: Double
        get() = steps.sumOf { it.durationInSec }
}

fun RecipeEntity.downloading() =
    RecipeEntity(id, title, image, country, description, steps, RecipeState.DOWNLOADING)

fun RecipeEntity.downloaded() =
    RecipeEntity(id, title, image, country, description, steps, RecipeState.DOWNLOADED)


enum class RecipeState {
    DOWNLOADING,
    DOWNLOADED,
    DEFAULT;
}