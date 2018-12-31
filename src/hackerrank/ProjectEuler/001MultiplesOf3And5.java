import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    private static long arithmeticSeries(long n) {
        return n * (n+1) / 2 ;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        for(int a0 = 0; a0 < t; a0++){
            long n = in.nextInt();
            n--;  // because we want the sum of all values below N
            long res = arithmeticSeries(n/3)*3 + arithmeticSeries(n/5)*5 - arithmeticSeries(n/15) * 15;
            System.out.println(res);
        }
    }
}