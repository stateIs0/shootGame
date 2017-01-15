package shoot.cxs;

import java.util.Random;

public class Bigplane extends FlyingObject implements Enemy ,Award{
	protected int spend=3;
	protected int life;
	protected int awardType;
	public Bigplane() {
		image=ShootFGame.bigplane;
		width=image.getWidth();
		height=image.getHeight();
		x=new Random().nextInt(ShootFGame.WIDTH-this.width);
		y=-this.height;
		awardType=new Random().nextInt(2);
		

	
	}public int getType() {
		return  awardType;
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
	@Override
	public int getScore() {
		// TODO Auto-generated method stub
		return 10000;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
		}
