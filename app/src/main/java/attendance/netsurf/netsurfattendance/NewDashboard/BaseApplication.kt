package attendance.netsurf.netsurfattendance.NewDashboard

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log



class BaseApplication : Application() {

    init {
        instance = this
    }




    companion object {
        private var instance: BaseApplication? = null
        private var mSharedPref: SharedPreferences? = null



        fun applicationContext() : BaseApplication {
            return instance as BaseApplication
        }



        object SharedPref {



            fun read(key: String?, defValue: String?): String? {
                return mSharedPref!!.getString(key, defValue)
            }

            fun write(key: String?, value: String?) {
                val prefsEditor = mSharedPref!!.edit()
                prefsEditor.putString(key, value)
                prefsEditor.apply()
            }

            fun writeBOOLEAN(key: String?, value: Boolean?) {
                val prefsEditor = mSharedPref!!.edit()
                if (value != null) {
                    prefsEditor.putBoolean(key, value)
                }
                prefsEditor.apply()
            }

            fun read(key: String?, defValue: Boolean): Boolean {
                return mSharedPref!!.getBoolean(key, defValue)
            }

            fun write(key: String?, value: Boolean) {
                val prefsEditor = mSharedPref!!.edit()
                prefsEditor.putBoolean(key, value)
                prefsEditor.apply()
            }

            fun read(key: String?, defValue: Int): Int {
                return mSharedPref!!.getInt(key, defValue)
            }
             fun read(key: String?, defValue: Float): Float {
                return mSharedPref!!.getFloat(key, defValue.toFloat())
            }



            fun clearData() {
                val prefsEditor = mSharedPref!!.edit()
                prefsEditor.clear()

                prefsEditor.apply()
            }

            fun write(key: String?, value: Int?) {
                val prefsEditor = mSharedPref!!.edit()
                prefsEditor.putInt(key, value!!).commit()
              }


        }
        fun getDouble(prefs: SharedPreferences, key: String?, defaultValue: Double): Double {
            return java.lang.Double.longBitsToDouble(
                prefs.getLong(
                    key,
                    java.lang.Double.doubleToLongBits(defaultValue)
                )
            )
        }
    }


    override fun onCreate() {
        super.onCreate()

        mSharedPref = getSharedPreferences(packageName, Activity.MODE_PRIVATE)

        //ONE SIGNAL



    }




}


