package com.thomaskioko.daraja.repository.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.thomaskioko.daraja.repository.db.entity.PushRequestResponse


/**
 * Interface for database access on Request related operations.
 */
@Dao
abstract class SafaricomPushRequestDao {

    @Query("SELECT * FROM PushRequestResponse")
    abstract fun findAll(): LiveData<List<PushRequestResponse>>

    @Query("SELECT * FROM PushRequestResponse WHERE merchantRequestID = :merchantRequestID")
    abstract fun findById(merchantRequestID: String): LiveData<PushRequestResponse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg pushRequestResponse: PushRequestResponse)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertPushResponse(pushRequestResponseList: List<PushRequestResponse>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun createPushResponseIfNotExists(pushRequestResponse: PushRequestResponse): Long

    @Query("DELETE FROM PushRequestResponse")
    abstract fun deleteAll()
}
