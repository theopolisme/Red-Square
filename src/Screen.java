import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;

import javax.swing.JPanel;


public class Screen extends JPanel {
	
	Square square;
	
	float height;
	boolean high;
	
	ArrayList<Point> lines;
	int numOfLines;
	
	ArrayList<Rectangle> rects;
	int numOfRects;
	
	public Screen(Square s) {
		square = s;
		
		height = 0;
		high = false;
		
		numOfLines = 30;
		lines = new ArrayList<Point>();
		for(int i = 0; i < numOfLines; i++) {
			lines.add(new Point( (int)(Math.random()*(480/numOfLines)) + i * 480 / numOfLines, (int)(Math.random()*(480/numOfLines)) + i * 480 / numOfLines));
		}
		
		numOfRects = 5;
		rects = new ArrayList<Rectangle>();
		for(int i = 0; i < numOfRects; i++) {
			rects.add( new Rectangle( (int) (Math.random()*640/3) + i * 640/3, (int) (Math.random()*480/3) + i * 480/3, 80, 20 ) );
		}
	}
	
	@Override
	public void paintComponent(Graphics surface) {
		super.paintComponent(surface);
		
		Graphics2D g = (Graphics2D) surface;
		
		square.update(g, rects);
		
		Point p = square.getCoords();
		
		high = p.y < 480f/3f;
		if(high) {
			height += 0.1;
		}
		g.drawString(Float.toString(height), 640/2, 480/2);
		
		g.setColor(Color.RED);
		if(p.y > 480) {
			g.drawString("You Lose. Press Space to Restart.", 640/2, 480/2 + 20);
		}
		
		g.setColor(Color.RED);
		for(Rectangle rect : rects) {
			g.fillRect(rect.x, rect.y, rect.width, rect.height);
			
			if(high) {
				//rect.x++;
				rect.y++;
			}
		}
		
		if(rects.get(numOfRects-1).y > 480) {
			rects.remove(numOfRects-1);
			rects.add(0, new Rectangle( (int) (Math.random()*640), -20, 80, 20) );
		}
		
		g.setColor(Color.BLACK);
		for(Point pair : lines) {
			g.drawLine(0, pair.x, 640, pair.y);
			
			if(high) {
				pair.x++;
				pair.y++;
			}
			
		}
		
		Point pair = lines.get(numOfLines-1);
		if(pair.x > 480 && pair.y > 480) {
			lines.remove(numOfLines-1);
			lines.add(0, (new Point( -(int)(Math.random()*(480/numOfLines)), -(int)(Math.random()*(480/numOfLines)) )));
		}
		
		//star(g);
	}
	
	public void reset() {
		height = 0;
		square.reset();
	}
	
	/**
	 * 
	 * @purpose None, really, but it's cool.
	 */
	public void star(Graphics2D g) {
		int xPoints[] = { 55, 67, 109, 73, 83, 55, 27, 37, 1, 43 };
		int yPoints[] = { 0, 36, 36, 54, 96, 72, 96, 54, 36, 36 };

		GeneralPath star = new GeneralPath();
		star.moveTo( xPoints[ 0 ], yPoints[ 0 ] );
		
		for ( int k = 1; k < xPoints.length; k++ ) star.lineTo( xPoints[ k ], yPoints[ k ] ); star.closePath(); 
		g.translate( 200, 200 ); 
		g.rotate( Math.PI / 20.0 ); 
		g.setColor( new Color( ( int ) ( Math.random() * 256 ), 
			( int ) ( Math.random() * 256 ), 
			( int ) ( Math.random() * 256 ) ) ); 
		g.fill( star );
	}
}