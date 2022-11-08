package Item;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import Util.Common;

import java.awt.*;

/**
 * 小球
 */
public class Ball extends Item {
    float radius; // 半径

    public Ball(Integer x, Integer y, String image) {
        super(x, y, image);
        this.radius = (float) Item.BASE_RADIUS;
        this.width = Item.BASE_WIDTH;
        this.height = Item.BASE_HEIGHT;
    }

    @Override
    public void initInWorld(){
        BodyDef bd = new BodyDef();
        //球形刚体位置：质心/圆心
        bd.position = new Vec2(x+radius,y+radius);
        bd.type = BodyType.DYNAMIC;// 可运动的
        // 设置刚体的物理描述，包括类型、形状和大小
        FixtureDef fd = new FixtureDef();
        CircleShape cs = new CircleShape();
        cs.m_radius = radius;
        fd.shape = cs; // 与形状绑定
        fd.restitution = 1f; // 完全弹性碰撞
        fd.friction = 0.5f; // 摩擦系数
        //创建刚体
        body = Common.world.createBody(bd);
        body.createFixture(fd);
    }

    public int getX(){
        int X;
        if(body != null){
            X = (int)(body.getPosition().x - radius);
        }
        else {
            X = x; // x y 记录初始位置， X Y 记录实时位置
        }
        return X;
    }

    public int getY(){
        int Y;
        if(body != null){
            Y = (int)(body.getPosition().y - radius);
        }
        else {
            Y = y;
        }
        return Y;
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.rotate(Math.toRadians(theta),x+radius,y+radius);
        g2d.drawImage(image,getX(), getY(), width, height,null);
        g2d.dispose();
    }

    @Override
    public void enlarge(){
        scale += 1;
        radius = (float)Item.BASE_RADIUS*scale;
        width = Item.BASE_WIDTH * scale;
        height = Item.BASE_HEIGHT * scale;
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
