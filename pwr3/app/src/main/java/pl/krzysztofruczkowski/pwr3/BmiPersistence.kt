package pl.krzysztofruczkowski.pwr3

import kotlinx.coroutines.runBlocking
import pl.krzysztofruczkowski.pwr3.database.RecordDao
import pl.krzysztofruczkowski.pwr3.database.RecordEntity
import pl.krzysztofruczkowski.pwr3.models.Record

class BmiPersistence(private val dao: RecordDao) {

    fun saveBmiRecord(newRecord: Record) = runBlocking { dao.insert(newRecord.toEntity()) }
    fun getBmiRecords(): List<RecordEntity> = runBlocking { dao.getTenRecords() }
}
