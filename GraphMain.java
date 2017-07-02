/*Programming Project 4– Graph
//Shashank Gupta, Nikita Kunchanwar, Varun Krishnan

README:

->Coded this project in Java.
->Input file name used for this project is "lesmis.gml" which is provided in the program itself.
->Command to provide to form a graph is "Graph"
->3 algorithms are implemented in this project namely Prim's, Dijkstra's, Bellman-ford
->Command to apply Prim's algorithm on generated graph is  -  Prim
->Prim algorithm- 
1. key for the source vertex provided in the program will be set to 0 
2. Priority queue will return the vertex with minimum key 
3. keys for the vertices returned from Adjecency list of the minimum key given by priority key is comapared to existing value 
and minimum value will be maintained in priority key.
4. Priority queue will maintain the vertices with minimum weights 
-> Prim algorithm will return the all the edges which are accessible from the provided source vertex in the program to form
minimum spanning tree 
->Command to apply Dijkstra's algorithm on the generated graph is- Dijkstras node'node_source' node'node_destination' for ex. Dijkstras node70 node12
->Dijkstras algo-
1.Initialize single source : distances and predecessor to some default values
2.set distance[source] to 0
3.Till vertices exists
4.Find the vertex u with the minimum distance
5.For such a vertex u, all the adjacent vertices v are checked to relax edge (u,v) 
->Command to apply Bellman-ford algorithm on the generated graph is- Bellman node'node_source' node'node_destination'  for ex. Bellman node70 node12
->Bellman ford algo-
1.Bellman Ford runs two loops 
2.The outer loop runs for (no of vertices - 1) times
3.The inner loop runs for each edge (u,v) to relax the edge
4.After the end of the two loops
5.Another loop is run to check whether any edge(u,v) can be further relax
If such is the case then negative weight cycle exists and return false
->The command "print" prints all the outputs calculated using above commands
->The command "quit" causes the program to exit.


 */





import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class GraphMain {

	static ArrayList<String> primoutput;
	static ArrayList<Vertex> prio_queue=new ArrayList<Vertex>();
	static int precedence[];
	static  int distance[];
	static int source,destination;
	static int vertexSize;
	static int flag=0;
	static int precedenceBell[];
	static  int distanceBell[];
	static int sourceBell,destinationBell;
	static int vertexSizeBell;
	static int flagBell=0;
	static boolean result;
	/* Method to return vertex with minimum key available in priority queue */
	Vertex extract_Min()
	{
		//System.out.println("Prio_queue"+prio_queue.toString());
		Collections.sort(prio_queue);
		return prio_queue.get(0);
	}
	/*Bellman Ford algorithm */
	static boolean bellmanFord(Graph g, int s, int d)
	{
		sourceBell=s;
		destinationBell=d;
		vertexSizeBell=g.vertexKeys().size();
		Set<Edge> edgeList=g.getEdges();
		//keeping minimum distance from source
		distanceBell=new int[vertexSizeBell];
		//keeping predecessor
		precedenceBell=new int[vertexSizeBell];

		//Initialize Single Source
		for( int i=0;i<vertexSizeBell;i++)
		{
			distanceBell[i]=Integer.MAX_VALUE;
			precedenceBell[i]=-1;
		}
		distanceBell[sourceBell]=0;


		for (int count = 0; count < vertexSizeBell-1; count++)
		{
			Iterator<Edge> it = edgeList.iterator();
			while(it.hasNext())	//iterating all edges
			{
				Edge e = it.next();
				if (distanceBell[e.getOne().getId()]!=Integer.MAX_VALUE && distanceBell[e.getTwo().getId()]>distanceBell[e.getOne().getId()]+e.getWeight())
				{
					//if smaller distance exists
					distanceBell[e.getTwo().getId()]=distanceBell[e.getOne().getId()]+e.getWeight();
					precedenceBell[e.getTwo().getId()]=e.getOne().getId();
				}
			}
		}


		Iterator<Edge> it = edgeList.iterator();

		while(it.hasNext())	//for each edge
		{
			Edge e = it.next();
			if (distanceBell[e.getOne().getId()]!=Integer.MAX_VALUE && distanceBell[e.getTwo().getId()]>distanceBell[e.getOne().getId()]+e.getWeight())
			{
				return false;
			}
		}

		//   printpath(precedenceBell, sourceBell, destinationBell);
		return true;

	}
	/*Dijkstra's algorithm*/
	static void dijkstra(Graph g, int s, int d)
	{
		source=s;
		destination=d;

		vertexSize=g.vertexKeys().size();
		//keeping minimum distance from source
		distance=new int[vertexSize];
		//keeping predecessor
		precedence=new int[vertexSize];
		boolean visited [] = new boolean [vertexSize];

		//Initialize Single Source
		for( int i=0;i<vertexSize;i++)
		{
			distance[i]=Integer.MAX_VALUE;
			precedence[i]=-1;
			visited [i]=false;
		}
		distance[source]=0;
		int u;

		for (int count = 0; count < vertexSize-1; count++)
		{

			u = minDistance(distance, visited, vertexSize);//Getting the vertex with the shortest distance
			if(u==-1)
				break;	//If no path exists 
			visited[u] = true;

			for (int j=0;j<g.getVertex(u).getNeighborCount();j++)
			{
				if (distance[g.getVertex(u).getNeighbor(j).getTwo().getId()]>distance[g.getVertex(u).getNeighbor(j).getOne().getId()]+g.getVertex(u).getNeighbor(j).getWeight())
				{
					//if smaller distance exists
					distance[g.getVertex(u).getNeighbor(j).getTwo().getId()]=distance[g.getVertex(u).getNeighbor(j).getOne().getId()]+g.getVertex(u).getNeighbor(j).getWeight();
					precedence[g.getVertex(u).getNeighbor(j).getTwo().getId()]=g.getVertex(u).getNeighbor(j).getOne().getId();
				}

			}
		}

		//    for (int i=0;i<vertexSize;i++)
		//    	System.out.println(i+","+distance[i]);
		//    printpath(precedence, source, destination);
		//    
	}

	static int minDistance(int distance[], boolean visited[], int vertexSize)
	{
		int min = Integer.MAX_VALUE, min_vertex=-1;

		for (int v = 0; v < vertexSize; v++)
			if (visited[v] == false && distance[v] < min)
			{
				min = distance[v];
				min_vertex = v;
			}

		return min_vertex;
	}

	static void printpath(int precedence[],int source, int destination)
	{
		if(destination==source)
			System.out.print(source);
		else if (precedence[destination]==-1)
			System.out.print("Not reachable");
		else {printpath(precedence,source,precedence[destination]);
		System.out.print("-->"+destination);}

	}


	/* Prim algorithm */
	void prim(Graph g,Vertex v)
	{
		Vertex v1;

		GraphMain obj2=new GraphMain();

		for(int i=0;i<g.vertexKeys().size();i++)
		{
			v1=g.getVertex(i);

			if(v1.equals(v))
			{
				//setting source to 0
				primoutput=new ArrayList<String>();
				v1.setKey(0);
				//System.out.println("setting source to 0");
			}
			else{

				v1.setKey(Integer.MAX_VALUE);
			}
			v1.setPred(null);
			//adding vertex to list
			prio_queue.add(v1);

		}

		while(!prio_queue.isEmpty())
		{
			Vertex vertex_min=obj2.extract_Min();

			//System.out.println("minimum_key"+vertex_min.key);
			prio_queue.remove(0);

			//			if(prio_queue.isEmpty())
			//			{
			//				primoutput.add(String.valueOf(vertex_min.getId()));
			//				//System.out.print(vertex_min.getId());
			//			}
			//			else
			//{
			//System.out.print(vertex_min.getId()+"->");
			if(vertex_min.getPred()!=null){
				primoutput.add(vertex_min.getPred().getId()+"->"+String.valueOf(vertex_min.getId()));
				primoutput.add("\n");
			}
			//}
			//System.out.println("neighbours" + vertex_min.getNeighbors());

			for(Edge e:vertex_min.getNeighbors())
			{

				if(prio_queue.contains(e.getTwo()))
				{
					// e.getTwo() is nothing but target 

					int neighbour_weight=e.getWeight();
					//System.out.println("ne_weight"+neighbour_weight);
					if(neighbour_weight<(e.getTwo().getKey()))
					{
						//System.out.println("hie low value");

						Vertex object_search=prio_queue.get(prio_queue.indexOf(e.getTwo()));
						object_search.setPred(vertex_min);
						object_search.setKey(neighbour_weight);
					}

				}

			}

		}


	}

	public static void main(String[] args)
	{
		//project 10
		//Shashank Gupta, Nikita Kunchanwar, Varun Krishnan
		Graph g=null;
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("Please provide a command");
			System.out.println("Graph -  Enter 'Graph' to form a graph");
			System.out.println("Prim's -  Enter Prim to apply prim's algorithm on graph to form minimum spanning tree");
			System.out.println("Dijkstra's -  Enter Dijkstra node1,node2  to apply Dijkstra's algorithm on graph to find shortest path from source");
			System.out.println("Bellman ford -  Enter Bellman node1,node2 to apply Bellman ford algorithm on graph to find shortest path from source");

			String selection = scanner.nextLine();  // reads the command provided in console
			if(selection.equalsIgnoreCase("Graph"))
			{
				try {
					g=ParseGraph.read(new FileInputStream("lesmis.gml"));
					//					for(int i=0;i<g.vertexKeys().size();i++)
					//					{
					//						System.out.println("adjecency_list(" + i + ")"+g.getVertex(i).getNeighbors());
					//					}


				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			else if(selection.startsWith("Prim"))
			{
				if(g!=null)
				{

					GraphMain obj1=new GraphMain();
					obj1.prim(g,g.getVertex(64));
				}
				else{
					System.out.println("no graph found to apply Prim's algo");
				}
			}
			else if(selection.startsWith("Dijkstra"))
			{
				if(g!=null)
				{
					try{
						int s=Integer.parseInt(selection.substring(selection.indexOf("node")+4, selection.lastIndexOf("node")).trim());
						int d=Integer.parseInt(selection.substring(selection.lastIndexOf("node")+4, selection.length()));
						//int s=Integer.parseInt(selection.substring(10, selection.length()));
						//int d=Integer.parseInt(selection.substring(10, selection.length()));
						dijkstra(g,s,d);
						flag=1;
					}
					catch(Exception e)
					{
						System.out.println("not a valid command");
						System.out.println("please provide a command as -  Dijkstras node'source_id' node'destination_id'  for ex. Dijkstras node70 node12");
					}



				}
				else{
					System.out.println("no graph found to apply Dijkstra's algo");
				}
			}
			else if(selection.equalsIgnoreCase("print"))
			{
				System.out.println("Prim output");
				if(primoutput==null||primoutput.isEmpty())
				{
					System.out.println("no results to show");
				}
				else{
					System.out.println("Minimum Spanning tree");
					System.out.println("list of edges");
					for(int i=0;i<primoutput.size();i++)
						System.out.print(primoutput.get(i));
				}
				System.out.println("");
				System.out.println("Dijkstra's output");
				if(flag==1)
				{
					System.out.println("Distance from source " + source);
					for (int i=0;i<vertexSize;i++)
						System.out.println(i+","+distance[i]);
					System.out.println("Path from source to destination");
					printpath(precedence, source, destination);
					System.out.println(" : " + distance[destination]);
					flag=0;
				}
				else{
					System.out.println("no results to show");
				}
				System.out.println("");
				System.out.println("Bellman ford's output");
				if(flagBell==1)
				{
					if(result){
						System.out.println("No negative weight cycle");
					}
					else {
						System.out.println("Negative weight cycle exists");
					}
					System.out.println("Path from source to destination");
					printpath(precedenceBell, sourceBell, destinationBell);
					flagBell=0;
				}
				else{
					System.out.println("no results to show");
				}



			}
			else if(selection.startsWith("Bellman"))
			{
				if(g!=null)
				{
					flagBell=1;
					try{
						int s=Integer.parseInt(selection.substring(selection.indexOf("node")+4, selection.lastIndexOf("node")).trim());
						int d=Integer.parseInt(selection.substring(selection.lastIndexOf("node")+4, selection.length()));

						result = bellmanFord(g,s,d);
					}
					catch(Exception e)
					{
						System.out.println("not a valid command");
						System.out.println("please provide a command as -  Bellman node'source_id' node'destination_id'  for ex. Bellman node70 node12");
					}
				}

				else{
					System.out.println("no graph found to apply Bellman ford's algo");
				}
			}
			else if (selection.equals("quit")) {
				// finish the program
				break;
			} else {
				System.out.println("Please give a valid command");
			}

		}

	}
}












