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
 * ִ����
 * @author Administrator
 *
 */
public class ShootFGame extends JPanel {
	public static final int WIDTH=400;
	public static final int HEIGHT=654;
	
	public static BufferedImage background;//����
	public static BufferedImage hero1;//Ӣ�ۻ�1
	public static BufferedImage hero0;//Ӣ�ۻ�2
	public static BufferedImage bee;//�۷�
	public static BufferedImage airplane;//�л�
	public static BufferedImage bullet;//�ӵ�
	public static BufferedImage start;//��ʼ
	public static BufferedImage pause;//��ͣ
	public static BufferedImage gameover;//����
	public static BufferedImage bigplane;//��ɻ�
	
	public static final int START=0;//��ʼ״̬
	public static final int GAMEOVER=1;//����״̬
	public static final int PAUSE=2;//��ͣ״̬
	public static final int RUNNING=3;//����״̬
	private int state=START;//��ǰ״̬ Ĭ������״̬
	
	
	static{
		try {
			background= ImageIO.read (ShootFGame.class.getResource("background_��ͼ��.png"));
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
	 * 1..������ɷ�����(�л����۷�)
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
		//2..�л���С�۷�����볡
		public void enteraction(){
			index++;
			if(index%40==0){//40��������һ������.
				flist.add(nextOne());
			}
		}
		
		
		/**3..�л���С�۷俪ʼ����.Ҳ�����߲�,�����ӵ������ƶ�*/
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
		
		/**4.�ӵ��볡(ͨ������Ӣ�ۻ��ķ����ӵ�shoot�����õ��ӵ�����*/
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
		
		/** 5.ɾ��Խ��ĵ���(�л�+С�۷�)���ӵ� */
		public synchronized  void  outOfBoundsAction(){ //10������һ��
			//�˴�ʹ�õ�����ɾ���򲻻���ֲ����İ�ȫ����.
			Iterator<FlyingObject> it = flist.iterator();
			List<FlyingObject> fflist=new ArrayList<FlyingObject>();
			int dex=-1;
			while(it.hasNext()){ //�������е���
				dex++;
				if(it.next().outOfBounds()){ //��Խ��
					it.remove(); //�Ƴ����б����״γ��ֵ�ָ��Ԫ��.
					//Ϊʲôֱ��remove������?��Ϊ���ǵ�����ԭ��ÿ�α���ʱ�����ƶ��±�,����ʱ�ǲ���ɾ��Ԫ�ص�,
	//				fflist.add(fo);
				
				}
			}
//			flist.clear();
//			for(FlyingObject foo:fflist){
//				flist.add(foo);
//			}
			
			List<Bullet> bblist=new ArrayList<Bullet>();
			Iterator<Bullet> it2 = blist.iterator();
			while(it2.hasNext()){ //���������ӵ�
			 //��ȡÿһ���ӵ�
				if(it2.next().outOfBounds()){ //��Խ��
						it2.remove();
		//			bblist.add(b);
					
				}
			}
			Iterator<FlyingObject> it3 = biglist.iterator();
			while(it3.hasNext()){ //�������д�ɻ�
			 //��ȡÿһ����ɻ�
				if(it3.next().outOfBounds()){ //��Խ��
						it3.remove();
		//			bblist.add(b);
					
				}
			}
//			blist.clear();
//			for(Bullet bu:bblist){
//				blist.add(bu);
//			}
		}
		/** 6.�����ӵ������е��˵���ײ */
		public void bangAction(){ //10������һ��
			Iterator<Bullet> it = blist.iterator();
			while(it.hasNext()){ //���������ӵ�
				if(bang(it.next())){
					it.remove();//һ���ӵ������е��˵���ײ
				}
		//		blist.remove(it.next());
			}
		}
		
	 //��ҵĵ÷�
		/** һ���ӵ������е��˵���ײ */
	
		int score=0;
		public boolean bang(Bullet b){
			int index = -1; //��ײ�����±�
			List<FlyingObject>fflist=new ArrayList<FlyingObject>();
			FlyingObject one =null;
			for(FlyingObject f:flist){ //�������е���
				 //��ȡÿһ������
				index ++; //��¼��ײ�����±�
				if(f.shootBy(b)){ //ײ����
					//��ȡ��ײ���˶���
					 //ʣ����˲��ٱȽ���
				//	 one = flist.get(index); 
			
					 one=flist.remove(index);
					// blist.remove(b);//?????�޷�ɾ���ӵ�.
					 break;
				}
			}
			if(index!=-1){ //��ײ����
				if(one instanceof Award && one instanceof FlyingObject){   //���ǽ���
					Award a = (Award)one;
					Enemy e = (Enemy)one; 
					score += e.getScore();//ǿתΪ��������
					int type = a.getType(); //��ȡ��������
					switch(type){ //���ݲ�ͬ�Ľ�����������ͬ����
					case Award.DOUBLE_FIRE:   //��Ϊ������
						hero.addDoubleFire(); //Ӣ�ۻ�������
						break;
					case Award.LIFE:    //��Ϊ����
						hero.addLife(); //Ӣ�ۻ�����
						return true;
					}
				}
		
				if(one instanceof Enemy){  //���ǵ���
					Enemy e = (Enemy)one;  //ǿתΪ��������
					score += e.getScore(); //��ҵ÷�
		//		System.out.println("�ط������");
					return true;
				}
				if(one instanceof Award){   //���ǽ���
					Award a = (Award)one;   //ǿתΪ��������
					int type = a.getType(); //��ȡ��������
					switch(type){ //���ݲ�ͬ�Ľ�����������ͬ����
					case Award.DOUBLE_FIRE:   //��Ϊ������
						hero.addDoubleFire(); //Ӣ�ۻ�������
						break;
					case Award.LIFE:    //��Ϊ����
						hero.addLife(); //Ӣ�ۻ�����
						return true;
					}
				}
		
				
			}
			return false;
		}
		
		/** �����Ϸ���� */
		public void checkGameOverAction(){ //10������һ��
			if(isGameOver()){ //��Ϸ����ʱ
				state = GAMEOVER; //��ǰ״̬�޸�Ϊ��Ϸ����
			}
		}
		
		/** �ж���Ϸ�Ƿ����(���Ӣ�ۻ��������ײ) */
		public boolean isGameOver(){
			Iterator<FlyingObject> it = flist.iterator();
			while(it.hasNext()){ //�������е���
			//��ȡÿһ������
				if(hero.hit(it.next())){ //ײ����
					hero.subtractLife(); //Ӣ�ۻ�����
					hero.clearDoubleFire(); //Ӣ�ۻ���ջ���ֵ
					it.remove();
				}
			}
			return hero.getLife()<=0; //����<=0����Ϊ��Ϸ������
		}
		
		
	
	
	
		public void action (){
			MouseAdapter l = new MouseAdapter(){ //����������
				/** ��д����ƶ��¼� */
				public void mouseMoved(MouseEvent e){
					if(state==RUNNING){ //����״̬ʱִ��
						int x = e.getX(); //��ȡ����x����
						int y = e.getY(); //��ȡ����y����
						hero.moveTo(x, y); //Ӣ�ۻ�������궯
					}
				}
				/** ��д������¼� */
				public void mouseClicked(MouseEvent e){
					switch(state){ //���ݵ�ǰ״̬����ͬ����
					case START: //����״̬ʱ
						state=RUNNING; //��Ϊ����״̬
						break;
					case GAMEOVER: //��Ϸ����״̬ʱ
						//�����ֳ�(�������ݹ���)
						score = 0;
						hero = new Hero();
						flist=new ArrayList<FlyingObject>();
						blist= new ArrayList<Bullet>();
						state=START; //��Ϊ����״̬
						break;
					}
				}
				/** ��д����Ƴ��¼� */
				public void mouseExited(MouseEvent e){
					if(state==RUNNING){ //����״̬ʱ
						state = PAUSE;  //��Ϊ��ͣ״̬
					}
				}
				/** ��д��������¼� */
				public void mouseEntered(MouseEvent e){
					if(state==PAUSE){    //��ͣ״̬ʱ
						state = RUNNING; //��Ϊ����״̬
					}
				}	
			};
			this.addMouseListener(l); //�����������¼�
			this.addMouseMotionListener(l); //������껬���¼�
			
			Timer timer = new Timer(); //��ʱ������
			int intervel = 10; //��ʱ���(�Ժ���Ϊ��λ)
			timer.schedule(new TimerTask(){
				public void run(){ //10������һ��---��ʱ�ɵ��Ǹ���
					if(state==RUNNING){ //����״̬ʱִ��
						shootAction();//4.�ӵ��볡(ͨ������Ӣ�ۻ��ķ����ӵ�shoot�����õ��ӵ�����
						enteraction();//2..�л���С�۷�����볡
						stepAction();//3..�л���С�۷俪ʼ����.Ҳ�����߲�,�����ӵ������ƶ�
						outOfBoundsAction();//ɾ��Խ��ķ�����
						bangAction();//�����ӵ������е��˵���ײ.
						 checkGameOverAction();//�ж���Ϸ�Ƿ�
					}
					repaint();     //�ػ�---����paint()����
				}
			},intervel,intervel);
		}
		
		
	
	
	
	/**���� paint*/
	public void paint(Graphics g){
		g.drawImage(background,0,0,null);//������ͼ
		paintHero(g);//��Ӣ�ۻ�ͼ
	paintFlyingObjects(g);//������ͼ,��������
		paintState(g);///��״̬,��ʼ����ͼ
		paintBullets(g);//���ӵ�ͼ.
		paintScoreAndLife(g);//���ֺͻ���
	}
	/**��Ӣ�ۻ�����*/
	public void paintHero(Graphics g){
		g.drawImage(hero.image,hero.x,hero.y,null); //��Ӣ�ۻ�����
	}
	/** ���ӵ����� */
	public void paintBullets(Graphics g){
		for(Bullet b:blist){ //�����ӵ�
			 //��ȡÿһ���ӵ�
			g.drawImage(b.image,b.x,b.y,null); //���ӵ�����
		}
	}
	/** ������(�л�+С�۷�)���� */
	public void paintFlyingObjects(Graphics g){
		for(FlyingObject fo:flist){ //��������(�л�+С�۷�)
		
			g.drawImage(fo.image,fo.x,fo.y,null); //������(�л�+С�۷�)����
		}
	}
	/** ���ֺͻ��� */
	public void paintScoreAndLife(Graphics g){
		g.setColor(new Color(0x003030)); //������ɫ--����
		g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,24)); //��������--����:SANS_SERIF ��ʽ:BOLD�Ӵ�  �ֺ�:24 
		g.drawString("SCORE: "+score,10,25); //����
		g.drawString("LIFE: "+hero.getLife(),10,45); //����
	}
	/** ��״̬ */
	public void paintState(Graphics g){
		switch(state){ //���ݲ�ͬ״̬����ͬ��ͼ
		case START: //����״̬ʱ������ͼ
			g.drawImage(start,0,0,null);
			break;
		case PAUSE: //��ͣ״̬ʱ����ͣͼ
			g.drawImage(pause,0,0,null);
			break;
		case GAMEOVER: //��Ϸ����״̬ʱ����Ϸ����ͼ
			g.drawImage(gameover,0,0,null);
			break;
		}
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("��ɻ�����Ȥֻ������۲��ܶ�!�����ѵ���ʱ��Ҫ���˵���ʱ�ļ�į."); //����
		ShootFGame game = new ShootFGame(); //���
		frame.add(game); //�������ӵ�������
		
		frame.setSize(WIDTH, HEIGHT); //���ô�С
		frame.setAlwaysOnTop(true); //���ô���һֱ��������
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //����Ĭ�Ϲرղ���(�رմ���ʱ�˳�����)
		frame.setLocationRelativeTo(null); //���ô��ھ�����ʾ
		frame.setVisible(true); //1.���ô��ڿɼ�  2.�������paint()����
		
		game.action(); //���������ִ��
		
	}
	
	
	
}
