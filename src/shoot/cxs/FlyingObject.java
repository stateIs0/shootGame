package shoot.cxs;

import java.awt.image.BufferedImage;



/**
 * ��Ϊ������abstract,
 * �������еķ�����̳��Դ��ಢ�̳з����ͳ�Ա����.
 * 
 * @author Administrator
 *
 */
public abstract class FlyingObject {
	/*
	 * �����������������:
	 * ͼƬ,��,��,x��,y��
	 * 
	 * ���������Ϊ:
	 * �߲�,Խ��,������
	 * 
	 */
	protected BufferedImage image;//ͼƬ
	protected int width; //ͼƬ���
	protected int height; //ͼƬ�߶�
	protected int x;//x ����
	protected int y;//y ����
	
	public  abstract void step();//�������߲�
	
	public  abstract boolean outOfBounds();//����Ƿ�Խ��
	
	
	/** ������(�л�+С�۷�)�Ƿ��ӵ����� this:���� bullet:�ӵ� */
	public boolean shootBy(Bullet bullet){
		int x1 = this.x;             //x1:���˵�x
		int x2 = this.x+this.width;  //x2:���˵�x+���˵Ŀ�
		int y1 = this.y;             //y1:���˵�y
		int y2 = this.y+this.height; //y2:���˵�y+���˵ĸ�
		int x = bullet.x;            //x:�ӵ���x
		int y = bullet.y;            //y:�ӵ���y
		
		return x>=x1 && x<=x2
			   &&
			   y>=y1 && y<=y2; //x��x1��x2֮�䣬���ң�y��y1��y2֮�䣬��Ϊײ����
	}
	
}
