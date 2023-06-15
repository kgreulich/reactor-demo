package kg.reactordemo.invoice

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/invoice", produces = [MediaType.APPLICATION_JSON_VALUE])
class InvoiceController(private val service: InvoiceService) {

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun get(@PathVariable id: Int): Mono<Invoice> {
        return service.get(id)
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun get(): Flux<Invoice> {
        return service.get()
    }

    @PutMapping
    fun save(@RequestBody invoice: Invoice): Mono<Invoice> {
        return service.save(invoice)
    }

    @GetMapping("/recipient/{recipient}")
    fun getByRecipient(@PathVariable recipient: String): Flux<Invoice> {
       return service.getByRecipient(recipient)
    }

}