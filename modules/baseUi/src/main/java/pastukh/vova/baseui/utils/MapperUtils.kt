package pastukh.vova.baseui.utils

import pastukh.vova.baseui.entity.RecipeEntity
import pastukh.vova.baseui.entity.RecipeState
import pastukh.vova.baseui.entity.RecipeStepEntity

// DTO mappers
fun pastukh.vova.data.server.entity.RecipeDTO.toEntity(isStored: Boolean = false) =
    RecipeEntity(
        id,
        title,
        image,
        country,
        description,
        steps.map { it.toEntity() },
        if (isStored) RecipeState.DOWNLOADED else RecipeState.DEFAULT
    )

fun pastukh.vova.data.server.entity.RecipeStepDTO.toEntity() =
    RecipeStepEntity(title, instructions, (durationInMillis / 1000).toDouble())

