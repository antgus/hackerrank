import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    // The largest number that divides both a and b with remainder=0
    // GreatestCommonDivisor calculated with the Euclidean algorithm: https://en.wikipedia.org/wiki/Euclidean_algorithm
    public static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a%b);
    }

    // LeastCommonMultiple
    public static int lcm(int a, int b) {
        return a / gcd(a, b) * b;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        for(int a0 = 0; a0 < t; a0++){
            int n = in.nextInt();
            int res = 1;
            // lcm([a,b,c,d]) = lcm(lcm([a,b,c], d)) = lcm(lcm([a,b], c), d) ,...
            // proof =?. The property is listed in https://en.wikipedia.org/wiki/Least_common_multiple
            for(int i=1; i <= n; i++) {
                res = lcm(res, i);
            }
            System.out.println(res);
        }
    }
}