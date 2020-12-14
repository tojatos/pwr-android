package pl.krzysztofruczkowski.pwr3

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.doubles.plusOrMinus
import io.kotest.matchers.shouldBe

class BmiForCmKgTest : FunSpec({
    test("Bmi should be calculated correctly") {
        BmiForCmKg(68.0, 182.0).count() shouldBe(20.52 plusOrMinus 0.01)
        BmiForCmKg(52.0, 163.0).count() shouldBe(19.57 plusOrMinus 0.01)
    }

    test("Zero height should return BMI equal to zero") {
        BmiForCmKg(68.0, 0.0).count() shouldBe 0
    }
})
