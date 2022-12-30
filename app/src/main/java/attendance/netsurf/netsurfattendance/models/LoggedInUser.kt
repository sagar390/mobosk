package attendance.netsurf.netsurfattendance.models

import com.google.gson.annotations.SerializedName

/**
 * Created by chetan on 15/05/15.
 */
class LoggedInUser {
   data class Request ( var loginid: String? = null,
                    var password: String? = null)


data class Response (

    @SerializedName("CompId"               ) var CompId               : Int?     = null,
    @SerializedName("EmployeeId"           ) var EmployeeId           : Int?     = null,
    @SerializedName("LoginId"              ) var LoginId              : String?  = null,
    @SerializedName("Pwd"                  ) var Pwd                  : String?  = null,
    @SerializedName("UserType"             ) var UserType             : String?  = null,
    @SerializedName("Active"               ) var Active               : Boolean? = null,
    @SerializedName("Name"                 ) var Name                 : String?  = null,
    @SerializedName("IsPoliciesAccepted"   ) var IsPoliciesAccepted   : Boolean? = null,
    @SerializedName("lastmodified"         ) var lastmodified         : String?  = null,
    @SerializedName("IsReporty"            ) var IsReporty            : String?  = null,
    @SerializedName("IsOnProbession"       ) var IsOnProbession       : Int?     = null,
    @SerializedName("IsOutstationEmployee" ) var IsOutstationEmployee : Int?     = null)


/* [
  [
    {
        "CompId": 1,
        "EmployeeId": 280,
        "LoginId": "280",
        "Pwd": "123",
        "UserType": "Employee",
        "Active": true,
        "Name": "Employee Test Prakash",
        "IsPoliciesAccepted": false,
        "lastmodified": "01-01-1900",
        "IsReporty": "0",
        "IsOnProbession": 0
    }
]
    ]*/

}