package me.shouheng.letscorp.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import me.shouheng.commons.util.LogUtils;

/**
 * @author shouh
 * @version $Id: Util, v 0.1 2018/6/23 18:52 shouh Exp$
 */
public class Util {

    private static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:sszzz";

    public static int parseInt(String str) {
        StringBuilder v = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                v.append(str.charAt(i));
            } else {
                break;
            }
        }
        try {
            return Integer.parseInt(v.toString());
        } catch (Exception e) {
            return 0;
        }
    }

    public static long parseDateTime(String source) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT, Locale.US);
            Date date = sdf.parse(source);
            return date.getTime();
        } catch (Exception e) {
            LogUtils.d("source:"+source + ", " + e);
        }
        return 0;
    }
}
