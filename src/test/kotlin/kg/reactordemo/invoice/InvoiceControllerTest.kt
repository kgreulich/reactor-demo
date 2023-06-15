package kg.reactordemo.invoice

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mockito.mock
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.LocalDate

internal class InvoiceControllerTest {

    companion object {
        val INVOICE = Invoice(
            1, 3, "biller", "recipient", LocalDate.of(2023, 1, 1)
        )
    }

    @AfterEach
    fun afterEach() {
        then(service).shouldHaveNoMoreInteractions()
    }

    private val service = mock(InvoiceService::class.java)
    private val controller = InvoiceController(service)

    @Test
    fun `should return invoices`() {
        given(service.get()).willReturn(Flux.fromIterable(listOf(INVOICE)))

        StepVerifier.create(controller.get())
            .expectNext(INVOICE)
            .verifyComplete()

        then(service).should().get()
    }

    @Test
    fun `should return invoice by id`() {
        given(service.get(INVOICE.id)).willReturn(Mono.just(INVOICE))

        StepVerifier.create(controller.get(INVOICE.id))
            .expectNext(INVOICE)
            .verifyComplete()

        then(service).should().get(INVOICE.id)
    }

    @Test
    fun `should save invoice`() {
        given(service.save(INVOICE)).willReturn(Mono.just(INVOICE))

        StepVerifier.create(controller.save(INVOICE))
            .expectNext(INVOICE)
            .verifyComplete()

        then(service).should().save(INVOICE)
    }

    @Test
    fun `should get invoice by recipient`() {
        val expectedInvoice = INVOICE.copy(recipient = "expectedRecipient")
        given(service.getByRecipient(expectedInvoice.recipient)).willReturn(Flux.just(expectedInvoice))

        StepVerifier.create(controller.getByRecipient(expectedInvoice.recipient))
            .expectNext(expectedInvoice)
            .verifyComplete()

        then(service).should().getByRecipient(expectedInvoice.recipient)
    }

    @Test
    fun `should return empty Flux when no invoices found`() {
        given(service.get()).willReturn(Flux.empty())

        StepVerifier.create(controller.get())
            .verifyComplete()

        then(service).should().get()
    }

    @Test
    fun `should return empty Flux when no invoice by recipient found`() {
        given(service.getByRecipient(INVOICE.recipient)).willReturn(Flux.empty())

        StepVerifier.create(controller.getByRecipient(INVOICE.recipient))
            .verifyComplete()

        then(service).should().getByRecipient(INVOICE.recipient)
    }
}
