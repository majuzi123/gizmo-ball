package Item;

import Util.ItemType;
import Util.VirtualWorld;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import java.awt.*;

/**
 * 弯轨
 */
public class Curve extends Item {
    float worldX, worldY;
    int count = 3; //顶点数量
    int h = Item.BASE_HEIGHT ; //三角形边长

    public Curve(Integer x, Integer y, String image){
        super(x,y,image);
        this.width = Item.BASE_WIDTH;
        this.height = Item.BASE_HEIGHT;
        this.worldX = (float) super.x/2;
        this.worldY = (float) super.y/2;
    }

    @Override
    public void initInWorld() { // 创建刚体
        BodyDef bd = new BodyDef();
        bd.type = BodyType.STATIC;
        bd.userData = ItemType.Curve;
        bd.position = new Vec2(worldX,worldY);
        FixtureDef fd = new FixtureDef();
        PolygonShape ps = new PolygonShape();
        fd.shape = ps;
        fd.density = 0f; //密度

        // 根据theta参数修改刚体
        if(theta == 0){
            ps.set(new Vec2[] {new Vec2(worldX,worldY+(float)h/4),
                    new Vec2(worldX+(float)h/2,worldY+h),
                    new Vec2(worldX,worldY+h) }, count); // 传入三角形的三个顶点（顺时针），count表示顶点的数量
        }else if(theta == 90){
            ps.set(new Vec2[] {new Vec2(worldX,worldY),
                    new Vec2(worldX+3*(float)h/4,worldY),
                    new Vec2(worldX,worldY+(float)h/2) }, count);
        }else if(theta == 180){
            ps.set(new Vec2[] {new Vec2(worldX+(float)h/2,worldY),
                    new Vec2(worldX+h,worldY),
                    new Vec2(worldX+h,worldY+3*(float)h/4) }, count);
        }else if(theta == 270){
            ps.set(new Vec2[] {new Vec2(worldX+h,worldY+(float)h/4),
                    new Vec2(worldX+h,worldY+h),
                    new Vec2(worldX+(float)h/2,worldY+h) }, count);
        }

        body = VirtualWorld.world.createBody(bd);
        body.createFixture(fd);
    }


    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.rotate(Math.toRadians(theta),x + (float)h/2,y + (float)h/2);
        g2d.drawImage(image, x, y, width, height,null);

    }

    @Override
    public void enlarge(){
        scale += 1;
        width = Item.BASE_WIDTH * scale;
        height = BASE_HEIGHT * scale;
        h = height;
    }

    @Override
    public void reduce(){
        if (scale > 1){
            scale -= 1;
            width = Item.BASE_WIDTH * scale;
            height = BASE_HEIGHT * scale;
            h = height;
        }
    }
}
