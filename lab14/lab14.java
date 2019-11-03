package com.company;

public class lab14 {

    public static void main(String[] args) {
        Graph g = new Graph(6);

        g.nodeList[g.A].add(new Edge(g.A, g.B, 3));
        g.nodeList[g.A].add(new Edge(g.A, g.E, 6));
        g.nodeList[g.A].add(new Edge(g.A, g.F, 5));
        g.nodeList[g.B].add(new Edge(g.B, g.A, 3));
        g.nodeList[g.B].add(new Edge(g.B, g.C, 1));
        g.nodeList[g.B].add(new Edge(g.B, g.F, 4));
        g.nodeList[g.C].add(new Edge(g.C, g.B, 1));
        g.nodeList[g.C].add(new Edge(g.C, g.D, 6));
        g.nodeList[g.C].add(new Edge(g.C, g.F, 4));
        g.nodeList[g.D].add(new Edge(g.D, g.C, 6));
        g.nodeList[g.D].add(new Edge(g.D, g.E, 8));
        g.nodeList[g.D].add(new Edge(g.D, g.F, 5));
        g.nodeList[g.E].add(new Edge(g.E, g.A, 6));
        g.nodeList[g.E].add(new Edge(g.E, g.D, 8));
        g.nodeList[g.E].add(new Edge(g.E, g.F, 2));
        g.nodeList[g.F].add(new Edge(g.F, g.A, 5));
        g.nodeList[g.F].add(new Edge(g.F, g.B, 4));
        g.nodeList[g.F].add(new Edge(g.F, g.C, 4));
        g.nodeList[g.F].add(new Edge(g.F, g.D, 5));
        g.nodeList[g.F].add(new Edge(g.F, g.E, 2));

        g.primAlgo();
    }
}
