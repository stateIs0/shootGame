package shoot.cxs;

import java.awt.image.BufferedImage;



/**
 * 此为抽象类abstract,
 * 包括所有的飞行物继承自此类并继承方法和成员变量.
 * 
 * @author Administrator
 *
 */
public abstract class FlyingObject {
	/*
	 * 飞行物的特征有以下:
	 * 图片,宽,高,x轴,y轴
	 * 
	 * 飞行物的行为:
	 * 走步,越界,被击中
	 * 
	 */
	protected BufferedImage image;//图片
	protected int width; //图片宽度
	protected int height; //图片高度
	protected int x;//x 坐标
	protected int y;//y 坐标
	
	public  abstract void step();//飞行物走步
	
	public  abstract boolean outOfBounds();//检查是否越界
	
	
	/** 检查敌人(敌机+小蜜蜂)是否被子弹击中 this:敌人 bullet:子弹 */
	public boolean shootBy(Bullet bullet){
		int x1 = this.x;             //x1:敌人的x
		int x2 = this.x+this.width;  //x2:敌人的x+敌人的宽
		int y1 = this.y;             //y1:敌人的y
		int y2 = this.y+this.height; //y2:敌人的y+敌人的高
		int x = bullet.x;            //x:子弹的x
		int y = bullet.y;            //y:子弹的y
		
		return x>=x1 && x<=x2
			   &&
			   y>=y1 && y<=y2; //x在x1和x2之间，并且，y在y1和y2之间，即为撞上了
	}
	
}
