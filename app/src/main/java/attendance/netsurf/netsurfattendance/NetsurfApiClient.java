package attendance.netsurf.netsurfattendance;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;

import java.util.Arrays;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

public class NetsurfApiClient {
    private static final String TAG = NetsurfApiClient.class.getName();
    private static UsApiInterface netsurfApiInterface;

    public static UsApiInterface getApiClient(final android.app.Activity context,
                                                   boolean showNetworkNotAvailableDialog) {
        if (Utils.isNetworkAvailable(context)) {
            if (netsurfApiInterface == null) {
                OkHttpClient okHttpClient = new OkHttpClient();
                okHttpClient.setProtocols(Arrays.asList(Protocol.HTTP_1_1));

                okHttpClient.setConnectTimeout(60, java.util.concurrent.TimeUnit.SECONDS);
                Gson gson = new GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                        .excludeFieldsWithModifiers(java.lang.reflect.Modifier.FINAL, java.lang.reflect.Modifier.TRANSIENT, java.lang.reflect.Modifier.STATIC)
                        .registerTypeAdapter(java.util.Date.class, new DateTypeAdapter())
                        .create();

                RestAdapter.Builder builder = new RestAdapter.Builder()
                        .setConverter(new GsonConverter(gson))
                        .setClient(new OkClient(okHttpClient));

                RestAdapter restAdapter;

           /*     if (BuildConfig.DEBUG) {
                    builder.setEndpoint(Constants.BASE_URL_FOR_DEBUG);
                    restAdapter = builder.build();
                    restAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
                } else {*/
                builder.setEndpoint(Constants.BASE_URL_FOR_RELEASE);
                restAdapter = builder.build();
                // }


                netsurfApiInterface = restAdapter.create(UsApiInterface.class);
            }
            return netsurfApiInterface;
        } else {
            if (showNetworkNotAvailableDialog && context != null) {
                try {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        /*    NetsurfDialog dialog = new NetsurfDialog(context,
                                    context.getString(R.string.no_network),
                                    context.getString(R.string.no_network_msg),
                                    context.getString(R.string.ok), null);
                            dialog.show();*/
                        }
                    });

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    // todo remove after auto update application code testing done
    public static UsApiInterface getApiClient(final android.app.Activity context,
                                                   boolean showNetworkNotAvailableDialog, String
                                                           baseUrl) {
        if (Utils.isNetworkAvailable(context)) {
            UsApiInterface netsurfApiInterface;
            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setProtocols(Arrays.asList(Protocol.HTTP_1_1));

            okHttpClient.setConnectTimeout(60, java.util.concurrent.TimeUnit.SECONDS);
            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                    .excludeFieldsWithModifiers(java.lang.reflect.Modifier.FINAL, java.lang.reflect.Modifier.TRANSIENT, java.lang.reflect.Modifier.STATIC)
                    .registerTypeAdapter(java.util.Date.class, new DateTypeAdapter())
                    .create();
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(baseUrl)
                    .setConverter(new GsonConverter(gson))
                    .setClient(new OkClient(okHttpClient))
                    .build();
            if (BuildConfig.DEBUG) {
                restAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
            }
            netsurfApiInterface = restAdapter.create(UsApiInterface.class);

            return netsurfApiInterface;
        } else {
            if (showNetworkNotAvailableDialog && context != null) {
                try {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                          /*  NetsurfDialog dialog = new NetsurfDialog(context,
                                    context.getString(R.string.no_network),
                                    context.getString(R.string.no_network_msg),
                                    context.getString(R.string.ok), null);
                            dialog.show();*/
                        }
                    });

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    public static UsApiInterface getApiClient(final android.app.Activity context) {
        if (Utils.isNetworkAvailable(context)) {
            if (netsurfApiInterface == null) {
                OkHttpClient okHttpClient = new OkHttpClient();
                okHttpClient.setProtocols(Arrays.asList(Protocol.HTTP_1_1));

                okHttpClient.setConnectTimeout(60, java.util.concurrent.TimeUnit.SECONDS);
                Gson gson = new GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                        .excludeFieldsWithModifiers(java.lang.reflect.Modifier.FINAL, java.lang.reflect.Modifier.TRANSIENT, java.lang.reflect.Modifier.STATIC)
                        .registerTypeAdapter(java.util.Date.class, new DateTypeAdapter())
                        .create();

                RestAdapter.Builder builder = new RestAdapter.Builder()
                        .setConverter(new GsonConverter(gson))
                        .setClient(new OkClient(okHttpClient));

                RestAdapter restAdapter;


           /*  if (BuildConfig.DEBUG) {
                    builder.setEndpoint(Constants.BASE_URL_FOR_DEBUG);
                    restAdapter = builder.build();
                    restAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
                } else {*/
                builder.setEndpoint(Constants.BASE_URL_FOR_RELEASE);
                restAdapter = builder.build();
                // }


                netsurfApiInterface = restAdapter.create(UsApiInterface.class);
            }
            return netsurfApiInterface;
        }
        return null;
    }


}
