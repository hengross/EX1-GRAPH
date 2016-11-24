/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ex1.b;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author amit
 */
public class Graph {

    String fileName;
    String fileTestName;
    String testAmswer = "C:\\\\Users\\\\amit\\\\Desktop\\\\testAnswer.txt";
    Dijkstra2 d;
    Vertex[] nodes;
    double radius = 0;
    public static double infinity = Double.POSITIVE_INFINITY;

    public Graph(String fileName) {
        this.fileName = fileName;
        nodes = createGraph();
    }

    public void setFileName(String otherFileName) {
        this.fileName = otherFileName;
    }

    public void setFileTestName(String otherTestFileName) {
        this.fileTestName = otherTestFileName;
    }

    public void createTestFileAnswer(String otherTestFileName) {
        this.fileTestName = otherTestFileName;
        nodes = createGraph();
        d = new Dijkstra2(nodes);
        try {
            FileInputStream fis = new FileInputStream(fileTestName);
            BufferedWriter out = null;
            FileWriter fstream = new FileWriter("C:\\Users\\amit\\Desktop\\out.txt", true); //true tells to append data.
            out = new BufferedWriter(fstream);
            Scanner scanner = new Scanner(fis);
            int numTests = scanner.nextInt();
            //System.out.println(numTests);
            int times = 1;
            while (times < numTests) {
                int kod1 = scanner.nextInt();
                int kod2 = scanner.nextInt();
                int arraySize = scanner.nextInt();
                int[] array = new int[arraySize];
                for (int i = 0; i < array.length; i++) {
                    array[i] = scanner.nextInt();
                    out.write(String.valueOf(array[i]));
                    out.write(" ");
                }
              d.computePathsBlackList(kod1, array);
              out.write(String.valueOf(d.getSortestWeight(kod1, kod2)));
              out.write(" ");
                  for (int i = 0; i < nodes.length; i++) {
                      nodes[i].visited = false;
                      nodes[i].previous = -1;
                      nodes[i].dist = infinity;
                  }
               out.write("\n");
               times++;
            }
            out.write(String.valueOf(d.diameter()));
            out.write(" ");
            out.write(String.valueOf(d.TriangleInequality()));
            out.write(" ");
            out.write(String.valueOf(d.radius()));
            out.write(" ");
            out.close();
            scanner.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    boolean flag = true;
    public Vertex[] createGraph() { //returns arr of vertex - a graph
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
            double infinity = Double.POSITIVE_INFINITY;
            for (int i = 0; i < nodes.length; i++) {
                nodes[i] = new Vertex(i, infinity);
            }
            while (scanner.hasNextInt()) { //read the fill untill we read null
                int sv = scanner.nextInt(); //read the first in the raw int from the text file
                int v = scanner.nextInt(); //read the second in the raw int from the text file
                double w = scanner.nextDouble(); //read the third in the raw int from the text file
                if(flag){
                    radius = w;
                    flag = false;
                }
                if(w<radius)
                    radius = w;
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
        Graph g = new Graph("C:\\Users\\amit\\Desktop\\G0.txt");
        //g.createGraph();
        g.createTestFileAnswer("C:\\Users\\amit\\Desktop\\test1.txt");
    }

}
