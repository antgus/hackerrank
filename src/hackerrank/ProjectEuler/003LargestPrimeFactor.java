import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static long findTheLargestPrimeFactorOf(long N) {
        // Theorem: the largest prime factor of a number N is either N or must be <= sqrt(N)
        // Proof: Assume N = f1 * f2
        // * f1 >= sqrt(N)
        // * f2 >= sqrt(N)
        // then f1*f2 > N, which breaks the original assumption. Hence either f1 or f2 must be < sqrt(N).

        // factorize as much as possible using all prime factors:
        long largestPrimeFactor = 1;
        long f = 2; // factor
        for(f = 2; f*f <= N; f++) {
            if (N % f == 0) largestPrimeFactor = f;

            while(N % f == 0) { // f is a factor of N
                N /= f;
            }
        }
        if (N > 1) largestPrimeFactor = N; // Could not further divide N -> N has no factors -> N must be prime.
        return largestPrimeFactor;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        for(int a0 = 0; a0 < t; a0++){
            long n = in.nextLong();

            long solution = findTheLargestPrimeFactorOf(n);
            System.out.println(solution);
        }
    }
}