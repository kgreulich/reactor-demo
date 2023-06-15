package kg.reactordemo.invoice

import kg.reactordemo.AbstractIntegrationTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.junit.jupiter.Testcontainers
import reactor.test.StepVerifier
import java.time.LocalDate

@Testcontainers
@SpringBootTest
internal class InvoiceServiceTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var repository: InvoiceRepository

    @Autowired
    private lateinit var service: InvoiceService

    companion object {
        val INVOICE = Invoice(
            1, 3, "biller", "recipient", LocalDate.of(2023, 1, 1)
        )
    }

    @AfterEach
    fun afterEach() {
        StepVerifier.create(repository.deleteAll())
            .verifyComplete()
    }

    @Test
    fun `should save invoice`() {
        StepVerifier.create(service.save(INVOICE))
            .expectNext(INVOICE)
            .verifyComplete()

        StepVerifier.create(repository.findAll())
            .expectNext(INVOICE)
            .verifyComplete()
    }


    @Test
    fun `should get invoice`() {
        StepVerifier.create(repository.save(INVOICE))
            .expectNext(INVOICE)
            .verifyComplete()

        StepVerifier.create(service.get())
            .expectNext(INVOICE)
            .verifyComplete()
    }

    @Test
    fun `should get invoice by recipient`() {
        val searchedRecipient = "searchedRecipient"

        val invoices = listOf(
            INVOICE, INVOICE.copy(id = 2, recipient = searchedRecipient)
        )

        StepVerifier.create(repository.saveAll(invoices))
            .expectNextCount(2)
            .verifyComplete()

        StepVerifier.create(service.getByRecipient(searchedRecipient))
            .expectNext(invoices[1])
            .verifyComplete()
    }

    @Test
    fun `should get invoice by id`() {
        val invoices = listOf(
            INVOICE, INVOICE.copy(25, value = 99)
        )

        StepVerifier.create(repository.saveAll(invoices)).expectNextCount(2).verifyComplete()

        StepVerifier.create(service.get(invoices[1].id)).expectNext(invoices[1]).verifyComplete()
    }

    @Test
    fun `should get value by recipient`() {
        StepVerifier.create(
            repository.saveAll(
                listOf(
                    INVOICE, INVOICE.copy(id = 2), INVOICE.copy(id = 3)
                )
            )
        ).expectNextCount(3).verifyComplete()

        StepVerifier.create(service.getValuesByRecipient(INVOICE.recipient))
            .expectNext(9)
            .verifyComplete()
    }
}
