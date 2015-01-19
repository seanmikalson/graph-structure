import java.util.*;
import java.io.*;

public class Graph {
	
	private int[][] amatrix; //adjacency matrix
	private int[] vertices; //keeps track of order visited; In dijkstra's, keeps track of distance
	private int[][] edges;	//an array to store the edges traversed in each traversal
	private int size;
	private int count;
	private String path;
	//global variable needed for recursive dfs() funtion; when found == dest, this is the an indication to 
	//return back to original dfs() call indicating dest has been found
	private int found; 
	
	public Graph(){
		amatrix = null;
		vertices = null;
		edges = null;
		size = 0;
	}
	
	//builds amatrix from adjacency matrix supplied in file
	public void buildfromfile(File f) throws FileNotFoundException{
		Scanner s1 = new Scanner(f);
		Scanner s2 = new Scanner(f);
		
		//determining size of matrix.
		for(size = 0; s1.hasNextLine(); size++, s1.nextLine()){}
		
		//allocating matrices of the appropriate size
		amatrix = new int[size][size];
		vertices = new int[size];
		edges = new int[size][2];
		
		//reading from file
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				amatrix[i][j] = s2.nextInt();
			}
			s2.nextLine();
		}
	}
	
	//returns string indicating path taken to find dest in depth first traversal
	public String depthfirstsearch(int start, int dest){
		//resets vertices to 0 to indicate that they need to be visited
		for(int i = 0; i < size; i++){
			 vertices[i] = 0;
		}
		//reset path
		 path = "";
		 //reset count
		 count = 1;
		 
		 int i = 0;
		 //while there exists a vertice that has not been visited call dfs
		 while(i < size){
			 if(vertices[i] == 0){
				 //found will equal dest if a path to dest has been found
				 int found = dfs(start, dest);
				 if(found == dest){
					 //traces path from start to dest using edges array; puts tracing into path
					 tracepath(start, dest);
					 path += Integer.toString(dest);
					 return path;
				 }
				//found not equal to dest
				 else{
					 path += start + ",\t-1,\t" + Integer.toString(dest);
					 return path;
				 }
			 }
			 i++;
		 }
		 
		 return path;
	}
	
	private int dfs(int v, int dest){
		//vertex v is being visited
		vertices[v] = count++;
		
		int i = 0;
		//for all vertices that are adjacent to v; record its edge to v and visit it.
		while(i < size){
			if(amatrix[v][i] >= 1){
				if(vertices[i] == 0){
					edges[count - 2][0] = v;
					edges[count - 2][1] = i;
					
					//checking to see if dest has been found; if not then call dfs again and visit i
					if(i == dest){
						return i;
					}else{
						//visit i; if dest is found in subsequent dfs calls is then this allows for a
						//direct return to original call
						found = dfs(i, dest);
						if(found == dest){
							return found;
						}
					}
				}

			}
			i++;
		}
		return -1;
	}
	
	//returns a string showing the path from start to dest using breadth first traversal
	public String breadthfirstsearch(int start, int dest){
		
		 //resets vertices to 0 indicating all vertices need visiting; resets edges.
		 for(int i = 0; i < size; i++){
			 vertices[i] = 0;
			 edges[i][0] = -1;
			 edges[i][1] = -1;
		 }
		 //Queue is adapted from queue used in Assign3
		 Queue q = new Queue();
		 //reset path		 
		 path = "";
		 //reset count
		 count = 1;
		 //a flag to indicate whether dest has been found or not
		 boolean flag = false;
		 
		 //start vertex is being visited; only concerned about vertices that are connected to start vertex
		 vertices[start] = count++;
		 q.enqueue(start);
		 while(!q.isEmpty()){
			 int v = q.dequeue();
			 
			 for(int i = 0; i < size; i++){
				 //for all vertices adjacent to v, visit these first, if they haven't been visited before
				 if(amatrix[v][i] >= 1){
					 if(vertices[i] == 0){
						 vertices[i] = count++;
						 q.enqueue(i);
						 edges[count - 3][0] = v;
						 edges[count - 3][1] = i;
						 //checking to see if dest has been found and thus, also a path
						 if(i == dest){
							 flag = true;
							 break;
						 }
					 }
				 }
			 }
		 }
		
		
		//checking to see if path has been found
		if(flag == true){
			 //traces a path from start to dest using edges array
			 tracepath(start, dest);
			 path += Integer.toString(dest);
		 }else{
			path += Integer.toString(start) + ",\t-1,\t" + Integer.toString(dest);
		 }
		 return path;

	}
	
	public String dijkstra(int start, int dest){
		//keeps track of which vertices have been checked
		boolean[] hasbeenchecked = new boolean[size];
		//keeps track of preceding vertex on the shortest path
		int[] pred = new int[size];
		
		for(int i = 0; i < size; i++){
			//vertices stores the distance from the start vertex to each vertex; resetting here
			vertices[i] = Integer.MAX_VALUE;
		}
		
		//distance from start to start = 0
		vertices[start] = 0;
		pred[start] = -1;
		
		//loop while there exists unchecked vertices
		while(uncheckedexists(hasbeenchecked)){
			//setting min index to first unchecked vertex
			int min = 0;
			for(int i = 0; i < size; i++){
				if(hasbeenchecked[i] == false){
					min = i;
					break;
				}
			}
			//v = vertex that has yet to be checked that has the smallest distance from start
			for(int k = 0; k < size; k++){
				if((hasbeenchecked[k] == false) && vertices[min] > vertices[k]){
					min = k;
				}
			}
			int v = min;
			
			//checking v
			hasbeenchecked[v] = true;
			
			for(int j = 0; j < size; j++){
				//for all vertices that are adjacent to v and have not been checked
				if(amatrix[v][j] != 0 && hasbeenchecked[j] == false){
					int weight = amatrix[v][j];
					//if distance to v + distance from v to j is smaller than current distance from start, update
					if(vertices[j] > (vertices[v] + weight)){
						vertices[j] = vertices[v] + weight;
						pred[j] = v;
					}
				}
			}
		}
		
		//tracing the path using the pred[] array; checking to see if there is a path to dest
		path = Integer.toString(dest);
		if(vertices[dest] != Integer.MAX_VALUE){
			for(int m = dest; m != start; m = pred[m]){
				path = Integer.toString(pred[m]) + ",\t" + path;
			}
		}else{
			path = Integer.toString(start) + ",\t-1,\t" + path;
		}
		
		return path;
	}
	
	//checks to see if an unchecked vertice exists in hasbeenchecked
	private boolean uncheckedexists(boolean[] hasbeenchecked){
		for(int i = 0; i < size; i++){
			if(hasbeenchecked[i] == false){
				return true;
			}
		}
		return false;
	}
	
	//recursively traces the path using the edges array
	private void tracepath(int start, int dest){
		if(start == dest){
			return;
		}else{
			int i;
			for(i = 0; i < size; i++){
				if(edges[i][1] == dest){
					break;
				}
			}
			if(i < size){
				tracepath(start, edges[i][0]);
				path += Integer.toString(edges[i][0]) + ",\t";
				return;
			}
		}
	}

}
