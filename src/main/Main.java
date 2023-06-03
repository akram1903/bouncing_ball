package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
//		Window w = new Window();
		JFrame w = new JFrame();
//		BouncingBall ball = new BouncingBall();
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		w.setResizable(false);
		w.setTitle("2D game");
		
		GamePanel gamePanel = new GamePanel();
		w.add(gamePanel);
//		w.add(ball);
		w.pack();
		
		w.setLocationRelativeTo(null);
		w.setVisible(true);
		gamePanel.startGameThread();

	}

}
