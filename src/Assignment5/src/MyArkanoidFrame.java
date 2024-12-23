import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MyArkanoidFrame extends JFrame{//MainFrame제작
	static int HighScore = 0;//최고 점수 시스템을 위해서 전역선언
	JPanel mainPanel;
	MyStartScene startScene;
	MyArkanoidFrame(){
		setSize(800,800);
		setResizable(false);
		setTitle("MyArkanoid");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());//Panel을 교체하는 방식 사용
		startScene = new MyStartScene();
		startScene.mainPanel = this.mainPanel;
		mainPanel.add(startScene);
		add(mainPanel);
		setVisible(true);
	}

	public static void main(String[] args) {
		new MyArkanoidFrame();
	}
}
