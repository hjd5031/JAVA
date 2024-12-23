import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
//Game Title Scene(Panel)
public class MyStartScene extends JPanel implements Runnable {
    JPanel mainPanel;//Scene 전환용
    MyArkanoidInGame nextStagePanel;
    FontMetrics metrics;
    int textWidth;
    int centerX;
    int x;
    Thread t;
    boolean running = true;

    MyStartScene(){
        t = new Thread(this);
        t.start();
        SoundManager.play(6);
        requestFocus();
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_SPACE){
                    mainPanel.removeAll();
                    nextStagePanel = new MyArkanoidInGame();
                    mainPanel.add(nextStagePanel);
                    mainPanel.revalidate();
                    mainPanel.repaint();
                    running = false;
                    setFocusable(false);
                    SoundManager.stop(6);
                    SoundManager.play(0);
                    nextStagePanel.mainPanel = mainPanel;
                    nextStagePanel.setFocusable(true);
                    nextStagePanel.requestFocusInWindow();
                }
            }
        });
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        int h = this.getHeight();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setPaint(new GradientPaint(0f,0f,new Color(2,2,2),0f,h, new Color(128,128,165)));
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

        midAlign("Java HomeWork #5", g2d,h/5,60, Color.WHITE);
        midAlign("Block Breaker", g2d,h/2,100, Color.WHITE);
        long currentTime = System.currentTimeMillis();
        boolean showPressKey = (currentTime % 500) < 250;

        if (showPressKey) {
            midAlign("Press Space to Play!", g2d,h/5*4,20,Color.RED);
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
        while(running){
            try{
                Thread.sleep(16);
            }catch(Exception e){}

            repaint();
        }
    }

}
