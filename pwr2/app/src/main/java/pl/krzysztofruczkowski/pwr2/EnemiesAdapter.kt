package pl.krzysztofruczkowski.pwr2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EnemiesAdapter(private val enemies: List<String>) : RecyclerView.Adapter<EnemiesAdapter.ViewHolder>() {
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val enemy: TextView = itemView.findViewById(R.id.item_enemy)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_enemy, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = enemies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val enemy: String = enemies[position]

        holder.enemy.text = enemy
    }
}