package attendance.netsurf.netsurfattendance.models

class AttendanceStatusCurrentDay {
    /*{"EmployeeID":"160"}*/
    data   class Request (
        var EmployeeID: String? = null
    )

   data class Response (
        var IsAttendanceDone : Int = 0
    ) /*{"EmployeeID":"160"}*/ /*[
    {
       [
    {
        "IsAttendanceDone": 1
    }
]
    }
]*/
}