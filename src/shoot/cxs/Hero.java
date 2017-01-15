package shoot.cxs;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;



/**
 * Ӣ�ۻ���.
 * @author Administrator
 *
 */
public class Hero extends FlyingObject{
	private List<BufferedImage> images;
	private int doubleFire;
	public int life;
	
	
	public Hero(){//����Ӣ�ۻ�
		image=ShootFGame.hero0;
		width=image.getWidth();
		height=image.getHeight();
		x=150;
		y=400;
		life = 3; //Ĭ��3����
		images=new ArrayList<BufferedImage>();
		images.add(ShootFGame.hero0);
		images.add(ShootFGame.hero1);
		doubleFire=10;
	}
	
	
	int index=0;//�����л�ͼƬ�ı���
	public void step() {
		image=images.get(index++%2);
	}

	/**
	 * Ӣ�ۻ������ӵ����㷨
	 */
	public List<Bullet> shoot(){
		List<Bullet> list=new ArrayList<Bullet>();//�������ӵ���
		int xStep=width/2;//������ӵ��ļ��(����Ӣ�ۻ��Ŀ�ȵĵ�)
		if(doubleFire>0){//�������ֵ����0.�ͼ�������
			list.add(0,new Bullet(this.x,this.y-20));
			list.add(1,new Bullet(this.x+xStep,this.y-20));
			list.add(2,new Bullet(this.x+width,this.y-20));
			doubleFire-=1;
			return list;
		}else{///�������ֵ����0.�򷵻ص����ӵ�.
			list.add(new Bullet(this.x+xStep,this.y-20));
			return list;
		}
			
	}
	
	
	
	
	/** ��дoutOfBounds() */
	public boolean outOfBounds(){
		return false; //����Խ��
	}

	/** ���� */
	public void addLife(){
		life++; //������1
	}
	/** ��ȡ�� */
	public int getLife(){
		return life; //��������
	}
	/** ���� */
	public void subtractLife(){
		life--; //������1
	}
	
	/** �ӻ��� */
	public void addDoubleFire(){
		doubleFire+=40; //����ֵ��40
	}
	/** ��ջ���ֵ */
	public void clearDoubleFire(){
		doubleFire = 0; //����ֵ����
	}
	/** Ӣ�ۻ�������궯  x:����x y:����y*/
	public void moveTo(int x,int y){
		this.x = x-this.width/2;  //Ӣ�ۻ���x=����x-1/2Ӣ�ۻ��Ŀ�
		this.y = y-this.height/2; //Ӣ�ۻ���y=����y-1/2Ӣ�ۻ��ĸ�
	}
	/** Ӣ�ۻ�����˵���ײ�㷨  this:Ӣ�ۻ� other:���� */
	public boolean hit(FlyingObject other){
		int x1 = other.x-this.width/2;               //x1:���˵�x-1/2Ӣ�ۻ��Ŀ�
		int x2 = other.x+other.width+this.width/2;   //x2:���˵�x+���˵Ŀ�+1/2Ӣ�ۻ��Ŀ�
		int y1 = other.y-this.height/2;              //y1:���˵�y-1/2Ӣ�ۻ��ĸ�
		int y2 = other.y+other.height+this.height/2; //y2:���˵�y+���˵ĸ�+1/2Ӣ�ۻ��ĸ�
		int x = this.x+this.width/2;                 //x:Ӣ�ۻ���x+1/2Ӣ�ۻ��Ŀ�
		int y = this.y+this.height/2;                //y:Ӣ�ۻ���y+1/2Ӣ�ۻ��ĸ�
		
		return x>=x1 && x<=x2
			   &&
			   y>=y1 && y<=y2; //x��x1��x2֮�䣬���ң�y��y1��y2֮�䣬��Ϊײ����
	}
	
}
