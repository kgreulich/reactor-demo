package kg.reactordemo

import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import java.time.Duration
import kotlin.random.Random

internal class ExampleTest {

    @Test
    fun problem() {
        calculationMono()
            .doOnError { e -> println(e) }
            .retry(50)
            .subscribe{ println(it) }
    }

    private fun calculation(): Int {
        val number = Random.nextInt(2)
        return try {
            10 / number
        } catch (e: ArithmeticException) {
            println("I might got $number as random number")
            throw e
        }
    }

    private fun calculationMono(): Mono<Int> {

        return Mono.fromCallable { Random.nextInt(2) }
            .map { 10 / it }


        // rechte vorhanden
        //
        // wenn rechte vorhanden
        // dann user save
    }

    @Test
    fun blockhound() {
        Mono.delay(Duration.ofSeconds(1))
            .doOnNext { _: Long? ->
                try {
                    Thread.sleep(10)
                } catch (e: InterruptedException) {
                    throw RuntimeException(e)
                }
            }
            .block()
    }
}