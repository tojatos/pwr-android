package pl.krzysztofruczkowski.pwr4.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.krzysztofruczkowski.pwr4.databinding.ItemTrackBinding
import pl.krzysztofruczkowski.pwr4.models.Track

class TrackAdapter(
    private val items: List<Track>,
    val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<TrackAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemTrackBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val item = items[position]
//            val imageId = getImageIdByName(pokemon.name.toLowerCase(Locale.ROOT), activity)
            binding.apply {
                itemTrackName.text = item.name
                itemTrackName.isSelected = true // needed to enable marquee
                itemTrack.setOnClickListener { onItemClick(position) }
//                itemPokemonImage.setImageResource(imageId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTrackBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(position)
}