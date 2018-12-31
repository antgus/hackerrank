import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    /**
     * Solution is to simply compute the fibonacci numbers and only consider the even ones for the sum.
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        long fib = 1;
        for(int a0 = 0; a0 < t; a0++){
            long n = in.nextLong();
            BigInteger sum = BigInteger.valueOf(0);
            long longSum = 0; // store the sum in a long for as long as that doesn't cause overflow -> faster, lower mem consumption;
            boolean fitsInLong = true;
            long fn = 2; // f[n]
            long fn_1 = 1;  // f[n-1]
            while(fn <= n) {
                if ( fn % 2 == 0) { // is even
                    if(fitsInLong) {
                        try {
                            longSum = Math.addExact(longSum, fn);
                        } catch(ArithmeticException e) {
                            fitsInLong = false;
                            sum = BigInteger.valueOf(longSum).add(BigInteger.valueOf(fn));
                        }
                    } else {
                        sum = sum.add(BigInteger.valueOf(fn));
                    }
                }
                long old_fn = fn;
                fn = fn + fn_1;
                fn_1 = old_fn;
            }
            if(fitsInLong) {
                System.out.println(longSum);
            } else {
                System.out.println(sum);
            }
        }
    }
}