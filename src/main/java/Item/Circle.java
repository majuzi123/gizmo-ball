package Item;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import util.Common;

import java.awt.*;

public class Circle extends Item{
    float radius;

    public Circle (Integer x, Integer y, String image) {
        super(x, y, image);
        this.radius = (float) Item.BASE_RADIUS;
        this.width = Item.BASE_WIDTH;
        this.height = Item.BASE_HEIGHT;
    }

    @Override
    public void initInWorld() {
        BodyDef bd = new BodyDef();
        //圆形刚体位置：圆心
        bd.position = new Vec2(x+radius,y+radius);
        bd.type = BodyType.STATIC; // 不可运动的
        FixtureDef fd = new FixtureDef();
        CircleShape cs = new CircleShape();
        cs.m_radius = radius;
        fd.shape = cs;
        // 创建刚体
        body = Common.world.createBody(bd);
        body.createFixture(fd);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.rotate(Math.toRadians(theta),x+radius,y+radius);
        g2d.drawImage(image, x, y, width, height,null);
    }

    @Override
    public void enlarge(){
        scale += 1;
        radius = (float)Item.BASE_RADIUS*scale;
        width = Item.BASE_WIDTH * scale;
        height = Item.BASE_HEIGHT * scale;
        System.out.println("radius++"+radius);
        setSize(width, height);
    }

    @Override
    public void reduce(){
        if (scale > 1){
            scale -= 1;
            radius = (float)Item.BASE_RADIUS * scale;
            width = Item.BASE_WIDTH * scale;
            height = Item.BASE_HEIGHT * scale;
        }
        setSize(width, height);
    }

}
