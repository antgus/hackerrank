package hackerrank.Equal;

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int numTests = in.nextInt();
        for(int i=0; i < numTests; i++) {
            int[] choc = readInput(in);
            solve(choc);
        }
    }
    
    public static void solve(int[] choc) {
        // possible actions:
        // increase everyone's chocolates except the chosen index by:
        // +5, +2 or +1
        
        // first, normalize the chocolates so 0 == min, because all we care about is the deltas
        int min = IntStream.of(choc).min().getAsInt();
        for(int i=0; i < choc.length; i++) {
            choc[i] -= min;
        }
        
        int minOps = Integer.MAX_VALUE;
        for(int offset =0; offset < 5; offset++) { // an offset >= 5 can't be optimal because it would just add a +5 operation to all elements of the vector
            int totalOps = 0;
            for(int c: choc) {
                c += offset;
                // c = 5*x + 2*y + 1*z
                int x = c/5;
                int y = (c%5)/2;
                int z = ((c%5)%2);
                totalOps += x + y + z;
            }
            minOps = Math.min(totalOps, minOps);
        }
        System.out.println(minOps);
    }
    
    public static int[] readInput(Scanner in) {
        int n = in.nextInt();
        int[] ar = new int[n];
        for(int i=0; i < n; i++) {
            ar[i] = in.nextInt();
        }
        return ar;
    }
}