package org.chun.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

public class IpAddressUtil {

  private static final int IP_ADDRESS_MAX_LENGTH = 39;

  private static final String LOCAL_ADDRESS = "127.0.0.1";

  // 外部ip
  private static final Pattern EXTERNAL_IP_ADDRESS_PATTERN = Pattern.compile("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");

  // 內部ip
  private static final List<Pattern> INTERNAL_IP_ADDRESS_PATTERNS = new ArrayList<>();

  static {

    // 24位元區塊 - 10.0.0.0 ~ 10.255.255.255
    INTERNAL_IP_ADDRESS_PATTERNS.add(Pattern.compile("^(10\\.)((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){2}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$"));

    // 20位元區塊 - 172.16.0.0 ~ 172.31.255.255
    INTERNAL_IP_ADDRESS_PATTERNS.add(Pattern.compile("^(172\\.)(1[6-9]|2[0-9]|3[0-1]?).(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?).(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$"));

    // 16位元區塊 - 192.168.0.0 ~ 192.168.255.255
    INTERNAL_IP_ADDRESS_PATTERNS.add(Pattern.compile("^(192\\.168\\.)(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?).(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$"));
  }


  public static boolean isIpAddress(String address) {

    if (StringUtils.isBlank(address)) {
      return false;
    }

    if (address.length() > IP_ADDRESS_MAX_LENGTH) {
      return false;
    }

    return EXTERNAL_IP_ADDRESS_PATTERN.matcher(address).matches();
  }


  public static boolean isInternalIpAddress(String address) {

    if (StringUtils.isBlank(address)) {
      return false;
    }

    if (address.length() > IP_ADDRESS_MAX_LENGTH) {
      return false;
    }

    if (LOCAL_ADDRESS.equals(address)) {
      return true;
    }

    for (Pattern pattern : INTERNAL_IP_ADDRESS_PATTERNS) {

      return pattern.matcher(address).matches();
    }

    return false;
  }


  public static boolean isExternalIpAddress(String address) {

    if (StringUtils.isBlank(address)) {
      return false;
    }

    if (address.length() > IP_ADDRESS_MAX_LENGTH) {
      return false;
    }

    if (LOCAL_ADDRESS.equals(address)) {
      return false;
    }

    // 不能為內部ip
    for (Pattern pattern : INTERNAL_IP_ADDRESS_PATTERNS) {

      if (pattern.matcher(address).matches()) {
        return false;
      }
    }

    return EXTERNAL_IP_ADDRESS_PATTERN.matcher(address).matches();
  }
}
