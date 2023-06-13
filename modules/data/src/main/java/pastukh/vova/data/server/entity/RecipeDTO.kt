package pastukh.vova.data.server.entity


data class RecipeDTO(
    val id: String,
    val title: String,
    val country: String,
    val image: String,
    val description: String,
    val steps: List<RecipeStepDTO>
)

