package org.chun.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.chun.helper.ThreadDateFormatter;

public class DateTimeUtil {


  public static String format(LocalDateTime localDateTime) {

    ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
    return zdt.format(ThreadDateFormatter.DTF.get());
  }


  public static LocalDateTime parse(String str) {

    return LocalDateTime.parse(str, ThreadDateFormatter.DTF.get());
  }


  public static long toEpochMilli(LocalDateTime localDateTime) {

    ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
    return zdt.toInstant().toEpochMilli();
  }


  public static LocalDateTime ofInstant(long timestamp) {

    Instant instant = Instant.ofEpochMilli(timestamp);
    return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
  }


  public static Instant toInstant(LocalDateTime localDateTime) {

    return localDateTime.atZone(ZoneId.systemDefault()).toInstant();
  }


  public static LocalDateTime min(LocalDateTime t1, LocalDateTime t2) {

    if (null == t1) {
      return t2;
    }

    if (null == t2) {
      return t1;
    }

    if (t1.isBefore(t2)) {
      return t1;
    }

    return t2;
  }


  public static LocalDateTime max(LocalDateTime t1, LocalDateTime t2) {

    if (null == t1) {
      return t2;
    }

    if (null == t2) {
      return t1;
    }

    if (t1.isAfter(t2)) {
      return t1;
    }

    return t2;
  }

}
