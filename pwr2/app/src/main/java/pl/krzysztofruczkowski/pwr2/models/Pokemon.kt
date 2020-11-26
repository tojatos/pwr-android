package pl.krzysztofruczkowski.pwr2.models

data class Pokemon(
    val name: String,
    val description: String = "",
    val category: PokeCategory = PokeCategory.None,
    val enemies: List<String> = emptyList(),
)

enum class PokeCategory {
    None,
    Lightning,
    Seed,
    Mouse,
    Flame,
}
