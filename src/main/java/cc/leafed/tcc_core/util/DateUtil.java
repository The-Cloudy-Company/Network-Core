package cc.leafed.tcc_core.util;

import java.sql.Timestamp;
import java.util.Date;

public class DateUtil {
    public static String getDateText(long time) {
        Timestamp ts = new Timestamp(time);
        Date date = new Date(ts.getTime());
        return date.toString();
    }
}
