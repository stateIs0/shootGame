package shoot.cxs;
/**
 * �ӵ���
 * @author Administrator
 *
 */
public class Bullet extends FlyingObject  {
	private int spend=3;
	
	public Bullet(int x,int y){
		image=ShootFGame.bullet;
		width=image.getWidth();
		height=image.getHeight();
		this.x=x;
		this.y=y;//����Ӣ�ۻ�������õ���xy
		
		
	}

	@Override
	public void step() {
		y-=spend;//��������spend�������ٶ�
		
	}

	public boolean outOfBounds(){
		return this.y<=-this.height; //�ӵ���y<=�����ӵ��ĸߣ���ΪԽ��
	}
}
