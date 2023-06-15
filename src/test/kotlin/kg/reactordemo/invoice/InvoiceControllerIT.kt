package kg.reactordemo.invoice

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDate

@SpringBootTest
@AutoConfigureMockMvc
class InvoiceControllerIT {

    companion object {
        val INVOICE = Invoice(
            1, 3, "biller", "recipient", LocalDate.of(2023, 1, 1)
        )
    }

    @Autowired
    private lateinit var mockMvc: MockMvc;

    @MockBean
    private lateinit var repository: InvoiceRepository

    @AfterEach
    fun afterEach() {
        then(repository).shouldHaveNoMoreInteractions()
    }

    @Test
    fun `should get invoice`() {
        given(repository.findAll()).willReturn(Flux.fromIterable(listOf(INVOICE)))

        mockMvc.get("/invoice")
            .asyncDispatch()
            .andExpect {
                content {
                    jsonPath("$[0].id") { value(INVOICE.id)}
                    jsonPath("$[0].value") { value(INVOICE.value) }
                    jsonPath("$[0].biller") { value(INVOICE.biller) }
                    jsonPath("$[0].recipient") { value(INVOICE.recipient) }
                    jsonPath("$[0].date") { value(INVOICE.date.toString()) }
                }
            }

        then(repository).should().findAll()
    }

    @Test
    fun `should get invoice by id`() {
        given(repository.findById(INVOICE.id)).willReturn(Mono.just(INVOICE))

        mockMvc.get("/invoice/" + INVOICE.id)
            .asyncDispatch()
            .andExpect {
                content {
                    jsonPath("$.id") { value(INVOICE.id)}
                    jsonPath("$.value") { value(INVOICE.value) }
                    jsonPath("$.biller") { value(INVOICE.biller) }
                    jsonPath("$.recipient") { value(INVOICE.recipient) }
                    jsonPath("$.date") { value(INVOICE.date.toString()) }
                }
            }

        then(repository).should().findById(INVOICE.id)
    }


}