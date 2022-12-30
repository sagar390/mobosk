package attendance.netsurf.netsurfattendance;

/**
 * Created by Aakash on 05-12-2015.
 */
public class AdvertiseModel {
    public static class Request {

    /**
     * ContentType : can be scheme,  photo or video
     */
    private int NotifCategoryId;

    public int getNotifCategoryId() {
        return NotifCategoryId;
    }

    /**
     * ContentType : can be scheme,  photo or video
     */
    public void setNotifCategoryId(int NotifCategoryId) {
        this.NotifCategoryId = NotifCategoryId;
    }
}


    public static class Response {


        /**
         * Category : NA
         * DownloadShareFilePath : 5055423.jpg
         * MediaLibraryContentID : 1
         * ContentDescription : Scheme
         * ThumbnailPath : http23.jpg
         * IsSubImages : No
         * ContentHeading : July Cycle 1 2015
         * ContentType : Scheme
         */
        private int NotifCategoryId;
        private String WebURL;
        private String image_path;
        private String Param1;
        private String Param2;
        private String Param3;
        private String ButtonText;

       // private String VideoServerFilePath;

        public int getNotifCategoryId() {
            return NotifCategoryId;
        }

        public void setNotifCategoryId(int NotifCategoryId) {
            this.NotifCategoryId = NotifCategoryId;
        }

        public void setParam2(String Param2) {
            this.Param2 = Param2;
        }

        public String getParam2() {
            return Param2;
        }

        public void setParam3(String Param3) {
            this.Param3 = Param3;
        }

        public String getParam3() {
            return Param3;
        }

        public String getWebURL() {return WebURL;}

        public void setWebURL(String WebURL) {
            this.WebURL = WebURL;
        }

        public String getimage_path() {
            return image_path;
        }

        public void setParam1(String Param1) {
            this.Param1 = Param1;
        }

        public String getParam1() {
            return Param1;
        }

        public void setimage_path(String image_path) {
            this.image_path = image_path;
        }
        public String getButtonText() {
            return ButtonText;
        }

        public void setButtonText(String ButtonText) {
            this.ButtonText = ButtonText;
        }
    }







}





/*

public static class Request{

    */
/**
     * PhaseId : 1
     * CustID : 1
     *//*

    private String PhaseId;
    private String CustID;

    public void setPhaseId(String PhaseId) {
        this.PhaseId = PhaseId;
    }

    public void setCustID(String CustID) {
        this.CustID = CustID;
    }

    public String getPhaseId() {
        return PhaseId;
    }

    public String getCustID() {
        return CustID;
    }
}

public static class Response {
    */
/**
     * Amount : 613.28
     * CategoryName : Herbs and More
     *//*

    private double Amount;
    private String CategoryName;

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    public void setCategoryName(String CategoryName) {
        this.CategoryName = CategoryName;
    }

    public double getAmount() {
        return Amount;
    }

    public String getCategoryName() {
        return CategoryName;
    }
}
}

*/
