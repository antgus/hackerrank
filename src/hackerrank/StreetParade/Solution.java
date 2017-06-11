/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackerrank.StreetParade;

import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] a = new int[n+2];
        for(int i=0; i < n ;i++) {
            a[i+1] = in.nextInt(); // points at which music changes.
        }
        a[0] = Integer.MIN_VALUE;
        a[a.length-1] = Integer.MAX_VALUE;
        int m = in.nextInt(); // duration of contiguous listening
        int hMin = in.nextInt(); // listen to music for at least hMin
        int hMax = in.nextInt(); // listen to music at most for hMax
        
        int hStartListen = a[1] - hMax;
        int iStart = 0; // refers to the index that comes before hStart
        int iEnd = 0; // refers to the index that comes before hEnd.
        while(true) {
            int hEndListen = hStartListen + m;
            iEnd = Math.max(iEnd, iStart);// refers to the index that comes before hEnd
            int iPrevEnd = iEnd;
            while(iEnd < a.length && a[iEnd+1] <= hEndListen) iEnd++; // find the appropriate iEnd
            assert(iEnd == a.length || a[iEnd+1] > hEndListen);
            boolean mustRestart = false;
            
            // verify constraints between iPrevEnd and iEnd
            for(int i=iPrevEnd; i < iEnd; i++) {
                int hSongEnd = iEnd == a.length-1 ? hEndListen : Math.min(hEndListen, a[i+1]);
                int hSongStart = Math.max(hStartListen, a[i]);
                int deltaH = hSongEnd - hSongStart;
                System.out.println("Delta h: " + deltaH);
                assert(deltaH > 0);
                if(deltaH < hMin) {
                    iStart = i+1;
                    hStartListen = a[iStart];
                    mustRestart = true;
                    break;
                } else if(deltaH > hMax) {
                    iStart = i;
                    hStartListen = a[iStart + 1] - hMax;
                    mustRestart = true;
                    break;
                }
            }
            if(mustRestart) {
                continue;
            }
            if(hEndListen - a[iEnd] < hMin) {
                // need to move the start so that it covers hMin of the last item
                // at the same time, iStart needs to be such that hMin at the start is covered. hMax is always covered when moving the starting point forward.
                hEndListen = a[iEnd] + hMin;
                hStartListen = hEndListen - m; // this may cause problems in iStart hMin and hMax. hMax can't be a problem, because it wasn't before.
                // find the right iStart here;
                while(a[iStart+1] <= hStartListen) iStart++;
                System.out.println("a[iStart]="+a[iStart]+ " hStart=" + hStartListen);
                assert(a[iStart] <= hStartListen);
                if(a[iStart+1] - hStartListen < hMin) {
                    iStart++;
                    hStartListen = a[iStart];
                } 
                continue;
                
            }
            break; // found a feasible solution that starts at iStart
        }
        System.out.println(hStartListen);
    }
}