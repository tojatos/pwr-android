package pl.krzysztofruczkowski.pwr2.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.krzysztofruczkowski.pwr2.databinding.ItemImageBinding

class GalleryAdapter(private val imageIds: List<Int>) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imageId: Int) {
            binding.itemImage.setImageResource(imageId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemImageBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = imageIds.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(imageIds[position])
}