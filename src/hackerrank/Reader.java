package hackerrank;

import java.io.InputStream;
import java.util.Scanner;

public class Reader {
    Scanner in;
    
    public Reader(InputStream in) {
        this.in = new Scanner(in);
    }

    public int[] nextIntArray(int length) {
        int[] ar = new int[length];
        for(int i=0; i < length; i++) {
            ar[i] = in.nextInt();
        }
        return ar;
    }
    
    public long[] nextLongArray(int length) {
        long[] ar = new long[length];
        for(int i=0; i < length; i++) {
            ar[i] = in.nextLong();
        }
        return ar;
    }
    
    public double[] nextDoubleArray(int length) {
        
    }
}
