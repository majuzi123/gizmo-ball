package Util;

import Item.Item;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;
import View.GameBoard;

import java.awt.*;
import java.io.Serializable;

/**
 * 存在于GameBoard中的模拟世界
 */
public class VirtualWorld implements Serializable {
    private GameBoard gameBoard;
    public static World world = new World(new Vec2(0f,10f)); // 引力
    public static final int WIDTH = 500;
    public static final int HIGHT = 500;
    public static final float TIME_STEP = 1f/30f; // 迭代频率

    public VirtualWorld(GameBoard gameBoard) {
        this.gameBoard = gameBoard;

        /**
         * 负责监听。当小球与其它组件发生碰撞时，触发该方法
         */
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();
                Body body1 = fixtureA.getBody();
                Body body2 = fixtureB.getBody();
                if(body2.getUserData() == ItemType.Ball) {
                    Body b = body1;
                    body1 = body2;
                    body2 = b;
                }
                if (body1.getUserData() == ItemType.Ball && body2.getUserData() == ItemType.BlackHole) { //判断黑洞吸收
                    for(Component component: gameBoard.getComponents()){
                        Item item = (Item) component;
                        if(item.getBody().hashCode()==body1.hashCode()){
                            item.destroyInWorld(); // 移除与黑洞碰撞的小球
                            gameBoard.remove(component);
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
        world.step(TIME_STEP,6,2);
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

