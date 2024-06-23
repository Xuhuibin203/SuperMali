package asup.mali;

import java.awt.image.BufferedImage;

public class Enemy implements Runnable{
private int x;
private int y;
private int type;
private boolean face_to = true;
private BufferedImage show;
private BackGround bg;
private int max_up = 0;
private int max_down = 0;
private Thread thread = new Thread(this);
private int image_type = 0;

public Enemy(int x, int y, boolean face_to, int type, BackGround bg) {
    this.x = x;
    this.y = y;
    this.face_to = face_to;
    this.type = type;
    this.bg = bg;
    show = StaticValue.mogu.get(0);
    thread.start();
}
public Enemy(int x,  int y, boolean face_to, int type,int max_up,int max_down, BackGround bg){
    this.x = x;
    this.y = y;
    this.face_to = face_to;
    this.type = type;
    this.max_up = max_up;
    this.max_down = max_down;
    this.bg = bg;
    show = StaticValue.flower.get(0);
    thread.start();
}

public void death(){
    show = StaticValue.mogu.get(2);
    this.bg.getEnemyList().remove(this);
}

@Override
public void run(){
    while(true){
        if(type == 1){
            if(face_to){
                this.x-=2;
            }else{
                this.x+=2;
            }
            image_type = image_type == 1 ? 0 : 1;
            show = StaticValue.mogu.get(image_type);
        }
        boolean canLeft = true;
        boolean canRight = true;
        for(int i = 0;i < bg.getObstacleList().size();i ++){
            Obstacle ob1 = bg.getObstacleList().get(i);
            //判断是否能像右走
            if(ob1.getX() == this.x + 36 && (ob1.getY() + 65 > this.y && ob1.getY() - 35 < this.y)){
                canRight = false;
            }
            if(ob1.getX() == this.x - 36 && (ob1.getY() + 65 > this.y && ob1.getY() - 35 < this.y)){
                canLeft = false;
            }
            if(face_to && !canRight || this.x == 0){
                face_to = false;
            } else if ((!face_to) && (!canRight) || this.x == 600){
                face_to = true;
            }
            if(type == 2){
                if(face_to){
                    this.y-=2;
                }else{
                    this.y+=2;
                }
                image_type = image_type == 1 ? 0 : 1;

                if(face_to && this.y == max_up){
                    face_to = false;
                }
                if((!face_to) && this.y == max_down){
                    face_to = true;
                }
                show = StaticValue.flower.get(image_type);
            }

        }
        try {
            Thread.sleep(35);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
public int getX() {
    return x;
}

public int getY() {
    return y;
}

public int getType() {return type;}

public BufferedImage getShow() {
    return show;
}
}
