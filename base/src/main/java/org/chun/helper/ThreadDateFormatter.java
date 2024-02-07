package org.chun.helper;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class ThreadDateFormatter {

  public static final String DATE_FORMAT_ISO8601 = "yyyy-MM-dd'T'HH:mm:ssXXX";

  public static final ThreadLocal<DateTimeFormatter> DTF = ThreadLocal.withInitial(() -> DateTimeFormatter.ofPattern(ThreadDateFormatter.DATE_FORMAT_ISO8601));

  public static final ThreadLocal<SimpleDateFormat> SDF = ThreadLocal.withInitial(() -> new SimpleDateFormat(ThreadDateFormatter.DATE_FORMAT_ISO8601));


}
