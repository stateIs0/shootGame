package shoot.cxs;

import java.util.Random;



/**
 * 敌机类
 * @author Administrator
 *
 */
public class Airplane extends FlyingObject implements Enemy {
	public int spend=2;//移动速度
	
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
	/** 重写outOfBounds() */
	public boolean outOfBounds(){
		return this.y>=ShootFGame.HEIGHT; //敌机的y>=窗口的高，即为越界
	}
	
}
