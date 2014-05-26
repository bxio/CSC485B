/*********************
** Bill Xiong       **
** V00737042        **
** CSc 485B         **
** Summer 2014      **
*********************/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class diameter{

	//Parses a file with a graph in .csv format and generates an adjacency list
	public static void parseFile(float[][] adj_list, String filename, int size)
	{
		File file = new File(filename);

		try{
			Scanner sc = new Scanner(file);
			String line = sc.nextLine();

			int row = size;
			int col = size;

			for(int i = 0; i < row; i++)
			{
				line = sc.nextLine();
				String[] nodes = line.split(";");

				for(int j = 0; j < col; j++)
				{
					adj_list[i][j] = Float.parseFloat(nodes[j+1]);
					if(adj_list[i][j] == 0)
						adj_list[i][j] = Float.POSITIVE_INFINITY;
				}
			}
		}

		catch (FileNotFoundException e)
		{
			System.out.println("file io error");
		}
	}

	//Runs the Floyd Warshall algorithm on the given adjacency list
	public static void floydWarshall(float[][] adj_list, int size){
		for(int k = 0; k < size; k++){
			for(int i = 0; i < size; i++){
				for(int j = 0; j < size; j++){
					adj_list[i][j] = Math.min(adj_list[i][j], adj_list[i][k] + adj_list[k][j]);
				}
			}
		}
	}

	//Gets the diameter of a graph given the adjacency list of
	//the graph and its size
	public static int getDiameter(float[][] adj_list, int size)
	{
		float[][] array_copy = adj_list.clone();

		floydWarshall(array_copy, size);

		float diameter = 0;
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				if(adj_list[i][j] != Float.POSITIVE_INFINITY){
					if (diameter < adj_list[i][j])
					diameter = adj_list[i][j];
				}
			}
		}

		return (int)diameter;
	}

	public static void main(String[] args)
	{
		//Declarations
		String filename = "graph.csv";
		int size = 89;
		float[][] adj_list = new float[size][size];

		//Generate Adjacency List
		parseFile(adj_list, filename, size);

		//get diameter
		int diameter = getDiameter(adj_list, size);

		System.out.println("Diameter of " + "\"" + filename + "\"" + " is: " + diameter);
	}
}
