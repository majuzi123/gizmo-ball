package View;

import Item.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Constructor;

import Item.*;
import Util.Common;
import Util.IconUtil;

/**
 * GamePane类，用于摆放组件和进行游戏
 */
public class GamePanel extends JPanel implements Runnable,Serializable{

    IconUtil kv = new IconUtil();
    // 左右挡板
    private LeftSlide lSlide;
    private RightSlide rSlide;
    private volatile Boolean stop = false;// 标志位，控制线程执行
    private final MyMouseListener myMouseListener = new MyMouseListener();
    private transient String itemType;// 用于添加下一个组件
    private transient Item curItem;// 指向当前选择的组件

    {
        try { // 添加Item时使用
            kv.load(this.getClass().getClassLoader().getResourceAsStream("properties/item.properties"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public GamePanel(){
        addMouseListener(myMouseListener);
        addKeyListener(new MyKeyListener());
        setPreferredSize(new Dimension(500, 500));
        setVisible(true);
        new Common(this);
        // 标示边界
        Common.updateBounds(500,500);
    }

    public void add(Item item){
        setCurItem(item);
        super.add(item);
    }

    public Item getCurItem() {
        return curItem;
    }

    public void setCurItem(Item curItem) {
        this.curItem = curItem;
    }

    public MyMouseListener getMyMouseListener() {
        return myMouseListener;
    }

    public LeftSlide getLSlide() {
        return lSlide;
    }

    public void setLSlide(LeftSlide lSlide) {
        this.lSlide = lSlide;
    }

    public RightSlide getRSlide() {
        return rSlide;
    }

    public void setRSlide(RightSlide rSlide) {
        this.rSlide = rSlide;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        // 绘制格子
        g2.setColor(new Color(235, 241, 241, 255));
        g2.fill3DRect(0, 0, 500, 500, true);
        g2.setColor(new Color(0, 0, 0, 255));
        g2.setStroke(new BasicStroke(1));
        for(int i = 1;i < 20;i ++) {
            g2.drawLine(0,25*i,500,25*i );
        }
        for(int i = 1;i < 20;i ++) {
            g2.drawLine(25*i,0,25*i,500);
        }
        // 绘制每一个Component
        for(Component i: getComponents()){
            i.paint(g);
        }
    }

    /**
     * 游玩模式，进入线程循环
     */
    @Override
    public void run() {
        try{
            while (!stop){
                Thread.sleep(5);
                this.repaint();
                logic();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 设置线程标志位stop为true，并且删除所有刚体
     */
    public void stop(){
        stop = true;
        destroyAllBody();
    }

    /**
     * 设置线程标志位stop为false，并且初始化所有刚体
     */
    public void begin(){
        stop = false;
        initAllBody();
    }

    private void logic() {
        Common.step();
    }

    public void initAllBody() {
        for(int i = 0; i<getComponentCount(); i++) {
            Item item = (Item)getComponent(i);
            item.initInWorld();
        }
    }

    public void destroyAllBody(){
        for(int i = 0; i<getComponentCount(); i++) {
            Item item = (Item)getComponent(i);
            if(item.getBody()!=null)
                item.destroyInWorld();
        }
    }

    /**
     * 鼠标监听，在GamePane中添加Component
     */
    @SuppressWarnings("unchecked")
    private class MyMouseListener implements MouseListener, Serializable {

        @Override
        public void mouseClicked(MouseEvent e) {
            // 可以获取
            if(MouseEvent.BUTTON1 == e.getButton()){
                GamePanel panel = (GamePanel) e.getSource();
                // 当类型不为箭头时，根据item类型创建并加入
                String itemType = panel.getItemType();
                int x = e.getX();
                int y = e.getY();
                if(itemType!=null){
                    if(!itemType.equals("Click")){
                        try {
                            if(panel.getComponentAt(x, y)==panel){
                                System.out.println("添加新item");
                                Class<Item> onClass = (Class<Item>) Class.forName("Item."+ itemType);
                                Constructor<Item> constructor = onClass.getDeclaredConstructor(Integer.class, Integer.class, String.class);
                                Item item = constructor.newInstance(x, y, itemType);
                                item.setImage(kv.getImageIcon(itemType).getImage());
                                panel.add(item);
                                // 如果加入的是挡板，需要额外设置为GamePane的属性，便于后续处理键盘事件监听
                                if(item instanceof LeftSlide)
                                    setLSlide((LeftSlide)item);
                                if(item instanceof RightSlide)
                                    setRSlide((RightSlide)item);
                            }
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                    else{
                        // 如果button值为click，就判断当前点击的位置上是否有component
                        Component component = panel.getComponentAt(x, y);
                        if(component!=panel){ // 选择要修改的组建
                            System.out.println("选择组件");
                            setCurItem((Item) component);
                        }
                    }
                    panel.repaint();
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    /**
     * 键盘监听，用于控制左右挡板
     */
    private class MyKeyListener implements KeyListener, Serializable {
        @Override
        public void keyTyped(KeyEvent e) {

        }
        @Override
        public void keyPressed(KeyEvent e) {
            GamePanel panel = (GamePanel)e.getSource();
            LeftSlide leftSlide = null;
            RightSlide rightSlide = null;
            // 获取左右挡板（如果有的话）
            if(panel.getLSlide()!=null)
              leftSlide = panel.getLSlide();
            if(panel.getRSlide()!=null)
                rightSlide = panel.getRSlide();
            // 根据键盘输入修改对应Slide的位置
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(rightSlide!=null){
                        rightSlide.setX(rightSlide.getX()-25);
                        rightSlide.move(-25);
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(rightSlide!=null){
                        rightSlide.setX(rightSlide.getX()+25);
                        rightSlide.move(25);
                    }
                    break;
                case KeyEvent.VK_A:
                    if(leftSlide!=null){
                        leftSlide.setX(leftSlide.getX()-25);
                        leftSlide.move(-25);
                    }
                    break;
                case KeyEvent.VK_D:
                    if(leftSlide!=null){
                        leftSlide.setX(leftSlide.getX()+25);
                        leftSlide.move(25);
                    }
                    break;
            }
        }
        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}
