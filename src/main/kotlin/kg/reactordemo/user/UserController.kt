package kg.reactordemo.user

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/user")
class UserController(private val service: UserService) {

    @GetMapping("/{id}")
    fun get(@PathVariable id: Int): User {
        return service.get(id)
    }

    @GetMapping
    fun get(): MutableList<User> {
        return service.get()
    }
}