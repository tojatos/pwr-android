package pl.krzysztofruczkowski.pwr1

class BmiForCmKg(
    private val mass: Double,
    private val height: Double
) : Bmi {
    override fun count(): Double =
        mass / (height * height / 10000)
}
