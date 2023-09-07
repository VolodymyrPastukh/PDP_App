package pastukh.vova.data.server.entity

data class RecipeStepDTO(
    val id: Int,
    val recipe_id: Int,
    val recipe_step_id: Int?,
    val recipe_step_title: String,
    val recipe_step_instruction: String,
    val recipe_step_duration_millis: Long,
    val recipe_step_img: String?,
)