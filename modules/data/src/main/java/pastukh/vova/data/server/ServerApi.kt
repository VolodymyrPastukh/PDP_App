package pastukh.vova.data.server

import pastukh.vova.data.server.entity.AddRecipeDTO
import pastukh.vova.data.server.entity.AuthorDTO
import pastukh.vova.data.server.entity.RecipeDTO
import pastukh.vova.data.server.entity.SignInDTO
import pastukh.vova.data.server.entity.SignInResultDTO
import pastukh.vova.data.server.entity.SignUpDTO
import pastukh.vova.data.server.entity.base.ServerResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ServerApi {
    @GET(ServerConstants.EVENTS_ENDPOINT)
    suspend fun getEvents(): List<RecipeDTO>

    @POST(ServerConstants.EVENTS_ENDPOINT)
    suspend fun postEvent(@Body body: RecipeDTO): AddRecipeDTO

    @POST(ServerConstants.AUTH_SIGN_IN)
    suspend fun signIn(@Body body: SignInDTO): ServerResult<SignInResultDTO>

    @POST(ServerConstants.AUTH_SIGN_UP)
    suspend fun signUp(@Body body: SignUpDTO): ServerResult<AuthorDTO>

    @POST(ServerConstants.AUTHORS)
    suspend fun authors(): ServerResult<List<AuthorDTO>>

    @GET(ServerConstants.AUTHOR_ID)
    suspend fun authorById(@Path("id") id: Int): ServerResult<AuthorDTO>

    @POST(ServerConstants.RECIPES)
    suspend fun postRecipe(@Body body: RecipeDTO): ServerResult<RecipeDTO>

    @GET(ServerConstants.RECIPES)
    suspend fun recipes(): ServerResult<List<RecipeDTO>>

    @GET(ServerConstants.RECIPE_ID)
    suspend fun recipeById(@Path("id") id: Int): ServerResult<RecipeDTO>
}