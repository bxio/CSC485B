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
	public void parseFile(float[][] adj_list, String filename, int size){

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

		}catch(FileNotFoundException e){
			System.out.println("File not found error.");
		}

	}

}
