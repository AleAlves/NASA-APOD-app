package com.aleson.example.nasaapodapp.common.constants;


public class Constants {

    public static final String TOKEN_KEY = "TOKEN_KEY";

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
        public static final String RQUEST_ID_TOKEN = "918818046831-mt24tfnfgsrbd1o5rsf9l6ts2vb82h92.apps.googleusercontent.com";
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
            public static final String FOLDER = "NASA%APOD%App/";
        }
    }

    public static final class MEDIA {
        public static final String IMAGE = "image";
        public static final String GIF = "gif";
        public static final String VIDEO = "video";
    }

    public static final class TIME_LAPSE {
        public static final int DEFAULT = 1000;
    }

    public static final class DATE {
        public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    }

    public class SERVICE {
        public static final String MAIN_SERVER = "https://nasa-apod-server.herokuapp.com/";
    }
}
