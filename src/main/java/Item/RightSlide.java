package Item;

import Util.ItemType;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import java.awt.*;
import Util.VirtualWorld;
/**
 * 右侧挡板
 */
public class RightSlide extends Item{
    float hw ; //半宽
    float hh ; //半高

    public RightSlide(Integer x, Integer y, String image){
        super(x,y,image);
        width = 3*Item.BASE_WIDTH;
        height = Item.BASE_HEIGHT;
        setSize(width, height);
    }

    @Override
    public void initInWorld() { // 创建刚体
        BodyDef bd = new BodyDef();
        hw = (float) width /2;
        hh = (float) height /2;
        bd.position = new Vec2(x + hw, y + hh);
        bd.type = BodyType.STATIC; // 固定不动的
        bd.userData = ItemType.RightSlide;
        FixtureDef fd = new FixtureDef();
        PolygonShape ps = new PolygonShape();
        ps.setAsBox(hw,hh/5);
        fd.shape = ps;

        body = VirtualWorld.world.createBody(bd);
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
    public void move(int lengthX,int lengthY){
        body.setTransform(new Vec2(body.getPosition().x + lengthX ,
                body.getPosition().y + lengthY),0);
    }
}
