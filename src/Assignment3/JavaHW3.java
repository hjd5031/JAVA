import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;


class Hw3Display extends JPanel{
    String displayText;
    Hw3Display(Hw3Panel panel){
        displayText = "0";
        setOpaque(false); // 배경을 투명하게 설정
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setFont(new Font("맑은고딕", Font.PLAIN, this.getSize().width/5));//디스플레이에 출력 값 출력
        g2.setColor(new Color(250,250,250));
        g2.drawString(this.displayText, (this.getSize().width/8)*(8- displayText.length())-(displayText.length()), (this.getSize().height/4)*3);
    }
}
class Hw3Button extends JButton {
    int idx;
    Hw3Button(){
        super();
        idx = -1;
        setContentAreaFilled(false);//둥근 버튼 만들기 위함
        setFocusPainted(false);//둥근 버튼 만들기 위함
        setBorderPainted(false);//둥근 버튼 만들기 위함
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //---------------------------------------------------------------
        if (getModel().isArmed()) {//버튼을 사각형에서 원으로 바꿈
            g2.setColor(Color.LIGHT_GRAY); // 클릭 시 색상
        } else {
            if (idx >= 10 && idx < 14) {
                g2.setColor(new Color(245,163,10));
            }
            else{
                g2.setColor(getBackground());
            }
        }
        g2.fill(new Ellipse2D.Double(0, 0, getWidth()-3, getHeight()-5));
        //---------------------------------------------------------------
        g.setFont(new Font("맑은고딕",Font.BOLD, this.getSize().width/2));
        g.setColor(new Color(0, 0, 0));//배경 텍스트 색

        switch(idx) {//버튼의 인덱스에 따라 버튼 텍스트 위치 보정
            case 10:{
                g.drawString("C", this.getSize().width/7*2,(this.getSize().height/3)*2);
                g.setColor(new Color(255, 255, 255));
                g.drawString("C", this.getSize().width/7*2-1,(this.getSize().height/3)*2+1);
            }
            break;

            case 11: {
                g.drawString("+", (this.getSize().width / 7) * 2, (this.getSize().height / 11) * 7);
                g.setColor(new Color(255, 255, 255));
                g.drawString("+", (this.getSize().width / 7) * 2-1, (this.getSize().height / 11) * 7+1);
            }
            break;

            case 12: {
                g.drawString("-", this.getSize().width / 3, (this.getSize().height / 11) * 7);
                g.setColor(new Color(255, 255, 255));
                g.drawString("-", this.getSize().width / 3-1, (this.getSize().height / 11) * 7+1);
            }
            break;

            case 13: {
                g.drawString("=", this.getSize().width / 7 * 2, (this.getSize().height / 11) * 7);
                g.setColor(new Color(255, 255, 255));
                g.drawString("=", this.getSize().width / 7 * 2-1, (this.getSize().height / 11) * 7+1);
            }
            break;

            default:{
                if(idx == -1)break;
                g.drawString(Integer.valueOf(idx).toString(), this.getSize().width/3,(this.getSize().height/11)*7);
                g.setColor(new Color(255, 255, 255));
                g.drawString(Integer.valueOf(idx).toString(), this.getSize().width/3-1,(this.getSize().height/11)*7+1);
            }
            break;
        }
    }
}

class Hw3Panel extends JPanel implements ActionListener {
    Hw3Button[] buttons = new Hw3Button[16]; // 버튼 배열
    Hw3Display display;
    int centerX;
    int centerY;
    int panelHeight;
    int panelWidth;
    int buttonHeight;
    int buttonWidth;
    int sum;
    int curInput;
    int flag;
    int curSign;

    Hw3Panel() {
        add(display = new Hw3Display(this));
        sum = 0;
        curInput = 0;
        flag = 0;
        curSign = 1;
        getPanelSize();//패널크기 초기화
        setLayout(null);
        makeButton();
    }
    void makeButton(){//16가지 버튼 생성
        int buttonIndex = 0;
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                buttons[buttonIndex] = new Hw3Button();
                buttons[buttonIndex].addActionListener(this);
                int value = i + 1 + (3*(2 - j));//1-9까지 숫자 할당
                if (i < 3 && j < 3) {
                    buttons[buttonIndex].idx = value;
                }
                else {
                    if (j == 0) {
                        buttons[buttonIndex].idx = 10;
                    }
                    if (j == 1) {
                        buttons[buttonIndex].idx = 11;
                    }
                    if (j == 2) {
                        buttons[buttonIndex].idx = 12;
                    }
                }
                if (i == 0 && j == 3) {
                    buttons[buttonIndex].idx = 0;
                }
                if (i == 3 && j == 3) {
                    buttons[buttonIndex].idx = 13;
                }
                buttons[buttonIndex].setBackground(new Color(81,82,84));
                add(buttons[buttonIndex]);
                buttonIndex++;
            }
        }
    }
    void getPanelSize() {//버튼의 재구성 위해 현재 패널의 사이즈 가져오기
        buttonWidth = panelWidth / 4;//가로에 버튼 4개
        buttonHeight = panelHeight / 6;//화면 2 : 키패드 4

        centerX = this.getSize().width / 2;
        centerY = this.getSize().height / 2;

        panelHeight = this.getSize().height;
        panelWidth = (panelHeight / 5) * 3;

        if (panelWidth > this.getSize().width) {
            panelWidth = this.getSize().width;
            panelHeight = (int)((float)panelWidth) / 3 *5;
        }
    }


    void resizePanel() {//현재 패널 크기에 따라 버튼 위치, 디스플레이의 위치 결정
        display.setBounds(centerX - panelWidth / 2 + buttonWidth/4, centerY - panelHeight / 2 + buttonWidth/4,
                (buttonWidth * 4)-buttonWidth/2, (buttonHeight * 2)-buttonWidth/4);//디스플레이
        int buttonIndex = 0;
        for (int i = 0; i < 4; i++) {//버튼
            for (int j = 0; j < 4; j++) {
                    int x = (centerX - panelWidth / 2) + (buttonWidth * i);
                    int y = (centerY - panelHeight / 2) + (buttonHeight * (j + 2));
                    buttons[buttonIndex].setBounds(x, y, buttonWidth, buttonHeight);
                    buttonIndex++;
            }
        }
        repaint();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        getPanelSize();
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setPaint(new GradientPaint(0f, 0f, new Color(45, 45, 45), 0f, this.getSize().height, new Color(70, 70, 70)));//둥근 회색 그라디언트 배경
        g2.fillRoundRect((centerX - panelWidth/2), (centerY - panelHeight / 2), panelWidth, panelHeight,40,40);

        if (flag == 0) {//누적 계산값 출력
            display.displayText = Integer.valueOf(sum).toString();
        }
        else if (flag == 1) {//현재 입력값 출력
            display.displayText = Integer.valueOf(curInput).toString();
        }
        resizePanel();//현재 패널의 크기에 따라 사이즈 재조정
    }
    @Override
    public void actionPerformed(ActionEvent e) {//클릭하는 버튼에 따라 이벤트 처리
        int value;
        if ((value = ((Hw3Button)e.getSource()).idx) >= 0&&e.getSource() instanceof Hw3Button ) {//버튼이고 어떤 기호/숫자일때
            switch(value){
                case 10:{
                    flag = 0;
                    curInput = 0;
                    curSign = 1;
                    sum = 0;
                }
                break;
                case 11,12,13:{
                    flag = 0;
                    sum += curInput * curSign;
                    curInput = 0;
                    curSign = 1;
                    if (value == 12) {
                        curSign = -1;
                    }
                }
                break;
                default:{
                    flag = 1;
                    curInput *= 10;
                    curInput += value;
                }
                break;
            }
        }
        repaint();
    }
}

public class JavaHW3 extends JFrame{
      JavaHW3() {
         setSize(300, 500);
         setTitle("Java Calculator by HJD");
         add(new Hw3Panel());
         setDefaultCloseOperation(EXIT_ON_CLOSE);
         setVisible(true);
      }
   public static void main(String[] args) {
      new JavaHW3();
   }
}
