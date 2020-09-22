package snakegame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GameController extends JPanel implements KeyListener, ActionListener{
	List<Point> snake;
	private int startX,startY,dirX,dirY,score,flag = 0;
	
	//food variables
	private int foodX,foodY;
	private boolean inclen = false;
	
	private boolean play = false;
	private boolean gameOver = false;
	
	Timer time;
	int delay = 90;
	
	public GameController() {
		snake = new ArrayList<Point>();
		startX = 150;
		startY = 150;
		dirX = -1;
		dirY = -1;
		score = 0;
		foodX = (int)(Math.random()*340) + 10;
		foodY = (int)(Math.random()*340) + 10;
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		time = new Timer(delay,this);
		time.start();
		snake.add(new Point(startX,startY));
		for(int i = 0; i < 10; i++) {
			snake.add(new Point(startX + i * 4, startY));
		}
	}
	
	public void paint(Graphics g) {
		//background
		g.setColor(Color.black);
		g.fillRect(1, 1, 400, 400);
		
		//borders
		g.setColor(Color.red);
		g.fillRect(0, 0, 5, 400);
		g.fillRect(0, 0, 400, 5);
		g.fillRect(382, 0, 5, 400);
		g.fillRect(0,358,400,5);
				
		if(gameOver) {
			g.setColor(Color.red);
			g.setFont(new Font("Arial", Font.BOLD, 30));
			g.drawString("Game Over", 115, 200);
			g.setFont(new Font("Arial", Font.BOLD, 20));
			g.drawString("Score :"+score, 150, 230);
			g.setColor(Color.green);
			g.setFont(new Font("Arial", Font.BOLD, 10));
			g.drawString("Press Enter To Restart.", 135, 250);
		}
		//snake
		g.setColor(Color.white);
		for(Point p : snake) {
			g.fillRect(p.getX(), p.getY(), 4, 4);
		}
		this.foodCreate(g);
	}
	
	public void autoMove() {
		if(play) {
			Point temp = snake.get(0);
			Point last = snake.get(snake.size()-1);
			Point newStart = new Point(temp.getX() + dirX * 4,temp.getY() + dirY *4); 
			for(int i = snake.size() - 1; i >= 1; i--) {
				snake.set(i, snake.get(i-1));
			}
			snake.set(0, newStart);
			if(inclen) {
				snake.add(last);
				inclen = false;
			}
		}
	}
	public void gameOver() {
		//int flag = 0;
		if((snake.get(0)).getX() < 10 || (snake.get(0)).getX() > 377 || (snake.get(0)).getY() < 5 || (snake.get(0)).getY() > 353) {
			play = false;
			gameOver = true;
			return;
		}
		int x = snake.get(0).getX();
		int y = snake.get(0).getY();
		for(int i = 1;i < snake.size(); i++) {
			if((snake.get(i)).getX() == x && (snake.get(i)).getY() == y){
				if(flag != 1) {
					flag = 1;
					play = true;
					return ;
				}
				play = false;
				gameOver = true;
				return ;
			}
		}
		play = true;
		return;
	}
	public int getXDir() {
		return dirX;
	}	
	public int getYDir() {
		return dirY;
	}
	private void randomPos() {
		foodX = (int)(Math.random()*340 + 10);
		foodY = (int)(Math.random()*340 + 10);
	}
	public void foodCreate(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(foodX, foodY, 6, 6);
	}
	private boolean foodDetect() {
		int x = snake.get(0).getX() + 2;
		int y = snake.get(0).getY() + 2;
		if(x >= foodX-1 && x <= foodX+7) {
			if(y >= foodY-1 && y <= foodY+7) {
				this.randomPos();
				score = score + 10;
				inclen = true;
				return true;
			}
		}
		return false;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(play) {
			this.autoMove();
			this.foodDetect();
			this.gameOver();
			this.repaint();
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}
	@Override
	public void keyPressed(KeyEvent e) {
		//play = true;
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			play = true;
			if(dirY != 1) {
				dirY = -1;
				dirX = 0;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			play = true;
			if(dirX != 1) {
				dirY = 0;
				dirX = -1;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			//play = true;
			if(dirX != -1) {
				dirY = 0;
				dirX = 1;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			//play = true;
			if(dirY != -1) {
				dirY = 1;
				dirX = 0;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!play) {
				play = true;
				snake = new ArrayList<Point>();
				startX = 150;
				startY = 150;
				dirY = 0;
				dirX = -1;
				score = 0;
				foodX = (int)(Math.random()*340) + 10;
				foodY = (int)(Math.random()*340) + 10;
				gameOver = false;				
				snake.add(new Point(startX,startY));
				for(int i = 0; i < 10; i++) {
					snake.add(new Point(startX + i * 4, startY));
				}
				repaint();
			}
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
