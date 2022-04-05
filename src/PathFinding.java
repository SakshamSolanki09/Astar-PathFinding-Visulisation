import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

// The main path finding class.
public class PathFinding 
{
	static int height = 600;	// Dimensions of the content pane.
	static int Size = 50;     //Grid size.
	static Grid Grid = new Grid(Size, height); // the grid to work with.	
	static JPanel panel = Grid;    //Used the grid as JPanel too !!!
	static boolean Completed, isRunning = false; 
	static int Sleep = 2;
	static JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	static int p2size = 30;
	static JLabel label = new JLabel();
	
	public static void main(String[] args) 
	{
		setUp(); //Sets up the window.
		while (true)
		{
			findPath();	 //Find the path.
		}		
		
	}
	
	static public void setUp() 
	{
		// Setting up the window.
		Controls lis = new Controls(); //Listener
		JFrame jframe = new JFrame("PathFinding");
		jframe.setSize(height, height);
		jframe.getContentPane().setPreferredSize(new Dimension(height, height + p2size));
		panel.setBackground(new Color(158, 143, 255));
		panel.addMouseListener(lis);
		panel.addMouseMotionListener(lis);
		jframe.addKeyListener(lis);
		label.setForeground(Color.cyan);
		panel2.setBackground(Color.DARK_GRAY);
		panel2.setPreferredSize(new Dimension(height, p2size));
		panel2.add(label);
		jframe.add(panel, BorderLayout.CENTER);
		jframe.add(panel2, BorderLayout.SOUTH);
		jframe.pack();
		jframe.setVisible(true);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Window Done
	}
	
	
	//A* search Algorithm
	public static void findPath() 
	{
		//PathFinding 
		while( Completed != true  )
		{
			label.setText("Not Running");
			if( Grid.openSet.size() > 0)
			{
				label.setText("Is Running");
				isRunning = true;
				//Index of node with least FCost.
				int Winner = 0;
					
				//Initializing Winner.
				for(int i = 0; i < Grid.openSet.size(); i++)
				{
					if(Grid.openSet.get(i).Fcost < Grid.openSet.get(Winner).Fcost)
					{
						Winner = i;
					}
				}			
				//Winner Node with least Fcost.
				Node Current = Grid.openSet.get(Winner);	
				
				//Temporary GCost.
				int tempG = Current.Gcost + 1;
					
				//Check if we are at the end node.
				if (Grid.check(Current, Grid.End))
				{
					label.setText("Not Running");
					isRunning = false;
					System.out.print("DONE!");
					Completed = true;
					panel.setBackground(new Color(10, 200, 255));
					//trace back the path.
					tracePath(Current);
					
					Grid.repaint();
					break;
				}
			
				//Initializing, assigning values and adding Neigbours to OpenSet
				for(Node n : Current.Neighbours)
				{
					//Ignore the node if its in the closed set
					//That is if its already initialised.
					if(Grid.check(Grid.closedSet, n) || Grid.check(Grid.Walls, n))
					{
						continue;
					}
					
					//If Neighbour is in the openset compare its Gcost with 
					//Temporary Gcost and Assign if its lower than its Gcost.
					//else Assigning it the temp Gcost.
					if (Grid.check(Grid.openSet,n))
					{
						if(n.Gcost > tempG)
						{
							n.Gcost = tempG;
							continue;
						}
					}
					else
					{
						n.Gcost = tempG;
					}
					//Remove Analysed Current node from openNode 
					//and add it to closedset. 
					
					n.addNeighbours();		//Add neighbours
					n.CameFrom = Current;		//Set previous node
					n.Hcost = heuristic(n, Grid.End);	//Set  HCost and GCost
					n.Fcost = n.Gcost + n.Hcost;
					Grid.addToOpen(n);
				}
				Grid.openSet.remove(Current);
				Grid.addToClose(Current);
				Grid.repaint();
			}
			else
			{
				if(Grid.Start != null)
				{
					label.setText("Not Running");
					isRunning = false;
					System.out.print("No Solution");
					panel.setBackground(Color.RED);
					Completed = true;
					break;
					
				}
			}
			//Pause Time
			try {
				Thread.sleep(Sleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	//Calculate distance from node 1 to node 2 aka Hcost
	static public int heuristic(Node Start, Node End) 
	{
		
		return Math.abs(End.x - Start.x) + Math.abs(End.y - Start.y);
	}
	
	//traces back path from Given node using cameFrom Nodes. 
	public static void tracePath(Node n)
	{
		Node current = n;
		while(current != null)
		{
			Grid.addToPath(current);
			if(current.CameFrom != null)
			{
			current = current.CameFrom;
			}
			else
			{
				break;
			}
		}
	}
	static void reset()
	{
		Grid.openSet.clear();
		Grid.closedSet.clear();
		Grid.Path.clear();
		Grid.Walls.clear();
		Completed = false;
		isRunning = false;
		Grid.Start = null;
		Grid.End = null;
		panel.setBackground(new Color(158, 143, 255));		
		Grid.repaint();
	}
}
