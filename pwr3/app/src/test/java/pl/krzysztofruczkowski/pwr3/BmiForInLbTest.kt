package pl.krzysztofruczkowski.pwr3

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.doubles.plusOrMinus
import io.kotest.matchers.shouldBe

class BmiForInLbTest : FunSpec({
    test("Bmi should be calculated correctly") {
        BmiForInLb(68.0, 182.0).count() shouldBe(1.44 plusOrMinus 0.01)
        BmiForInLb(52.0, 163.0).count() shouldBe(1.37 plusOrMinus 0.01)
    }

    test("Zero height should return BMI equal to zero") {
        BmiForInLb(68.0, 0.0).count() shouldBe 0
    }
})
