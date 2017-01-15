package shoot.cxs;
/**
 * 奖品接口
 * 包括双子弹(火力值)和命
 * @author Administrator
 *2016-10-28
 */
public interface Award {
	public int DOUBLE_FIRE=0;//火力值
	public int LIFE=1;//命
	public int getType();//返回的类型
}
 	