package com.thomaskioko.paybillmanager.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thomaskioko.paybillmanager.cache.model.CachedBills
import io.reactivex.Flowable


@Dao
abstract class BillsDao {

    @Query("SELECT * FROM bills")
    @JvmSuppressWildcards
    abstract fun getBills(): Flowable<List<CachedBills>>

    @Query("SELECT * FROM bills where id = :billId")
    @JvmSuppressWildcards
    abstract fun getBillById(billId: String): Flowable<CachedBills>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    abstract fun insertCachedBills(cachedBills: List<CachedBills>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    abstract fun insertBill(cachedBills: CachedBills)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    abstract fun updateBill(cachedBills: CachedBills)

    @Query("DELETE FROM bills")
    abstract fun deleteBills()

}