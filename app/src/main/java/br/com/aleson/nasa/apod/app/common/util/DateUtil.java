package br.com.aleson.nasa.apod.app.common.util;

import java.util.Random;

public class DateUtil {

    public String getRandomDate() {
        return generate();
    }

    private String randomDateValue;
    private int[] monthRange = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private int todayDay;
    private int todayMonth;
    private int todayYear;


    public DateUtil(String date) {
        this.todayYear = Integer.parseInt(date.substring(0, 4));
        this.todayMonth = Integer.parseInt(date.substring(6, 7));
        this.todayDay = Integer.parseInt(date.substring(9, 10));
    }

    private String generate() {
        Random year = new Random(Double.doubleToLongBits(Math.random()));
        Random month = new Random(System.currentTimeMillis());
        Random day = new Random(System.currentTimeMillis());
        int yearR = 1995 + year.nextInt((todayYear - 1995) + 1);
        int monthR = 1 + month.nextInt((12 - 1) + 1);
        int dayR = 1 + day.nextInt((31 - 1) + 1);
        if (monthR == 2 && dayR >= 28) {
            this.generate();
        }
        if (yearR == 1995 && monthR < 6) {
            this.generate();
        }
        if (yearR == 1995 && monthR == 6 && dayR < 16) {
            this.generate();
        }
        if (todayYear == yearR && monthR > todayMonth) {
            this.generate();
        }
        if (todayYear == yearR && todayMonth == monthR && todayDay > dayR) {
            this.generate();
        }
        if (!validateMonth(monthR, dayR)) {
            this.generate();
        }
        if (todayDay == dayR && todayMonth == monthR && todayYear == yearR) {
            this.generate();
        }
        if (todayDay >= dayR && todayMonth > monthR && todayYear == yearR) {
            this.generate();
        }
        return yearR + "-" + String.format("%01d", monthR) + "-" + String.format("%01d", dayR);
    }

    private boolean validateMonth(int monthR, int dayR) {
        return dayR <= monthRange[--monthR];
    }

    public String getRequestFormatedDate(int year, int month, int dayOfMonth) {
        String formatedDate = "";
        formatedDate = String.valueOf(year);
        formatedDate += "-" + castMonth(++month);
        formatedDate += "-" + castDay(dayOfMonth);
        return formatedDate;
    }

    private String castMonth(int month) {
        return String.valueOf(month).length() == 1 ? "0" + String.valueOf(month) : String.valueOf(month);
    }

    private String castDay(int day) {
        return String.valueOf(day).length() == 1 ? "0" + String.valueOf(day) : String.valueOf(day);
    }
}
