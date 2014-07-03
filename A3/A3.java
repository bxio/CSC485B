/*********************
** Bill Xiong       **
** V00737042        **
** CSC485B - A3     **
** Summer 2014      **
*********************/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class A3{
  /**
   * Returns the size of the adjacency matrix to be generated from parsing
   * the .csv file.
   *
   * @param  firstline  the first line of the csv file, separated by semicolons
   * @return      the maximum lenght and width needed to fit the csv into an adjacency matrix.
   */
  public static int findSize(String firstLine){
    String[] nodes = firstLine.split(";");
    return nodes.length;
  }

  /**
   * Parses the csv file into a weighted adjacency matrix.
   *
   * @param  filename  the name of the file, provided by the user
   * @param  adj_matrix  the adjacency matrix to be populated
   * @param  size  the size of the adjacency matrix for the graph in question
   */
   public static void parseFile(String filename, float[][] adj_matrix, int size){
    File file = new File(filename);
    try{
      Scanner sc = new Scanner(file);
      String line = sc.nextLine();

      int row = size-1;
      int col = size-1;

      for(int i = 0; i < row; i++){
        line = sc.nextLine();
        String[] nodes = line.split(";");

        for(int j = 0; j < col; j++){
          adj_matrix[i][j] = Float.parseFloat(nodes[j+1]);

          if(adj_matrix[i][j] == 0){
            adj_matrix[i][j] = Float.POSITIVE_INFINITY;
          }
        }
      }
      sc.close();//close the scanner
    }catch (FileNotFoundException e){
      System.out.println("file io error");
    }
  }

  public static void makeRandomFirstAdopters(int numToAdopt, boolean[] status_matrix){
    for(int i=0;i<numToAdopt;i++){
      status_matrix[i] = true;
    }
    //TODO: Find minimal case.
  }

  public static void printAdoptionStatus(boolean[] status_matrix){
    for(int i=0;i<status_matrix.length;i++){
      System.out.print(status_matrix[i]+",");
    }

    System.out.print("\n");
  }

  public static void main (String[] args){
    if(args.length == 0){
      System.out.println("Usage: java A3 location_of_file.csv <q value>");
    }else{
      String csvOfGraph = args[0];
      int numAdopters = Integer.parseInt(args[1]);
      float qValue = Float.parseFloat(args[2]);
      System.out.println("Now analyzing '"+csvOfGraph +"' with "+numAdopters+" adoptors and q-value of '"+qValue+ "'.");
      try{
        File file = new File(csvOfGraph);
        Scanner sc = new Scanner(file);
        String line = sc.nextLine();
        int size = findSize(line);
        System.out.print("Size of graph is: "+size+"\n");
        //Create the adj matrix
        float[][] adj_matrix = new float[size][size];
        boolean[] status_matrix = new boolean[size];
        //populate the adj matrix
        parseFile(csvOfGraph, adj_matrix, size);
        makeRandomFirstAdopters(2,status_matrix);

        printAdoptionStatus(status_matrix);

      }catch(FileNotFoundException e){
        System.out.println("File not found error.");
      }
    }
  }

}
