package usefuljavas;

/**
 * My String Util library 
 *
 *  project: Web eigo
 *
 * @author Ransom Barber 2005/05/23
 *
 * note: more stuff I made early on, there's probably a better way
 *
 */

public final class MyStrUtil {
 
 // will not have any actual instances!


 public static String padZerosLeft (String s, int width) {
  String r = s;
  while (r.length() < width) {
   r = "0" + r;
  }
  return r;
 }

 public static String right (String s, int width) {
  int l = s.length();
  int i = l - width;
  if (i < 0) { i = 0; }
  return s.substring(i, l);
 }

 public static String replaceSubString (String s, String target, String value) {
  String r = s;
  int tl = target.length();
  int vl = value.length();
  int l = r.length() - (tl - 1);
  int i = 0;
  while (i < l) {
   if (r.substring(i, i + tl).compareTo(target) == 0) {
    r = r.substring(0, i) + value + r.substring(i + tl);
    l = r.length() - (tl - 1);
    i = i + vl; 
   } else {
    i++;
   }
  }
  return r;
 }


 public static int findString (String s, String target, int offset) {
  int tl = target.length();
  int i = offset;
  int l = s.length() - (tl - 1);
  int found = -1;
  while (i < l) {
   if (s.substring(i, i + tl).equals(target)) {
    found = i;
    i = l;    
   } else {
    i++;
   }
  }
  return found;
 }


 public static int findChar (String s, char target, int offset) {
  int l = s.length();
  int i = offset;
  int found = -1;
  while (i < l) {
   if (s.charAt(i) == target) {
    found = i;
    i = l;
   }
   i++;
  }
  return found;
 }

 public static char toLowerChar(char c) {
  return String.valueOf(c).toLowerCase().charAt(0);
 }

 public static char toUpperChar(char c) {
  return String.valueOf(c).toUpperCase().charAt(0);
 }

 public static boolean isUpperCase(char c) {
  return ((c >= 'A') && (c <= 'Z'));
 }

 public static boolean isLowerCase(char c) {
  return ((c >= 'a') && (c <= 'z'));
 }


}
