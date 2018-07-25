import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Solution {

    // Complete the encryption function below.
    static String encryption(String s) {
        s = s.replace(" ", "");
        int nRows = (int) Math.floor(Math.sqrt(s.length()));
        int nCols = (int) Math.ceil(Math.sqrt(s.length()));
        if(nRows * nCols < s.length()) {
            nRows = nCols;
        }

        Character[][] matrix = new Character[nRows][nCols];
        for (int i=0; i < nRows; i++) {
            for (int j=0; j < nCols; j++) {
                if (i*nCols + j >= s.length()) {
                    break;
                }
                matrix[i][j] = s.charAt(i*nCols + j);
            }
        }

        StringBuilder b = new StringBuilder(s.length() + nRows);
        for(int j=0; j < nCols; j++) {
            for(int i=0; i < nRows; i++) {
                if (i*nCols + j >= s.length()) {
                    break;
                }
                b.append(matrix[i][j]);
            }
            if (j < nCols - 1) {
                b.append(" ");
            }
        }
        return b.toString();
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String s = scanner.nextLine();

        String result = encryption(s);

        bufferedWriter.write(result);
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
