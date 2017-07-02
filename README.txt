Graph Algorithms- Prim, Dijkstras, Bellman
Nikita Kunchanwar

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

Total classes used:
GraphMain  (main class)
Graph
Vertex
Edge
ParseGraph


 */


/*GraphMain class */


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


/*Graph class*/

import java.util.*;

public class Graph {
	private HashMap<Integer, Vertex> vertices;
    private HashMap<Integer, Edge> edges;
    @Override
   	public String toString() {
   		// TODO Auto-generated method stub
   		return "vertices" + vertices.toString() + "edges"+ edges.toString();
   	}
    
    public Graph(){
        this.vertices = new HashMap<Integer, Vertex>();
        this.edges = new HashMap<Integer, Edge>();
    }
    
  

    public boolean addEdge(Vertex one, Vertex two, int weight){
//        if(one.equals(two)){
//            return false;   
//        }

        //ensures the Edge is not in the Graph
        Edge e = new Edge(one, two, weight);
        if(edges.containsKey(e.hashCode())){
            return false;
        }
       
        //and that the Edge isn't already incident to one of the vertices
        else if(one.containsNeighbor(e) || two.containsNeighbor(e)){
            return false;
        }
            
        edges.put(e.hashCode(), e);
        one.addNeighbor(e);
       // two.addNeighbor(e);
        return true;
    }
    
//    
//      
//       e - The Edge to look up
//      returns true iff this Graph contains the Edge e
//     
    public boolean containsEdge(Edge e){
        if(e.getOne() == null || e.getTwo() == null){
            return false;
        }
        
        return this.edges.containsKey(e.hashCode());
    }
    
    
      
    // Vertex vertex to look up
    //   true if this Graph contains vertex
     
    public boolean containsVertex(Vertex vertex){
        return this.vertices.get(vertex.getLabel()) != null;
    }
    
    public Vertex getVertex(int id){
        return vertices.get(id);
    }
    
   
   
     // returns true iff vertex was added to the Graph
     
    public boolean addVertex(Vertex vertex, boolean overwriteExisting){
        Vertex current = this.vertices.get(vertex.getLabel());
        if(current != null){
            if(!overwriteExisting){
                return false;
            }
            
        }
        
        
        vertices.put(vertex.getId(), vertex);
        return true;
    }
     
    
     // returns Set<String>  unique labels of the Graph's Vertex objects
     
    public Set<Integer> vertexKeys(){
        return this.vertices.keySet();
    }
    
   
     // returns Set<Edge> The Edges of this graph
     
    public Set<Edge> getEdges(){
        return new HashSet<Edge>(this.edges.values());
    }
    
}

/*Vertex class*/
import java.util.ArrayList;

/**
 * This class is for a vertex in a graph.
 * Graph object only accepts one Vertex per label,
 * so uniqueness of labels is important. This vertex's neighborhood
 * is described by the Edges incident to it.
*/
public class Vertex implements Comparable {

    private ArrayList<Edge> neighborhood;
    private String label;
    private int id;
    Vertex pred;
    int key;
    
    // getters, setters for predecessor of vertices
     public Vertex getPred() {
		return pred;
	}


	public void setPred(Vertex pred) {
		this.pred = pred;
	}


	public int getKey() {
		return key;
	}


	public void setKey(int key) {
		this.key = key;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public void setLabel(String label) {
		this.label = label;
	}


	// unique label associated with this Vertex
     
    public Vertex(String label,int id){
        this.label = label;
        this.neighborhood = new ArrayList<Edge>();
        this.id=id;
    }
    
    
    // This method adds an Edge to the incidence neighborhood of this graph if
    //the edge is not already present. 
     
    public void addNeighbor(Edge edge){
        if(this.neighborhood.contains(edge)){
            return;
        }
        
        this.neighborhood.add(edge);
    }
    
    
   
     //pass The edge for which to search
     //return true iff other is contained in this.neighborhood
     
    public boolean containsNeighbor(Edge other){
        return this.neighborhood.contains(other);
    }
    
    //pass index of the Edge to retrieve
    //return The Edge at the specified index in this.neighborhood
     
    public Edge getNeighbor(int index){
        return this.neighborhood.get(index);
    }
    
    
    
     
    // pass index of the edge to remove from this.neighborhood
    //return Edge The removed Edge
    
    Edge removeNeighbor(int index){
        return this.neighborhood.remove(index);
    }
    
    // pass The Edge to remove from this.neighborhood
     
    public void removeNeighbor(Edge e){
        this.neighborhood.remove(e);
    }
    
    

//     returns The number of neighbors of this Vertex
    
    public int getNeighborCount(){
        return this.neighborhood.size();
    }
    
    
    
      
     //returns the label of this Vertex
     
    public String getLabel(){
        return this.label;
    }
    
    
   //String representation of this Vertex
    
    public String toString(){
        return "id" + id + "key" + key;
    }
    
    
    // returns The hash code of this Vertex's label
    
    public int hashCode(){
        return this.label.hashCode();
    }
    
   
    public boolean equals(Object other){
        if(!(other instanceof Vertex)){
            return false;
        }
        
        Vertex v = (Vertex)other;
        return this.label.equals(v.label);
    }
    
    /**
     * 
     * @return ArrayList<Edge> A copy of this.neighborhood. Modifying the returned
     * ArrayList will not affect the neighborhood of this Vertex
     */
    public ArrayList<Edge> getNeighbors(){
        return new ArrayList<Edge>(this.neighborhood);
    }
    // implemented compareTo to call from priority queue implementation 

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		
		int key=((Vertex) arg0).getKey();
		return this.key-key;
	}
    
}

/*Edge class*/
/*
 An Edge contains two vertices and a weight
 */
public class Edge implements Comparable<Edge> {

    private Vertex one, two;
    private int weight;
    
  
     public Edge(Vertex one, Vertex two, int weight){
        this.one = one;
        this.two = two;
        this.weight = weight;
    }
    
     // return The neighbor of current along this Edge
     
    public Vertex getNeighbor(Vertex current){
        if(!(current.equals(one) || current.equals(two))){
            return null;
        }
        
        return (current.equals(one)) ? two : one;
    }
    
   
     //return Vertex this.one
  
    public Vertex getOne(){
        return this.one;
    }
    
    
    // return Vertex this.two
     
    public Vertex getTwo(){
        return this.two;
    }
    
    
    
    // return The weight of this Edge
     
    public int getWeight(){
        return this.weight;
    }
    
    
    
     // set the new weight of this Edge
    
    public void setWeight(int weight){
        this.weight = weight;
    }
    
    
    
    public int compareTo(Edge other){
        return this.weight - other.weight;
    }
    
   
    public String toString(){
        return "({" + one + ", " + two + "}, " + weight + ")";
    }
    
    
    //returns the hash code which is created using id's of two vertices of edge and weight as well to make it unique 
     
    public int hashCode(int weight){
    //	System.out.println(one.getId());
        return String.valueOf((one.getId()+two.getId()+weight)).hashCode(); 
    }
   
    public boolean equals(Object other){
        if(!(other instanceof Edge)){
            return false;
        }
        
        Edge e = (Edge)other;
        
        return e.one.equals(this.one) && e.two.equals(this.two);
    }   
}


/*ParseGraph class*/

import java.io.*; 



public class ParseGraph {
	// Stream tokenizer used in parsing the file.
	private static StreamTokenizer st;
	int countv=0,counte=0;
	// Gets the next token from the stream.
	private static int nextToken() {
		// Try to read next token from stream tokenizer.
		try {
			return st.nextToken();
		}
		// If something bad happened then return end-of-file.
		catch ( IOException e ) {
			return StreamTokenizer.TT_EOF;
		}
	} // nextToken

	public static Graph read(FileInputStream fis){

		// Initiate the stream tokenizer for parsing GML files.
		Reader r = new BufferedReader( new InputStreamReader( fis ) );
		st = new StreamTokenizer( r );
		st.commentChar( '#' );
		st.wordChars( 95, 95 ); 
		// holds current state of parser
		int state = 0;
		// holds current token value
		int token;
		// indicates whether to loop or not
		boolean loop = true;
		// indicates if parse was successful (assume failure)
		boolean success = false;
		// graph object which will contain new graph read from file
		Graph graph = null;
		// current node being created
		boolean node = false;
		int openAttributes = 0;
		int nodeOpenAttributes = 0;
		int edgeOpenAttributes = 0;

		// attributes of the current node
		int id = 0; 
		String nodeLabel = null;
		String color;
		// attributes of the current edge
		int edgeFrom = 0;
		int edgeTo = 0;
		int value=0;

		// This loops until
		// there is a parse
		// error or  when it has reached end of the file
		// or finished parsing the graph (which may be before the EOF).
		while ( loop ) {
			// Read in the next token and switch based on current state.
			token = nextToken();


			// If we reached end of file, break out of while loop.
			if ( token == StreamTokenizer.TT_EOF ) {
				break;
			}
			switch ( state ) {
			case 0 :
				// Look for the opening "graph" keyword.
				if ( token == StreamTokenizer.TT_WORD ) {
					if ( st.sval.equalsIgnoreCase( "graph" ) ) {
						// Create the new graph object.
						graph = new Graph();
						state = 1;
					}
					else if (!st.sval.equalsIgnoreCase( "graph" ))
					{

						state=0;
					}
					else {
						System.out.println( "Missing graph keyword" );
						loop = false;
					}


				}
				else if( token == StreamTokenizer.TT_NUMBER )
				{
					state=0;

				}
				else if(token== '"' || token==':')
				{
					state=0;


				}
				else {
					System.out.println( "No valid word token found" + token );
					loop = false;
				}
				break;
			case 1 :
				// A [ must follow the "graph" keyword.
				if ( token == '[' ) {
					state = 2;
				}
				else {
					System.out.println( "Missing [ after graph" );
					loop = false;
				}
				break;
			case 2 :
				// Look for "node", "edge", or ].
				if ( token == StreamTokenizer.TT_WORD ) {
					if ( st.sval.equalsIgnoreCase( "node" ) ) {
						state = 3;
					}
					else if ( st.sval.equalsIgnoreCase( "edge" ) ) {
						// Begin edge definition.
						edgeFrom = 0;
						edgeTo = 0;
						value = 0;
						state = 10;
					}
				}
				else if (token == '['){
					openAttributes++;
				}
				else if ( token == ']' ) {
					if(openAttributes == 0){
						// Stop parsing file and return "success".
						success = true;
						loop = false;
					}
					else
						openAttributes--;
				}
				// else we ignore the token
				break;
			case 3 :
				// A [ must follow the "node" keyword.
				if ( token == '[' ) {
					state = 4;
				}
				else {
					System.out.println( "Missing [ after node" );
					loop = false;
				}
				break;
			case 4 :
				// Look for "id", "graphics", "label", or ].
				if ( token == StreamTokenizer.TT_WORD ) {
					if ( st.sval.equalsIgnoreCase( "id" ) ) {
						state = 5;
					}
					else if ( st.sval.equalsIgnoreCase( "label" ) ) {
						state = 14;
					}
					//			  else if ( st.sval.equalsIgnoreCase( "graphics" ) ) {
					//			    state = 6;
					//			  }
				}
				else if (token == '['){
					nodeOpenAttributes++;
				}
				else if ( token == ']' )  {
					if(nodeOpenAttributes == 0){

						// Create new vertex and add to graph. 
						Vertex v=new Vertex(nodeLabel,id);
						node = graph.addVertex(v,true); 
						id = 0;
						nodeLabel = null;
						// Node definition complete.
						state = 2;
					}
					else
						nodeOpenAttributes--;
				}
				// else we ignore the token
				break;
			case 5 :
				// A number must follow the "id" keyword.
				if ( token == StreamTokenizer.TT_NUMBER ){
					id = (int)st.nval;
					state = 4;
				}
				else {
					System.out.println( "Missing node id value" );
					loop = false;
				}
				break;
			case 10 :
				// A [ must follow the "edge" keyword.
				if ( token == '[' ) {
					state = 11;
				}
				else {
					System.out.println( "Missing [ after edge" );
					loop = false;
				}
				break;
			case 11 :
				// Look for "source", "target", "fill", or ].
				if ( token == StreamTokenizer.TT_WORD ) {
					if ( st.sval.equalsIgnoreCase( "source" ) ) {
						state = 12;
					}
					else if ( st.sval.equalsIgnoreCase( "target" ) ) {
						state = 13;
					}
					else if ( st.sval.equalsIgnoreCase( "value" ) ) {
						state = 19;
					}
				}
				else if (token == '[' )
					edgeOpenAttributes++;
				else if ( token == ']' ){
					if(edgeOpenAttributes == 0){
						// Edge definition complete, add it to the graph.
						// If an edge already exists, it won't be added.
						graph.addEdge(graph.getVertex(edgeFrom),graph.getVertex(edgeTo),value);
						state = 2; 
					}
					else
						edgeOpenAttributes--;
				}
				// else we ignore the token
				break;
			case 12 :
				// A number must follow the "source" keyword.
				if ( token == StreamTokenizer.TT_NUMBER ) {
					// Set edge source.
					edgeFrom = (int)st.nval;
					state = 11;
				}
				else {
					System.out.println( "Missing edge source value" );
				}
				break;
			case 13 :
				// A number must follow the "target" keyword.
				if ( token == StreamTokenizer.TT_NUMBER ) {
					// Set edge target.
					edgeTo = (int)st.nval;
					state = 11;
				}
				else {
					System.out.println( "Missing edge target value" );
				}
				break;
			case 14 :
				// A String must follow the label keyword.
				if ( token == '"' ) {
					//set the nodeLabel variable
					nodeLabel = st.sval;
					state = 4;
				}
				else {
					System.out.println( "Label must be followed by a string" );
					loop = false;
				}
				break;	
			case 19 :
				// A String must follow the value keyword.
				if (  token == StreamTokenizer.TT_NUMBER ) {
					value=(int)st.nval;
					state = 11;

				}
				else {
					System.out.println( "value must be followed by an integer" );
					loop = false;
				}
				break;	
			default :
				// Something really strange happened.
				System.out.println( "Error: Invalid state value" );
				loop = false;
				break;
			}// switch state
		} // while true

		// If we were successful return the graph, otherwise null.
		if ( success ) {
			return graph;
		}
		else {
			return null;
		}
	}
}










