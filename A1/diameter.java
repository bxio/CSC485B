/*********************
** Bill Xiong       **
** V00737042        **
** CSC485B - A1     **
** Summer 2014      **
*********************/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class diameter{
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
	 * Runs the Floyd Warshall algorithm on the given adjacency list
	 *
	 * @param  adj_matrix  the adjacency matrix representing the graph
	 * @param  size  the size of the adjacency matrix (it's offset by 1, rememeber.)
	 */
	public static void floydWarshall(float[][] adj_matrix, int size){
		for(int k = 0; k < size; k++){
			for(int i = 0; i < size; i++){
				for(int j = 0; j < size; j++){
					adj_matrix[i][j] = Math.min(adj_matrix[i][j], adj_matrix[i][k] + adj_matrix[k][j]);
				}
			}
		}
	}

	/**
	 * Finds the diameter of the graph
	 *
	 * @param  adj_matrix  the adjacency matrix representing the graph
	 * @return      the diameter of the graph.
	 */
	public static int getDiameter(float[][] adj_matrix, int size){
		//copy the adj matrix
		float[][] copyOfAdjMatrix = adj_matrix.clone();
		//run the floyd-warshall algorithm
		floydWarshall(copyOfAdjMatrix, size);

		float diameter = 0;
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				if(adj_matrix[i][j] != Float.POSITIVE_INFINITY){
					if (diameter < adj_matrix[i][j]){
						diameter = adj_matrix[i][j];
					}
				}
			}
		}
		return (int)diameter;
	}

	public static void main (String[] args){
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter filename:");
		String filename = sc.nextLine();
		sc.close();

		System.out.println(filename);
		try{
			File file = new File(filename);
			sc = new Scanner(file);
			String line = sc.nextLine();
			//System.out.println("First line:"+line);
			//file exists, let's find out size of the adj matrix.
			int size = findSize(line);
			System.out.print("Size of graph is:"+size+" ");
			//Create the adj matrix
			float[][] adj_matrix = new float[size][size];
			//populate the adj matrix
			parseFile(filename, adj_matrix, size);
			//grab the diameter
			int diameter = getDiameter(adj_matrix, size-1);
			System.out.println("Diameter is:"+diameter);

		}catch(FileNotFoundException e){
			System.out.println("File not found error.");
		}
	}
}
