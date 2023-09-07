package pastukh.vova.baseui.utils

import pastukh.vova.baseui.entity.RecipeEntity
import pastukh.vova.baseui.entity.RecipeState
import pastukh.vova.baseui.entity.RecipeStepEntity
import pastukh.vova.data.server.entity.RecipeDTO
import pastukh.vova.data.server.entity.RecipeStepDTO

// DTO mappers
fun RecipeDTO.toEntity(isStored: Boolean = false) =
    RecipeEntity(
        id,
        recipe_title,
        recipe_img,
        recipe_country,
        recipe_description,
        steps.map { it.toEntity() },
        if (isStored) RecipeState.DOWNLOADED else RecipeState.DEFAULT
    )

fun RecipeStepDTO.toEntity() =
    RecipeStepEntity(
        recipe_step_title,
        recipe_step_instruction,
        (recipe_step_duration_millis / 1000).toDouble()
    )

