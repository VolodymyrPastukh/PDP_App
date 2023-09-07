package pastukh.vova.data.server.entity


data class RecipeDTO(
    val id: Int,
    val author_id: Int,
    val recipe_title: String,
    val recipe_country: String,
    val recipe_img: String?,
    val recipe_description: String,
    val steps: List<RecipeStepDTO>
)

