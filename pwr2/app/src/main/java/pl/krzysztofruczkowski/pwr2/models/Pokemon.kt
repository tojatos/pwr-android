package pl.krzysztofruczkowski.pwr2.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class Pokemon(
    val name: String,
    val description: String = "",
    val category: PokeCategory = PokeCategory.None,
    val enemies: List<String> = emptyList(),
    var favourite: Boolean = false,
)
{
    companion object {
        fun serialize(pokemon: Pokemon) = Json.encodeToString(pokemon)
        fun deserialize(pokemonString: String) = Json.decodeFromString<Pokemon>(pokemonString)
    }
}

enum class PokeCategory {
    None,
    Lightning,
    Seed,
    Mouse,
    Flame,
}
