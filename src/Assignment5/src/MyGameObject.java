import java.awt.*;

abstract class MyGameObject {
    float x,y;
    Color color;
    MyGameObject() { }

    abstract void draw(Graphics g);
    void update() {};
    void collisionResolution(MyGameObject o) {}
    void checkCollision(MyHWBall ball, float left, float right, float top, float bottom) {
        if (ball.prev_y < top) {
            ball.y = top - ball.r;
            ball.vy = -ball.vy;
        }
        else if (ball.prev_y > bottom) {
            ball.y = bottom + ball.r;
            ball.vy = -ball.vy;
        }
        if (ball.prev_x < left) {
            ball.x = left - ball.r;
            ball.vx = -ball.vx;
        }
        else if (ball.prev_x > right) {
            ball.x = right + ball.r;
            ball.vx = -ball.vx;
        }
    }
}

class MyBar extends MyGameObject {
    int x,y;
    int w = 180;
    int h = 30;
    int vx;  // 속도

    MyBar(){
        color = Color.black;
        x = 400;   // 초기 위치
        y = 700;
    }

    @Override
    void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        GradientPaint gp = new GradientPaint(x - (float) w /2, y - (float) h /2, Color.DARK_GRAY, x + (float) w /2, y + (float) h /2, Color.ORANGE);// 테두리 그라디언트 효과
        g2d.setPaint(gp);
        g2d.fillRoundRect(x - w/2, y - h/2, w, h, 15, 15);// 모서리를 둥글게
        // 바깥 테두리
        g2d.setColor(Color.ORANGE);
        g2d.fillRoundRect(x - w/2+5, y - h/2+5, w-10, h-10, 15, 15);
    }

    @Override
    void update() {
        x += vx * (float) 0.016;
        // 막대 화면 못나가게 설정
        if (x - w/2 < 30) {
            x = 30 + w/2;
        }
        if (x + w/2 > 770) {
            x = 770 - w/2;
        }
    }
}

class MyWall extends MyGameObject {//상,좌,우 벽
    float w, h;
    MyWall(int _x, int _y, int _w, int _h){
        x = _x;
        y = _y;
        w = _w;
        h = _h;
        color = Color.gray;
    }

    @Override
    void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setPaint(new GradientPaint(x,y,new Color(80,80,80),x,y+h, new Color(200,200,200)));
        g2d.fillRect((int)x, (int)y, (int)w, (int)h);
        g.setColor(color);
        g.fillRect((int)x+4, (int)y+4, (int)w-8, (int)h-8);
    }
}

class MyHWBall extends MyGameObject {
    float vx, vy;
    float prev_x, prev_y;
    float r;     // 반지름

    MyHWBall(){//공 초기값
        r = 6;  // 공 크기
        x = 400;
        y = 500;
        prev_x = x;
        prev_y = y;
        color = Color.WHITE;
    }

    @Override
    void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);
        g2d.fillOval((int)(x-r), (int)(y-r), (int)(2*r), (int)(2*r));
        g2d.setColor(Color.BLACK);
        g2d.drawOval((int)(x-r), (int)(y-r), (int)(2*r), (int)(2*r));
    }

    @Override
    void update() {
        prev_x = x;
        prev_y = y;
        x += vx * (float) 0.016;
        y += vy * (float) 0.016;
    }

    @Override
    void collisionResolution(MyGameObject o) {
        // 벽과 충돌
        if(o instanceof MyWall wall) {
            float left = wall.x - r;
            float right = wall.x + wall.w + r;
            float top = wall.y - r;
            float bottom = wall.y + wall.h + r;
            if (x > left && x < right && y > top && y < bottom) {// 현재 공의 위치가 벽 범위 안이라면 충돌 처리
                if (prev_y < top) {// 위쪽
                    y = top - r;
                    vy = -vy;
                }
                else if (prev_y > bottom) {// 아래쪽
                    y = bottom + r;
                    vy = -vy;
                }
                if (prev_x < left) {// 왼쪽
                    x = left - r;
                    vx = -vx;
                }
                else if (prev_x > right) {// 오른쪽
                    x = right + r;
                    vx = -vx;
                }
            }
        }

        if(o instanceof MyBar bar) {//하단 라켓과 충돌
            float left = bar.x - (float)bar.w/2 - r-5;
            float right = bar.x + (float)bar.w/2 + r+5;
            float top = bar.y - (float)bar.h/2 - r;
            float bottom = bar.y + (float)bar.h/2 + r;
            if (x > left && x < right && y > top&&y<bottom) {// 공이 막대의 범위에 들어온 경우

                if (prev_y < top) {
                    SoundManager.play(1);

                    float hitPosition = (x - bar.x) / ((float) bar.w / 2);//막대 중앙을 기준으로 좌우 -1.0~1.0
                    float speed = (float) Math.sqrt(vx*vx + vy*vy);  // 기존 속도 크기 유지

                    double newAngle = hitPosition * (Math.PI / 3); // -60도 ~ 60도, 공이 맞은 위치에 따라 각도 다르게
                    vx = (float)( speed * Math.sin(newAngle));
                    vy = -(float)( speed * Math.cos(newAngle));
                    y = top - r;
                }

                else if (prev_y > bottom) {// 아래쪽
                    y = bottom + r;
                    vy = -vy;
                }
                if (prev_x < left) {// 왼쪽
                    x = left - r;
                    vx = -vx;
                }
                else if (prev_x > right) {// 오른쪽
                    x = right + r;
                    vx = -vx;
                }

            }
        }
    }
}


class MyBlock extends MyGameObject {//일반 블록
    float w, h;
    boolean isDestroyed;//파괴 여부 확인

    MyBlock(int _x, int _y, int _w, int _h) {
        x = _x;
        y = _y;
        w = _w;
        h = _h;
        color = Color.BLUE;
        isDestroyed = false;
    }

    @Override
    void draw(Graphics g) {
        if (!isDestroyed) {
            Graphics2D g2d = (Graphics2D) g;
            int startRed = color.getRed()-120;if(startRed < 0) startRed = 0;
            int startGreen = color.getGreen()-120;if(startGreen < 0) startGreen = 0;
            int startBlue = color.getBlue()-120;if(startBlue < 0) startBlue = 0;

            int endRed = color.getRed()+80;if(endRed > 255) endRed = 255;
            int endGreen = color.getGreen()+80;if(endGreen > 255) endGreen = 255;
            int endBlue = color.getBlue()+80;if(endBlue > 255) endBlue = 255;

            Color startColor = new Color(startRed, startGreen, startBlue, 255);
            Color endColor = new Color(endRed, endGreen, endBlue, 255);

            g2d.setPaint(new GradientPaint(x,y,startColor,x,y+h, endColor));//배경을 그라디언트로 설정
            g2d.fillRect((int) x, (int) y, (int) w, (int) h);
            g2d.setColor(color);//위는 그냥 색 입체감을 줌
            g2d.fillRect((int)(x+h/10), (int)(y+h/10), (int)(w-h/5), (int)(h-h/5));
        }
    }

    @Override
    void collisionResolution(MyGameObject o) {
        if (isDestroyed) return;  // 이미 깨진 블록은 무시

        if (o instanceof MyHWBall ball) {
            float left = x - ball.r;
            float right = x + w + ball.r;
            float top = y - ball.r;
            float bottom = y + h + ball.r;

            // 블록 범위 안에 공이 들어왔을 때
            if (ball.x > left && ball.x < right && ball.y > top && ball.y < bottom) {
                SoundManager.play(2);
                isDestroyed = true;
                checkCollision(ball, left, right, top, bottom);// 어느 방향에서 들어왔는지 판단
            }
        }
    }


}

class MyItemBlock extends MyBlock {
    float destroyX;
    float destroyY;
    boolean itemUsed = false; // 아이템(새 공 3개) 생성 여부 체크
    float destroyVX;
    float destroyVY;

    long flickerPhase;
    MyItemBlock(int _x, int _y, int _w, int _h) {
        super(_x, _y, _w, _h);
        this.flickerPhase = (long)(Math.random() * 10000);
    }

    @Override
    void draw(Graphics g) {
        if (!isDestroyed) {
            super.draw(g);//부모 불러서 그라디언트 효과 적용
            long currentTime = System.currentTimeMillis();
            double period = 1000.0;
            double t = ((currentTime + flickerPhase) % (long)period) / period;
            double wave = Math.sin(2 * Math.PI * t);//경과 시간에 따라서 아이템 블록 깜빡임 효과
            double alpha = (wave + 1.0) / 2.0;  // 0~1
            Color silver = new Color(150, 150, 150);

            int flickerRed   = (int) (color.getRed()   + alpha * (silver.getRed()   - color.getRed()));
            int flickerGreen = (int) (color.getGreen() + alpha * (silver.getGreen() - color.getGreen()));
            int flickerBlue  = (int) (color.getBlue()  + alpha * (silver.getBlue()  - color.getBlue()));


            g.setColor(new Color(flickerRed, flickerGreen, flickerBlue));
            g
                    .fillRect((int)(x+h/10), (int)(y+h/10), (int)(w-h/5), (int)(h-h/5));
        }
    }

    @Override
    void collisionResolution(MyGameObject o) {
        if (isDestroyed) return; // 이미 깨진 블록은 무시

        if (o instanceof MyHWBall ball) {
            float left = x - ball.r;
            float right = x + w + ball.r;
            float top = y - ball.r;
            float bottom = y + h + ball.r;

            // 충돌 시
            if (ball.x > left && ball.x < right && ball.y > top && ball.y < bottom) {
                SoundManager.play(3);
                isDestroyed = true;
                destroyX = ball.x;
                destroyY = ball.y;
                destroyVX = ball.vx;
                destroyVY = ball.vy;
                // 공 반사 처리
                checkCollision(ball, left, right, top, bottom);
            }
        }
    }
}