package kg.reactordemo.invoice

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.lang.Integer.sum

@Service
class InvoiceService(private val repository: InvoiceRepository) {

    fun get(id: Int): Mono<Invoice> {
        return repository.findById(id)
    }

    fun save(invoice: Invoice): Mono<Invoice> {
        return repository.save(invoice)
    }

    fun get(): Flux<Invoice> {
        return repository.findAll()
    }

    fun getByRecipient(recipient: String): Flux<Invoice> {
        return repository.findAll()
            .filter { it.recipient == recipient }
    }

    fun getValuesByRecipient(recipient: String): Mono<Int> {
        return repository.findAll()
            .filter { it.recipient == recipient }  // .filter(it -> it.recipient == recipient)
            .map { it.value } // .map( it -> it.value)
            .reduce { a, b -> sum(a, b) }
    }

}