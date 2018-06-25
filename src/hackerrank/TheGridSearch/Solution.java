import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Solution {

    /**
     * Find the 2D pattern P in the original 2D matrix G
     */

    static String gridSearch(String[] G, String[] P) {
        int iMax = G.length - P.length + 1;
        for(int iStart=0; iStart < iMax; iStart++) {
            int jMax = G[iStart].length() - P[0].length() + 1;
            for(int jStart =0; jStart < jMax; jStart++) {
                jStart = G[iStart].indexOf(P[0], jStart);
                if (jStart == -1) break;
                if (match(G, iStart, jStart, P)) {
                    return "YES";
                }
                jStart++;
            }
        }
        return "NO";
    }

    static boolean match(String[] G, int iFirst, int jFirst, String[] P) {
        for(int i=0; i < P.length; i++) {
            for (int j=0; j < P[0].length(); j++) {
                if(G[i+iFirst].charAt(j+jFirst)
                        != P[i].charAt(j)
                        ) {
                    return false;
                }
            }
        }
        return true;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        // input reading goes here
    }
}
