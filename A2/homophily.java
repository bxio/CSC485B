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
			Node tmp = new Node();

			for(int i=0;i<size;i++){
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
				//grab the diameter
			}catch(FileNotFoundException e){
				System.out.println("File not found error.");
			}
		}
	}

}
