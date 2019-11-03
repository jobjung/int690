package com.company;

import java.util.LinkedList;
import java.util.ListIterator;

public class Graph {
    LinkedList<Edge> nodeList[];
    int NUM_NODES = 6;
    int A = 0, B = 1, C = 2, D = 3, E = 4, F = 5;

    public Graph(int NUM_NODES) {
        this.NUM_NODES = NUM_NODES;
        this.nodeList = new LinkedList[this.NUM_NODES];
        for (int i = 0; i < this.NUM_NODES; i++) {
            this.nodeList[i] = new LinkedList<>();
        }
    }

    public void primAlgo() {
        ListIterator<Edge> iterator;
        Edge e;
        LinkedList<Edge> treeVertices = new LinkedList<>();
        LinkedList<Edge> remainingVertices = new LinkedList<>();
        treeVertices.add(new Edge(A, A, -1));
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < this.NUM_NODES; i++) {
            iterator = nodeList[0].listIterator();
            for (e = iterator.next(); e.to != i && iterator.hasNext(); e = iterator.next()) {
            }
            if (e.to == i) {
                if (e.distance < min) {
                    remainingVertices.addFirst(e);
                    min = e.distance;
                } else {
                    remainingVertices.add(e);
                }
            } else {
                remainingVertices.add(new Edge(A, i, Integer.MAX_VALUE));
            }
        }
        iterator = remainingVertices.listIterator();
        while (iterator.hasNext()) {
            e = iterator.next();
            System.out.print(e.to + "(" + e.from + "," + e.distance + ") ");
        }
        System.out.println();

        Edge removeNodeFromRemaining = remainingVertices.getFirst();
        treeVertices.add(removeNodeFromRemaining);
        remainingVertices.remove();
        Edge eRemain, e1;
        while (!remainingVertices.isEmpty()) {
            min = Integer.MAX_VALUE;
            int minIndex = 0;
            ListIterator<Edge> iteratorForRemaining = remainingVertices.listIterator();
            for (int i = 0; iteratorForRemaining.hasNext(); i++) {
                eRemain = iteratorForRemaining.next();
                iterator = nodeList[removeNodeFromRemaining.to].listIterator();
                while (iterator.hasNext()) {
                    e1 = iterator.next();
                    if (e1.to == eRemain.to && e1.distance < eRemain.distance) {
                        eRemain.from = e1.from;
                        eRemain.distance = e1.distance;
                    }
                }
                if (eRemain.distance < min) {
                    minIndex = i;
                    min = eRemain.distance;
                }
            }
            removeNodeFromRemaining = remainingVertices.get(minIndex);
            treeVertices.add(removeNodeFromRemaining);
            remainingVertices.remove(minIndex);
            iterator = remainingVertices.listIterator();
            while (iterator.hasNext()) {
                e = iterator.next();
                System.out.print(e.to + "(" + e.from + "," + e.distance + ") ");
            }
            System.out.println();
        }
        System.out.println("Spanning Tree");
        iterator = treeVertices.listIterator();
        while (iterator.hasNext()) {
            e = iterator.next();
            System.out.print(e.to + "(" + e.from + "," + e.distance + ") ");
        }
        System.out.println();
    }

}
