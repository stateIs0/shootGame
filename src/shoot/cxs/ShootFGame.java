package shoot.cxs;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.junit.Test;

















/**
 * 执行类
 * @author Administrator
 *
 */
public class ShootFGame extends JPanel {
	public static final int WIDTH=400;
	public static final int HEIGHT=654;
	
	public static BufferedImage background;//背景
	public static BufferedImage hero1;//英雄机1
	public static BufferedImage hero0;//英雄机2
	public static BufferedImage bee;//蜜蜂
	public static BufferedImage airplane;//敌机
	public static BufferedImage bullet;//子弹
	public static BufferedImage start;//开始
	public static BufferedImage pause;//暂停
	public static BufferedImage gameover;//结束
	public static BufferedImage bigplane;//大飞机
	
	public static final int START=0;//开始状态
	public static final int GAMEOVER=1;//结束状态
	public static final int PAUSE=2;//暂停状态
	public static final int RUNNING=3;//运行状态
	private int state=START;//当前状态 默认运行状态
	
	
	static{
		try {
			background= ImageIO.read (ShootFGame.class.getResource("background_看图王.png"));
			hero0=ImageIO.read (ShootFGame.class.getResource("hero0.png"));
			hero1=ImageIO.read (ShootFGame.class.getResource("hero1.png"));
			bee=ImageIO.read (ShootFGame.class.getResource("bee.png"));
			airplane=ImageIO.read (ShootFGame.class.getResource("airplane.png"));
			bullet=ImageIO.read (ShootFGame.class.getResource("bullet.png"));
			start=ImageIO.read (ShootFGame.class.getResource("start.png"));
			pause=ImageIO.read (ShootFGame.class.getResource("pause.png"));
			gameover=ImageIO.read (ShootFGame.class.getResource("gameover.png"));
			bigplane=ImageIO.read (ShootFGame.class.getResource("bigplane.png"));
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private Hero hero=new Hero();
	private List<Bullet> blist=new ArrayList<Bullet>();
	private List<FlyingObject> flist=new ArrayList<FlyingObject>();
	private List<FlyingObject> biglist=new ArrayList<FlyingObject>();

	/**
	 * 1..随机生成飞行物(敌机＋蜜蜂)
	 */
	public FlyingObject nextOne(){
		Random ran=new Random();
		int num=ran.nextInt(10);
		if(num!=0){
			return new Airplane();
		}else if(num<10){
			return new Bigplane();
		}else{
			return new Bee();
		}
	}
	int index=0;
		//2..敌机和小蜜蜂随机入场
		public void enteraction(){
			index++;
			if(index%40==0){//40毫秒生成一个敌人.
				flist.add(nextOne());
			}
		}
		
		
		/**3..敌机和小蜜蜂开始下落.也就是走步,包括子弹向上移动*/
		public void stepAction(){
			hero.step();
			for(FlyingObject fo:flist){
				fo.step();
			}
			for(Bullet bo:blist){
				bo.step();
			}
			for(FlyingObject bp:biglist){
				bp.step();
			}
		}
		
		/**4.子弹入场(通过调用英雄机的发射子弹shoot方法得到子弹集合*/
		int bulletdex=0;
		public void shootAction(){
			bulletdex++	;
			if(bulletdex%30==0){
				List<Bullet> bblist=hero.shoot();
				for(int i=0;i<bblist.size();i++){
					blist.add(bblist.get(i));
				}
			}
		
		}
		
		/** 5.删除越界的敌人(敌机+小蜜蜂)和子弹 */
		public synchronized  void  outOfBoundsAction(){ //10毫秒走一次
			//此处使用迭代器删除则不会出现并发的安全问题.
			Iterator<FlyingObject> it = flist.iterator();
			List<FlyingObject> fflist=new ArrayList<FlyingObject>();
			int dex=-1;
			while(it.hasNext()){ //遍历所有敌人
				dex++;
				if(it.next().outOfBounds()){ //不越界
					it.remove(); //移出此列表中首次出现的指定元素.
					//为什么直接remove不行呢?因为这是迭代器原理每次遍历时都会移动下标,遍历时是不能删除元素的,
	//				fflist.add(fo);
				
				}
			}
//			flist.clear();
//			for(FlyingObject foo:fflist){
//				flist.add(foo);
//			}
			
			List<Bullet> bblist=new ArrayList<Bullet>();
			Iterator<Bullet> it2 = blist.iterator();
			while(it2.hasNext()){ //遍历所有子弹
			 //获取每一个子弹
				if(it2.next().outOfBounds()){ //不越界
						it2.remove();
		//			bblist.add(b);
					
				}
			}
			Iterator<FlyingObject> it3 = biglist.iterator();
			while(it3.hasNext()){ //遍历所有大飞机
			 //获取每一个大飞机
				if(it3.next().outOfBounds()){ //不越界
						it3.remove();
		//			bblist.add(b);
					
				}
			}
//			blist.clear();
//			for(Bullet bu:bblist){
//				blist.add(bu);
//			}
		}
		/** 6.所有子弹与所有敌人的碰撞 */
		public void bangAction(){ //10毫秒走一次
			Iterator<Bullet> it = blist.iterator();
			while(it.hasNext()){ //遍历所有子弹
				if(bang(it.next())){
					it.remove();//一个子弹与所有敌人的碰撞
				}
		//		blist.remove(it.next());
			}
		}
		
	 //玩家的得分
		/** 一个子弹与所有敌人的碰撞 */
	
		int score=0;
		public boolean bang(Bullet b){
			int index = -1; //被撞敌人下标
			List<FlyingObject>fflist=new ArrayList<FlyingObject>();
			FlyingObject one =null;
			for(FlyingObject f:flist){ //遍历所有敌人
				 //获取每一个敌人
				index ++; //记录被撞敌人下标
				if(f.shootBy(b)){ //撞上了
					//获取被撞敌人对象
					 //剩余敌人不再比较了
				//	 one = flist.get(index); 
			
					 one=flist.remove(index);
					// blist.remove(b);//?????无法删除子弹.
					 break;
				}
			}
			if(index!=-1){ //被撞上了
				if(one instanceof Award && one instanceof FlyingObject){   //若是奖励
					Award a = (Award)one;
					Enemy e = (Enemy)one; 
					score += e.getScore();//强转为奖励类型
					int type = a.getType(); //获取奖励类型
					switch(type){ //根据不同的奖励类型做不同操作
					case Award.DOUBLE_FIRE:   //若为火力则
						hero.addDoubleFire(); //英雄机增火力
						break;
					case Award.LIFE:    //若为命则
						hero.addLife(); //英雄机增命
						return true;
					}
				}
		
				if(one instanceof Enemy){  //若是敌人
					Enemy e = (Enemy)one;  //强转为敌人类型
					score += e.getScore(); //玩家得分
		//		System.out.println("地方大幅度");
					return true;
				}
				if(one instanceof Award){   //若是奖励
					Award a = (Award)one;   //强转为奖励类型
					int type = a.getType(); //获取奖励类型
					switch(type){ //根据不同的奖励类型做不同操作
					case Award.DOUBLE_FIRE:   //若为火力则
						hero.addDoubleFire(); //英雄机增火力
						break;
					case Award.LIFE:    //若为命则
						hero.addLife(); //英雄机增命
						return true;
					}
				}
		
				
			}
			return false;
		}
		
		/** 检查游戏结束 */
		public void checkGameOverAction(){ //10毫秒走一次
			if(isGameOver()){ //游戏结束时
				state = GAMEOVER; //当前状态修改为游戏结束
			}
		}
		
		/** 判断游戏是否结束(检测英雄机与敌人碰撞) */
		public boolean isGameOver(){
			Iterator<FlyingObject> it = flist.iterator();
			while(it.hasNext()){ //遍历所有敌人
			//获取每一个敌人
				if(hero.hit(it.next())){ //撞上了
					hero.subtractLife(); //英雄机减命
					hero.clearDoubleFire(); //英雄机清空火力值
					it.remove();
				}
			}
			return hero.getLife()<=0; //命数<=0，即为游戏结束了
		}
		
		
	
	
	
		public void action (){
			MouseAdapter l = new MouseAdapter(){ //侦听器对象
				/** 重写鼠标移动事件 */
				public void mouseMoved(MouseEvent e){
					if(state==RUNNING){ //运行状态时执行
						int x = e.getX(); //获取鼠标的x坐标
						int y = e.getY(); //获取鼠标的y坐标
						hero.moveTo(x, y); //英雄机随着鼠标动
					}
				}
				/** 重写鼠标点击事件 */
				public void mouseClicked(MouseEvent e){
					switch(state){ //根据当前状态做不同操作
					case START: //启动状态时
						state=RUNNING; //变为运行状态
						break;
					case GAMEOVER: //游戏结束状态时
						//清理现场(所有数据归零)
						score = 0;
						hero = new Hero();
						flist=new ArrayList<FlyingObject>();
						blist= new ArrayList<Bullet>();
						state=START; //变为启动状态
						break;
					}
				}
				/** 重写鼠标移出事件 */
				public void mouseExited(MouseEvent e){
					if(state==RUNNING){ //运行状态时
						state = PAUSE;  //变为暂停状态
					}
				}
				/** 重写鼠标移入事件 */
				public void mouseEntered(MouseEvent e){
					if(state==PAUSE){    //暂停状态时
						state = RUNNING; //变为运行状态
					}
				}	
			};
			this.addMouseListener(l); //处理鼠标操作事件
			this.addMouseMotionListener(l); //处理鼠标滑动事件
			
			Timer timer = new Timer(); //定时器对象
			int intervel = 10; //定时间隔(以毫秒为单位)
			timer.schedule(new TimerTask(){
				public void run(){ //10毫秒走一次---定时干的那个事
					if(state==RUNNING){ //运行状态时执行
						shootAction();//4.子弹入场(通过调用英雄机的发射子弹shoot方法得到子弹集合
						enteraction();//2..敌机和小蜜蜂随机入场
						stepAction();//3..敌机和小蜜蜂开始下落.也就是走步,包括子弹向上移动
						outOfBoundsAction();//删除越界的飞行无
						bangAction();//所有子弹和所有敌人的相撞.
						 checkGameOverAction();//判断游戏是否
					}
					repaint();     //重画---调用paint()方法
				}
			},intervel,intervel);
		}
		
		
	
	
	
	/**画笔 paint*/
	public void paint(Graphics g){
		g.drawImage(background,0,0,null);//画背景图
		paintHero(g);//画英雄机图
	paintFlyingObjects(g);//画敌人图,在坐标外
		paintState(g);///画状态,开始运行图
		paintBullets(g);//画子弹图.
		paintScoreAndLife(g);//画分和画命
	}
	/**画英雄机对象*/
	public void paintHero(Graphics g){
		g.drawImage(hero.image,hero.x,hero.y,null); //画英雄机对象
	}
	/** 画子弹对象 */
	public void paintBullets(Graphics g){
		for(Bullet b:blist){ //遍历子弹
			 //获取每一个子弹
			g.drawImage(b.image,b.x,b.y,null); //画子弹对象
		}
	}
	/** 画敌人(敌机+小蜜蜂)对象 */
	public void paintFlyingObjects(Graphics g){
		for(FlyingObject fo:flist){ //遍历敌人(敌机+小蜜蜂)
		
			g.drawImage(fo.image,fo.x,fo.y,null); //画敌人(敌机+小蜜蜂)对象
		}
	}
	/** 画分和画命 */
	public void paintScoreAndLife(Graphics g){
		g.setColor(new Color(0x003030)); //设置颜色--纯红
		g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,24)); //设置字体--字体:SANS_SERIF 样式:BOLD加粗  字号:24 
		g.drawString("SCORE: "+score,10,25); //画分
		g.drawString("LIFE: "+hero.getLife(),10,45); //画命
	}
	/** 画状态 */
	public void paintState(Graphics g){
		switch(state){ //根据不同状态画不同的图
		case START: //启动状态时画启动图
			g.drawImage(start,0,0,null);
			break;
		case PAUSE: //暂停状态时画暂停图
			g.drawImage(pause,0,0,null);
			break;
		case GAMEOVER: //游戏结束状态时画游戏结束图
			g.drawImage(gameover,0,0,null);
			break;
		}
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("打飞机的乐趣只有麒麟臂才能懂!当你脱单的时候不要忘了单身时的寂寞."); //窗口
		ShootFGame game = new ShootFGame(); //面板
		frame.add(game); //将面板添加到窗口上
		
		frame.setSize(WIDTH, HEIGHT); //设置大小
		frame.setAlwaysOnTop(true); //设置窗口一直在最上面
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //设置默认关闭操作(关闭窗口时退出程序)
		frame.setLocationRelativeTo(null); //设置窗口居中显示
		frame.setVisible(true); //1.设置窗口可见  2.尽快调用paint()方法
		
		game.action(); //启动程序的执行
		
	}
	
	
	
}
