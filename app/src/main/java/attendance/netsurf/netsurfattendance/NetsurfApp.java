package attendance.netsurf.netsurfattendance;

import android.app.Application;
import android.graphics.Typeface;
import android.os.StrictMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NetsurfApp extends Application {

    private static final String TAG = NetsurfApp.class.getSimpleName();

    private static NetsurfApp baseApplicationContext = null;
    //    private LoggedInUser loggedInUser;
    // fonts
    private Typeface typefaceRobotoMedium = null;
    private Typeface typefaceRobotoLight = null;
    private Typeface typefaceRobotoRegular = null;
    private Typeface typefaceRobotoThin = null;
    private Typeface typefaceRobotoBlack = null;
    private Typeface typefaceRobotoCondensedLight = null;
    private Typeface typefaceRobotoCondensedRegular = null;
    private Typeface typefaceRobotoCondensedBold = null;
    private Typeface typefaceRobotoBold = null;
    //  private ObjectGraph applicationGraph;

    private static final String RX_EXCEPTION_TO_IGNORE = "rx.exceptions.OnErrorNotImplementedException";
    // private static final String PARSE_CHANNEL = "Netsurf_Dev";
    private static final String PARSE_CHANNEL = "Netsurf";
    //private ArrayList<RegionListModel> regionListModels = new ArrayList<>();
    public static final String PARSE_API_KEY = "LYSqGRxixSPQ3mfNCGXfJnUGP012XNQlXDKwFoL1";
    public static final String PARSE_CLIENT_ID = "DNFgAxu04MeX2jj3StVdf5cK89LAU4farfTY4uWk";
    public  static boolean validuser;


    public static NetsurfApp getInstance() {

        return baseApplicationContext;
    }

    @Override
    public void onCreate() {
        // MultiDex.install(this);
        super.onCreate();

        //  initializeAppGraph();
        //  ActiveAndroid.initialize(this);

        baseApplicationContext = this;


        if (BuildConfig.DEBUG) {
        } else {

        }



    }







}
