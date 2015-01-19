import java.util.*;
import java.io.*;

public class Assign4 {

	
	public static void main(String[] args) throws IOException {
		
		if(args.length != 5){
			System.out.println("Error: Invalid number of files specified.");
			System.exit(1);
		}
		
		Graph g = new Graph();
		BufferedWriter out1 = new BufferedWriter(new FileWriter(args[2]));
		BufferedWriter out2 = new BufferedWriter(new FileWriter(args[3]));
		BufferedWriter out3 = new BufferedWriter(new FileWriter(args[4]));
		
		try {
			g.buildfromfile(new File(args[0]));
			Scanner query = new Scanner(new File(args[1]));
			
			while(query.hasNextLine()){
				int start, dest;
				start = query.nextInt();
				dest = query.nextInt();
				out1.write(g.depthfirstsearch(start, dest) + "\n");
				out2.write(g.breadthfirstsearch(start, dest) + "\n");
				out3.write(g.dijkstra(start, dest) + "\n");
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("Cannot find at least one of the specified input files");
			System.exit(1);
		}
		
		out1.close();
		out2.close();
		out3.close();
	}
}
