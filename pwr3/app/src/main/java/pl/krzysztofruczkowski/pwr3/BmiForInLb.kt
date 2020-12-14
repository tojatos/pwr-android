package pl.krzysztofruczkowski.pwr3

class BmiForInLb(
    private val mass: Double,
    private val height: Double
) : Bmi {
    override fun count(): Double =
        if(height == 0.0) 0.0 else mass / (height * height) * 703
}
