package pl.krzysztofruczkowski.pwr3.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.krzysztofruczkowski.pwr3.BmiPersistence
import pl.krzysztofruczkowski.pwr3.models.Record

@Entity(tableName = "record_table")
data class RecordEntity(
    @PrimaryKey(autoGenerate = true)
    var recordId: Long = 0L,
    val bmi: Double,
    val date: String,
    val height: Double,
    val mass: Double,
    val heightUnit: String,
    val massUnit: String,
) {
    fun toRecord() = Record(bmi, date, height, mass, heightUnit, massUnit)
}
