package pastukh.vova.data.server.entity

data class RecipeStepDTO(
    val title: String,
    val instructions: String,
    val durationInMillis: Long,
)