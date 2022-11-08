package Item;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import util.Common;

import java.awt.*;

//监听键盘按键
public class LeftSlide extends Item{
    float hw ; //半宽
    float hh ; //半高

    public LeftSlide(Integer x, Integer y, String image){
        super(x,y,image);
        width = 3*Item.BASE_WIDTH;
        height = Item.BASE_HEIGHT;
        setSize(width, height);
    }

    @Override
    public void initInWorld() {
        BodyDef bd = new BodyDef();
        hw = (float) width /2;
        hh = (float) height /2;
        //矩形刚体的position 记录矩形的对角线中心位置
        bd.position = new Vec2(x + hw, y + hh);
        bd.type = BodyType.STATIC; // 不可运动的
        FixtureDef fd = new FixtureDef();
        PolygonShape ps = new PolygonShape();
        ps.setAsBox(hw,hh/5);
        fd.shape = ps;
        // 创建刚体
        body = Common.world.createBody(bd);
        body.createFixture(fd);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.drawImage(image, x, y, width, height,null);
    }

    /**
     * 移动挡板（刚体）的方法
     */
    public void move(int length){
        //设置刚体位置和姿态角，position表示要设置的位置坐标，angle表示要设置的姿态角弧度
        body.setTransform(new Vec2(body.getPosition().x + length ,
                body.getPosition().y),0);
    }
}
