package pl.krzysztofruczkowski.pwr2.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.krzysztofruczkowski.pwr2.databinding.ItemEnemyBinding

class EnemiesAdapter(private val enemies: List<String>) : RecyclerView.Adapter<EnemiesAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemEnemyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(enemy: String) {
            binding.itemEnemy.text = enemy
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemEnemyBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = enemies.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(enemies[position])
}