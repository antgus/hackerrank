package hackerrank.TheValueOfFriendship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.stream.Collectors;

public class Solution {

    private static final int NO_GROUP = -1;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int numTests = in.nextInt();
        for(int i=0; i < numTests; i++) {
            Graph g = initGraph(in);
            solve(g);
        }
    }
    
    public static void solve(Graph g) {
        Map<Integer, List<Node>> groups = findSingleConnectedComponents(g);
        List<List<Node>> sortedGroups = groups.values().stream().sorted((l1,l2) -> l2.size() - l1.size()).collect(Collectors.toList()); // largest group first
        long total = 0;
        long totalFriendsInOtherGroups = 0;
        long numRedundantEdges = 0;
        for(List<Node> group : sortedGroups) {
            long numEdges = countUndirectedEdges(group);
            long numNonRedundantEdges = group.size() - 1;
            total += numNonRedundantEdges * totalFriendsInOtherGroups; // for each added edge I'll add all the existing friends of the other groups
            long cumSum = countCumSum(group.size()); // then I compute the part that corresponds to the new group, only for edges that add a friend
            total += cumSum;
            long sumOfFriendsInThisGroup = (long)group.size() * ((long)group.size()-1);
            totalFriendsInOtherGroups += sumOfFriendsInThisGroup;
            numRedundantEdges += numEdges - numNonRedundantEdges;
        }
        total += totalFriendsInOtherGroups * numRedundantEdges;
        System.out.println(total);
    }
    
    public static Graph initGraph(Scanner in) {
        Graph g = new Graph();
        int numNodes = in.nextInt();
        int numEdges = in.nextInt();
        List<Node> nodes = new ArrayList<>(numNodes);
        for(int i=0; i < numNodes; i++) {
            Node n = new Node();
            n.idx = i;
            nodes.add(n);
        }
        for(int a1 = 0; a1 < numEdges; a1++){
            Node n1 = nodes.get(in.nextInt()-1);
            Node n2 = nodes.get(in.nextInt()-1);
            Edge n1to2 = new Edge(n1,n2);
            Edge n2to1 = new Edge(n2,n1);
            n1.outNodes.add(n2);
            n2.outNodes.add(n1);
            n1.outEdges.add(n1to2);
            n2.inEdges.add(n1to2);
            n2.outEdges.add(n2to1);
            n1.inEdges.add(n2to1);            
        }
        g.allNodes = nodes;
        return g;
    }
    
    public static int countUndirectedEdges(List<Node> nodes) {
        int count = 0;
        for(Node n: nodes) {
            for(Edge e: n.outEdges) {
                if(!e.visited) {
                    e.visited = true;
                    count++;
                }
            }
            n.inEdges.forEach(e -> {e.visited = true;});
        }
        return count;
    }
    
    public static long countCumSum(int n) {
        long total = 0;
        for(long i=2; i <= n; i++) {
            total += i*(i-1);
        }
        return total;
    }

    public static class Graph {
        List<Node> allNodes;

        public List<Node> getAllNodes() {
            return allNodes;
        }
    }

    public static Map<Integer, List<Node>> findSingleConnectedComponents(Graph g) {
        int groupId = 0;
        Map<Integer, List<Node>> groups = new HashMap<>();
        int[] groupIds = new int[g.getAllNodes().size()];
        for (int i = 0; i < groupIds.length; i++) {
            groupIds[i] = NO_GROUP;
        }

        for (Node start : g.getAllNodes()) {
            if (groupIds[start.idx] != NO_GROUP) {
                continue;
            }
            DepthFirstSearch dfs = new DepthFirstSearch(g, start);
            List<Node> group = new ArrayList<>();
            while (true) {
                Node n = dfs.next();
                if (n == null) {
                    break;
                }
                group.add(n);
                groupIds[n.idx] = groupId;
            }
            groups.put(groupId, group);
            groupId++;
        }
        return groups;
    }

    public static class DepthFirstSearch {

        Stack<Node> stack = new Stack<>();
        boolean[] visited;
        Node start;

        DepthFirstSearch(Graph g, Node start) {
            this.visited = new boolean[g.getAllNodes().size()];
            reset(start);
        }

        /**
         * @param newStartNode
         */
        public void reset(Node newStartNode) {
            stack.clear();
            this.start = newStartNode;
            addToStack(this.start);
        }

        /**
         * returns null if there are no more elements to search?
         * @return 
         */
        public Node next() {
            if(stack.isEmpty()) return null;
            Node node = stack.pop();
            node.outNodes.stream().filter((neighbor) -> (!visited[neighbor.idx]))
                    .forEach((neighbor) -> {
                        addToStack(neighbor);
                    });
            return node;
        }

        private void addToStack(Node n) {
            stack.push(n);
            visited[n.idx] = true;
        }
    }

    public static class Node {

        int idx; // an idx, to be used in an array representation of the graph.
        List<Edge> outEdges = new ArrayList<>();
        List<Edge> inEdges = new ArrayList<>();
        List<Node> outNodes = new ArrayList<>();
    }

    public static class Edge {
        Node from;
        Node to;
        boolean visited = false; // hackish, to be removed.
        Edge(Node from, Node to) {
            this.from = from;
            this.to = to;
        }
    }
}
