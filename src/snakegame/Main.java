package snakegame;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		GameController gm = new GameController();
		JFrame jf = new JFrame();
		jf.setTitle("Snake 4");
		jf.setBounds(30,30,400,400);
		jf.setResizable(false);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		jf.add(gm);
	}
}
