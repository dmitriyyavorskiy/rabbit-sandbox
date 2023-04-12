package com.icemobile.rabbitsandbox.commons.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@Slf4j
@UtilityClass
public class SerializationUtils {

    public static final String DATETIME_FORMAT_STRING = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static final String[] DATETIME_FORMAT_STRINGS = {DATETIME_FORMAT_STRING, "yyyy-MM-dd'T'HH:mm:ssZZZZZ", "yyyy-MM-dd'T'HH:mm:ssZZZZZ", "yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ", "yyyy-MMM-dd'T'HH:mm:ss.SSSZZZZZ", "yyyy-MM-dd HH:mm:ssZZZZZ"};

    public static OffsetDateTime parseDateTimeString(String value) {

        if (checkValue(value) == null) {
            return null;
        }

        for (String format : DATETIME_FORMAT_STRINGS) {
            try {
                return OffsetDateTime.parse(value, DateTimeFormatter.ofPattern(format, Locale.US));
            } catch (Exception e) {
            }
        }

        if (!StringUtils.endsWith(value, "Z")) {
            value += "Z";
            for (String format : DATETIME_FORMAT_STRINGS) {
                try {
                    return OffsetDateTime.parse(value, DateTimeFormatter.ofPattern(format, Locale.US));
                } catch (Exception e) {
                }
            }
        }

        log.error("Cannot parse a data string {}", value);
        return null;
    }

    public static Date parseStringToDate(String value) {
        if (checkValue(value) == null) {
            return null;
        }

        for (String format : DATETIME_FORMAT_STRINGS) {
            try {
                return DateUtils.parseDate(value, Locale.US, format);
            } catch (Exception e) {
            }
        }

        log.error("Cannot parse a data string {}", value);
        return null;
    }

    private static String checkValue(String value) {
        if (value == null) {
            return null;
        }
        // we should handle AUG and other stuff like this
        if (value.charAt(4) == '-' && value.charAt(8) == '-') {
            value = replaceByIndex(value, 6, String.valueOf(value.charAt(6)).toLowerCase());
            value = replaceByIndex(value, 7, String.valueOf(value.charAt(7)).toLowerCase());
        }

        // handle 1 or 2 decimal after point e.g. 2015-08-20T14:11:56.16Z
        if (value.indexOf(".") > 10 && value.indexOf("Z") > 10) {
            int gap = value.indexOf("Z") - value.indexOf(".");
            log.debug("Gap is {} for {}", gap, value);
            if (gap != 4) {
                log.error("After dot less then 3 symbols before Z for string {}", value);
                return null;
            } else {
                String millisecondsValue = value.substring(value.indexOf("."), value.indexOf("Z"));
                if (millisecondsValue.equals(".000")) {
//					log.error("Empty milliseconds for string {}", value);
//					return null;
                }
            }

        }
        return value;
    }

    public static String parseDateTimeToString(OffsetDateTime value) {
        String date = value.format(DateTimeFormatter.ofPattern(DATETIME_FORMAT_STRING));
        log.debug("Date {} became {}", value, date);
        return date;
    }

    public static String replaceByIndex(String value, int index, String replacement) {
        StringBuffer buf = new StringBuffer(value);
        buf.replace(index, index + 1, replacement);
        return buf.toString();
    }
}
