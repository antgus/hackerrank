package hackerrank.KingdomDivision;

public class Solution {
    
    public static void main(String[] args) {
        
        solveNaive();
    }
    
    public static void solveNaive() {
        for(int k=1; k < 9; k++) {
            BinaryGenerator gen = new BinaryGenerator(k, 0);
            int count = 0;
            while(gen.hasNext()) {
                String s = gen.nextStr();
                boolean isOk = true;
                for(int i=0; i < s.length();i++) {
                    boolean isSameAsPrev = i > 0 && s.charAt(i-1) == s.charAt(i);
                    boolean isSameAsNext = i < s.length()-1 && s.charAt(i) == s.charAt(i+1);
                    if(!isSameAsPrev && !isSameAsNext) {
                        isOk = false;
                        break;
                    }
                }
                if(isOk) {
                    count++;
                }
            }
            System.out.println(k + " -> " + count);
        }
    }
    
    static class BinaryGenerator {
        private boolean[] set;
        private StringBuilder strBuilder;
        private int next;
        private int maxValue;

        BinaryGenerator(int n, int start) {
            assert(n <= 30);
            maxValue = (1 << n) - 1;
            this.set = new boolean[n];
            this.next = start;
            strBuilder = new StringBuilder();
            for(int i=0; i < n; i++) {
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
