package hackerrank.TheGreatXor;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int q = in.nextInt();
        for(int a0 = 0; a0 < q; a0++){
            long x = in.nextLong();
            long solution = solveFast(x);
            System.out.println(solution);
        }
    }
    
    public static long solveNaive(long x) {
        long countLarger = 0;
        for(long i=1; i < x; i++) {
            long result = x ^ i;
            if(result > x) {
                countLarger++;
            }
        }
        return countLarger;
    }
    
    public static long solveFast(long x) {
        if(isPowerOfTwo(x)) {
            return x - 1;
        } else {
            return nextPowerOfTwo(x) - x - 1;
        }
    }
    
    public static long nextPowerOfTwo(long x) {
        // note: this apparently doesn't work for x > 4611686018427387904
        // taken from: http://stackoverflow.com/questions/1322510/given-an-integer-how-do-i-find-the-next-largest-power-of-two-using-bit-twiddlin
        x--;
        x |= x >> 1;   // Divide by 2^k for consecutive doublings of k up to 32,
        x |= x >> 2;   // and then or the results.
        x |= x >> 4;
        x |= x >> 8;
        x |= x >> 16;
        x |= x >> 32;
        x++;
        return x;
    }
    
    public static boolean isPowerOfTwo(Long x) {
        // http://stackoverflow.com/questions/600293/how-to-check-if-a-number-is-a-power-of-2
         return (x & (x - 1)) == 0;
    }
}
