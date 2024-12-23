import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import javax.swing.JPanel;


//InGame Scene(Panel)
class MyArkanoidInGame extends JPanel implements KeyListener, Runnable {
    JPanel mainPanel;//Scene 전환용
    LinkedList<MyGameObject> objs;     // 화면에 존재하는 모든 오브젝트
    Set<Integer> keysPressed = new HashSet<>();//키입력 보관
    ArrayList<MyHWBall> newBalls = new ArrayList<>();// 새로 추가될 공들을 모아두는 임시 리스트
    MyBar bar;                         // 막대
    MyHWBall mainBall;                 // 초기 공
    int stage = 1;         // 스테이지

    boolean isGameOver = false;
    boolean running = true;
    boolean stageClearSong = false;

    //String 중앙정렬
    FontMetrics metrics;
    int textWidth;
    int centerX;
    int x;

    int score = 0;//현재 점수

    boolean isPaused = false;//Scene 일시정지(p누를시)
    boolean stageCleared = false;
    long clearedTime = 0;

    MyArkanoidInGame() {
        SoundManager.loop(7,true,10);
        initializeStage();
        addKeyListener(this);
        Thread t = new Thread(this);
        t.start();
    }


    void initializeStage() {// 스테이지 초기화
        stageClearSong = false;
        objs = new LinkedList<>();
        int w = 800;
        int h = 800;

        // 테두리 벽
        objs.add(new MyWall(0, 0, w, 30));          // 위
        objs.add(new MyWall(0, 30, 30, h - 30));    // 왼
        objs.add(new MyWall(w - 30, 30, 30, h - 30)); // 오른

        // 막대
        bar = new MyBar();
        objs.add(bar);

        // 공
        mainBall = new MyHWBall();
        mainBall.x = 400;
        mainBall.y = bar.y - bar.h - mainBall.r;//막대 위에서 스폰
        Random random = new Random();
        double angleInDegrees = -30 + random.nextDouble() * 60; // -30도 ~ 30도 범위의 랜덤 각도
        double angleInRadians = Math.toRadians(angleInDegrees); // 라디안으로 변환

        float speed = 400 + (stage - 1) * 20; // 속도 크기
        mainBall.vx = (float) (speed * Math.sin(angleInRadians)); // x축 속도
        mainBall.vy = (float) (-speed * Math.cos(angleInRadians)); // y축 속도 (위 방향으로 발사)
        objs.add(mainBall);

        // 블록 배치
        int blockAreaWidth = 740;   // 블록 놓을 가로 영역
        int blockAreaHeight = 400;  // 블록 놓을 세로 영역

        // 스테이지별 행/열
        int rows, cols;
        rows = stage*3;
        cols = stage*3;

        // 블록 크기
        int blockWidth = blockAreaWidth / cols;
        int blockHeight = blockAreaHeight / rows;

        // 블록 색상 팔레트(무지개)
        Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN,
                Color.BLUE, Color.MAGENTA};
        //블록 생성
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int blockX = 30 + j * blockWidth;  // 왼쪽 여백 30
                int blockY = 50 + i * blockHeight; // 위쪽 여백 50

                // 행에 따라 색상 선택
                Color chosenColor = colors[i % colors.length];

                // 20% 확률로 아이템블록, 아니면 일반블록
                if (Math.random() < 0.2) {
                    MyItemBlock itemBlock = new MyItemBlock(blockX, blockY,
                            blockWidth - 5, blockHeight - 5);
                    itemBlock.color = chosenColor;
                    objs.add(itemBlock);
                } else {
                    MyBlock block = new MyBlock(blockX, blockY,
                            blockWidth - 5, blockHeight - 5);
                    block.color = chosenColor;
                    objs.add(block);
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color topColor, bottomColor;
        switch(stage) {
            case 1:
                topColor = new Color(10, 10, 80);
                bottomColor = new Color(0, 128, 255);
                break;
            case 2:
                topColor = new Color(0, 80, 0);
                bottomColor = new Color(0, 200, 0);
                break;
            default:
                topColor = new Color(128, 0, 0);
                bottomColor = new Color(255, 50, 50);
                break;
        }

        int h = this.getHeight();
        int w = this.getWidth();
        g2d.setPaint(new GradientPaint(0f, 0f, topColor,
                0f, h, bottomColor));
        g2d.fillRect(0, 0, w, h);

        long currentTime = System.currentTimeMillis();
        // 스테이지 클리어 시
        if (stageCleared) {
            if(!stageClearSong) {
                stageClearSong = true;
                SoundManager.play(4);
            }

            boolean showStageClear = (currentTime % 500) < 250;//스테이지 클리어 깜빡임 효과
            if(showStageClear) {
                midAlign("Stage " + stage + " Cleared!!!", g2d, this.getHeight() / 2, 60, Color.GREEN);
            }
        }

        // objs 리스트 순회하며 그리기 & 블록 파괴 처리
        ListIterator<MyGameObject> it = objs.listIterator();
        while (it.hasNext()) {
            MyGameObject o = it.next();
            o.draw(g);

            // 블록이 깨졌으면 제거 + 점수 처리
            if((o instanceof MyBlock || o instanceof MyItemBlock)) {
                MyBlock block = (MyBlock)o;
                if(block.isDestroyed) {
                    // 점수 추가
                    if (block instanceof MyItemBlock) {
                        score += 50;
                    } else {
                        score += 10;
                    }
                    // 아이템 블록이면 새 공 생성 처리
                    if(o instanceof MyItemBlock itemBlock) {
                        if(!itemBlock.itemUsed) {
                            itemBlock.itemUsed = true;//공 중복 담기 방지
                            // 공 2개를 추가 목록에 담아둠
                            for(int i = 0; i < 2; i++){
                                MyHWBall newBall = new MyHWBall();
                                newBall.x = itemBlock.destroyX;//블록에 닿은 공과 동일 위치
                                newBall.y = itemBlock.destroyY;
                                float speed = (float) Math.sqrt(itemBlock.destroyVX*itemBlock.destroyVX + itemBlock.destroyVY*itemBlock.destroyVY);  // 기존 속도 크기 유지
                                double originalAngle = Math.atan2(itemBlock.destroyVY, itemBlock.destroyVX);
                                originalAngle = Math.toRadians(originalAngle);
                                double spreadAngle = Math.toRadians(10 *(i+1));
                                double angle1 = originalAngle + spreadAngle;//분산 효과

                                newBall.vx = (float)( speed * Math.sin(angle1));
                                newBall.vy = (float)( speed * Math.cos(angle1));
                                newBalls.add(newBall);
                            }
                        }
                    }
                    it.remove();
                }
            }
        }
        // 점수 표시 (좌상단)
        g.setColor(Color.BLACK);
        g.setFont(new Font("American TypeWriter", Font.PLAIN, 20));
        g.drawString("Score: " + score, 52, 24);
        g.setColor(Color.WHITE);
        g.setFont(new Font("American TypeWriter", Font.PLAIN, 20));
        g.drawString("Score: " + score, 50, 22);

        g.setColor(Color.BLACK);
        g.setFont(new Font("American TypeWriter", Font.PLAIN, 20));
        g.drawString("Press \"P\" to Pause", 602, 24);
        g.setColor(Color.WHITE);
        g.setFont(new Font("American TypeWriter", Font.PLAIN, 20));
        g.drawString("Press \"P\" to Pause", 600, 22);
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
    // 모든 블록이 깨졌는지 확인
    boolean isStageCleared() {
        return objs.stream()//objs리스트 돌면서 MyBlock을 필터해서 파괴됐는지 확인
                .filter(o -> (o instanceof MyBlock))
                .noneMatch(o -> !((MyBlock)o).isDestroyed);
    }


    void goToNextStage() {//다음 스테이지 넘길때 스테이지 초기화
        newBalls.clear();
        objs.clear();
        stage++;
        initializeStage();
    }

    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        keysPressed.add(e.getKeyCode());

        // 좌우 동시 누르면 bar 정지
        if (keysPressed.contains(KeyEvent.VK_LEFT) && keysPressed.contains(KeyEvent.VK_RIGHT)) {
            bar.vx = 0;
        }
        else if (keysPressed.contains(KeyEvent.VK_LEFT)) {
            bar.vx = -600;
        }
        else if (keysPressed.contains(KeyEvent.VK_RIGHT)) {
            bar.vx = 600;
        }

        //일시정지 기능 구현(P 키)
        if (e.getKeyCode() == KeyEvent.VK_P) {
            SoundManager.play(0);
            isPaused = !isPaused;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keysPressed.remove(e.getKeyCode());

        // 왼/오/둘다 아닌 상태이면 막대 정지
        if (keysPressed.contains(KeyEvent.VK_LEFT)) {
            bar.vx = -600;
        }
        else if (keysPressed.contains(KeyEvent.VK_RIGHT)) {
            bar.vx = 600;
        }
        else {
            bar.vx = 0;
        }

        repaint();
    }

    public void run() {
        while (running) {
            try {
                Thread.sleep(16);
                if (!isGameOver && !isPaused) {
                    // 아직 스테이지 클리어가 안 된 상태라면 게임 진행
                    if(!stageCleared) {
                        for (MyGameObject o : objs) {
                            o.update();//움직이는 모든 오브젝트 update
                        }


                        for (MyGameObject o1 : objs) {//물체간 충돌 확인
                            for (MyGameObject o2 : objs) {
                                o1.collisionResolution(o2);
                            }
                        }


                        objs.addAll(newBalls);//담아놨던 공 화면에 추가(생성하자마자 벽에 충돌방지)
                        newBalls.clear();


                        if (isStageCleared()) {
                            stageCleared = true;
                            clearedTime = System.currentTimeMillis();
                        }

                        boolean allBallsOut = true;
                        for (MyGameObject o : objs) {//공 전부 나갔는지 판단
                            if (o instanceof MyHWBall b) {
                                if (b.y < 800) {
                                    allBallsOut = false;
                                    break;
                                }
                            }
                        }
                        if (allBallsOut) {//GameOver Scene으로 전환
                            isGameOver = true;
                            running = false;
                            if(MyArkanoidFrame.HighScore<score){
                                MyArkanoidFrame.HighScore=score;

                            }
                            mainPanel.removeAll();
                            MyFailScene retryPanel = new MyFailScene(score);
                            mainPanel.add(retryPanel);
                            mainPanel.revalidate();
                            mainPanel.repaint();
                            SoundManager.stop(7);
                            setFocusable(false);
                            retryPanel.mainPanel = mainPanel;
                            retryPanel.setFocusable(true);
                            retryPanel.requestFocusInWindow();
                        }
                    }
                    else {
                        // 스테이지 클리어 된 후 2초 대기 후 다음 스테이지로 넘어감
                        if(System.currentTimeMillis() - clearedTime > 3000) {
                            stageCleared = false;
                            goToNextStage();
                        }
                    }
                }
                repaint();
            }
            catch (Exception e) {}
        }
    }
}