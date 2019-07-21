import java.awt.Color;
import java.awt.Graphics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Random;

import javax.swing.*;

//Class responsible for drawing grid, nodes, UI.
@SuppressWarnings("serial")
public class Grid extends JPanel
{
	int Size; // Grid Size
	Random r;
	float Spacing;
	int Height;
	List<Node> openSet  = new ArrayList<Node>(); // Open Nodes that needs to be analyzed.
	List<Node> closedSet = new ArrayList<Node>(); // Analyzed Nodes.
	List<Node> Path = new ArrayList<Node>(); // list of path nodes.
	List<Node> Walls = new ArrayList<Node>(); // List of wall nodes.
	Node End;
	Node Start;
	
	
	//constructor
	Grid(int size, int height)
	{
		Size = size;
		r  = new Random(Size);
		Spacing = (height/Size);
		Height = height;		
		
//		for(int i = 1; i < 25;i++)
//		{			
//			addToWalls(new Node(i, i));
//			addToWalls(new Node(i,Size-i));
//			addToWalls(new Node(Size - i,Size-i));
//			addToWalls(new Node(Size - i,i));
//		}
	}
	//Paint everything on the screen.
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
				
		//Draw the openSet Nodes in yellow.
		for(int i = 0; i < openSet.size() ; i++) 
		{		
			g.setColor(new Color(100, 255, 100, 180));
			g.fillRect(getPixel(openSet.get(i).x), getPixel(openSet.get(i).y),(int) Spacing,(int) Spacing);
		}
		
		//Draw the closedSet Nodes in white.
		for(int i = 0; i < closedSet.size() ; i++) 
		{
			g.setColor(new Color(255, 100, 100 , 130));
			g.fillRect(getPixel(closedSet.get(i).x), getPixel(closedSet.get(i).y) ,(int) Spacing,(int) Spacing);
		}
		
		//Draw the path Nodes in orange.
		for(int i = 0; i < Path.size() ; i++) 
		{		
			g.setColor(Color.ORANGE);
			g.fillRect(getPixel(Path.get(i).x), getPixel(Path.get(i).y) ,(int) Spacing,(int) Spacing);
		}
		
		//Draw walls in Black
		for(int i = 0; i < Walls.size() ; i++) 
		{
			g.setColor(Color.darkGray);
			g.fillRect(getPixel(Walls.get(i).x), getPixel(Walls.get(i).y) ,(int) Spacing,(int) Spacing);
		}
		
		//Drawing the end Node.
		if(End != null)
		{
			g.setColor(new Color  (100, 100, 255));
			g.fillRect(getPixel(End.x), getPixel(End.y), (int) Spacing, (int) Spacing);
		}
		//Draw the Start Node.
		if(Start != null)
		{
			g.setColor(new Color(0, 100, 100));
			g.fillRect(getPixel(Start.x), getPixel(Start.y), (int) Spacing, (int) Spacing);
		}
			
		//Drawing the grid!		
		int CountX = 1;		// X line counter.
		int CountY = 1;		// Y line counter.
		
		//loops to draw lines as fake nodes.
		for(int i = 0; i < Size -1; i++) 
		{
			g.setColor(Color.BLACK);
			g.drawLine(0, (int) Spacing * CountX, Height, (int) Spacing * CountX);
			CountX++;
			g.drawLine((int) Spacing * CountY, 0, (int) Spacing * CountY, Height);
			CountY++;
		}
	}
	
	//Voids for node functionalities.
	
	//Get index of node on grid from its Pixels.
	public int[] getIndex(int x, int y) 
	{
		//IndexX to return
		int indexX = 1;
		
		//Index logic!
		for(int i = 0; i < Size -1;i++)
		{
			if(x < Spacing * indexX)
			{
				break;
			}
			indexX++;
		}
		//IndexY to return
		int indexY = 1;
				
		//Index logic!
		for(int i = 0; i < Size-1;i++)
		{
			if(y < Spacing * indexY)
			{
				break;
			}
			indexY++;
		}
		
		return new int[] {indexX};
	}
	//Version of getIndex with one input
	public int getIndex(int x) 
	{
		//IndexX to return
		int indexX = 1;
		
		//Index logic!
		for(int i = 0; i < Size -1;i++)
		{
			if(x < Spacing * indexX)
			{
				break;
			}
			indexX++;
		}
		return indexX;
	}
	//Get Pixel in screen for index of node.
	public int getPixel(int x)
	{
		return (int) Spacing*(x - 1);
	}
	
	//Adding Start Node on Left Mouse Click
	void addStart(int x, int y)
	{
		if(!PathFinding.isRunning)
		{
			if(openSet.size() <= 0 && End != null)
			{
				Start = new Node(getIndex(x), getIndex(y));
				Start.addNeighbours();
				Start.Gcost = 0;
				Start.Hcost = PathFinding.heuristic(Start, End);
				Start.Fcost = Start.Gcost + Start.Hcost;
				openSet.add(Start);		
			}
			
			this.repaint();
		}
		
	}
	
	//Adding End Node on Right Mouse Click
	void addEnd(int x, int y)
	{
		if(!PathFinding.isRunning && !PathFinding.Completed)
		{
			End = new Node(getIndex(x), getIndex(y));
			this.repaint();
		}
	}
	//Add Nodes to openSet With Functionalities.
	void addToOpen(Node n)
	{
		if(!check(openSet,n))
		{
			openSet.add(n);
		}
	}
	//Add Nodes to closeset With Functionalities.
	void addToClose(Node n)
	{
		if(!check(closedSet, n))
		{
			closedSet.add(n);
		}
	}
	//Add Nodes to Path With Functionalities.
	void addToPath(Node n)
	{
		if(!check(Path, n))
		{
			Path.add(n);
		}
	}
	void addToWalls(Node n, boolean t)
	{
		if(!PathFinding.isRunning && !PathFinding.Completed)
		{
			if(t == true)
			{
				Node temp = new Node(getIndex(n.x), getIndex(n.y));
				if(!check(Walls, temp))
				{
					Walls.add(temp);
				}
			}
			else
			{
				if(!check(Walls, n))
				{
					Walls.add(n);
				}
			}
			this.repaint();
		}
	}
	//Checks for Given node in the given list.
	boolean check(List<Node> arr, Node node)
	{
		for (Node n : arr)
		{
			if(n.x == node.x && n.y == node.y)
			{
				return true;
			}
		}
		return false;
	}
	//Compares two Nodes.
	boolean check(Node n1, Node n2)
	{		
		if(n1.x == n2.x && n1.y == n2.y)
		{
			return true;
		}		
		return false;
	}
	void GenerateMaze()
	{
		if(!PathFinding.isRunning && !PathFinding.Completed)
		{
			Walls.clear();
			for (int i = 0; i <= Size; i++)
			{
				for(int j = 0; j <= Size ;j++)
				{
					
					if(Math.random() < 0.3)
					{
						addToWalls(new Node(i, j), false);
					}
				}
			}
			repaint();
		}
	}
	
}