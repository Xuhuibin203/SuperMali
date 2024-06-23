package asup.mali;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class BackGround {
    //当前场景要显示的图像
    private BufferedImage bgImage = null;
    //记录当前是第几个场景
    private int sort;
    //判断是否是最后一个场景
    private boolean flag;

    //存放所有障碍物
    private List<Obstacle> obstacleList = new ArrayList<>();


    private List<Enemy> enemyList = new ArrayList<>();

    //显示旗杆
    private BufferedImage gan = null;
    //用于显示城堡
    private BufferedImage tower = null;

    //判断马里奥是否到达旗杆位置
    private boolean isReach = false;

    //判断旗子是否落地
    private boolean isBase = false;

    //空参构造（当不知道往里面传什么参数却又需要这个类时，需要这个空参构造）
    public BackGround(){

    }

    public BackGround(int sort,boolean flag){
        this.flag = flag;
        this.sort = sort;
        if(flag){
            bgImage = StaticValue.bg2;
        }else{
            //第一关和第二关
            bgImage = StaticValue.bg;
        }
        if(sort==2){
            //第一关的上地面和下地面（解密）
            for(int i = 0 ; i < 27 ; i ++){
                obstacleList.add(new Obstacle(i*30,420,1,this));
            }
            //下地面
            for(int i = 0 ; i <= 120 ; i += 30){
                for(int j = 0 ; j < 27 ; j ++){
                    obstacleList.add(new Obstacle(j*30,570-i,2,this));
                }
            }
            //绘制砖块A
            for(int i = 120 ; i <= 150 ; i+=30){
                obstacleList.add(new Obstacle(i,300,7,this));
            }
            obstacleList.add(new Obstacle(60,240,7,this));

            obstacleList.add(new Obstacle(120,180,0,this));//(坑点0)

            obstacleList.add(new Obstacle(180,120,7,this));

            obstacleList.add(new Obstacle(240,180,7,this));
            obstacleList.add(new Obstacle(270,180,7,this));
            obstacleList.add(new Obstacle(330,180,7,this));
            obstacleList.add(new Obstacle(450,210,0,this));     //方形
            obstacleList.add(new Obstacle(420,210,0,this));     //方形
            obstacleList.add(new Obstacle(510,180,0,this));     //原地顶格跳（坑点1）
            obstacleList.add(new Obstacle(510,120,7,this));     //原地顶格跳
            obstacleList.add(new Obstacle(540,120,7,this));     //原地顶格跳
            obstacleList.add(new Obstacle(570,120,7,this));     //原地顶格跳
            //绘制砖块B-F
            for(int i = 270 ; i <= 570 ; i+=30){
                if(i==360||i==390||i==480||i==510||i==540){
                    //7是普通石砖
                    obstacleList.add(new Obstacle(i,300,7,this));
                }else{
                    //0是奖励块
                    if(i!=420)
                        obstacleList.add(new Obstacle(i,300,0,this));//(坑点2)
                    if(i==450)
                        obstacleList.add(new Obstacle(i,300,7,this));
                }
            }
            //绘制砖块G
            for(int i = 420 ; i <= 450 ; i += 30){
                obstacleList.add(new Obstacle(i,240,7,this));
            }

            //绘制水管
            for(int i = 360 ; i<=600 ; i += 25){
                if(i==360){
                    obstacleList.add(new Obstacle(620,i,3,this));
                    obstacleList.add(new Obstacle(645,i,4,this));
                }else{
                    obstacleList.add(new Obstacle(620,i,5,this));
                    obstacleList.add(new Obstacle(645,i,6,this));
                }
            }
//            for(int i = 100 ; i<=600 ; i += 25){
//                if(i==100){
//                    obstacleList.add(new Obstacle(660,i,3,this));
//                    obstacleList.add(new Obstacle(685,i,4,this));
//                }else{
//                    obstacleList.add(new Obstacle(660,i,5,this));
//                    obstacleList.add(new Obstacle(685,i,6,this));
//                }
//            }
            enemyList.add(new Enemy(580,385,true,1,this));
            enemyList.add(new Enemy(430,385,true,1,this));
            //食人花敌人
//            enemyList.add(new Enemy(635,420,true,2,328,428,this));
        }
        //判断是否是第二关
        if(sort==1){
            //第二关的上地面和下地面
            for(int i = 0 ; i < 27 ; i ++){
                obstacleList.add(new Obstacle(i*30,420,1,this));
            }
            //下地面
            for(int i = 0 ; i <= 120 ; i += 30){
                for(int j = 0 ; j < 27 ; j ++){
                    obstacleList.add(new Obstacle(j*30,570-i,2,this));
                }
            }
            //绘制水管①
            for(int i = 360 ; i<=600 ; i += 25){
                if(i==360){
                    obstacleList.add(new Obstacle(60,i,3,this));
                    obstacleList.add(new Obstacle(85,i,4,this));
                }else{
                    obstacleList.add(new Obstacle(60,i,5,this));
                    obstacleList.add(new Obstacle(85,i,6,this));
                }
            }
            //绘制水管②
            for(int i = 100 ; i<=600 ; i += 25){
                if(i==100){
                    obstacleList.add(new Obstacle(620,i,3,this));
                    obstacleList.add(new Obstacle(645,i,4,this));
                }else{
                    obstacleList.add(new Obstacle(620,i,5,this));
                    obstacleList.add(new Obstacle(645,i,6,this));
                }
            }
            //绘制砖块C
            obstacleList.add(new Obstacle(300,330,0,this));

            //绘制砖块B,E,G
            for(int i = 270 ; i<=330 ; i+=30){
                if(i==270||i==330){
                    obstacleList.add(new Obstacle(i,360,0,this));
                }else{
                    obstacleList.add(new Obstacle(i,360,7,this));
                }
            }

            //绘制砖块A,D,F,H,I
            for(int i = 240 ; i <= 360 ; i+=30){
                if(i==240||i==360){
                    obstacleList.add(new Obstacle(i,390,0,this));
                }else{
                    obstacleList.add(new Obstacle(i,390,7,this));
                }
            }

            //绘制妨碍砖块
            obstacleList.add(new Obstacle(240,300,7,this));

            //绘制空1-4砖块
            for(int i = 0 ; i <= 230 ; i+= 30){
                obstacleList.add(new Obstacle(i,300,7,this));
            }
            obstacleList.add(new Obstacle(210,270,7,this));
            //竖排跑酷1
            for(int i = 120 ; i <= 240 ; i += 60){
                obstacleList.add(new Obstacle(30,i,7,this));
            }
            //顶上横排
            for(int i = 60 ; i <= 420 ; i+=30){
                int point = i / 30;
                point %= 2;
                obstacleList.add(new Obstacle(i,60,point * 7,this));
            }
            for(int i = -60 ; i <= 390 ; i+=30){
                if(i!=270&&i!=300)
                    obstacleList.add(new Obstacle(510,i,7,this));
            }
            for(int i = 60 ; i <= 390 ; i+= 60){
                obstacleList.add(new Obstacle(580,i,7,this));
            }
            //蘑菇敌人
            enemyList.add(new Enemy(580,385,true,1,this));
            enemyList.add(new Enemy(150,265,true,1,328,428,this));
            //食人花
//            enemyList.add(new Enemy(635,385,false,2,328,428,this));
        }

        //第三关
        if(sort==3){
            //第三关的上地面和下地面
            for(int i = 0 ; i < 27 ; i ++){
                obstacleList.add(new Obstacle(i*30,420,1,this));
            }
            //下地面
            for(int i = 0 ; i <= 120 ; i += 30){
                for(int j = 0 ; j < 27 ; j ++){
                    obstacleList.add(new Obstacle(j*30,570-i,2,this));
                }
            }

            //绘制A-O砖块 //三角形阶梯
            int temp = 290;
            for(int i = 390 ; i >= 270 ; i -= 30){
               for(int j = temp ; j <= 410 ; j += 30){
                   obstacleList.add(new Obstacle(j,i,7,this));
               }
               temp+=30;
            }

            //第二种通关方式
            obstacleList.add(new Obstacle(230,330,0,this));
            obstacleList.add(new Obstacle(230,360,0,this));
            obstacleList.add(new Obstacle(260,360,0,this));
            obstacleList.add(new Obstacle(290,330,0,this));
            obstacleList.add(new Obstacle(320,300,0,this));
            obstacleList.add(new Obstacle(350,270,0,this));
            obstacleList.add(new Obstacle(380,240,0,this));

            //绘制P-R砖块
            temp = 60;
            for(int i = 390 ; i >= 360 ; i-=30 ){
                for(int j = temp ; j <= 90 ; j += 30){
                    obstacleList.add(new Obstacle(j,i,7,this));
                }
                temp += 30;
            }

            //绘制旗杆
            gan = StaticValue.gan;

            //绘制城堡
            tower = StaticValue.tower;

            //添加棋子到杆子上
            obstacleList.add(new Obstacle(515,220,8,this));
        }
    }

    public BufferedImage getBgImage() {
        return bgImage;
    }

    public int getSort() {
        return sort;
    }

    public boolean isFlag() {
        return flag;
    }

    public List<Obstacle> getObstacleList() {
        return obstacleList;
    }

    public BufferedImage getGan() {
        return gan;
    }

    public BufferedImage getTower() {
        return tower;
    }

    public void setReach(boolean reach) {
        isReach = reach;
    }

    public boolean isReach() {
        return isReach;
    }

    public boolean isBase() {
        return isBase;
    }

    public void setBase(boolean base) {
        isBase = base;
    }

    public List<Enemy> getEnemyList() {return enemyList;}
}
