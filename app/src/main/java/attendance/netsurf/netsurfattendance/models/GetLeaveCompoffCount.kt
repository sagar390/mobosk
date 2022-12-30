package attendance.netsurf.netsurfattendance.models

class GetLeaveCompoffCount {
    data  class Request ( var Emp_Id: String? = null)



    data class Response (
        var IsLeaveApprover: Int = 0,
        var LeaveCount: Int = 0,

       var CompoffCount: Int = 0
    )

/*[
{
   "IsLeaveApprover": 1,
   "LeaveCount": 0,
   "CompoffCount": 0
}
]*/
}