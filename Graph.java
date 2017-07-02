
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
