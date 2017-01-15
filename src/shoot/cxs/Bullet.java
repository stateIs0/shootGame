package shoot.cxs;
/**
 * 子弹类
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
		this.y=y;//根据英雄机的坐标得到的xy
		
		
	}

	@Override
	public void step() {
		y-=spend;//向上移送spend个坐标速度
		
	}

	public boolean outOfBounds(){
		return this.y<=-this.height; //子弹的y<=负的子弹的高，即为越界
	}
}
