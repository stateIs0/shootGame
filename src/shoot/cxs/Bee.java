package shoot.cxs;

import java.util.Random;



public class Bee extends FlyingObject implements Award  {
	protected int xspend=8;
	protected int yspend=1;
	protected int awardType;
	public Bee() {
		image=ShootFGame.bee;
		width=image.getWidth();
		height=image.getHeight();
		x=new Random().nextInt(ShootFGame.WIDTH-this.width);
		y=-this.height;
		awardType=new Random().nextInt(2);
		
		
		
		
	}

	@Override
	public int getType() {
		return  awardType;
	}

	@Override
	public void step() {
		x+=xspend;
		y+=yspend;
		if(x>(ShootFGame.WIDTH-width)){
			xspend-=1;
		}
		if(x<0){
			xspend+=1;
		}
		
		
		
	}

	/** 重写outOfBounds() */
	public boolean outOfBounds(){
		return this.y>=ShootFGame.HEIGHT; //小蜜蜂的y>=窗口的高，即为越界
	}
}
