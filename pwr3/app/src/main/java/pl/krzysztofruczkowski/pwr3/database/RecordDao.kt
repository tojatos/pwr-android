package pl.krzysztofruczkowski.pwr3.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecordDao {

    @Insert
    suspend fun insert(record: RecordEntity)

    @Query("SELECT * FROM record_table ORDER BY recordId DESC")
    suspend fun getAllRecords(): List<RecordEntity>

    @Query("SELECT * FROM record_table ORDER BY recordId DESC LIMIT 10")
    suspend fun getTenRecords(): List<RecordEntity>
}