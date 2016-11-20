package ex1.b;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

class Vertex {

    public int name;
    public Edge[] edges;
    public double dist;
    public int previous;
    public boolean visited;

    public Vertex(int name, double dist) {
        this.name = name;
        this.dist = dist;
        previous = -1;
        visited = false;
    }

    public Vertex(Vertex v) {
        this.name = v.name;
        this.dist = v.dist;
        previous = v.previous;
        visited = v.visited;

    }

    public String toString() {
        return "" + name;
    }
}

class Edge {

    public int vert;
    public double weight;
    public int sourceV;

    public Edge(int v, double w) {
        vert = v;
        weight = w;
    }

    public Edge(int v, double w, int source) {
        vert = v;
        weight = w;
        sourceV = source;
    }
}

public class Dijkstra2 {

    Vertex[] vertices;
    int source;
    public static double infinity = Double.POSITIVE_INFINITY;
    //int [] Arr;

    public Dijkstra2(Vertex[] vs) {
        //this.source = source;
        vertices = new Vertex[vs.length];
        for (int i = 0; i < vs.length; i++) {
            vertices[i] = vs[i];
        }
    }
    
        public void computePaths(int Vertexsource) {
        Vertex s = vertices[Vertexsource];
        s.dist = 0.;
        HeapMin Q = new HeapMin();
        Q.minHeapInsert(s);
        for (int i = 0; i < vertices.length; i++) {
            if (i != Vertexsource) {
                Q.minHeapInsert(vertices[i]);
            }
        }
        while (!Q.isEmpty()) {
            Vertex u = Q.heapExtractMin();
            // Visit each edge exiting u
            for (Edge e : u.edges) {
                Vertex v = vertices[e.vert];
                if (!v.visited) {
                    double distU = u.dist + e.weight;
                    if (distU < v.dist) { //relaxation
                        v.dist = distU;
                        v.previous = vertices[u.name].name;
                        Q.heapDecreaseKey(v);
                    }
                }
            }
            u.visited = true;
        }
    }

    public boolean isInArr(int index,int [] arr){
        for (int i = 0; i < arr.length; i++) {
            if(index == arr[i]){
                return true;
            }
        }
        return false;
    } 
    
    public void computePathsBlackList(int Vertexsource,int [] arr) {
        source = Vertexsource;
        Vertex s = vertices[Vertexsource];
        s.dist = 0.;
        HeapMin Q = new HeapMin();
        Q.minHeapInsert(s);
        for (int i = 0; i < vertices.length; i++) {
            if (i != Vertexsource && isInArr(i,arr)==false) {
                Q.minHeapInsert(vertices[i]);
            }
        }
        while (!Q.isEmpty()) {
            Vertex u = Q.heapExtractMin();
            // Visit each edge exiting u
            for (Edge e : u.edges) {
                if(isInArr(e.vert,arr)==false){
                Vertex v = vertices[e.vert];
                if (!v.visited) {
                    double distU = u.dist + e.weight;
                    if (distU < v.dist) { //relaxation
                        v.dist = distU;
                        v.previous = vertices[u.name].name;
                        Q.heapDecreaseKey(v);
                    }
                }
            }
               
        }
            u.visited = true;
        }
    }

    //return the path between u to v
    public String getShortestPath(int u, int v) {
        int t = v;
        String ans = t + "";
        while (t != u) {
            t = vertices[t].previous;
            ans = t + "->" + ans;
        }
        return ans;
    }

    //return the shortest distance between u to v
    public double getSortestWeight(int u, int v) {
        return vertices[v].dist;
    }
    
        public double diameter(){
        double dia = 0;
        for (int i = 0; i <vertices.length ; i++) {
            computePaths(vertices[i].name);
            for (int j = 0; j < vertices.length; j++) {
                if(vertices[j].dist > dia)
                    dia = vertices[j].dist;
            }
        }
        return dia;
    }
        
        //return true if the shortest path is in length of 1, otherwise returns false
     public boolean directPath(int v) {
        int t = v;
        String ans = t + "";
        int len = 0;
        while (t != source) {
            if(len > 1)
                return false;
            t = vertices[t].previous;
            ans = t + "->" + ans;
            len ++;
        }
        if(len > 1)
            return false;
        return true;
    }
    //מחזיר אמת אם הגרף מקיים את משפט אי שוויון המשולש, אחרת הוא מחזיר שקר
    public boolean TriangleInequality(){
        for (int i = 0; i <vertices.length ; i++) {
            computePaths(vertices[i].name);
            for (int j = 0; j < vertices.length; j++) {
                if(vertices[i].name!=vertices[j].name){
                if(directPath(vertices[j].name) == false)
                    return false;
            }
            }
        }
        return true;
    }
    
//        public void printWeights(){
//		System.out.print("weights: ");
//		for (Vertex v : vertices) {
//                    
//			System.out.printf(v.dist + ", ");
//		}
//		System.out.println();
//	}
//	public String getPath(int v){
//		int t = v;
//		String ans = t + "";
//		while(t != source){
//			t = vertices[t].previous;
//			ans = t + "->" + ans;
//		}
//		return ans;
//	}
//	public void printPaths(){
//		for (Vertex v : vertices){
//			System.out.println("price of " + v.name+" = " + v.dist + ", path: " +  getPath(v.name));
//		}
//		System.out.println();
//	}

    public static void main(String[] args) {
//        Dijkstra2 ds = new Dijkstra2(initGraph1());
//        int [] arr = {0};
//        ds.computePathsBlackList(2, arr);
//        System.out.println(ds.getSortestWeight(2, 3));
//        System.out.println(ds.getShortestPath(2, 3));
//        System.out.println(ds.getSortestWeight(0, 5));
//        System.out.println(ds.getShortestPath(0, 4));
//        System.out.println(ds.diameter());
        /*
		ds.computePaths(1);
		ds.printWeights();
		ds.printPaths();
         */
    }
}
/*
		OUTPUT inint1
	weights: 0.0, 7.0, 9.0, 20.0, 20.0, 11.0, 
	price of 0 = 0.0, path: 0
	price of 1 = 7.0, path: 0->1
	price of 2 = 9.0, path: 0->2
	price of 3 = 20.0, path: 0->2->3
	price of 4 = 20.0, path: 0->2->5->4
	price of 5 = 11.0, path: 0->2->5

	 	OUTPUT inint2
	weights: 0.0, 8.0, 9.0, 7.0, 5.0, 
	price of 0 = 0.0, path: 0
	price of 1 = 8.0, path: 0->4->1
	price of 2 = 9.0, path: 0->4->1->2
	price of 3 = 7.0, path: 0->4->3
	price of 4 = 5.0, path: 0->4
 */
