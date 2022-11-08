package Item;

import org.jbox2d.collision.shapes.PolygonShape;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import util.Common;

import java.awt.*;

public class Triangle extends Item {
    float worldX, worldY; // 坐标/2
    int count = 3; // vertex number
    int h = Item.BASE_HEIGHT ; // h表示三角形边长

    public Triangle(Integer x, Integer y, String image){
        super(x,y,image);
        this.width = Item.BASE_WIDTH;
        this.height = Item.BASE_HEIGHT;

        //worldX, worldY 表示三角形刚体位置，用左上角横、纵坐标分别除以2
        this.worldX = (float) super.x/2;
        this.worldY = (float) super.y/2;
    }

    @Override
    public void initInWorld() {
        BodyDef bd = new BodyDef();
        bd.type = BodyType.STATIC; // 不可运动的
        bd.position = new Vec2(worldX, worldY);//设置三角形刚体位置

        // 刚体内部属性
        FixtureDef fd = new FixtureDef();
        PolygonShape ps = new PolygonShape();

        // 根据旋转角theta改变刚体的形状和位置
        if(theta == 0){
            ps.set(new Vec2[] {new Vec2(worldX,worldY),
                    new Vec2(worldX+h,worldY+h),
                    new Vec2(worldX,worldY+h) }, count);
            // 传入顶点序列中三角形坐标顶点（顺时针），count表示顶点的数量
        }else if(theta == 90){
            ps.set(new Vec2[] {new Vec2(worldX,worldY),
                    new Vec2(worldX+h,worldY),
                    new Vec2(worldX,worldY+h) }, count);
            // 传入顶点序列中三角形坐标顶点（顺时针），count表示顶点的数量
        }else if(theta == 180){
            ps.set(new Vec2[] {new Vec2(worldX,worldY),
                    new Vec2(worldX+h,worldY),
                    new Vec2(worldX+h,worldY+h) }, count);
            // 传入顶点序列中三角形坐标顶点（顺时针），count表示顶点的数量
        }else{
            ps.set(new Vec2[] {new Vec2(worldX+h,worldY),
                    new Vec2(worldX+h,worldY+h),
                    new Vec2(worldX,worldY+h) }, count);
            // 传入顶点序列中三角形坐标顶点（顺时针），count表示顶点的数量
        }

        fd.shape = ps;
        fd.density = 0f; // 密度
        body = Common.world.createBody(bd);
        body.createFixture(fd);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g.create();
        //三角形图片的旋转：以三角形垂心为旋转中心
        g2d.rotate(Math.toRadians(theta),x + (float)h/2,y + (float)h/2);
        g2d.drawImage(image, x, y, width,height,null);
    }

    @Override
    public void enlarge(){
        scale += 1;
        width = Item.BASE_WIDTH * scale;
        height = Item.BASE_HEIGHT * scale;
        h = height;
        setSize(width, height);
    }

    @Override
    public void reduce(){
        if (scale > 1){
            scale -= 1;
            width = Item.BASE_WIDTH * scale;
            height = Item.BASE_HEIGHT * scale;
            h = height;
        }
        setSize(width, height);
    }
}
