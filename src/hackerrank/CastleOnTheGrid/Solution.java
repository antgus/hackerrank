package hackerrank.CastleOnTheGrid;

import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        boolean[][] matrix = new boolean[n][n];
        boolean[][] visited = new boolean[n][n];
        for(int i=0; i < n; i++) {
            String s = in.next();
            for(int k = 0 ; k < s.length(); k++) {
                matrix[i][k] = s.charAt(k) == '.';
            }
        }
        Pos from = new Pos(in.nextInt(), in.nextInt());
        Pos to = new Pos(in.nextInt(), in.nextInt());
        
        Queue<Pos> open = new ArrayDeque<>(1000);
        open.add(from);
        visited[from.i][from.j] = true;
        
        int[] directions = {1,-1};
        int countAtCurrentStep = 1;
        int step = 0;
        while(!open.isEmpty()) {
            Pos cur = open.poll();
            if(cur.equals(to)) {
                break;
            }
            for(int d : directions) {
                for(int i=cur.i; i>=0 && i< n && matrix[i][cur.j];i += d) {
                    if(!visited[i][cur.j]) {
                        open.add(new Pos(i, cur.j));
                        visited[i][cur.j] = true;
                    }
                }
                for(int j=cur.j; j>=0 && j<n && matrix[cur.i][j]; j += d) {
                    if(!visited[cur.i][j]) {
                        open.add(new Pos(cur.i, j));
                        visited[cur.i][j] = true;
                    }
                }
            }
            if(--countAtCurrentStep == 0) {
                step++;
                countAtCurrentStep = open.size();
            }
        }
        System.out.println(step);
    }
    
    static class Pos {
        int i;
        int j;
        Pos(int i, int j) {
            this.i = i;
            this.j = j;
        }
        
        @Override
        public boolean equals(Object other) {
            if(other instanceof Pos) {
                Pos p = (Pos) other;
                return this.i == p.i && this.j == p.j;
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 23 * hash + this.i;
            hash = 23 * hash + this.j;
            return hash;
        }
    }
}