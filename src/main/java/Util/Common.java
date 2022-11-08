package Util;

import Item.BlackHole;
import Item.Item;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;
import View.BoardPanel;

import java.awt.*;

/**
 * 物理引擎控制类
 */
public class Common {
    private BoardPanel boardPanel;
    public static World world = new World(new Vec2(0f,10f)); // 设置重力，模拟“世界”
    public static final int WIDTH = 500;
    public static final int HIGHT = 500;
    public static final float TIME_STEP = 1f/30f;

    public Common(BoardPanel boardPanel) {
        this.boardPanel = boardPanel;

        world.setContactListener(new ContactListener() { // 设置碰撞监听，用于黑洞吸收
            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();
                Vec2 position1 = fixtureA.getBody().getPosition();
                Vec2 position2 = fixtureB.getBody().getPosition();
                Component component1 = boardPanel.getComponentAt((int)position1.x, (int)position1.y);
                Component component2 = boardPanel.getComponentAt((int)position2.x, (int)position2.y);
                // 判断碰撞双方中是否有黑洞（碰撞另一方必为小球）
                if(component1 instanceof BlackHole || component2 instanceof BlackHole){
                    Fixture ballFixture = fixtureA.getBody().getType()==BodyType.DYNAMIC?fixtureA:fixtureB;
                    // 遍历BoardPanel的组件，通过判断组件的物体实体（Body）和发生碰撞的小球物理实体是否相同，找到小球组件将其删除
                    for(Component component: boardPanel.getComponents()){
                        Item item = (Item) component;
                        if(item.getBody().hashCode()==ballFixture.getBody().hashCode()){
                            ballFixture.setSensor(true);
                            item.destroyInWorld(); // 移除与黑洞碰撞的小球
                            boardPanel.remove(component);
                            System.out.println("被黑洞吸收！");
                        }
                    }
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold manifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse contactImpulse) {
            }
        });
    }

    /**
     * 物理世界向前推进
     */
    public static void step(){
        world.step(TIME_STEP,6,6);
    }

    /**
     * 使用刚体设置世界边界产生碰撞效果
     */
    public static void updateBounds(int height, int width){
        // 创建静态刚体
        BodyDef bodyDef = new BodyDef();

        // 确定左右两侧侧刚体的位置和形状，通过世界创建刚体并赋予刚体属性
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        shape.setAsBox(1, height);
        fixtureDef.shape = shape;
        bodyDef.position.set(-1, 0);
        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        bodyDef.position.set(width + 1, 0);
        world.createBody(bodyDef).createFixture(fixtureDef);

        // 确定上下两侧侧刚体的位置和形状，通过世界创建刚体并赋予刚体属性
        shape.setAsBox(width, 1);
        fixtureDef.shape = shape;
        bodyDef.position.set(0, -1);
        world.createBody(bodyDef).createFixture(fixtureDef);
        bodyDef.position.set(0, height + 1);
        world.createBody(bodyDef).createFixture(fixtureDef);
    }

}

