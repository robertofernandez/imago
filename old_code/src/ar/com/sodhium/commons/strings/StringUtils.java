package ar.com.sodhium.commons.strings;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Utility for strings processing and transformations.
 * 
 * @author Roberto G. Fernandez
 *
 */
public class StringUtils {

    public static boolean equals(String s1, String s2) {
        if (isBlank(s1) && isBlank(s2)) {
            return true;
        } else {
            if (isBlank(s1) || isBlank(s2)) {
                return false;
            } else {
                return s1.equals(s2);
            }
        }
    }

    public static boolean equalsIgnoreCase(String s1, String s2) {
        if (isBlank(s1) && isBlank(s2)) {
            return true;
        } else {
            if (isBlank(s1) || isBlank(s2)) {
                return false;
            } else {
                return s1.equalsIgnoreCase(s2);
            }
        }
    }

    public static boolean equalsIgnoreCaseAndTrailing(String s1, String s2) {
        if (isBlank(s1) && isBlank(s2)) {
            return true;
        } else {
            if (isBlank(s1) || isBlank(s2)) {
                return false;
            } else {
                return s1.trim().equalsIgnoreCase(s2.trim());
            }
        }
    }

    public static String getMostSignificant(String s1, String s2) {
        if (s2 == null) {
            return s1;
        }
        if (s1 == null) {
            return s2;
        }
        if ("".equals(s2)) {
            return s1;
        }
        if ("".equals(s1)) {
            return s1;
        }
        if ("null-content".equals(s2)) {
            return s1;
        }
        if ("null-content".equals(s1)) {
            return s1;
        }
        return s1;
    }

    public static boolean isNotBlank(String input) {
        if (input == null) {
            return false;
        }
        if ("".equals(input)) {
            return false;
        }
        if (input.trim().equals("")) {
            return false;
        }
        return true;
    }

    public static boolean isNotUndefined(String input) {
        if (isBlank(input)) {
            return false;
        }
        if ("_UK".equals(input)) {
            return false;
        }
        if ("_NF".equals(input)) {
            return false;
        }
        if ("_NR".equals(input)) {
            return false;
        }
        if ("null-content".equals(input)) {
            return false;
        }
        return true;
    }

    public static boolean isUndefined(String input) {
        return !isNotUndefined(input);
    }

    public static boolean isBlank(String input) {
        return !isNotBlank(input);
    }

    public static String removeSymbols(String input) {
        if (isBlank(input)) {
            return input;
        }
        String output = "";
        for (Character character : input.toCharArray()) {
            if (Character.isLetterOrDigit(character)) {
                output += character;
            }
        }
        return output;
    }

    public static String getNumbers(String input) {
        if (isBlank(input)) {
            return input;
        }
        String output = "";
        for (Character character : input.toCharArray()) {
            if (Character.isDigit(character)) {
                output += character;
            }
        }
        return output;
    }

    public static String getFirstNumber(String input) {
        if (isBlank(input)) {
            return input;
        }
        String output = "";
        boolean addedNumber = false;
        for (Character character : input.toCharArray()) {
            if (Character.isDigit(character)) {
                output += character;
                addedNumber = true;
            } else {
                if (addedNumber) {
                    return output;
                }
            }
        }
        return output;
    }

    public static boolean isNumber(String input) {
        if (isBlank(input)) {
            return false;
        }
        String cleanedInput = input.trim();
        for (Character character : cleanedInput.toCharArray()) {
            if (!Character.isDigit(character)) {
                return false;
            }
        }
        return true;
    }

    public static Double getPercentage(String input) {
        if (isBlank(input)) {
            throw new IllegalArgumentException("An empty string is not a percentage");
        }
        boolean containsSymbol = false;
        boolean numberFound = false;
        String remainingChars = "";
        for (Character character : input.toCharArray()) {
            if (Character.isDigit(character)) {
                numberFound = true;
            }
            if ("%".equals("" + character)) {
                containsSymbol = true;
                break;
            }
            if (numberFound) {
                remainingChars += character;
            }
        }
        if (!containsSymbol || !numberFound) {
            throw new IllegalArgumentException("String is not in percentage format");
        }
        try {
            return Double.parseDouble(remainingChars);
        } catch (Exception e) {
            throw new IllegalArgumentException("Can not parse number from " + remainingChars);
        }
    }

    public static int distance(String x, String y) {
        int[][] dp = new int[x.length() + 1][y.length() + 1];

        for (int i = 0; i <= x.length(); i++) {
            for (int j = 0; j <= y.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = min(dp[i - 1][j - 1] + costOfSubstitution(x.charAt(i - 1), y.charAt(j - 1)),
                            dp[i - 1][j] + 1, dp[i][j - 1] + 1);
                }
            }
        }

        return dp[x.length()][y.length()];
    }

    public static int min(int... numbers) {
        return Arrays.stream(numbers).min().orElse(Integer.MAX_VALUE);
    }

    public static int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }

    public static String getCurrentDate() {
        return formatDate(new Date());
    }

    public static String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("EEE dd/MM/yyyy HH:mm:ss zzz");
        format.setTimeZone(TimeZone.getTimeZone("GMT-03:00"));
        return format.format(date);
    }

    public static String getEnterpriseNameFromRemoteOk(String enterpriseUrl) {
        String[] splitted = enterpriseUrl.split("/");
        return splitted[splitted.length - 1];
    }

    public static String getTechnologyNameFromRemoteOkCombinedSearch(String searchUrl) {
        String[] splitted = searchUrl.split("\\+");
        return removeJobsSuffix(splitted[splitted.length - 1]);
    }

    private static String removeJobsSuffix(String input) {
        int lastIndexOf = input.lastIndexOf("-");
        if (lastIndexOf > 0) {
            return input.substring(0, lastIndexOf);
        }
        return input;
    }

    public static List<String> getTokensFromPath(String urlPath) {
        ArrayList<String> output = new ArrayList<String>();
        String[] splitted = urlPath.trim().split("/");
        if (splitted.length > 0) {
            for (String token : splitted) {
                if (isNotBlank(token)) {
                    output.add(token);
                }
            }
        }
        return output;
    }

    // FUTURE remove, use generic remove transformation
    public static String fromDiceUrl(String input) {
        String[] split = input.split("\\?");
        if (split.length > 0) {
            return split[0];
        }
        return input;
    }

    public static String removeUrlParameters(String input) {
        String[] split = input.split("\\?");
        if (split.length > 0) {
            return split[0];
        }
        return input;
    }

    public static boolean containsMonetarySymbol(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        ArrayList<String> symbols = new ArrayList<>();
        symbols.add("$");
        char poundChar = 163;
        symbols.add("" + poundChar);
        char euroChar = 8364;
        symbols.add("" + euroChar);
        for (String symbol : symbols) {
            if (input.contains(symbol)) {
                return true;
            }
        }
        return false;
    }

    public static String getCsv(List<String> elements) {
        if (elements == null) {
            return "";
        }
        String separator = "";
        String output = "";
        for (String element : elements) {
            output += separator + element;
            separator = ",";
        }
        return output;
    }

    public static String getFirstClosingJson(String input) throws Exception {
        String output = "";
        String[] characters = input.split("");
        int open = 0;
        for (String character : characters) {
            if ("{".equals(character)) {
                open++;
            } else if ("}".equals(character)) {
                open--;
            }
            output += character;
            if (open < 1) {
                break;
            }
        }
        return output;
    }

    public static String nonBlankOrReplacement(String input, String replacement) {
        if (isNotBlank(input)) {
            return input;
        } else {
            return replacement;
        }
    }

    public static String cleanupReturnedLines(String input) {
        String[] elements = input.split("\n");
        String output = "";
        String separator = "";
        for (String element : elements) {
            output += separator + element.trim();
            separator = " ";
        }
        return output;
    }

    public static String cleanupLinkedinUrl(String input) {
        String[] elements = input.split("/");
        String output = "";
        int leftElements = -1;

        for (String element : elements) {
            output += element + "/";
            if (element.contains("linkedin")) {
                leftElements = 3;
            }
            if (leftElements != -1) {
                if ("pub".equalsIgnoreCase(element)) {
                    leftElements = 5;
                }
                leftElements--;
                if (leftElements == 0) {
                    break;
                }
            }
        }
        return output;
    }

    public static String toHtmlString(String source) {
        String output = source;
        if (isBlank(source)) {
            return "";
        }
        try {
            output = source.replaceAll("\n", "<br/>");
        } catch (Exception e) {
            output = source;
        }
        return output;
    }

    public static String capitalizeFirst(String input) {
        if (input == null) {
            return "";
        }
        if (input.length() > 1) {
            return input.substring(0, 1).toUpperCase() + input.substring(1);
        } else if (input.length() > 0) {
            return input.toUpperCase();
        } else {
            return input;
        }
    }

    public static String lowCase(String input) {
        if (input == null) {
            return "";
        }
        if (input.length() > 0) {
            return input.toLowerCase();
        } else {
            return input;
        }
    }

    public static String lowerCaseFirst(String input) {
        if (input == null) {
            return "";
        }
        if (input.length() > 1) {
            return input.substring(0, 1).toLowerCase() + input.substring(1);
        } else if (input.length() > 0) {
            return input.toLowerCase();
        } else {
            return input;
        }
    }
}
