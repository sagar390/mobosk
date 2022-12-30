package attendance.netsurf.netsurfattendance;


import java.util.ArrayList;

import attendance.netsurf.netsurfattendance.models.AttendanceStatusCurrentDay;
import attendance.netsurf.netsurfattendance.models.GetLeaveCompoffCount;
import attendance.netsurf.netsurfattendance.models.InsertUpdateDeviceTokenId;
import attendance.netsurf.netsurfattendance.models.LoggedInUser;
import attendance.netsurf.netsurfattendance.models.MarkAttendance;
import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by Sagar on 17-10-2018.
 */

public interface UsApiInterface

{


    @POST("/Login")
    Observable<ArrayList<LoggedInUser.Response>> login(@Body LoggedInUser.Request loginRequest);

    @POST("/MarkAttendance")
    Observable<ArrayList<MarkAttendance.Response>> markAttendance(@Body MarkAttendance.Request loginRequest);


    @POST("/AttendanceStatusCurrentDay")
    Observable<ArrayList<AttendanceStatusCurrentDay.Response>> CountAttendance(@Body AttendanceStatusCurrentDay.Request loginRequest);



    @POST("/GetLeaveCompoffCount")
    Observable<ArrayList<GetLeaveCompoffCount.Response>> getLeaveCount(@Body GetLeaveCompoffCount.Request loginRequest);


    @POST("/InsertUpdateDeviceTokenId")
    Observable<ArrayList<InsertUpdateDeviceTokenId.Response>> insertToken(@Body InsertUpdateDeviceTokenId.Request loginRequest);





}


