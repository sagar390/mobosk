package mylab.android.pc_app_new.CommanListofOfficiers.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import attendance.netsurf.netsurfattendance.models.AttendanceStatusCurrentDay
import attendance.netsurf.netsurfattendance.models.GetLeaveCompoffCount
import attendance.netsurf.netsurfattendance.models.InsertUpdateDeviceTokenId
import attendance.netsurf.netsurfattendance.models.LoggedInUser
import kotlinx.coroutines.*


import mylab.android.pc_app_new.CommanListofOfficiers.repository.CommanListRepository

class ApiViewModel : ViewModel() {





    private lateinit var login_obj: ArrayList<LoggedInUser.Response>
    private lateinit var attendance_current_status_cnt_obj : ArrayList<AttendanceStatusCurrentDay.Response>
    private lateinit var leave_cnt_obj: ArrayList<GetLeaveCompoffCount.Response>
    private lateinit var insertToken_obj: ArrayList<InsertUpdateDeviceTokenId.Response>



     var mRepository: CommanListRepository? = null


    val errorMessage = MutableLiveData<String>()



    val loading = MutableLiveData<Boolean>()

    var job: Job? = null

    init {
        mRepository = CommanListRepository()


    }
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled login: ${throwable.localizedMessage}")
    }







    fun getLoginApi(login_ojb_api: LoggedInUser.Request): LiveData<ArrayList<LoggedInUser.Response>> {
        Log.d("API CAll", "getLoginApi: 2")

        val data: MutableLiveData<ArrayList<LoggedInUser.Response>> =
            MutableLiveData<ArrayList<LoggedInUser.Response>>()

        job = CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val response = mRepository!!.getLoginFRepo(login_ojb_api)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    login_obj = response.body()!!
                    data.setValue(login_obj)
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }

        return data


    }



    fun getCurrentStatusApi(login_ojb_api: AttendanceStatusCurrentDay.Request): LiveData<ArrayList<AttendanceStatusCurrentDay.Response>> {
        Log.d("API CAll", "getLoginApi: 2")

        val data: MutableLiveData<ArrayList<AttendanceStatusCurrentDay.Response>> =
            MutableLiveData<ArrayList<AttendanceStatusCurrentDay.Response>>()

        job = CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val response = mRepository!!.getAttendanceRepo(login_ojb_api)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    attendance_current_status_cnt_obj = response.body()!!
                    data.setValue(attendance_current_status_cnt_obj)
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }

        return data


    }



    fun getLeaveCountApi(login_ojb_api: GetLeaveCompoffCount.Request): LiveData<ArrayList<GetLeaveCompoffCount.Response>> {
        Log.d("API CAll", "getLoginApi: 2")

        val data: MutableLiveData<ArrayList<GetLeaveCompoffCount.Response>> =
            MutableLiveData<ArrayList<GetLeaveCompoffCount.Response>>()

        job = CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val response = mRepository!!.getLeaveCompOffRepo(login_ojb_api)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    leave_cnt_obj = response.body()!!
                    data.setValue(leave_cnt_obj)
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }

        return data


    }


    fun insertUpdateDeviceTokenApi(login_ojb_api: InsertUpdateDeviceTokenId.Request): LiveData<ArrayList<InsertUpdateDeviceTokenId.Response>> {
        Log.d("API CAll", "getLoginApi: 2")

        val data: MutableLiveData<ArrayList<InsertUpdateDeviceTokenId.Response>> =
            MutableLiveData<ArrayList<InsertUpdateDeviceTokenId.Response>>()

        job = CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val response = mRepository!!.insertTokenRepo(login_ojb_api)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    insertToken_obj = response.body()!!
                    data.setValue(insertToken_obj)
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }

        return data


    }






    private fun onError(message: String) {

        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                errorMessage.value = message
                loading.value = false
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }



}