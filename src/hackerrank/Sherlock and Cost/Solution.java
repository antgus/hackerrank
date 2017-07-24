import java.io.*;
import java.util.*;

public class Solution {

    /*
    https://www.hackerrank.com/challenges/sherlock-and-cost
    Statement:
    1 <= Ai <= Bi
    S_n = sum_i=2_to_n |A_i-A_{i-1}|

    Conclusions:
    - There are only two possibilities for each Ai as to maximize the sum, Either Ai=1 or Ai=Bi
      Proof: missing
    - Note that S_n = S_{n-1} + |A_n - A_{n-1}|
    - Rest of conclusions still missing
    */


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int numTests = in.nextInt();
        for(int i=0; i < numTests; i++) {
            int N = in.nextInt();
            int[] b = new int[N];
            for(int k=0; k < N ; k++) {
                b[k] = in.nextInt();
            }
            int maxSum = findMaxSum(b);
            System.out.println(maxSum);
        }
    }

    public static int findMaxSum(int[] b) {
        int high = 0;
        int low = 0;
        for(int i=1; i < b.length; i++) {
            int high_to_low_diff = Math.abs(b[i-1] - 1);
            int low_to_high_diff = Math.abs(b[i] - 1);
            int high_to_high_diff = Math.abs(b[i] - b[i-1]);

            int low_next = Math.max(low, high + high_to_low_diff);
            int high_next = Math.max(high + high_to_high_diff, low + low_to_high_diff);

            low = low_next;
            high = high_next;
        }
        return Math.max(high, low);
    }
}