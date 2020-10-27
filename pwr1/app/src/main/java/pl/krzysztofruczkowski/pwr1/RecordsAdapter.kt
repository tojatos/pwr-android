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
        val bmiValueTextView = itemView.findViewById<TextView>(R.id.bmi_value)
        val dateTextView = itemView.findViewById<TextView>(R.id.date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.item_bmi, parent, false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: RecordsAdapter.ViewHolder, position: Int) {
        val record: Record = records.get(position)
        val bmiValueView = holder.bmiValueTextView
        val dateView = holder.dateTextView

        bmiValueView.text = DecimalFormat("#.##").format(record.bmi)
        dateView.text = record.date
    }

    override fun getItemCount(): Int = records.size

}