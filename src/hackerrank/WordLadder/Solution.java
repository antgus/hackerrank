package hackerrank.WordLadder;

import java.util.*;

/**
 */
public class Solution {

    int wordLadder(String beginWord, String endWord, String[] wordList) {
        int NO_PATH_EXISTS = 0;

        Map<String, Node> graph = new HashMap<>();
        for(String s: wordList) {
            graph.put(s, new Node(s));
        }
        if(!graph.containsKey(endWord)) {
            return NO_PATH_EXISTS;
        }

        for(int i=0; i < wordList.length; i++) {
            String s1 = wordList[i];
            for(int k=i+1; k < wordList.length; k++) {
                String s2 = wordList[k];
                if(editDistance(s1,s2) == 1) {
                    Node n1 = graph.get(s1);
                    Node n2 = graph.get(s2);
                    n1.neighbors.add(n2);
                    n2.neighbors.add(n1);
                }
            }
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(graph.get(beginWord));
        int pathLength = 0;
        while(!queue.isEmpty()) {
            Node current = queue.poll();
            pathLength++;
            for(Node n: current.neighbors) {
                if(!n.visited) {
                    if(n.data.equals(endWord)) {
                        return pathLength + 1;
                    } else {
                        queue.add(n);
                        n.visited = true;
                    }
                }
            }
        }
        return NO_PATH_EXISTS;
    }

    int editDistance(String s1, String s2) {
        int editDistance = 0;
        for(int i=0; i < s1.length(); i++) {
            editDistance += s1.charAt(i) == s2.charAt(i) ? 1 : 0;
        }
        return editDistance;
    }

    class Node {
        String data;
        List<Node> neighbors = new ArrayList<>();
        boolean visited = false;
        Node(String s) {
            this.data = data;
        }
    }
}
