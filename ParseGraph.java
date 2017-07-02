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
