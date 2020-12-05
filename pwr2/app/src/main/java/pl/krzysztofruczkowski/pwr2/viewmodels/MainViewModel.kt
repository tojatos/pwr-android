package pl.krzysztofruczkowski.pwr2.viewmodels
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import pl.krzysztofruczkowski.pwr2.R
import pl.krzysztofruczkowski.pwr2.getJsonDataFromAsset
import pl.krzysztofruczkowski.pwr2.models.PokeCategory
import pl.krzysztofruczkowski.pwr2.models.Pokemon

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _pokemons = MutableLiveData<ArrayList<Pokemon>>()
    val pokemons: LiveData<ArrayList<Pokemon>>
        get() = _pokemons

    private val _selectedPokemon = MutableLiveData<Pokemon>()
    val selectedPokemon: LiveData<Pokemon>
        get() = _selectedPokemon

    private val _selectedCategory  = MutableLiveData<PokeCategory>()
    val selectedCategory: LiveData<PokeCategory>
        get() = _selectedCategory

    private val _filterByFavourite = MutableLiveData<Boolean>()
    val filterByFavourite: LiveData<Boolean>
        get() = _filterByFavourite

    val filteredPokemons: List<Pokemon>
        get() {
            var ps = _pokemons.value as List<Pokemon>

            // filter by fav
            if(_filterByFavourite.value == true)
                ps = ps.filter { p -> p.favourite }

            // filter by category
            ps =  when (_selectedCategory.value) {
                PokeCategory.None -> ps
                else -> ps.filter { p -> p.category == _selectedCategory.value }
            }

            return ps
        }

    fun selectPokemon(position: Int) {
        _selectedPokemon.value = filteredPokemons[position]
    }

    fun onPokemonSwipe(position: Int) {
        _pokemons.value?.remove(filteredPokemons[position])
        _pokemons.value = _pokemons.value // notify observers
    }

    fun onPokemonFavourite(position: Int) {
        val favPokemon = filteredPokemons[position]
        val index = _pokemons.value!!.indexOf(favPokemon)
        _pokemons.value!![index].favourite = !favPokemon.favourite
        _pokemons.value = _pokemons.value // notify observers
    }

    fun onCategorySelect(category: PokeCategory) {
        _selectedCategory.value = category
    }

    fun onFavouriteFilterClick() {
        _filterByFavourite.value = !_filterByFavourite.value!!
    }

    init {
        val context = getApplication<Application>().applicationContext
        val jsonData = getJsonDataFromAsset(context, context.resources.getString(R.string.pokemonsFileName))
        _pokemons.value = Json.decodeFromString<ArrayList<Pokemon>>(jsonData!!)
        _selectedPokemon.value = Pokemon("temp")
        _selectedCategory.value = PokeCategory.None
        _filterByFavourite.value = false
    }
}
