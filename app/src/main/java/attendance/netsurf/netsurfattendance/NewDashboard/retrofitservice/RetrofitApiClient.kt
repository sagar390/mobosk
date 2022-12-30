package mylab.android.pc_app_new.retrofitservice

import android.content.Context
import attendance.netsurf.netsurfattendance.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitApiClient {


    //object APIService {



        private var retrofit: Retrofit? = null
        private var retrofit_one_signal: Retrofit? = null


      fun getRetrofitInstance(): Retrofit? {

            val logging = HttpLoggingInterceptor()
            // set your desired log level
            logging.level = HttpLoggingInterceptor.Level.BODY
            val httpClient = OkHttpClient.Builder()
                    .connectTimeout(1000, TimeUnit.SECONDS)
                    .readTimeout(1000, TimeUnit.SECONDS)
                //.addInterceptor(NetworkInterceptorApiCall(mcontext))

        .addInterceptor(logging)
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                        .baseUrl(Constants.BASE_URL_FOR_RELEASE)
                        .client(httpClient.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }
            return retrofit
        }
        fun getRetrofitInstanceOneSignal(): Retrofit? {

            val logging = HttpLoggingInterceptor()
            // set your desired log level
            logging.level = HttpLoggingInterceptor.Level.BODY
            val httpClient = OkHttpClient.Builder()
                    .connectTimeout(1000, TimeUnit.SECONDS)
                    .readTimeout(1000, TimeUnit.SECONDS)
              // .addInterceptor(                .addInterceptor(NetworkConnectionInterceptor(mcontext)())
         .addInterceptor(logging)
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL_FOR_RELEASE)
                        .client(httpClient.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }
            return retrofit
        }





}