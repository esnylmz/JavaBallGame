package HW1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class HW01 extends JPanel implements ActionListener, MouseListener {

	private JLabel lblLife;
	private JMenuBar menubar;
	private JMenu menuGame, menuDebug;
	private JMenuItem menuItemStart, menuItemPause, menuItemExit, menuItemDebug1, menuItemDebug2;
	private final int SIZE = 4;
	private BufferedImage droplet;
	private BufferedImage ball;
	private Random rand;
	private int x2, y2;
	private int a = 30;
	private int k = 30;
	private int score;

	private boolean moveToRigt = true;
	private boolean moveToUp = true;
	private boolean movedrplt = true;
	private Droplet d1;
	private Ball b;
	private Point imgPoint, imgPoint2;

	Timer timer;

	public HW01() {// creating the constructor

		setLayout(new BorderLayout());

		setFocusable(true);
		requestFocus();

		timer = new Timer(9, this);

		// creating the menu application
		menubar = new JMenuBar();

		menuGame = new JMenu("Game");
		menuDebug = new JMenu("Debug");

		menuItemStart = new JMenuItem("Start");
		menuItemPause = new JMenuItem("Pause");
		menuItemExit = new JMenuItem("Exit");
		menuItemDebug1 = new JMenuItem("Change ball speed");
		menuItemDebug2 = new JMenuItem("Change droplet speed");

		menuItemStart.addActionListener(this);
		menuItemPause.addActionListener(this);
		menuItemExit.addActionListener(this);
		menuItemDebug1.addActionListener(this);
		menuItemDebug2.addActionListener(this);

		menuGame.add(menuItemStart);
		menuGame.add(menuItemPause);
		menuGame.add(menuItemExit);

		menuDebug.add(menuItemDebug1);
		menuDebug.add(menuItemDebug2);
		//creating the label for showing the life 
		lblLife = new JLabel("Life: " + a);
		lblLife.setBounds(100, 0, 100, 100);

		menubar.setLayout(new GridLayout(1, 3));

		menubar.add(menuGame);
		menubar.add(menuDebug);
		menubar.add(lblLife);

		add(menubar, BorderLayout.NORTH);// adding the menupanel to the frame

		try {//creating the buffered image ball
			ball = ImageIO.read(new FileImageInputStream(new File("ball2.png")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {//creating the buffered image droplet
			droplet = ImageIO.read(new FileImageInputStream(new File("droplet.png")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //instantiating the Droplet object, d1
		d1 = new Droplet(150, 0);
		//instantiating the Ball object,b
		b = new Ball(50, 300);
		addMouseListener(this);
		//creating points to use in the rectangle for mouseEvent
		imgPoint = new Point(d1.getX(), d1.getY() + 25);

		imgPoint2 = new Point(b.getX(), b.getY());
	}

	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);

		//drawing the ball, getting the x and y's from the Ball class
		g.drawImage(ball, b.getX(), b.getY(), 60, 60, this);
		//drawing the droplet, getting the x and y's from the Droplet class
		g.drawImage(droplet, d1.getX(), d1.getY(), this);

	}
//*******************************MY IF-ELSE CONDITIONS TO MOVE THE  Ball b and Droplet **************************
	private void controlAnimation() {

		rand = new Random();
		// move to righ side
		if (b.getX() < 580 && moveToRigt) {
			int x = b.getX();
			x += 5;
			b.setX(x);
			// System.out.println(x);
		} else if (b.getX() > 5) {
			// move to left side
			moveToRigt = false;
			int k = b.getX();
			k -= 4;
			b.setX(k);
		} else {
			// set the boolean value to true...
			moveToRigt = true;
		}

		// move to rigt side
		if (b.getY() < 550 && moveToUp) {
			int y = b.getY();
			y += 1;
			b.setY(y);
			if (a <= 0) {
				timer.stop();
			}
			// System.out.println(x);
		} else if (b.getY() > 35) {
			// move to left side
			moveToUp = false;
			int m = b.getY();
			m -= 1;
			b.setY(m);
			if (a <= 0) {
				timer.stop();
				lblLife.setText("Life: 0");
			}
		} else {
			// set the boolean value to true...
			moveToUp = true;
		}

		if (d1.getY() < 551 && movedrplt) {

			int c = d1.getY();
			c += 3;
			d1.setY(c);

		} else if (d1.getY() > 5) {
			movedrplt = false;
			d1.setY(0);
			x2 = rand.nextInt(650);
			d1.setX(x2);

		}

		else {
			movedrplt = true;
		}

		if (d1.getY() >= 550) {
			String txt = lblLife.getText();
			a -= 3;
			txt = "Life: " + a;
			lblLife.setText(txt);
		}

	}
	//MY MAIN METHOD
	public static void main(String[] args) {

		HW01 f = new HW01();
		JFrame frame = new JFrame();

		frame.add(f);
		frame.setSize(650, 650);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	//my method for collision
	public boolean collision() {
		Rectangle rct1= new Rectangle(b.getX(),b.getY(),15,15);
		Rectangle rct2= new Rectangle(d1.getX(),d1.getY(),8,8);
		
		
		if(rct1.intersects(rct2)) {
			System.out.println("they intersect");
			return true;
		}
		else {
			return false;
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		score += 50;
		String s = "YOUR SCORE IS: " + score;
       //checking when the life is ended end showing the Option and Message Dialog
		if (a <= 0) {//a is the value on my life label
			//rewriting the life as 0
			a = 0;
			String t3 = "Life: " + a;
			lblLife.setText(t3);
			JOptionPane.showMessageDialog(null, s, "YOU DIED!!", JOptionPane.INFORMATION_MESSAGE);
			int i = JOptionPane.showConfirmDialog(null, "Do you want to restart?", "THE END",
					JOptionPane.OK_CANCEL_OPTION);
			
			
			if (i == 0) {//if the confirmation is OK in the dialog pane
				//to restart the life values
				a = 30;
				score = 0;
				String t = "Life: " + a;
				lblLife.setText(t);

				timer.restart();//restarting

			}
			if (i == 1) {//if the confirmation is Cancel in the dialog pane
				JOptionPane.showMessageDialog(null, s, "YOU DIED!!", JOptionPane.INFORMATION_MESSAGE);
				a = 0;
				String t2 = "Life: " + a;
				lblLife.setText(t2);
			}
		}
		
		

		if (e.getSource().equals(timer)) {
		
			
			controlAnimation();
			if(collision()) {//checking if the collision happens 
				//if it is true, decrease the life
				String txt = lblLife.getText();
				a -= 2;
				txt = "Life: " + a;
				lblLife.setText(txt);
				
			}

		} else if (e.getSource().equals(menuItemStart)) {
			timer.start();
		} else if (e.getSource().equals(menuItemPause)) {
			timer.stop();
		} else if (e.getSource().equals(menuItemExit)) {
			System.exit(1);
		}

		repaint();
	}
	
	

	@Override
	public void mouseClicked(MouseEvent e) {

		Random r = new Random();
		int random = r.nextInt();

		Point me = e.getPoint();
		//creating rectangles with droplet d1's x and y
		Rectangle bounds = new Rectangle(imgPoint, new Dimension(d1.getX() * 2, d1.getY() * 2));
		//creating rectangles with ball b's x and y
		Rectangle bounds2 = new Rectangle(imgPoint2, new Dimension(b.getX() * 2, b.getY() + 100));

		if (bounds.contains(me)) {//if we click on the droplet, this increases life by one
			d1.setX(random);
			String txt = lblLife.getText();
			a += 1;
			txt = "Life: " + a;
			lblLife.setText(txt);

		} else if (bounds2.contains(me)) {//if we click on the ball, this increases life by three

			String text = lblLife.getText();
			a += 3;
			text = "Life: " + a;
			lblLife.setText(text);
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}