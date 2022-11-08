package Item;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import Util.Common;
import java.awt.*;

/**
 * 黑洞
 */
public class BlackHole extends Item {
    float radius; // 半径

    public BlackHole (Integer x, Integer y, String image){
        super(x, y, image);
        this.radius = (float) Item.BASE_RADIUS;
        this.width = Item.BASE_WIDTH;
        this.height = Item.BASE_HEIGHT;
    }

    @Override
    public void initInWorld() {
        BodyDef hole = new BodyDef();
        //圆形刚体位置：圆心
        hole.position = new Vec2(x+radius,y+radius);
        hole.type = BodyType.STATIC; // 不可运动的
        // 设置刚体的物理描述，包括类型、形状和大小
        FixtureDef fd = new FixtureDef();
        CircleShape cs = new CircleShape();
        cs.m_radius = radius;
        fd.shape = cs; // 与形状绑定
        fd.restitution = 1f; // 完全弹性碰撞
        //创建刚体
        body = Common.world.createBody(hole);
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
    }

    @Override
    public void reduce(){
        if (scale > 1){
            scale -= 1;
            radius = (float)Item.BASE_RADIUS * scale;
            width = Item.BASE_WIDTH * scale;
            height = Item.BASE_HEIGHT * scale;
        }
    }
}
