package app.pptikitb.siap.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.text.format.DateUtils;
import com.google.android.gms.maps.model.LatLng;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import com.mapbox.mapboxsdk.geometry.LatLng;

/**
 * Created by fiyyanp on 2/1/2018.
 */

public class Utils {
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private static final Pattern VALID_INDONESIA_PHONE_NUMBER_REGEX =
            Pattern.compile("\\(?(?:\\+62|62|0)(?:\\d{2,3})?\\)?[ .-]?\\d{2,4}[ .-]?\\d{2,4}[ .-]?\\d{2,4}");

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public static String removeTextNull(String str) {
        return str
                .replace(", null - null", "")
                .replace(", null", "")
                ;
    }

    public static boolean validatePhone(String phoneNumber) {
        Matcher matcher = VALID_INDONESIA_PHONE_NUMBER_REGEX.matcher(phoneNumber);
        return matcher.find();
    }

    public static boolean isEmptyString(String s) {
        return (s == null || s.length() == 0);
    }

    public static Double estimatePrice(String _distance) {
        _distance = _distance.replace(',', '.');
        Double price = 50000.0;
        Double distance = Double.valueOf(_distance.replace(',', '.'));
        if (distance >= 10) {
            price = price + ((distance - 10) * 10000);
        }

        return price;
    }


    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static String estimatePrice(double _distance) {
        Double price = 50000.0;
        if (_distance >= 10) {
            price = price + ((_distance - 10) * 10000);
        }

        return String.valueOf(price);
    }


//    public static String convertMongoDate(String val) {
//        ISO8601DateFormat df = new ISO8601DateFormat();
//        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        try {
//            Date d = df.parse(val);
//            String finalStr = outputFormat.format(d);
//            val = finalStr;
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return val;
//    }
//
//    public static String convertMongoDateToAgo(String val) {
//        ISO8601DateFormat df = new ISO8601DateFormat();
//        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        try {
//            Date d = df.parse(val);
//            String finalStr = outputFormat.format(d);
//            val = getTimeAgo(finalStr);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return val;
//    }
//
//    public static Date convertMongodateToDate(String val) {
//        ISO8601DateFormat df = new ISO8601DateFormat();
//        Date date = null;
//        try {
//            date = df.parse(val);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return date;
//    }
//
//    public static String convertMongoDateToAgo7(String val) {
//        ISO8601DateFormat df = new ISO8601DateFormat();
//        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        try {
//            final long millisToAdd = 28_800_000; //7 hours
//            Date d = df.parse(val);
//            d.setTime(d.getTime() - millisToAdd);
//            String finalStr = outputFormat.format(d);
//
//            val = finalStr;
//            //Log.i("7", finalStr);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return val;
//    }

    public static String getTimeAgo(String dateInput) {
        String timeformat = dateInput;
        try {
            long now = System.currentTimeMillis();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date convertedDate = dateFormat.parse(dateInput);
            CharSequence relavetime1 = DateUtils.getRelativeTimeSpanString(
                    convertedDate.getTime(),
                    now,
                    DateUtils.SECOND_IN_MILLIS);
            timeformat = String.valueOf(relavetime1);
        } catch (Exception e) {
            e.printStackTrace();
            return timeformat;
        }

        return timeformat;
    }


    public static boolean isContainUpperCase(String str) {
        char ch;
        boolean capitalFlag = false;
        boolean lowerCaseFlag = false;
        boolean numberFlag = false;
        for (int i = 0; i < str.length(); i++) {
            ch = str.charAt(i);
            if (Character.isDigit(ch)) {
                numberFlag = true;
            } else if (Character.isUpperCase(ch)) {
                capitalFlag = true;
            } else if (Character.isLowerCase(ch)) {
                lowerCaseFlag = true;
            }
            if (numberFlag && capitalFlag && lowerCaseFlag)
                return true;
        }
        return false;
    }


    public static Bitmap tintImage(Bitmap bitmap) {

        bitmap = bitmap.copy(bitmap.getConfig(), true);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        //paint.setColor(color);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawCircle(bitmap.getWidth()/5, bitmap.getHeight()/5, 15, paint);

        return bitmap;
    }


//    public static int generateRandomArgb(){
//        return Color.argb(255, RandomUtils.nextInt(1, 255),
//                RandomUtils.nextInt(1, 255),
//                RandomUtils.nextInt(1, 255));
//    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }


    public static float distanceBetween(LatLng from, LatLng to){
        Location loc1 = new Location("");
        loc1.setLatitude(from.latitude);
        loc1.setLongitude(from.longitude);

        Location loc2 = new Location("");
        loc2.setLatitude(to.latitude);
        loc2.setLongitude(to.longitude);
        return (float) round((loc1.distanceTo(loc2)/1000),2);

    }

//    public static String convertMongoDateAndTime(String val) {
//        ISO8601DateFormat df = new ISO8601DateFormat();
//        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM, yyyy,HH:mm:ss");
//        try {
//            Date d = df.parse(val);
//            String finalStr = outputFormat.format(d);
//            val = finalStr;
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return val;
//    }



}
