package pl.krzysztofruczkowski.pwr1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.krzysztofruczkowski.pwr1.models.Record
import java.text.DecimalFormat

class RecordsAdapter (private val records: List<Record>) : RecyclerView.Adapter<RecordsAdapter.ViewHolder>() {
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val massTV: TextView = itemView.findViewById(R.id.mass)
        val massUnitTV: TextView = itemView.findViewById(R.id.mass_unit)
        val heightTV: TextView = itemView.findViewById(R.id.height)
        val heightUnitTV: TextView = itemView.findViewById(R.id.height_unit)
        val bmiValueTV: TextView = itemView.findViewById(R.id.bmi_value)
        val dateTV: TextView = itemView.findViewById(R.id.date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.item_bmi, parent, false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: RecordsAdapter.ViewHolder, position: Int) {
        val record: Record = records[position]

        holder.massTV.text = record.mass.toInt().toString()
        holder.massUnitTV.text = record.massUnit
        holder.heightTV.text = record.height.toInt().toString()
        holder.heightUnitTV.text = record.heightUnit
        holder.bmiValueTV.text = DecimalFormat("#.##").format(record.bmi)
        holder.dateTV.text = record.date
    }

    override fun getItemCount(): Int = records.size

}