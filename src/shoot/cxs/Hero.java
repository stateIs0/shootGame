package shoot.cxs;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;



/**
 * 英雄机类.
 * @author Administrator
 *
 */
public class Hero extends FlyingObject{
	private List<BufferedImage> images;
	private int doubleFire;
	public int life;
	
	
	public Hero(){//构造英雄机
		image=ShootFGame.hero0;
		width=image.getWidth();
		height=image.getHeight();
		x=150;
		y=400;
		life = 3; //默认3条命
		images=new ArrayList<BufferedImage>();
		images.add(ShootFGame.hero0);
		images.add(ShootFGame.hero1);
		doubleFire=10;
	}
	
	
	int index=0;//辅助切换图片的变量
	public void step() {
		image=images.get(index++%2);
	}

	/**
	 * 英雄机发射子弹的算法
	 */
	public List<Bullet> shoot(){
		List<Bullet> list=new ArrayList<Bullet>();//定义存放子弹的
		int xStep=width/2;//定义多子弹的间距(根据英雄机的宽度的到)
		if(doubleFire>0){//如果火力值大于0.就继续发射
			list.add(0,new Bullet(this.x,this.y-20));
			list.add(1,new Bullet(this.x+xStep,this.y-20));
			list.add(2,new Bullet(this.x+width,this.y-20));
			doubleFire-=1;
			return list;
		}else{///如果火力值等于0.则返回单个子弹.
			list.add(new Bullet(this.x+xStep,this.y-20));
			return list;
		}
			
	}
	
	
	
	
	/** 重写outOfBounds() */
	public boolean outOfBounds(){
		return false; //永不越界
	}

	/** 加命 */
	public void addLife(){
		life++; //命数增1
	}
	/** 获取命 */
	public int getLife(){
		return life; //返回命数
	}
	/** 减命 */
	public void subtractLife(){
		life--; //命数减1
	}
	
	/** 加火力 */
	public void addDoubleFire(){
		doubleFire+=40; //火力值增40
	}
	/** 清空火力值 */
	public void clearDoubleFire(){
		doubleFire = 0; //火力值归零
	}
	/** 英雄机随着鼠标动  x:鼠标的x y:鼠标的y*/
	public void moveTo(int x,int y){
		this.x = x-this.width/2;  //英雄机的x=鼠标的x-1/2英雄机的宽
		this.y = y-this.height/2; //英雄机的y=鼠标的y-1/2英雄机的高
	}
	/** 英雄机与敌人的碰撞算法  this:英雄机 other:敌人 */
	public boolean hit(FlyingObject other){
		int x1 = other.x-this.width/2;               //x1:敌人的x-1/2英雄机的宽
		int x2 = other.x+other.width+this.width/2;   //x2:敌人的x+敌人的宽+1/2英雄机的宽
		int y1 = other.y-this.height/2;              //y1:敌人的y-1/2英雄机的高
		int y2 = other.y+other.height+this.height/2; //y2:敌人的y+敌人的高+1/2英雄机的高
		int x = this.x+this.width/2;                 //x:英雄机的x+1/2英雄机的宽
		int y = this.y+this.height/2;                //y:英雄机的y+1/2英雄机的高
		
		return x>=x1 && x<=x2
			   &&
			   y>=y1 && y<=y2; //x在x1和x2之间，并且，y在y1和y2之间，即为撞上了
	}
	
}
