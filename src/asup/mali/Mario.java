package asup.mali;

import java.awt.image.BufferedImage;

public class Mario implements Runnable {
    //定义横纵坐标
    private int x;
    private int y;

    //用于表示马里奥当前的状态（用mario的各种方法改变status，在run方法中判断状态）
    private String status;

    //用于表示当前状态对应的图像
    private BufferedImage show = null;

    //定义一个BackGround对象，用来获取障碍物的信息
    private  BackGround backGround = new BackGround();

    //进程，用来实现马里奥的动作（变量thread放在上面整理代码
    private Thread thread = null;

    //马里奥的移动速度
    private int xSpeed;
    //马里奥的跳跃速度
    private int ySpeed;

    //定义一个索引（在零和一之间变换）
    private int index;

    //表示马里奥上升的时间
    private int uptime = 0;

    //用于判断马里奥是否走到了城堡门口
    private boolean isOK = false;



    private int score = 0;

    private boolean isDeath = false;

    public Mario(){
    }

    public Mario(int x,int y){
        this.x = x;
        this.y = y;
        show = StaticValue.stand_R;
        this.status = "stand--right";
        //开始进程
        thread = new Thread(this);
        thread.start();

    }

    //马里奥的死亡方法
    public void  death(){
        isDeath = true;
    }

    //马里奥向左移动的方法
    public void leftMove(){
        xSpeed = -5;
        //判断马里奥是否碰到旗子
        if(backGround.isBase()){
            xSpeed = 0;
        }

        if(status.indexOf("jump")!=-1){
            status = "jump--left";
        }else{
            status = "move--left";
        }
    }
    //马里奥向右移动的方法
    public void rightMove(){
        xSpeed = 5;
        //判断马里奥是否碰到旗子
        if(backGround.isBase()){
            xSpeed = 0;
        }
        if(status.indexOf("jump")!=-1){
            status = "jump--right";
        }else{
            status = "move--right";
        }
    }
    //马里奥向左停止
    public void leftStop(){
        xSpeed = 0;
        if(status.indexOf("jump")!=-1){
            status = "jump--left";
        }else{
            status = "stop--left";
        }
    }
    //马里奥向右停止
    public void rightStop(){
        xSpeed = 0;
        if(status.indexOf("jump")!=-1){
            status = "jump--right";
        }else{
            status = "stop--right";
        }
    }

    //马里奥跳跃
    public void jump(){
        if(status.indexOf("jump")==-1){ //代表此时不是跳跃的状态
            if(status.indexOf("left") != -1){   //方向是向左
                status = "jump--left";
            }else{      //方向是向右
                status = "jump--right";
            }
            ySpeed = -10;
            uptime = 7;
        }
        //判断马里奥是否碰到旗子
        if(backGround.isBase()){
            ySpeed = 0;
        }
    }

    //马里奥下落
    public void fall(){
        if(status.indexOf("left") != -1){       //方向向左
            status = "jump--left";
        }else{
            status = "jump--right";         //方向向右
        }
        ySpeed = 10;
    }

    //TODO 调用上面这些方法以改变“status”对应的值，然后在run()方法中去判断当前马里奥的状态

    @Override
    public void run() {
        while (true){
            //判断是否处于障碍物上
            Boolean onObstacle = false;
            //判断是否可以往右走
            Boolean canRight = true;
            //判断是否可以往左走
            Boolean canLeft = true;
            //判断马里奥是否到达旗杆位置
            if(backGround.isFlag() && this.x >= 500){
                this.backGround.setReach(true);
                //判断旗子是否下落完成
                if(this.backGround.isBase()){
                    status = "move--right";
                    //如果旗子下落完成之后马里奥移动到了城堡处
                    if(x < 690){
                        x+=5;
                    }else{
                        isOK = true;
                    }
                }else{
                    if(y<395){
                        xSpeed = 0;
                        this.y+=5;
                        status = "jump--right";
                    }
                    if(y>395){
                        this.y = 395;
                        status = "stop--right";
                    }
                }
            }else{
                //遍历当前场景的障碍物
                for(int i = 0 ; i < backGround.getObstacleList().size() ; i ++){
                    Obstacle ob = backGround.getObstacleList().get(i);
                    //判断马里奥是否处于障碍物上
                    if(ob.getY()==this.y+25){
                        if((ob.getX() > this.x - 30 && ob.getX() < this.x + 25)){
                            onObstacle = true;
                        }
                    }
                    //判断是否跳起来顶到砖块
                    if((ob.getY()>=this.y-30 &&ob.getY()<=this.y - 20) && (ob.getX() > this.x-30 &&ob.getX() < this.x+25)){
                        if(ob.getType() == 0){
                            backGround.getObstacleList().remove(ob);
                            score++;
                        }
                        uptime = 0;//当顶到障碍物后就可以往下掉了
                    }
                    //判断是否可以往右走
                    if(ob.getX()==this.x + 25 && (ob.getY() > this.y - 30 && ob.getY() < this.y + 25)){
                        canRight = false;
                    }
                    //判断是否可以往左走
                    if(ob.getX()==this.x - 30 && (ob.getY() > this.y - 30 && ob.getY() < this.y+25)){
                        canLeft = false;
                    }
                }

                //判断马里奥是否碰到敌人死亡或者踩死蘑菇敌人
                for(int i = 0 ; i < backGround.getEnemyList().size() ; i++){
                    Enemy e = backGround.getEnemyList().get(i);
                    if((e.getX() - 25 < this.x &&e.getX() + 35 >= this.x )&& e.getY()==this.y + 20){
                        e.death();
                        uptime = 3;
                        ySpeed = -10;
                        score += 2;
                    }
                    if((e.getX() + 35 > this.x && e.getX() - 25 < this.x ) && (e.getY() + 35 > this.y && e.getY() - 20 < this.y)){
                        //马里奥死亡
                        death();
                    }
                }


                //进行马里奥跳跃的操作
                if(onObstacle && uptime == 0){          //判断是否在跳跃
                    if(status.indexOf("left")!=-1){     //判断左右
                        if(xSpeed!=0){                  //判断运动状态
                            status = "move--left";
                        }else{
                            status = "stop--left";
                        }
                    }else{
                        if(xSpeed!=0){
                            status = "move--right";
                        }else{
                            status = "stop--right";
                        }
                    }
                }else{  //跳跃状态进此分支
                    if(uptime!=0){
                        uptime--;
                    }else{
                        fall();
                    }
                    y += ySpeed;
                }
            }


            if((canLeft&&xSpeed<0) || (canRight&&xSpeed>0)){
                x+=xSpeed;
                //判断马里奥是否到了最左边
                if(x<0){
                    x = 0;
                }
            }
            //判断当前是否是移动状态,左右移动都是两张图片，所以在移动的时候用索引在两张图片之间切换
            if(status.contains("move")){
                index = index==0?1:0;
            }
            //判断是否向左移动(equals是用来判断两者内容是否相同的)
            if(status.equals("move--left")){
                show = StaticValue.run_L.get(index);
            }
            //判断是否向右移动
            if(status.equals("move--right")){
                show = StaticValue.run_R.get(index);
            }
            //判断是否向左停止
            if(status.equals("stop--left")){
                show = StaticValue.stand_L;
            }
            //判断是否向右停止
            if(status.equals("stop--right")){
                show = StaticValue.stand_R;
            }
            //判断是否向左跳跃
            if(status.equals("jump--left")){
                show = StaticValue.jump_L;
            }
            //判断是否向右跳跃
            if(status.equals("jump--right")){
                show = StaticValue.jump_R;
            }
            //休眠50毫秒
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public BufferedImage getShow() {
        return show;
    }

    public void setShow(BufferedImage show) {
        this.show = show;
    }

    public void setBackGround(BackGround backGround) {
        this.backGround = backGround;
    }
    public Boolean getOK() {
        return isOK;
    }
    public boolean isDeath() {
        return isDeath;
    }
    public int getScore() {
        return score;
    }
}