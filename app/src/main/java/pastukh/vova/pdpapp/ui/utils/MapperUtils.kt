package pastukh.vova.pdpapp.ui.utils

import pastukh.vova.pdpapp.ui.screen.entity.RecipeEntity
import pastukh.vova.pdpapp.ui.screen.entity.RecipeState
import pastukh.vova.pdpapp.ui.screen.entity.RecipeStepEntity

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

