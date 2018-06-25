import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Solution {

    // Problem solution code is here:
    static int[] climbingLeaderboard(int[] scores, int[] alice) {
        // alice is in ascending order
        // scores are in descending order
        int[] otherRanks = new int[scores.length];
        int[] ranks = new int[alice.length];
        otherRanks[0] = 1;
        for(int i=1; i < otherRanks.length; i++) {
            otherRanks[i] = scores[i] == scores[i-1] ? otherRanks[i-1] : otherRanks[i-1] + 1;
        }
        int i = 0;
        for (int currentIdx = alice.length -1; currentIdx >= 0; currentIdx--) {
            while(i < scores.length && alice[currentIdx] < scores[i]) i++;
            if (i >= otherRanks.length) {
                ranks[currentIdx] = otherRanks[otherRanks.length-1] + 1;
            } else {
                ranks[currentIdx] = otherRanks[i];
            }
        }
        return ranks;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        // read input here
    }
}
