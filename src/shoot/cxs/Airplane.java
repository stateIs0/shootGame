package shoot.cxs;

import java.util.Random;



/**
 * �л���
 * @author Administrator
 *
 */
public class Airplane extends FlyingObject implements Enemy {
	public int spend=2;//�ƶ��ٶ�
	
	public Airplane(){
		image=ShootFGame.airplane;
		width=image.getWidth();
		height=image.getHeight();
		x=new Random().nextInt(ShootFGame.WIDTH-this.width);
		y=-this.height;
		
		
		
	}

	@Override
	public int getScore() {
		return 5;
	}

	@Override
	public void step() {
		y=y+spend;
		
	}

	@Override
	/** ��дoutOfBounds() */
	public boolean outOfBounds(){
		return this.y>=ShootFGame.HEIGHT; //�л���y>=���ڵĸߣ���ΪԽ��
	}
	
}
