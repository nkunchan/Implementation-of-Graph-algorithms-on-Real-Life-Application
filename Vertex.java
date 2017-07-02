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
