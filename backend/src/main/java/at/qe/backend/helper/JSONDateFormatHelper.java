package at.qe.backend.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JSONDateFormatHelper {

    public static String format(Date date) {
        if (date == null) {
            return "-";
        }
        return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(date);
    }
    private JSONDateFormatHelper() {
    }
}
