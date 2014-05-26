/*********************
** Bill Xiong       **
** V00737042        **
** CSC485B          **
** Summer 2014      **
*********************/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class diameter{

	//scan in the file?
	public static void parseFile(String filename, float[][] adj_list, int size){
		File file = new File(filename);

		try{
			Scanner sc = new Scanner(file);
			String line = sc.nextLine();

			int row = size-1;
			int col = size-1;

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
		}catch (FileNotFoundException e){
			System.out.println("file io error");
		}
		sc.close();
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

	public static int findSize(String firstLine){
		String[] nodes = firstLine.split(";");
		return nodes.length;
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
			System.out.println("First line:"+line);
			//file exists, let's find out size of the adj matrix.
			int size = findSize(line);
			System.out.println("Size is:"+size);
			//Create the adj matrix
			float[][] adj_matrix = new float[size][size];
			//populate the adj matrix
			parseFile(filename, adj_matrix, size);

		}catch(FileNotFoundException e){
			System.out.println("File not found error.");
		}

	}

}
