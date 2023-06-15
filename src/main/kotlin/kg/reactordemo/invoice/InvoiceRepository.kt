package kg.reactordemo.invoice

import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface InvoiceRepository : ReactiveMongoRepository<Invoice, Int>