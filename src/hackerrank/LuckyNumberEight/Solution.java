package hackerrank.LuckyNumberEight;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) {
        //Scanner in = new Scanner(System.in);
        //int n = in.nextInt();
        //String number = in.next();
        
        List<String> tests = new ArrayList<>();
        tests.addAll(Arrays.asList("8","88","288","2288","22288","222288","2222288", "222888", "228288","282288","822288", "222288"));
        tests.addAll(Arrays.asList("1","11","111","1111","18","118","1118", "88", "188","1188","11188", "111188", "811188", "1111188", "8111188", "1888", "11888", "111888"));
        tests.addAll(Arrays.asList("800", "8000", "80000","800000","8000000", "80000000"));
        tests.addAll(Arrays.asList("808", "8008", "800800","8000008","8000080", "8000800", "8008000"));
        tests.addAll(Arrays.asList("8","88","888","8888","88888"));
        tests.addAll(Arrays.asList("28","288","2888","28888","288888"));
//        List<String> tests = new ArrayList<>();
//        for(int i=0; i < 10; i++) {
//            StringBuilder b = new StringBuilder();
//            for(int k=1; k < 6; k++) {
//                b.append(i);
//                tests.add(b.toString());
//            }
//        }
        
        // 3rd digit %2 == 1 -> 04,12,20,28,36,44,52,60,68,76,84,92
        // 3rd digit %2 == 0 -> 00,08,16,24,32,40,48,56,64,72,80,88,96  (valid if 3rd digit is 0)
        
        for(String s: tests) {
            if(solveNaive(s) != solveFast(s)) {
                System.out.println("SOLUTIONS DIFFER!");
            } else {
                System.out.println("OK!");
            }
            //System.out.println(s + " -> " + solveNaive(s) + " FAST= " + solveFast(s));
        }
        
        
//        for(int i=0; i < 2000; i++) {
//            if(i % 8 == 0) {
//                System.out.println(i);
//            }
//        }
//        
        // given n digits, how can I know how many end like that?
        // how many distinct ways to order things?
        // a - 1
        // ab - 3
        // abc - a, b, c , ab,bc, ac, abc - 7
        // abcd = 15
        // number of possibilities = 2^n - 1
        
        // now, how many possibilities include the following at the end?
    }
    
    
    /*
int len,ar[MAXLEN],dp[MAXLEN][MAXN];
int fun(int idx,int m)
{
    if(idx==len)                        // One sub-sequence completed
        return (m==0);                    // return 1 only if modulus by n is 0
    if(dp[idx][m]!=-1)
        return dp[idx][m];
    int ans=fun(idx+1,m);                // skip this element in current sub-sequence
    ans+=fun(idx+1,(m*10+ar[idx])%n);    // Include this element. Find the new modulo by 'n' and pass it recursively
    return dp[idx][m]=ans;
}
int main()
{
    // input len , n , array , array is the int[], n is the modulus, len = number of digits
    memset(dp,-1,sizeof(dp));
    printf("%d\n",fun(0,0));            // initially we begin by considering array of length 1 i.e. upto index 0
    return 0;
}
     */
    
    public static int solveFast(String s) {
        int[] digits = new int[s.length()];
        for(int i=0; i < s.length(); i++) {
            digits[i] = s.charAt(i);
        }
        int modulus = 8;
        int[][] dp = new int[digits.length][modulus];
        for(int i=0; i < dp.length; i++) {
            for(int j=0; j < dp[i].length; j++) {
                dp[i][j] = -1;
            }
        }
        return solveDp(digits, dp, modulus, 0, 0) - 1;
    }
    
    public static int solveDp(int[] digits, int[][] dp, int modulus, int idx, int m) {
        if(idx == digits.length) {
            if(m == 0) {
                return 1;
            } else {
                return 0;
            }
        } else if(dp[idx][m] != -1) {
            return dp[idx][m];
        } else {
            long ans = solveDp(digits, dp, modulus, idx+1, m); // skip element in the current subsequence
            ans += solveDp(digits, dp, modulus, idx+1, (m*10 + digits[idx])%modulus); // include element. Pass the new modulo recursively
            dp[idx][m] = (int) (ans% 1000000007);
            return dp[idx][m];
        }
    }
    
    public static int solveNaive(String original) {
        List<String> all = generateAllSubSequences(original);
        int count = 0;
        for(String s: all) {
            if(Integer.parseInt(s) % 8 == 0) {
                count++;
            }
        }
        return count;
    }
    
    public static List<String> generateAllSubSequences(String s) {
        List<String> all = new ArrayList<>(((int)Math.pow(2, s.length())) -1);
        StringBuilder builder = new StringBuilder(s);
        int nDigits = (int) Math.pow(2,s.length());
        for(int i=1; i < nDigits; i++) {
            builder.setLength(0);
            String binary = Integer.toBinaryString(i);
            int offset = s.length() - binary.length();
            for(int k=0; k < binary.length(); k++) {
                if(binary.charAt(k) == '1') {
                    builder.append(s.charAt(offset + k));
                }
            }
            all.add(builder.toString());
        }
        return all;
    }
}

