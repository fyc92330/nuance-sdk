package org.chun.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

public class XssUtils {

  private static final int XSS_MIN_LENGTH = 6;

  private static final List<Pattern> XSS_PATTERNS = new ArrayList<>();

  static {

    // script fragments
    XSS_PATTERNS.add(Pattern.compile("&lt;script&gt;(.*?)&lt;/script&gt;", Pattern.CASE_INSENSITIVE));
    XSS_PATTERNS.add(Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE));

    // src='...'
    XSS_PATTERNS.add(Pattern.compile("src[\r\n]*=[\r\n]*\\'(.*?)\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
    XSS_PATTERNS.add(Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));

    // lonely script tags
    XSS_PATTERNS.add(Pattern.compile("&lt;/script&gt;", Pattern.CASE_INSENSITIVE));
    XSS_PATTERNS.add(Pattern.compile("</script>", Pattern.CASE_INSENSITIVE));
    XSS_PATTERNS.add(Pattern.compile("&lt;script(.*?)&gt;", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
    XSS_PATTERNS.add(Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));

    // eval(...)
    XSS_PATTERNS.add(Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));

    // expression(...)
    XSS_PATTERNS.add(Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));

    // javascript:...
    XSS_PATTERNS.add(Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE));

    // vbscript:...
    XSS_PATTERNS.add(Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE));

    // onload=... || onload(...)=...
    XSS_PATTERNS.add(Pattern.compile("\\bonload(=|(\\(.*\\)=)|\\b.*=)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));

    // onclick=... || onclick(...)=...
    XSS_PATTERNS.add(Pattern.compile("\\bonclick(=|(\\(.*\\)=)|\\b.*=)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
  }

  public static String stripXss(String str) {

    if (StringUtils.isBlank(str)) {
      return str;
    }

    if (str.length() < XSS_MIN_LENGTH) {
      return str;
    }

    str = str.replaceAll("\0", "");

    for (Pattern pattern : XSS_PATTERNS) {

      str = pattern.matcher(str).replaceAll("");
    }

    return str;
  }

}
