package attendance.netsurf.netsurfattendance.models

class MarkAttendance {
   data class Request (
        var employeeID: String? = null,
        var isWorking: String? = null,
        var latitude: String? = null,
        var longitude: String? = null,
        var userLocation: String? = null,
        var userRemarks: String? = null,
        var actualMapLocation: String? = null

)
  data  class Response (

        var retu_Value : Int = 0,
        var retu_message: String? = null
    )

    /*[
{
"Retu_Value": 1,
"Retu_message": "Attandance for Date:- 17 Jul 2019 Recorded as Absent"
}
]*/
}