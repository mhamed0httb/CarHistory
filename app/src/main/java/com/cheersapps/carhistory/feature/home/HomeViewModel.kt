package com.cheersapps.carhistory.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cheersapps.carhistory.common.application.App
import com.cheersapps.carhistory.data.entity.AppLanguage
import com.cheersapps.carhistory.data.entity.Location
import com.cheersapps.carhistory.data.entity.Repair
import com.cheersapps.carhistory.data.entity.User
import com.cheersapps.carhistory.data.repository.location.LocationRepository
import com.cheersapps.carhistory.data.repository.repair.RepairRepository
import com.cheersapps.carhistory.data.repository.user.UserRepository
import com.cheersapps.carhistory.usecase.login.GetLoggedInUserUseCase
import com.cheersapps.carhistory.usecase.login.SetStayLoggedIn
import com.cheersapps.carhistory.usecase.profile.CheckEditPasswordFieldsUseCase
import com.cheersapps.carhistory.usecase.profile.FieldsValidator
import com.cheersapps.carhistory.usecase.profile.GetAppLanguageUseCase
import com.cheersapps.carhistory.usecase.profile.SaveAppLanguageUseCase
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor() : ViewModel() {

    init {
        App.appComponent.inject(this)
    }

    @Inject
    lateinit var setStayLoggedIn: SetStayLoggedIn

    @Inject
    lateinit var checkEditPasswordFieldsUseCase: CheckEditPasswordFieldsUseCase

    @Inject
    lateinit var getLoggedInUserUseCase: GetLoggedInUserUseCase

    @Inject
    lateinit var repairRepository: RepairRepository

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var locationRepository: LocationRepository

    @Inject
    lateinit var getAppLanguageUseCase: GetAppLanguageUseCase

    @Inject
    lateinit var saveAppLanguageUseCase: SaveAppLanguageUseCase

    private val deleteLocationLiveData = MutableLiveData<Int>()
    fun observeDeleteLocation(): LiveData<Int> = deleteLocationLiveData

    private val insertLocationLiveData = MutableLiveData<Location>()
    fun observeInsertLocation(): LiveData<Location> = insertLocationLiveData

    fun setStayLoggedIn(stay: Boolean) {
        setStayLoggedIn.execute(stay)
    }

    fun checkEditPasswordFields(oldPass: String, newPass: String, confirmPass: String): Triple<FieldsValidator, FieldsValidator, FieldsValidator> {
        return checkEditPasswordFieldsUseCase.execute(Triple(oldPass, newPass, confirmPass))
    }

    fun getLoggedInUser() = getLoggedInUserUseCase.execute()

    fun insertRepair(repair: Repair): Completable {
        return repairRepository.insert(repair)
    }

    fun getRepairs(): LiveData<List<Repair>> {
        return repairRepository.findAll()
    }

    fun updateUser(user: User): Completable {
        return userRepository.updateUser(user)
    }

    fun deleteRepair(repair: Repair): Completable {
        return repairRepository.delete(repair)
    }

    fun updateUserPassword(user: User, oldPass: String): Single<Boolean> {
        return userRepository.updatePassword(user, oldPass)
    }

    fun insertLocation(location: Location) {
        locationRepository.insert(location)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DisposableCompletableObserver() {
                    override fun onComplete() {
                        insertLocationLiveData.postValue(location)
                    }

                    override fun onError(e: Throwable) {
                        insertLocationLiveData.postValue(null)
                    }

                })
    }

    fun deleteLocation(location: Location, position: Int) {
        locationRepository.delete(location)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DisposableCompletableObserver() {
                    override fun onComplete() {
                        deleteLocationLiveData.postValue(position)
                    }

                    override fun onError(e: Throwable) {
                        deleteLocationLiveData.postValue(-1)
                    }

                })
    }

    fun getLocations(): LiveData<List<Location>> {
        return locationRepository.findAll()
    }

    fun getAppLanguage(): AppLanguage = getAppLanguageUseCase.execute()

    fun saveAppLanguage(lang: AppLanguage) = saveAppLanguageUseCase.execute(lang.toString())

}