
package hackerrank.SwapNodesAlgo;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int numNodes = in.nextInt();
        Node[] nodes = new Node[numNodes];
        for(int i=0; i < numNodes; i++) {
            nodes[i] = new Node(i);
        }
        for(int i=0; i < numNodes; i++) {
            int iLeft = in.nextInt();
            int iRight = in.nextInt();
            assert(iLeft != 1 && iRight !=1); // checking  that the root has index 1, so no node can link to it.
            nodes[i].left = iLeft == -1 ? null : nodes[iLeft - 1]; //0-indexed
            nodes[i].right = iRight == -1 ? null : nodes[iRight - 1];
        }
        Node root = nodes[0];
        
        Map<Integer, List<Node>> depthMap = new HashMap<>();
        populateDepthMap(depthMap, root);
        int treeHeight = depthMap.keySet().stream().max(Integer::compare).get();

        int numSwaps = in.nextInt();
        int[] kDepth = new int[numSwaps];
        for(int i=0; i < numSwaps; i++) {
            kDepth[i] = in.nextInt();
        }
        
        for(int k: kDepth) {
            for(int h = k; h <= treeHeight; h+=k) {
                List<Node> nodesAtHeight = depthMap.getOrDefault(h, Collections.emptyList());
                nodesAtHeight.stream().forEach((n) -> {
                    swapSubtrees(n);
                });
            }
        }
    }
    
    static void swapSubtrees(Node n) {
        Node aux = n.left;
        n.left = n.right;
        n.right = aux;
    }
    
    static void populateDepthMap(Map<Integer, List<Node>> depthMap, Node root) {
        _populateDepthMap(depthMap, root, 0);
    }
    
    static void _populateDepthMap(Map<Integer, List<Node>> depthMap, Node root, int depth) {
        if(root == null) return;
        depth++;
        List<Node> newList = depthMap.getOrDefault(depth, new LinkedList<>());
        newList.add(root);
        depthMap.put(depth, newList);
        _populateDepthMap(depthMap, root.left, depth);
        _populateDepthMap(depthMap, root.right, depth);
    }

    void printInOrder(Node root) {
        if(root == null) return;
        printInOrder(root.left);
        System.out.print(root.id + " ");
        printInOrder(root.right);
    }
    
    static class Node {
        int id;
        Node left;
        Node right;
        
        Node(int id) {
            this.id = id;
        }
    }
}
