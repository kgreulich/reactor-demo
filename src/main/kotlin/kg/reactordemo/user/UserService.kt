package kg.reactordemo.user

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException

@Service
class UserService(private val repository: UserRepository) {

    fun get(): MutableList<User> {
        return repository.findAll()
    }

    fun get(id: Int): User {
        return repository.findById(id).orElseThrow { HttpClientErrorException(HttpStatus.NOT_FOUND) }
    }

}