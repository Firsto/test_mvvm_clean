package studio.inprogress.yetanothertestapi.providers.database

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Update

interface IBaseDao<T> {

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(list: List<T>)

    @Insert(onConflict = REPLACE)
    suspend fun insert(data: T)

    @Update
    suspend fun update(data: T)

    @Delete
    suspend fun delete(data: T)
}