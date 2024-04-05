import com.dicegame.utils.BetRandomNumberGenerator
import com.dicegame.utils.RandomNumberGenerator
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class BetRandomNumberGeneratorTest {

    private val randomNumberGenerator: RandomNumberGenerator = BetRandomNumberGenerator()

    @Test
    fun `nextInt returns a number within the specified range`() {
        val from = 1
        val until = 10

        repeat(100) {
            val result = randomNumberGenerator.nextInt(from, until)

            assertTrue(result >= from && result < until, "The result $result is not within the range [$from, $until)")
        }
    }

}
