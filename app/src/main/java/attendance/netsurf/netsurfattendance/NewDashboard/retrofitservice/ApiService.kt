package mylab.android.pc_app_new.retrofitservice

import attendance.netsurf.netsurfattendance.models.AttendanceStatusCurrentDay
import attendance.netsurf.netsurfattendance.models.GetLeaveCompoffCount
import attendance.netsurf.netsurfattendance.models.InsertUpdateDeviceTokenId
import attendance.netsurf.netsurfattendance.models.LoggedInUser
import attendance.netsurf.netsurfattendance.models.MarkAttendance
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {


    @POST("Login")
    suspend fun getLogin(@Body login_ojb_api: LoggedInUser.Request): Response<ArrayList<LoggedInUser.Response>>



    @POST("MarkAttendance")
    suspend fun markAttendance(@Body loginRequest: MarkAttendance.Request?): Response<ArrayList<MarkAttendance.Response>>


    @POST("AttendanceStatusCurrentDay")
    suspend fun CountAttendance(@Body loginRequest: AttendanceStatusCurrentDay.Request?): Response<ArrayList<AttendanceStatusCurrentDay.Response>>


    @POST("GetLeaveCompoffCount")
    suspend fun getLeaveCount(@Body loginRequest: GetLeaveCompoffCount.Request?): Response<ArrayList<GetLeaveCompoffCount.Response>>


    @POST("InsertUpdateDeviceTokenId")
    suspend fun insertToken(@Body loginRequest: InsertUpdateDeviceTokenId.Request?): Response<ArrayList<InsertUpdateDeviceTokenId.Response>>


/*


   */
/* @POST("ConsumerGetOfferBanner")
    suspend fun getOfferList(@Body login_ojb_api: ConsumerGetOfferBanner.Request): Response<ArrayList<ConsumerGetOfferBanner.Response>>
    *//*

   @POST("ConsumerGetOfferBannerFromOnlineBuyerId")
    suspend fun getOfferList(@Body login_ojb_api: ConsumerGetOfferBanner.Request): Response<ArrayList<ConsumerGetOfferBanner.Response>>

    @POST("GetProductSubCategoryConsumerApp")
    suspend fun getSubCategoryList(@Body login_ojb_api: GetProductSubCategoryConsumerApp.Request): Response<ArrayList<GetProductSubCategoryConsumerApp.Response>>
*/


}

