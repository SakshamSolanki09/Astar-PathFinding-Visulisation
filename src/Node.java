import java.util.ArrayList;
import java.util.List;
public class Node 
{
	//Gcost the distances from start to this node 
	//Hcost distance from this node to the end.
	//Fcost is Gcost + Hcost
	//XY are the index positions of this node in grid.
	int x= -1,y= -1;
	double Gcost= -1,Hcost= -1,Fcost = -1;
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

		if(y > 1 && x <= PathFinding.Size - 1)
		{
			Neighbours.add(new Node(x+1, y - 1));
		}
		if(x <= PathFinding.Size - 1 && y <= PathFinding.Size - 1)
		{
			Neighbours.add(new Node(x + 1, y+1));
		}
		if(y <= PathFinding.Size - 1 && x > 1)
		{
			Neighbours.add(new Node(x-1, y + 1));
		}
		if(x > 1 && y > 1)
		{
			Neighbours.add(new Node(x - 1, y-1));
		}		
		
		
	}
}
