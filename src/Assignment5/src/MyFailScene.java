import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
//GameOver Scene(Panel)
public class MyFailScene extends JPanel implements Runnable{
    JPanel mainPanel;
    int textWidth;
    int centerX;
    int x;
    FontMetrics metrics;
    int score;
    boolean running = true;
    MyFailScene(int _score){
        score = _score;
        Thread t = new Thread(this);
        t.start();
        SoundManager.play(5);
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_SPACE){
                    mainPanel.removeAll();
                    MyStartScene startAgain = new MyStartScene();
                    mainPanel.add(startAgain);
                    mainPanel.revalidate();
                    mainPanel.repaint();
                    running = false;
                    setFocusable(false);
                    startAgain.mainPanel = mainPanel;
                    startAgain.setFocusable(true);
                    startAgain.requestFocusInWindow();
                    SoundManager.stop(5);
                    SoundManager.play(0);
                }
            }
        });
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        long currentTime = System.currentTimeMillis();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setPaint(new GradientPaint(0f,0f,new Color(2,2,2),0f,this.getHeight(), new Color(128,128,165)));
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        midAlign("GAME OVER!!!",g2d,this.getHeight()/5*2,100,Color.RED);

//            // 점수 표시
        midAlign("High Score: "+ MyArkanoidFrame.HighScore,g2d,this.getHeight()/5*3,60,Color.RED);
        midAlign("Your Score: "+score,g2d,this.getHeight()/5*3+50,60,Color.RED);


        boolean showPressKey = (currentTime % 500) < 250;

        if (showPressKey) {
            midAlign("Press Spacebar to Restart!!!", g2d, this.getHeight() /5*4, 20, Color.RED);
        }
    }
    void midAlign(String str, Graphics2D g2d,int h, int size, Color color){
        g2d.setFont(new Font("American TypeWriter",Font.BOLD,size));
        metrics = g2d.getFontMetrics(g2d.getFont());
        textWidth = metrics.stringWidth(str); // 텍스트의 폭
        centerX = this.getWidth() / 2;
        x = centerX - (textWidth / 2);
        g2d.setColor(Color.BLACK);
        g2d.drawString(str,x,h+3);
        g2d.setColor(color);
        g2d.drawString(str,x,h);
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {}
            repaint();
        }
    }
}
