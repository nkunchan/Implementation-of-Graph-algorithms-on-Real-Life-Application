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

