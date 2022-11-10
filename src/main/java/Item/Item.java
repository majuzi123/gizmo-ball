package Item;


import org.jbox2d.dynamics.Body;
import Util.VirtualWorld;
import javax.swing.*;
import java.awt.*;

/**
 * 继承JComponent用于画图，并且JComponent已经实现了Serializable，可以直接用于序列化保存
 */
public class Item extends JComponent{
    public static final double BASE_LENGTH = 25;
    public static final double BASE_RADIUS = BASE_LENGTH/2;
    public static final int BASE_WIDTH = 25;
    public static final int BASE_HEIGHT = 25;

    int x = 50;// 左上角x坐标
    int y = 50;// 左上角y坐标
    int width;
    int height;
    int scale;// 放大倍数（必须>=1）
    double theta;// 旋转角度
    transient Image image;
    String imageUrl;
    transient Body body; // transient关键字修饰！在序列化处理过程中忽略

    public Item(Integer x, Integer y, String imageUrl){
        setX(x); //将鼠标点击位置转换为左上角坐标，使对应的图形画在格子内部（正中间）
        setY(y);
        setImageUrl(imageUrl);
        setBounds(this.x, this.y, 25, 25);
        this.scale = 1;
        this.theta = 0;
        setVisible(true);
    }

    // 工具操作，提供统一接口，在子类里进行重写
    /**
     * 放大
     */
    public void enlarge(){

    }

    /**
     * 缩小
     */
    public void reduce(){

    }

    /**
     * 旋转
     */
    public void rotate(){
        theta = (theta+90)%360;
    }

    /**
     * 在world中创建该item对应的刚体
     */
    public void initInWorld(){

    }

    /**
     * 在world里删除对应的刚体
     */
    public void destroyInWorld(){
        VirtualWorld.world.destroyBody(body);
        body = null;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x/BASE_WIDTH*BASE_WIDTH;
    } // 设置横坐标，使Item落在格子内部

    public void setY(int y) {
        this.y = y/BASE_HEIGHT*BASE_HEIGHT;
    } // 设置纵坐标，使Item落在格子内部

    public double getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public double getTheta() {
        return theta;
    }

    public void setTheta(double theta) {
        this.theta = theta;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Body getBody() {
        return body;
    }
}
