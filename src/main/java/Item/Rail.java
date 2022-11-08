package Item;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import Util.Common;

import java.awt.*;

/**
 * 直轨
 */
public class Rail extends Item{
    float hw,hh;
    /**
     * 直道的实现：通过两个细长的矩形模拟管道。因此分别创建两个刚体rail1 & rail2
     */
    Body rail1;
    Body rail2;

    public Rail(Integer x, Integer y, String image){
        super(x,y,image);
        this.width = Item.BASE_WIDTH;
        this.height = Item.BASE_HEIGHT;
    }

    @Override
    public void initInWorld() {
        BodyDef bd1 = new BodyDef();
        BodyDef bd2 = new BodyDef();
        //将管道看作两条平行的细长矩形
        hw = (float) 1/100;
        hh = (float) height /2 - 5;

        FixtureDef fd1 = new FixtureDef();
        FixtureDef fd2 = new FixtureDef();

        PolygonShape ps1 = new PolygonShape();
        PolygonShape ps2 = new PolygonShape();

        if(theta == 0 || theta == 180 ){ // 如果管道是竖的
            bd1.position = new Vec2(x - hw ,y + hh);
            ps1.setAsBox(hw, hh);
            bd2.position = new Vec2(x + width + hw ,y + hh);
            ps2.setAsBox(hw,hh);
        }else if(theta == 90 || theta == 270){ // 如果管道是横的
            bd2.position = new Vec2(x + hh, y + hw + height);
            ps2.setAsBox((float)height/2,hw);
        }

        fd1.shape = ps1;
        fd2.shape = ps2;

        rail1 = Common.world.createBody(bd1);
        rail1.createFixture(fd1);

        rail2 = Common.world.createBody(bd2);
        rail2.createFixture(fd2);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.rotate(Math.toRadians(theta),x + width /2,y + height /2);
        g2d.drawImage(image, x, y, width, height,null);
    }

    @Override
    public void enlarge(){
        scale += 1;
        width = Item.BASE_WIDTH * scale;
        height = Item.BASE_HEIGHT * scale;
    }

    @Override
    public void reduce(){
        if (scale > 1){
            scale -= 1;
            width = Item.BASE_WIDTH * scale;
            height = Item.BASE_HEIGHT * scale;
        }
    }

    //回到布局模式时要分别删除原来创建的刚体
    @Override
    public void destroyInWorld(){
        Common.world.destroyBody(rail1);
        Common.world.destroyBody(rail2);
    }

    @Override
    public Body getBody() {
        return rail1;
    }
}
