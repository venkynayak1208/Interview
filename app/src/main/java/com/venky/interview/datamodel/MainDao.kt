package com.venky.interview.datamodel

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface MainDao {
    @Insert(onConflict = REPLACE)
    fun insertFile(task: MainModel): Completable

    @Query("SELECT *FROM datalist")
    fun getAll():Observable<MainModel>
}