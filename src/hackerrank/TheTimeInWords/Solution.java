import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Solution {
    private static String[] numberStrings = new String[] {
            "zero",
            "one",
            "two",
            "three",
            "four",
            "five",
            "six",
            "seven",
            "eight",
            "nine",
            "ten",
            "eleven",
            "twelve",
            "thirteen",
            "fourteen",
            "quarter",
            "sixteen",
            "seventeen",
            "eighteen",
            "nineteen",
            "twenty",
            "twenty one",
            "twenty two",
            "twenty three",
            "twenty four",
            "twenty five",
            "twenty six",
            "twenty seven",
            "twenty eight",
            "twenty nine"};

    // Complete the timeInWords function below.
    static String timeInWords(int h, int m) {

        if (m > 30) {
            int left = 60-m;
            String mins = minutesToString(left);
            if(h == 12) {
                h = 0;
            }
            return mins + " to " + numberStrings[h + 1];
        } else if(m == 30) {
            return "half past " + numberStrings[h];
        } else if(m == 0) {
            return numberStrings[h] + " o' clock";
        } else {
            String mins = minutesToString(m);
            return mins + " past " + numberStrings[h];
        }
    }

    private static String minutesToString(int m) {
        String mins = numberStrings[m];
        if (m != 15) {
            if (m == 1) {
                mins += " minute";
            } else {
                mins += " minutes";
            }
        }
        return mins;
    }

    private static void debugPrintAll() {
        for(int h=1; h <= 12; h++) {
            for(int m=0; m < 60; m++) {
                System.out.println(timeInWords(h, m));
            }
        }
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int h = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int m = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        String result = timeInWords(h, m);

        bufferedWriter.write(result);
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
