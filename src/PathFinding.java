import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

// The main path finding class.
public class PathFinding 
{
	static int height = 700;	// Dimensions of the content pane.
	static int Size = 100;     //Grid size.
	static Grid Grid = new Grid(Size, height); // the grid to work with.	
	static JPanel panel = Grid;    //Used the grid as JPanel too !!!
	static boolean Completed, isRunning = false; 
	static int Sleep = 1;
	static JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	static JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	static int p2size = 30;
	static int p3size = 450;
	static JLabel label = new JLabel();
	static JLabel label2 = new JLabel();
	static boolean showStat;
	static double diagonalDistance;
	static double distance;
	static long starttime;
	static float elapsedtime;
	
	public static void addToGrid(int x)
	{
		Grid.addSize(x);
		if((Size+x >0) && (Size+x <= 100))
		{Size += x;}
		
	}
	static public void setUp() 
	{
		
		// Setting up the window.
		Controls lis = new Controls(); //Listener
		JFrame jframe = new JFrame("PathFinding");
		jframe.setSize(height, height);
		jframe.getContentPane().setPreferredSize(new Dimension(height+p3size, height + p2size));
		panel.setBackground(new Color(158, 143, 255));
		panel.addMouseListener(lis);
		panel.addMouseMotionListener(lis);
		panel.addMouseWheelListener(lis);
		label.setForeground(Color.cyan);
		label2.setForeground(Color.cyan);
		panel2.setBackground(Color.DARK_GRAY);
		panel2.setPreferredSize(new Dimension(height, p2size));
		panel2.add(label);
		panel3.setBackground(Color.DARK_GRAY);
		panel3.setPreferredSize(new Dimension(p3size,height));
		panel3.add(label2);
		jframe.addKeyListener(lis);
		jframe.add(panel, BorderLayout.CENTER);
		jframe.add(panel2, BorderLayout.SOUTH);
		jframe.add(panel3, BorderLayout.EAST);
		jframe.pack();
		jframe.setVisible(true);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Window Done
	}
	
	
	//A* search Algorithm
	public static void findPath() 
	{
		starttime=System.currentTimeMillis();
		distance=Grid.Spacing;
		diagonalDistance= Math.sqrt(2*(distance*distance));
		updateLabel();
		//PathFinding 
		while( Completed != true  )
		{

			updateLabel();
			if( Grid.openSet.size() > 0)
			{
				
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
				double tempG;

					
				//Temporary GCost.
				
				//Check if we are at the end node.
				if (Grid.check(Current, Grid.End))
				{
					elapsedtime = (System.currentTimeMillis()-starttime)/10;
					Current.Fcost=0;
					isRunning = false;
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
					if(Current.x-n.x!=0&&Current.y-n.y!=0)
					 {tempG = Current.Gcost + diagonalDistance;}
					else {
						tempG=Current.Gcost+distance;
					}
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
					elapsedtime = (System.currentTimeMillis()-starttime)/10;
					isRunning = false;
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
		return Math.abs(Grid.getPixel(End.x) - Grid.getPixel(Start.x)) + Math.abs(Grid.getPixel(End.y) - Grid.getPixel(Start.y));
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
	static void updateLabel()
	{
		label2.setText("<html>"
				+ "<br><h2><u> Colour Codes:</u></h2><h3>"
				+ "<br> <span style=\"color:#9e8fff\">	Clear path</span>"
				+ "<br> <span style=\"color:#303030\">	Obstacle/Walls</span>"
				+ "<br> <span style=\"color:#0066ff\">	Start Block</span>"
				+ "<br> <span style=\"color:#ff00ff\">	Destination Block</span>"
				+ "<br> <span style=\"color:#64ff64\">	OpenSet</span>"
				+ "<br> <span style=\"color:#ff6464\">	ClosedSet</span>"
				+ "<br> <span style=\"color:#ff9500\">	Traced Path</span>"
				+ "<br><br><h2><u>Controls:</u></h2><h3>"
				+ "<br>- &lt; and &gt; for speed control"
				+ "<br>- Right Click to mark a block as Target Block"
				+ "<br>- Left Click to mark a block as Start Block and start PathFinding</h3>"
				+ "</h3></html>");
		
		
		
		if(showStat) {
			return;
		}
		if(isRunning) {
			
			label.setText("<html> <span style=\"color:#9e8fff\">Elapsed Time:</span>"+ Math.round(elapsedtime/Sleep)+"ms <span style=\"color:#9e8fff\"> | Delay:</span>"+Sleep+"<html>");
		}else {
			label.setText("<html><span style=\"color:#9e8fff\">Elapsed Time:</span>"+ Math.round(elapsedtime/Sleep) +"ms <span style=\"color:#9e8fff\"> | Delay:</span>"+Sleep+"<html>");
			
		}
	}
	static void updateLabel(int x, int y)
	{
		if(!showStat) {
			return;
		}
		if(isRunning) {
			int xy[] = Grid.getIndex(x,y);
			Node n = Grid.getNode(xy[0], xy[1]);
			label.setText("<Html><span style=\"color:#9e8fff\">Delay:</span>:"+Sleep+" <span style=\"color:#9e8fff\"> | Gcost: </span>"+n.Gcost+" | <span style=\"color:#9e8fff\">Hcost: </span>"+n.Hcost+" | <span style=\"color:#9e8fff\">Fcost: </span>"+n.Fcost+"</Html>");
		}else {
			int xy[] = Grid.getIndex(x,y);
			Node n = Grid.getNode(xy[0], xy[1]);
			label.setText("<Html><span style=\"color:#9e8fff\">Delay:</span>"+Sleep+" <span style=\"color:#9e8fff\"> | Gcost: </span>"+n.Gcost+" | <span style=\"color:#9e8fff\">Hcost: </span>"+n.Hcost+" | <span style=\"color:#9e8fff\">Fcost: </span>"+n.Fcost+"</Html>");
		}
			panel2.repaint();
			Grid.repaint();
			panel.repaint();
	}
	public static void speedControl(int x) {
		if(Sleep+x>0) {
		Sleep+=x;
		}	}
}