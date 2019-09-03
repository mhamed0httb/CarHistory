package com.cheersapps.carhistory.data.local.datasource.repair

import androidx.lifecycle.LiveData
import com.cheersapps.carhistory.data.entity.Repair
import com.cheersapps.carhistory.data.entity.RepairType
import com.cheersapps.carhistory.data.local.db.AppDatabase
import io.reactivex.Completable
import javax.inject.Inject

class RepairLocal @Inject constructor() {

    @Inject
    lateinit var appDatabase: AppDatabase


    fun insert(repair: Repair): Completable {
        return Completable.fromAction {
            appDatabase.repairDao.insert(repair)
        }
    }

    fun findAll(): LiveData<List<Repair>> {
        return appDatabase.repairDao.findAll()
    }

    fun delete(repair: Repair): Completable {
        return Completable.fromAction {
            appDatabase.repairDao.delete(repair)
        }
    }

    fun getLastOilChange(): LiveData<Repair> {
        return appDatabase.repairDao.getLastByType(RepairType.OIL_CHANGE.name)
    }
}