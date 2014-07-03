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

  public static void makeRandomFirstAdopters(int numToAdopt, boolean[] adopt_matrix){
    for(int i=0;i<numToAdopt;i++){
      adopt_matrix[i] = true;
    }
    //TODO: Find minimal case.
  }

  public static void printStatus(boolean[] adopt_matrix){
    System.out.print("[");
    for(int i=0;i<adopt_matrix.length;i++){
      System.out.print(adopt_matrix[i]+",");
    }

    System.out.println("]");
  }

  public static boolean determineMyAdoptionStatus(float[][] adj_matrix, boolean[] adopt_matrix, float qValue, int poi){
    float pValue;
    int numConnectedAdopters = 0;
    int numConnectedNonAdopters = 0;
    //traverse the matrix
    for(int i=0;i<adj_matrix[poi].length;i++){
      if(adj_matrix[poi][i]==1){
        if(adopt_matrix[i]){
          numConnectedAdopters++;
        }else{
          numConnectedNonAdopters++;
        }
      }
    }
    //calculate the p-value
    System.out.print("Node "+poi+" CA:"+numConnectedAdopters+" CNA: "+numConnectedNonAdopters);
    pValue = ((float)numConnectedAdopters/((float)(numConnectedAdopters+numConnectedNonAdopters)));
    System.out.print("p:"+pValue+" q:"+qValue+"=");

    if((pValue>=qValue) || adopt_matrix[poi]){
      System.out.println("True.");
      return true;
    }else{
      System.out.println("False.");
      return false;
    }
  }

  public static void determineAndAdopt(float[][] adj_matrix, boolean[] adopt_matrix, float qValue){
    //initially determine previous converted count
    boolean[] nextAdoptMatrix = new boolean[adopt_matrix.length];
    System.arraycopy(adopt_matrix, 0, nextAdoptMatrix, 0, adopt_matrix.length);

    int previousConvertedCount = 0;
    for(int i=0;i<adopt_matrix.length;i++){
      if(adopt_matrix[i]){
        previousConvertedCount++;
      }
    }
    //printStatus(nextAdoptMatrix);

    boolean[] eligableStatus = new boolean[adopt_matrix.length];
    Arrays.fill(eligableStatus, false);

    //start loop
    for(int i=0;i<adj_matrix.length;i++){
      for(int j=0;j<adj_matrix[i].length;j++){
        //traverse the array
        if(adj_matrix[i][j] == 1 && adopt_matrix[i] && !adopt_matrix[j] && !eligableStatus[j]){
          System.out.println("Node "+j+" eligable.");
          eligableStatus[j] = true;
        }
      }
    }
    printStatus(eligableStatus);

    //convert the ones that are potentially eligable
    for(int i=0;i<eligableStatus.length;i++){
      if(eligableStatus[i] && determineMyAdoptionStatus(adj_matrix,adopt_matrix,qValue,i)){
        System.out.println("Adopted Node "+i);
        adopt_matrix[i] = true;
      }
    }
    //reset.
    Arrays.fill(eligableStatus, false);
    //end loop

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
        boolean[] adopt_matrix = new boolean[size];
        //populate the adj matrix
        parseFile(csvOfGraph, adj_matrix, size);
        makeRandomFirstAdopters(2,adopt_matrix);

        printStatus(adopt_matrix);

        //System.out.println(determineMyAdoptionStatus(adj_matrix, adopt_matrix, qValue, 2));
        determineAndAdopt(adj_matrix, adopt_matrix, qValue);
      }catch(FileNotFoundException e){
        System.out.println("File not found error.");
      }
    }
  }

}
