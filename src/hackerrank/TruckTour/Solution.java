package hackerrank.TruckTour;

import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] petrol = new int[n];
        int[] dist = new int[n];
        for(int i=0; i< n; i++) {
            petrol[i] = in.nextInt();
            dist[i] = in.nextInt();
        }
        /*
         * Double pointer strategy. Keep start and end. Move end forward until it's unfeasible or solution is found. Whenever unfeasible, move start until end becomes feasible. If start > end, move both.
         */
        int iStart = 0;
        int iEnd = 0;
        int currPetrol = petrol[0];
        while(true) {
            boolean solutionFound = false;
            // try to move the end forward
            while(true) {
                assert(currPetrol >= 0);
                if(dist(iStart,iEnd, petrol.length) == petrol.length-1 && currPetrol >= 0) {
                    solutionFound = true;
                    break;
                }
                if(currPetrol - dist[iEnd] < 0) {
                    // can't move foward
                    break;
                } else {
                    // move end forward
                    currPetrol -= dist[iEnd];
                    iEnd = (iEnd + 1) % petrol.length;
                    currPetrol += petrol[iEnd];
                }
            }
            if(solutionFound) break;
            // if I got here, it means we can't move foward
            while(currPetrol - dist[iEnd] < 0) { // can't move foward
                currPetrol -= petrol[iStart];
                if(iStart == iEnd) {
                    iEnd++;
                    currPetrol += petrol[iEnd]; // I move the start and end, so need to add the petrol for that.
                } else {
                    currPetrol += dist[iStart]; // I get back the petrol necessary to move from iStart to iStart +1
                }
                iStart++;
            }
        }
        System.out.println(iStart);
    }
    
    public static int dist(int i1, int i2, int length) {
        if(i2 >= i1) {
            return i2-i1;
        } else {
            return i2 - i1 + length;
        }
    }
}
