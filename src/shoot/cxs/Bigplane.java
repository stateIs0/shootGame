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
	/** ��дoutOfBounds() */
	public boolean outOfBounds(){
		return this.y>=ShootFGame.HEIGHT; //�л���y>=���ڵĸߣ���ΪԽ��
	}
	@Override
	public int getScore() {
		// TODO Auto-generated method stub
		return 10000;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
		}
