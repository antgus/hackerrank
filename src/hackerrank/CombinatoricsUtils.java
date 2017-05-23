package hackerrank;

public class CombinatoricsUtils {

    /**
     * Generates all possible binary values with nBits starting from 'start';
     */
    static class BinaryGenerator {
        private final boolean[] set;
        private final StringBuilder strBuilder;
        private final int maxValue;
        private int next;

        BinaryGenerator(int nBits, int start) {
            assert(nBits <= 30);
            maxValue = (1 << nBits) - 1;
            this.set = new boolean[nBits];
            this.next = start;
            strBuilder = new StringBuilder();
            for(int i=0; i < nBits; i++) {
                strBuilder.append('0');
            }
        }
        
        boolean hasNext() {
            return next <= maxValue;
        }
        
        String nextStr() {
            String s = Integer.toBinaryString(next);
            int offset = set.length - s.length();
            strBuilder.setLength(offset);
            strBuilder.append(s);
            next++;
            return strBuilder.toString();
        }
        
        boolean[] next() {
            String s = Integer.toBinaryString(next);
            int offset = set.length - s.length();
            for(int i=0; i < s.length(); i++) {
                set[i+offset] = s.charAt(i) == '1';
            }
            next++;
            return set;
        }
    }
}
