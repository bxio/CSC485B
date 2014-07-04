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
    return nodes.length-1;
  }

  /**
   * Parses the csv file into a weighted adjacency matrix.
   *
   * @param  filename  the name of the file, provided by the user
   * @param  adj_matrix  the adjacency matrix to be populated
   * @param  size  the size of the adjacency matrix for the graph in question
   */
   public static void parseFileIntoAdjMatrix(String filename, float[][] adj_matrix, int size){
    File file = new File(filename);
    try{
      Scanner sc = new Scanner(file);
      String line = sc.nextLine();

      int row = size;
      int col = size;

      for(int i = 0; i < row; i++){
        line = sc.nextLine();
        String[] nodes = line.split(";");
        for(int j = 0; j < col; j++){
          adj_matrix[i][j] = Float.parseFloat(nodes[j+1]);
        }
      }
      sc.close();//close the scanner
    }catch (FileNotFoundException e){
      System.out.println("file io error");
    }
  }

  /**
   * Prints out the given 2d matrix (float onlys)
   *
   * @param  adj_matrix  the adjacency matrix to be populated
   */
  public static void printMatrix(float[][] adj_matrix){
    for(int i=0;i<adj_matrix.length;i++){
      for(int j=0;j<adj_matrix[i].length;j++){
        System.out.print(" "+adj_matrix[i][j]);
      }
      System.out.println("");
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

    //start loop
    adoptNeighbours(adj_matrix, adopt_matrix, qValue);

  }

  public static void adoptNeighbours(float[][] adj_matrix, boolean[] adopt_matrix, float qValue){
    boolean[] eligibleStatus = new boolean[adopt_matrix.length];
    Arrays.fill(eligibleStatus, false);

    System.out.print("Initial: ");
    printStatus(adopt_matrix);

    for(int i=0;i<adj_matrix.length;i++){
      System.out.print("Start "+i+": ");
      printStatus(adopt_matrix);
      for(int j=0;j<adj_matrix[i].length;j++){
        //traverse the array
        if(adj_matrix[i][j] == 1 && adopt_matrix[i] && !adopt_matrix[j] && !eligibleStatus[j]){
          //I'm converted, my neighbour is not, and isn't already on the list to check
          System.out.println("Node "+j+" potentially eligible.");
          eligibleStatus[j] = true;
        }
      }
      System.out.print("Eligibles: ");
      printStatus(eligibleStatus);
      if(getEligibleCount(eligibleStatus) == 0){
        break;
      }

      //convert the ones that are potentially eligible
      for(int k=0;k<eligibleStatus.length;k++){
        if(eligibleStatus[k] && determineAdoptionStatus(adj_matrix,adopt_matrix,qValue,k)){
          System.out.println("Adopted Node "+k);
          adopt_matrix[k] = true;
        }
      }
      System.out.print("Finish "+i+": ");
      printStatus(adopt_matrix);
      Arrays.fill(eligibleStatus, false);
    }

    System.out.print("Finish: ");
    printStatus(adopt_matrix);
  }


  public static int getEligibleCount(boolean[] adopt_matrix){
    int count = 0;
    for(int i=0;i<adopt_matrix.length;i++){
      if(adopt_matrix[i]){
        count++;
      }
    }
    return count;
  }
  public static boolean determineAdoptionStatus(float[][] adj_matrix, boolean[] adopt_matrix, float qValue, int poi){
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
    System.out.print("Node "+poi+": "+numConnectedAdopters+" CA "+numConnectedNonAdopters+" CNA ");
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

  public static void main(String [] args){
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
        parseFileIntoAdjMatrix(csvOfGraph, adj_matrix, size);
        //printMatrix(adj_matrix);
        makeRandomFirstAdopters(3, adopt_matrix);
        //printStatus(adopt_matrix);

        determineAndAdopt(adj_matrix, adopt_matrix, qValue);

      }catch(FileNotFoundException e){
        System.out.println("File not found error.");
      }
    }
  }
}
