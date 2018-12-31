import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static long[] solveAllUpTo(int n) {
        long sum = 0;
        long sumOfSquares = 0;
        long solutions[] = new long[n+1];
        for(int i=1; i <= n; i++) {
            sum += i;
            sumOfSquares += i*i;
            solutions[i] = sum*sum - sumOfSquares;
        }
        return solutions;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();

        ArrayList<Integer> nList = new ArrayList<>();
        for(int a0 = 0; a0 < t; a0++){
            nList.add(in.nextInt());
        }
        int nMax = Collections.max(nList);

        // Step 1: precompute all solutions and store in array solutions[]
        long[] solutions = solveAllUpTo(nMax);
        // Step 2: output the solution for each test case
        for(int n: nList) {
            System.out.println(solutions[n]);
        }

        // NOTE: this problem can also be solved by using the formulas to directly compute the result of the summation series:
        // Sum[n]_i=1_to_n = n(n+1)/2
        // Sum[n^2]_i=1_to_n = n(n+1)(2n+1)/6  . Derivation : http://mathforum.org/library/drmath/view/56920.html
    }
}