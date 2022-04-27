import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Controls implements MouseListener, MouseMotionListener, KeyListener
{
	@Override
	public void mouseClicked(MouseEvent e)
	{
		if(e.getButton() == 1)
		{
			PathFinding.Grid.addStart(e.getX(), e.getY());
		}
		else 
		{
			PathFinding.Grid.addEnd(e.getX(), e.getY());
		}		

		
	}
	@Override
	public void mouseEntered(MouseEvent e)
	{
		
	}
	@Override
	public void mouseExited(MouseEvent e)
	{
		
	}
	@Override
	public void mousePressed(MouseEvent e)
	{
		
	}
	@Override
	public void mouseReleased(MouseEvent e)
	{
		
	}
	@Override
	public void mouseDragged(MouseEvent e)
	{		
		if(e.getButton() == 0)
		{
			PathFinding.Grid.addToWalls(new Node(e.getX(), e.getY()), true);
		}
		else if(e.getButton() == 1)
		{
		}
	}
	@Override
	public void mouseMoved(MouseEvent e)
	{
				
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyChar() == 'r')
		{
			PathFinding.reset();
		}
		if(e.getKeyChar() == 'w')
		{
			PathFinding.Grid.GenerateMaze();
		}
	}
	@Override
	public void keyReleased(KeyEvent e) 
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent e)
	{
		// TODO Auto-generated method stub
	}

}
