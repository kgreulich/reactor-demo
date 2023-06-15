package kg.reactordemo.invoice

import org.springframework.data.annotation.Id
import java.time.LocalDate

data class Invoice(
    @Id var id: Int,
    var value: Int,
    var biller: String,
    var recipient: String,
    var date: LocalDate
)
