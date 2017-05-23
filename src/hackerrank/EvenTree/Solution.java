
package hackerrank.EvenTree;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    static class Node {
        int id;
        List<Node> children = new ArrayList<>();
        
        Node(int id) {
            this.id = id;
        }
    }
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int numNodes = in.nextInt();
        int numEdges = in.nextInt();
        Node[] nodes = new Node[numNodes];
        for(int i=0; i < nodes.length; i++) {
            nodes[i] = new Node(i);
        }
        for(int i=0; i < numEdges; i++) {
            int to = in.nextInt() - 1;
            int from = in.nextInt() - 1;
            nodes[from].children.add(nodes[to]);
        }
        Node root = nodes[0];
        
        countNodes(root);
        System.out.println(countRemovedEdges);
    }
    
    static int countRemovedEdges = 0;

    static int countNodes(Node root) {
        if(root == null) {
            return 0;
        }
        int total = 0;
        for(Node n: root.children) {
            int count = countNodes(n);
            if(count % 2 == 0) {
                countRemovedEdges++; // this subtree can be removed.
            } else {
                total += count;
            }
        }
        return total + 1; // the node itself
    }
}