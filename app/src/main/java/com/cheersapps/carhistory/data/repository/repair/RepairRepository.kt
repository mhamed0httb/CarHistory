package com.cheersapps.carhistory.data.repository.repair

import com.cheersapps.carhistory.data.entity.Repair
import com.cheersapps.carhistory.data.local.datasource.repair.RepairLocal
import io.reactivex.Completable
import javax.inject.Inject

class RepairRepository @Inject constructor() {

    @Inject
    lateinit var repairLocal: RepairLocal

    fun insert(repair: Repair): Completable {
        return repairLocal.insert(repair)
    }
}