package hackerrank.SimpleTextEditor;

import java.io.*;
import java.util.*;

public class Solution {
    private static final int APPEND = 1;
    private static final int DELETE = 2;
    private static final int PRINT = 3;
    private static final int UNDO = 4;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int countOp = in.nextInt();
        List<Operation> ops = new ArrayList<>(countOp);
        for(int i=0; i < countOp; i++) {
            int op = in.nextInt();
            switch(op) {
                case APPEND:
                    String s = in.next();
                    ops.add(new Append(s));
                    break;
                case DELETE:
                    ops.add(new Delete(in.nextInt()));
                    break;
                case PRINT:
                    ops.add(new Print(in.nextInt()-1));
                    break;
                case UNDO:
                    ops.add(new Undo());
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown op: " + op);
            }
        }
        StringBuilder builder = new StringBuilder();
        Stack<Undoable> opStack = new Stack<>();
        
        ops.forEach(op -> op.apply(builder, opStack));
    }
    
    interface Operation {
        void apply(StringBuilder builder, Stack<Undoable> opStack);
    }
    interface Undoable {
        void undo(StringBuilder builder);
    }
    
    static class Append implements Operation, Undoable {
        String s;
        Append(String s) {
            this.s = s;
        }
        @Override
        public void apply(StringBuilder builder, Stack<Undoable> opStack) {
            builder.append(s);
            opStack.push(this);
        }
        @Override
        public void undo(StringBuilder builder) {
            builder.setLength(Math.max(0, builder.length() - s.length()));
        }
    }
    
    static class Delete implements Operation, Undoable{
        int numChars;
        String deletedChars;
        Delete(int numChars) {
            this.numChars = numChars;
        }
        
        @Override
        public void apply(StringBuilder builder, Stack<Undoable> opStack) {
            this.deletedChars = builder.substring(builder.length() - this.numChars);
            builder.setLength(Math.max(0, builder.length() - this.numChars));
            opStack.push(this);
        }
        @Override
        public void undo(StringBuilder builder) {
            builder.append(deletedChars);
        }
    }
    
    static class Print implements Operation {
        int k;
        Print(int k) {
            this.k = k;
        }
        @Override
        public void apply(StringBuilder builder, Stack<Undoable> opStack) {
            System.out.println(builder.charAt(k));
        }
    }
    
    static class Undo implements Operation {
        @Override
        public void apply(StringBuilder builder, Stack<Undoable> opStack) {
            if(!opStack.isEmpty()) {
                opStack.pop().undo(builder);
            }
        }
    }
}