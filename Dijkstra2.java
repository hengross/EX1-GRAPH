package ex1.b;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

class Vertex implements Comparable<Vertex>{

    public int name;
    public List<Edge> edges = new ArrayList<Edge>();
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
    public int compareTo(Vertex other)
	{
		return Double.compare(dist, other.dist);
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
                this.vertices[Vertexsource].dist= 0.;
		PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
		vertexQueue.add(vertices[Vertexsource]);
		while (!vertexQueue.isEmpty()) 
		{
			Vertex u = vertexQueue.poll();
			// Visit each edge exiting u
			for (Edge e : u.edges)
			{
				Vertex v = vertices[e.vert];
				double weight = e.weight;
				double distanceThroughU = u.dist + weight;
				if (distanceThroughU < v.dist) {//relaxation
					vertexQueue.remove(v);
					v.dist = distanceThroughU ;
					v.previous = vertices[u.name].name;
					vertexQueue.add((Vertex)(v));
				}
			}
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
                this.vertices[Vertexsource].dist= 0.;
		PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
		vertexQueue.add(vertices[Vertexsource]);
		while (!vertexQueue.isEmpty()) 
		{
                        
			Vertex u = vertexQueue.poll();
                        
			// Visit each edge exiting u
			for (Edge e : u.edges)
			{
                            if(isInArr(e.vert,arr)==false){
				Vertex v = vertices[e.vert];
				double weight = e.weight;
				double distanceThroughU = u.dist + weight;
				if (distanceThroughU < v.dist) {//relaxation
					vertexQueue.remove(v);
					v.dist = distanceThroughU ;
					v.previous = vertices[u.name].name;
					vertexQueue.add((v));
				}
                            }
			}
                        
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
    
    public void printWeights(){
		System.out.print("weights: ");
		for (Vertex v : vertices) {
			System.out.printf(v.dist + ", ");
		}
		System.out.println();
	}
    
    public double radius(){
        double [] maxEdges = new double [vertices.length];
        double diaMin = 0;
        double dia = 0;
        int index = 0;
        for (int i = 0; i <vertices.length ; i++) {
            computePaths(vertices[i].name);
            for (int j = 0; j < vertices.length; j++) {
                if(vertices[j].dist > dia)
                    dia = vertices[j].dist;
            }
            maxEdges[index++] = dia;
            dia = 0;
            for (int k = 0; k < vertices.length; k++) {
                      vertices[k].visited = false;
                      vertices[k].previous = -1;
                      vertices[k].dist = infinity;
                  }
        }
        diaMin = maxEdges[0];
        for (int i = 0; i < maxEdges.length; i++) {
            if(maxEdges[i] < diaMin)
                diaMin = maxEdges[i];
        }
        return diaMin;
    }
    
    public double diameter(){
        double dia = 0;
        for (int i = 0; i <vertices.length ; i++) {
            computePaths(vertices[i].name);
            for (int j = 0; j < vertices.length; j++) {
                if(vertices[j].dist > dia)
                    dia = vertices[j].dist;
            }
            for (int k = 0; k < vertices.length; k++) {
                      vertices[k].visited = false;
                      vertices[k].previous = -1;
                      vertices[k].dist = infinity;
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
    
     boolean flag = true;
     double radius = 0;
     double w = 0;
    public static Vertex[] initGraph1(){
        int numNodes = 0;
        int numEdges = 0;
        Vertex[] nodes = null;
        Edge edges[];
        try {
            FileInputStream fis = new FileInputStream("C:\\Users\\amit\\Desktop\\G0.txt");
            Scanner scanner = new Scanner(fis);
            numNodes = scanner.nextInt(); //read the first number = number of nudes
            numEdges = scanner.nextInt(); //read the second number = number of edges
            nodes = new Vertex[numNodes]; //array of vertex 
            edges = new Edge[numEdges]; //array of edges *2
            int e = 0;
            //int[] arr = new int[numNodes];
            double infinity = Double.POSITIVE_INFINITY;
            for (int i = 0; i < nodes.length; i++) {
                nodes[i] = new Vertex(i, infinity);
                //nodes[i].edges.add(arr[i]);
                //nodes[i].edges = new Edge[arr[i]];
            }
            while (scanner.hasNextInt()) { //read the fill untill we read null
                int sv = scanner.nextInt(); //read the first in the raw int from the text file
                int v = scanner.nextInt(); //read the second in the raw int from the text file
                double w = scanner.nextDouble(); //read the third in the raw int from the text file
                nodes[v].edges.add(new Edge(sv,w));
                nodes[sv].edges.add(new Edge(v,w));
            }
            scanner.close();      
        } catch (IOException ex) {
            System.out.print("Error reading file\n" + ex);
            System.exit(2);
        }
        return nodes;
    }

    public static void main(String[] args) {
        Vertex [] arr = initGraph1();
        Dijkstra2 ds = new Dijkstra2(arr);
        ds.computePaths(1);
        System.out.println(ds.getSortestWeight(1,53));
//        System.out.println(ds.getShortestPath(32,53));
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
