package kg.reactordemo.user

import org.springframework.data.annotation.Id


data class User(
    @Id var id: Int,
    var name: String
)
