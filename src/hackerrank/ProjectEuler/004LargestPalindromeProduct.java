import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static boolean isPalindromeOfLength6(StringBuilder b) {
        if(b.length() != 6) {
            return false;
        } else {
            for(int i=0; i < 3; i++) {
                if(b.charAt(i) != b.charAt(b.length()-i-1)) {
                    return false;
                }
            }
            return true;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        StringBuilder b = new StringBuilder(10);
        for(int a0 = 0; a0 < t; a0++){
            int n = in.nextInt();

            int max = 0;
            // we can do a brute force search since the 3-digit numbers are only [100:999].length <= 1000
            // which means we only need to test at most 1000 * 1000 possibilities. Since multiplication is commutative, this is further divided in half since testing 100*500 is the same as 500*100, we can skip the second one.
            for(int i = 100; i <= 999; i++) {
                for(int k=999; k >= i; k--) {
                    int res = i*k;
                    b.setLength(0);
                    b.append(res);
                    if(isPalindromeOfLength6(b) && res > max && res < n) {
                        max = res;
                    }
                }
            }
            System.out.println(max);
        }
    }
}