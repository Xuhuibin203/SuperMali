package asup.mali;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class MyJFrame extends JFrame implements KeyListener,Runnable {
    //存储所有背景图片
    private List<BackGround> allBg = new ArrayList<>();
    //存储当前背景
    private BackGround nowBg = new BackGround();
    //用于双缓存
    private Image offScreenImage = null;

    //马里奥对象
    private Mario mario = new Mario();

    //定义一个线程对象，用于实现马里奥的运动
    private Thread thread = new Thread(this);

    public MyJFrame(){
        //设置窗口大小为800 * 600
        this.setSize(800,600);
        //设置窗口居中显示
        this.setLocationRelativeTo(null);
        //设置窗口可见性
        this.setVisible(true);
        //设置点击窗口关闭键，结束程序
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置窗口大小不可变
        this.setResizable(false);

        //想窗口对象添加键盘监听器
        this.addKeyListener(this);
        //设置窗口名称
        this.setTitle("许辉彬的超级玛丽");
        //初始化图片
        StaticValue.init();
        //初始化马里奥
        mario = new Mario(10,355);
        //创建全部场景
        for(int i = 1; i <= 3; i ++){
            allBg.add(new BackGround(i, i == 3));
        }//TODO 这个add是从0开始的，所以下面get的时候是0开始的下标
        //将第一个场景设置为当前场景
        nowBg = allBg.get(1);

        mario.setBackGround(nowBg);
        //绘制图片
        repaint();
        thread.start();
    }
    @Override
    public void paint(Graphics g){
        if(offScreenImage == null){
            offScreenImage = createImage(800,600);
        }
        Graphics graphics = offScreenImage.getGraphics();
        graphics.fillRect(0,0,800,600);

        //绘制背景
        graphics.drawImage(nowBg.getBgImage(),0,0,this);
        //绘制敌人
        for(Enemy e:nowBg.getEnemyList()){
            graphics.drawImage(e.getShow(),e.getX(),e.getY(),this);
        }
        //绘制障碍物
        for(Obstacle ob:nowBg.getObstacleList()){
            graphics.drawImage(ob.getShow(),ob.getX(),ob.getY(),this);
        }
        //绘制城堡
        graphics.drawImage(nowBg.getTower(),620,270,this);
        //绘制旗杆
        graphics.drawImage(nowBg.getGan(),500,220,this);
        //绘制马里奥
        graphics.drawImage(mario.getShow(),mario.getX(),mario.getY(),this);
        //分数
        Color c = graphics.getColor();
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("黑体",Font.BOLD,25));
        graphics.drawString("当前的分数："+mario.getScore(),300,130);
        graphics.setColor(c);
        //将图片绘制到窗口中
        g.drawImage(offScreenImage,0,0,this);


    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //向右移动
        if(e.getKeyCode() == 39){
            mario.rightMove();
        }
        //向左移动
        if(e.getKeyCode() == 37){
            mario.leftMove();
        }
        //跳跃
        if(e.getKeyCode() == 38){
             mario.jump();
        }
        //卡关时候输入R重新开始
        if(e.getKeyCode() == 82){
            allBg.clear();
            for(int i = 1; i <= 3; i ++){
                allBg.add(new BackGround(i, i == 3));
            }
            nowBg = allBg.get(nowBg.getSort()-1);
            mario.setBackGround(nowBg);
            mario.setX(10);
            mario.setY(355);
        }
    }
    //当键盘松开时候调用
    @Override
    public void keyReleased(KeyEvent e) {
        //向右停止
        if(e.getKeyCode() == 39){
            mario.rightStop();
        }
        //向左停止
        if(e.getKeyCode() == 37){
            mario.leftStop();
        }
    }

    @Override
    public void run() {
        while(true){
            repaint();
            try {
                Thread.sleep(50);
                //休眠的时候判断一下马里奥有没有走到最右边
                if(mario.getX()>=750){
                    nowBg = allBg.get(nowBg.getSort());//+1?
                    mario.setBackGround(nowBg);
                    mario.setX(10);
                    mario.setY(355);
                }
                //判断马里奥是否死亡
                if(mario.isDeath()){
                    JOptionPane.showMessageDialog(this,"gameover");
                    System.exit(0);
                }

                //判断游戏是否结束
                if(mario.getOK()){
                     JOptionPane.showMessageDialog(this,"恭喜你成功过关！");
                     System.exit(0);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
