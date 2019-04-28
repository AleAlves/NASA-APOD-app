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
        public static final int SERVER_ERROR = 500;
        public static final int UNAVAILABLE_APOD = 601;
    }


}
