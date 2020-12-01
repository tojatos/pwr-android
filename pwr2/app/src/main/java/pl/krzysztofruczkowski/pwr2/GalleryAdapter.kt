package pl.krzysztofruczkowski.pwr2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class GalleryAdapter(private val imageIds: List<Int>) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val image: ImageView = itemView.findViewById(R.id.item_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_image, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = imageIds.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageId: Int = imageIds[position]

        holder.image.setImageResource(imageId)
    }
}