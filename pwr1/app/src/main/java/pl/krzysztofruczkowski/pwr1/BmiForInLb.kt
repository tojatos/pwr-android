package pl.krzysztofruczkowski.pwr1

class BmiForInLb(
    private val mass: Double,
    private val height: Double
) : Bmi {
    override fun count(): Double =
        mass / (height * height) * 703
}
