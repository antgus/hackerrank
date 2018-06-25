import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;

public class Solution {

    /*
     * Bricks game is a DP problem to maximize the player's score. The main insight is that
     * the two player game can be solved without considering both player's moves explicitly.
     * It's a zero-sum game, so the total score is the max score - optimal_score_of_the_other_player.
     */
    static long bricksGame(int[] arr) {
        reverse(arr);
        /*
         * DP:
         * T[i] = sum_0_to_i arr[i]   // == cumulative sum
         * S[i] = max {
            T[i] - S[i-1],
            T[i] - S[i-2],
            T[i] - S[i-3]
         }
         */
        long[] total = new long[arr.length]; // cumulative sum array
        total[0] = arr[0];
        for(int i=1; i< total.length; i++) {
            total[i] = total[i-1] + arr[i];
        }

        long[] S = new long[arr.length];
        for(int i=0; i < Math.min(3,S.length); i++) {
            S[i] = total[i];
        }

        for(int i=3; i < S.length; i++) {
            long S1 = total[i] - S[i-1];
            long S2 = total[i] - S[i-2];
            long S3 = total[i] - S[i-3];
            S[i] = Math.max(S1,Math.max(S2,S3));
        }
        return S[S.length-1];
    }

    private static void reverse(int[] a) {
        int left = 0;
        int right = a.length-1;
        while(left < right) {
            int aux = a[left];
            a[left] = a[right];
            a[right] = aux;
            left++;
            right--;
        }
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        // code for parsing input file is here
    }
}