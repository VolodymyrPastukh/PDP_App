package pastukh.vova.baseui.entity

data class RecipeStepEntity(
    val title: String,
    val description: String,
    val durationInSec: Double,
){
    companion object{
        val TEST = RecipeStepEntity(
            title = "Step 1",
            description = "Boil",
            durationInSec = 3600.0,
        )
        val TEST_LIST = listOf(TEST,TEST,TEST)
    }
}