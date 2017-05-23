package hackerrank.ChoosingWhiteBalls;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;


public class Solution {
    static int n;

    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        int n = in.nextInt();
//        int k = in.nextInt();
//        String balls = in.next();
        int k = 2;
        String balls = "WBWB";
        double solution = solve(balls, k);
        System.out.println(solution);
        // your code goes here
        // for all possible state transitions, i.e. all possible index, get the expectation.
        // note: simmetry: there are n/2 possible cases.
        //// Never take a Black ball if a white is available -> don't need to max over this case, reduces branching factor.
        //// TODO
    }
     
    public static double solve(String balls, int k) {
        int startBits = toInt(balls);
        n = balls.length();
        Dp dp = new Dp(balls.length(), k-1);
        return recurseBitSet(dp, startBits, k);
    }
    
    public static double recurseBitSet(Map<BitSet, Double> dp, int bits, int kRemaining) {
        Double v = Dp.get(bits, kRemaining);
        if(kRemaining == 0) {
            return 0; // TODO optimize this by avoiding the recursive call in the first place.
        } else if(v != null) {
            return v;
        } else {
            double expected = 0;
            int k = kRemaining -1;
            int iRight = 0;
            int iLeft = bits.length() - 1;
            while(iRight < iLeft) {
                expected += Math.max(
                        reward(iRight, bits) + recurseBitSet(dp, remove(iRight, bits), k),
                        reward(iLeft, bits) + recurseBitSet(dp, remove(iLeft, bits), k)
                );
                iRight++;
                iLeft--;
            }
            expected *= 2 /(double) bits.length();
            int iMid = kRemaining / 2;
            expected += kRemaining % 2 == 0 ? 0 : reward(iMid, bits) + recurseBitSet(dp, remove(iMid, bits), k);
            dp.put(bits, expected);
            return expected;
        }
    }
    
    public static BitSet remove(int idx, BitSet bits) {
        BitSet newBits = new BitSet(bits.length()-1);
        for(int i=0; i < idx; i++) {
            newBits.set(i,bits.get(i));
        }
        for(int i=idx+1; i < bits.length(); i++) {
            newBits.set(i-1,bits.get(i));
        }
        return newBits;
    }
    
    public static double reward(int idx, BitSet bits) {
        return bits.get(idx) ? 1 : 0;
    }
    
    public static void recurseInts() {
        throw new RuntimeException("TODO");
    }
    
    public static void solveUsingInts(String balls) {
        // ArrayList<ArrayList<Integer>> dp = new ArrayList<>(); // need this in order to store all possible states? .. but do I ? Should I instead use a map? maybe.
    }
    
    public static int toInt(String balls) {
        int pow2 = 1;
        int bits = 0;
        for(int i=0; i < balls.length(); i++) {
            bits += balls.charAt(i) != 'W' ? 0 : pow2;
            pow2 = pow2 << 1;
        }
        return bits;
    }
}
