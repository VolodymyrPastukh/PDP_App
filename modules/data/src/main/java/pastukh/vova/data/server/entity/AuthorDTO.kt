package pastukh.vova.data.server.entity

data class AuthorDTO(
    val id: Int,
    val author_name: String,
    val author_email: String,
    val author_password: String,
    val author_description: String,
    val author_img: String?,
)