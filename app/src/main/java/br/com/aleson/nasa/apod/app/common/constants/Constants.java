package br.com.aleson.nasa.apod.app.common.constants;

public class Constants {


    public static final class SWIPE {
        public static int LEFT = 1;
        public static int RIGHT = -1;
        public static int IDLE = 0;
    }

    public static final class BUSINESS {
        public static final String FIRST_APOD = "1995-06-16";
        public static final String STORAGE_DIR = "NASA APOD App";
    }

    public static final class HTTP_CODE {
        public static final int SUCCESS = 200;
        public static final int CREATED = 201;
        public static final int ACCEPTED = 202;
        public static final int UNAVAILABLE_APOD = 601;
    }

    public static final class FIREBASE {
        public static final String RQUEST_ID_TOKEN = "564935331593-788ive354t904oj80g8sqeum4a3krbcu.apps.googleusercontent.com";
    }

    public static final class NOTIFICATIONS {
        public static final String DAILY_NOTIFICATION = "DAILY_NOTIFICATION";

    }

    public static final class FILE {
        public static final class OPERATIONS {

            public static final String SUCESS = "Image saved to Gallery";
            public static final String FAILED = "Download Failed";
            public static final String SAVED = "Image Already saved";
            public static final String DELETED = "File deleted";
            public static final String ERROR = "Something Went Wrong";
        }
    }

    public static final class MEDIA {
        public static final String IMAGE = "image";
        public static final String GIF = "gif";
        public static final String VIDEO = "video";
    }

    public static final class TIME_LAPSE {
        public static final int DEFAULT = 2000;
    }

    public static final class DATE{
        public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    }

    public class SERVICE {
        public static final String MAIN_SERVER = "https://nasa-apod-server.herokuapp.com/";
    }
}
