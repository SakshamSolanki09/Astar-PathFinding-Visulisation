import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Controls implements MouseListener, MouseMotionListener, KeyListener, MouseWheelListener
{
	int currentMouseButton = -1;
	boolean d ;
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
		
		currentMouseButton = e.getButton();
	}
	@Override
	public void mouseReleased(MouseEvent e)
	{

		currentMouseButton = -1;
	}
	@Override
	public void mouseDragged(MouseEvent e)
	{		
		if(currentMouseButton == 1)
		{
			PathFinding.Grid.addToWalls(new Node(e.getX(), e.getY()), true);
		}
		else if(currentMouseButton == 3)
		{
			PathFinding.Grid.removeWalls(new Node(e.getX(), e.getY()), true);
		}
	}
	@Override
	public void mouseMoved(MouseEvent e)
	{
		if(d) {
			PathFinding.updateLabel(e.getX(),e.getY());
		}
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
		if(e.getKeyChar() == 'd') {
			PathFinding.showStat = true;
			d = true;
		}
		if(e.getKeyChar() == '.') {
			PathFinding.speedControl(6);
		}
		if(e.getKeyChar() == ',') {
			PathFinding.speedControl(-6);
		}
	}
	@Override
	public void keyReleased(KeyEvent e) 
	{

		if(e.getKeyChar() == 'd') {
			d = false;
			PathFinding.showStat = false;
		}
		
	}
	@Override
	public void keyTyped(KeyEvent e)
	{
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(e.getWheelRotation()>0) {

			PathFinding.addToGrid(50);
		}

		if(e.getWheelRotation()<0) {
			PathFinding.addToGrid(-50);
		}
		
	}

}
