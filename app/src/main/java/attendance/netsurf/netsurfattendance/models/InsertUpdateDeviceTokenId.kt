package attendance.netsurf.netsurfattendance.models

class InsertUpdateDeviceTokenId {


  data  class Request (

        var DeviceTokenId: String? = null,
        var EmployeeId: String? = null
    )

 data   class Response (
        var DeviceTokenId: String? = null
    )
}