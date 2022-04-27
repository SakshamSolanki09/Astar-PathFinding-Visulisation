import java.util.ArrayList;
import java.util.List;
public class Node 
{
	//Gcost the distances from start to this node 
	//Hcost distance from this node to the end.
	//Fcost is Gcost + Hcost
	//XY are the index positions of this node in grid.
	int x,y,Gcost,Hcost,Fcost = 0;
	Node CameFrom;
	List<Node> Neighbours = new ArrayList<Node>();
	
	//constructor
	Node(int _x, int _y)
	{
		x = _x;
		y = _y;
		
	}
	
	//Silly way to add Neighbours.
	void addNeighbours() 
	{
		if(y > 1)
		{
			Neighbours.add(new Node(x, y - 1));
		}
		if(x <= PathFinding.Size - 1)
		{
			Neighbours.add(new Node(x + 1, y));
		}
		if(y <= PathFinding.Size - 1)
		{
			Neighbours.add(new Node(x, y + 1));
		}
		if(x > 1)
		{
			Neighbours.add(new Node(x - 1, y));
		}		
		
		
	}
}
