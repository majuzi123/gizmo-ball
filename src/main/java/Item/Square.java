package Item;

import Util.ItemType;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import Util.VirtualWorld;
import java.awt.*;

/**
 * 正方形障碍
 */
public class Square extends Item{
    float hw ;
    float hh ; // 半宽 & 高

    public Square(Integer x, Integer y, String image){
        super(x,y,image);
        width = Item.BASE_WIDTH;
        height = Item.BASE_HEIGHT;
        hw = (float) width /2;
        hh = (float) height /2;
    }

    @Override
    public void initInWorld() { // 创建刚体
        BodyDef bd = new BodyDef();
        bd.position = new Vec2(x+hw,y+hh);
        bd.type = BodyType.STATIC; // 不可运动的
        bd.userData = ItemType.Square;
        FixtureDef fd = new FixtureDef();
        PolygonShape ps = new PolygonShape();
        ps.setAsBox(hw,hh); // 将形状设置为矩形，hw=hh
        fd.shape = ps;

        body = VirtualWorld.world.createBody(bd);
        body.createFixture(fd);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.rotate(Math.toRadians(theta),x+hw,y+hh); //正方形图片的旋转：以正方形中心为旋转中心
        g2d.drawImage(image, x, y, width, height,null);
    }

    @Override
    public void enlarge(){ // 放大Item
        scale += 1;
        width = Item.BASE_WIDTH * scale;
        height = Item.BASE_HEIGHT * scale;
        hw = (float) width /2;
        hh = (float) height /2;
        setSize(width, height);
    }

    @Override
    public void reduce(){ // 缩小Item
        if (scale > 1){
            scale -= 1;
            width = Item.BASE_WIDTH * scale;
            height = Item.BASE_HEIGHT * scale;
            hw = (float) width /2;
            hh = (float) height /2;
        }
        setSize(width, height);
    }
}
