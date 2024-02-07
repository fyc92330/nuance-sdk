package org.chun.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomUtils {


  private static final String DIGITS = "0123456789";

  private static final String ALPHABETIC = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

  private static final String ALPHANUMERIC = DIGITS + ALPHABETIC;


  public static String randomDigitChars(int count) {

    return RandomStringUtils.random(count, DIGITS);
  }


  public static String randomAlphanumericChars(int count) {

    return RandomStringUtils.random(count, ALPHANUMERIC);
  }

}
