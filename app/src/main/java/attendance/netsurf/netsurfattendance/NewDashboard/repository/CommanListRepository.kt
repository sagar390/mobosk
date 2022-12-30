package mylab.android.pc_app_new.CommanListofOfficiers.repository


import attendance.netsurf.netsurfattendance.models.AttendanceStatusCurrentDay
import attendance.netsurf.netsurfattendance.models.GetLeaveCompoffCount
import attendance.netsurf.netsurfattendance.models.InsertUpdateDeviceTokenId
import attendance.netsurf.netsurfattendance.models.LoggedInUser
import mylab.android.pc_app_new.retrofitservice.ApiService
import mylab.android.pc_app_new.retrofitservice.RetrofitApiClient

class CommanListRepository() {

    private val apiRequest: ApiService



    suspend fun getLoginFRepo(sub_category_obj: LoggedInUser.Request) = apiRequest.getLogin(sub_category_obj)


    suspend fun getAttendanceRepo(my_profile_obj: AttendanceStatusCurrentDay.Request) = apiRequest.CountAttendance(my_profile_obj)

    suspend fun getLeaveCompOffRepo(address_obj: GetLeaveCompoffCount.Request) = apiRequest.getLeaveCount(address_obj)

    suspend fun insertTokenRepo(redeemrepo: InsertUpdateDeviceTokenId.Request) = apiRequest.insertToken(redeemrepo)


    init {

        apiRequest = RetrofitApiClient.getRetrofitInstance()!!.create(ApiService::class.java)

    }


}
