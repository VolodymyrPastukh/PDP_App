package com.example.pdpapp.ui.utils

import com.example.pdpapp.data.server.entity.RecipeDTO
import com.example.pdpapp.data.server.entity.RecipeStepDTO
import com.example.pdpapp.ui.screen.entity.RecipeEntity
import com.example.pdpapp.ui.screen.entity.RecipeState
import com.example.pdpapp.ui.screen.entity.RecipeStepEntity

// DTO mappers
fun RecipeDTO.toEntity(isStored: Boolean = false) =
    RecipeEntity(
        id,
        title,
        image,
        country,
        description,
        steps.map { it.toEntity() },
        if (isStored) RecipeState.DOWNLOADED else RecipeState.DEFAULT
    )

fun RecipeStepDTO.toEntity() =
    RecipeStepEntity(title, instructions, (durationInMillis / 1000).toDouble())

