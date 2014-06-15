/*********************
** Bill Xiong       **
** V00737042        **
** CSC485B - A2     **
** Summer 2014      **
*********************/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class homophily{
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

	/**
	 * Parses the csv file into a weighted adjacency matrix.
	 *
	 * @param  filename  the name of the file, provided by the user
	 * @param  gender_matrix  the adjacency matrix to be populated
	 * @param  size  the size of the adjacency matrix for the graph in question
	 */
	 public static void parseFileForGender(String filename, Node[] gender_matrix, int size){
		File file = new File(filename);
		try{
			Scanner sc = new Scanner(file);
			String line = sc.nextLine();

			String[] parts;

			for(int i=0;i<size;i++){
				Node tmp = new Node();
				System.out.println(line);
				parts = line.split(",");
				tmp.setID(parts[0]);
				tmp.setLabel(parts[1]);
				try{
					if(parts[2].equals("male")){
						tmp.setGender(0);
					}else if(parts[2].equals("female")){
						tmp.setGender(1);
					}
				}catch(ArrayIndexOutOfBoundsException e){
					System.out.println("Node gender not set!");
					tmp.setGender(-1);
				}
				gender_matrix[i]=tmp;
				if(sc.hasNextLine()){
					line = sc.nextLine();
				}
			}

			sc.close();//close the scanner
		}catch (FileNotFoundException e){
			System.out.println("file io error");
		}
	}

	/**
	 * Traverses the given Adjacency matrix and returns the cross-edge percentage.
	 *
	 * @param  adj_matrix  the adjacency matrix to be traversed
	 * @param  nodeMatrix  the matrix of nodes containing the gender information
	 * @param  size  the size of the adjacency matrix for the graph in question
	 * @return      the float describing the percentage of cross-edges in the graph, out of 1.
	 */
	public static float getActualCrossEdgePercentage(float[][] adj_matrix, Node[] nodeMatrix, int size){
		//copy the adj matrix
		float[][]adj_copy = new float[size][size];

		for (int i = 0; i < adj_matrix.length; i++){
			System.arraycopy(adj_matrix[i], 0, adj_copy[i], 0, adj_matrix[0].length);
		}
		float numCrossEdges = 0;
		float numTotalEdges = 0;
		//interate through, find cross-edges
		for(int i=0;i<adj_copy.length;i++){
			for(int j=0;j<adj_copy[i].length;j++){
				if(adj_copy[i][j] == 1){
					numTotalEdges++;
					//found an edge, check for cross-edge property
					if((nodeMatrix[i].getGender() != nodeMatrix[j].getGender()) && (nodeMatrix[i].getGender() != -1)){
						numCrossEdges++;
					}
					//delete the corresponding edge.
					adj_copy[j][i] = 0;
				}
			}
		}
		System.out.println("Actual number of cross-edges:"+numCrossEdges+" out of "+numTotalEdges);
		return (numCrossEdges/numTotalEdges);
	}

	public static void main (String[] args){
		if(args.length == 0){
			System.out.println("Usage: java diameter location_of_file.csv location_of_gender_file.csv");
		}else{
			String csvOfGraph = args[0];
			String csvOfGender = args[1];
			System.out.println("Now analyzing '"+csvOfGraph +"' and '"+csvOfGender+ "' for homophily wrt gender.");
			try{
				File file = new File(csvOfGraph);
				Scanner sc = new Scanner(file);
				String line = sc.nextLine();
				//System.out.println("First line:"+line);
				//file exists, let's find out size of the adj matrix.
				int size = findSize(line);
				System.out.print("Size of graph is: "+size+" ");
				//Create the adj matrix
				float[][] adj_matrix = new float[size][size];
				Node[] node_matrix = new Node[size];
				//populate the adj matrix
				parseFile(csvOfGraph, adj_matrix, size);
				parseFileForGender(csvOfGender, node_matrix,size);

				//System.out.println("Size of node matrix is:"+node_matrix.length);

				//Calculate expected cross-edge percentage
				float numMale = 0;
				float numFemale = 0;
				for(int i=0;i<node_matrix.length;i++){
					if(node_matrix[i].getGender() == 0){
						numMale++;
					}else if(node_matrix[i].getGender() == 1){
						numFemale++;
					}
				}
				System.out.println("Males:"+numMale+ " Females:"+numFemale+" out of a total "+(numMale+numFemale));
				System.out.println("Expected Cross-Edge Percentage:"+(2 * (numMale/(numMale+numFemale)) * (numFemale/(numMale+numFemale)) ) );
				//Traverse the Adj matrix, find actual Cross-edge percentage.
				System.out.println("Actual Cross-Edge Percentage:"+getActualCrossEdgePercentage(adj_matrix,node_matrix,size));

			}catch(FileNotFoundException e){
				System.out.println("File not found error.");
			}
		}
	}

}
