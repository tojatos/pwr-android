package pl.krzysztofruczkowski.pwr2.models

data class Pokemon(
    val name: String,
    val description: String = "",
    val category: PokeCategory = PokeCategory.None,
    val enemies: List<String> = emptyList(),
    var favourite: Boolean = false,
)

enum class PokeCategory {
    None,
    Lightning,
    Seed,
    Mouse,
    Flame,
}
