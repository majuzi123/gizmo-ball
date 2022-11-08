package Controller;

import Handler.FileHandler;
import Handler.ItemHandler;
import Handler.ModeHandler;
import Handler.OperationHandler;
import Item.Item;
import View.*;
import Util.IconUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

/**
 * 顶层Frame类，包含所有的Panel
 */
public class GizmoBall extends JFrame {
    IconUtil kv = new IconUtil();
    private GamePanel gamePanel;
    private FileHandler fileHandler;
    private FilePanel filePanel;
    private JPanel rightPanel;
    private ItemHandler itemHandler;
    private ItemPanel itemPanel;
    private OperationHandler operationHandler;
    private OperationPanel operationPanel;
    private ModeHandler modeHandler;
    private ModePanel modePanel;

    // 静态代码块，初始化加载图片
    {
        try {
            kv.load(this.getClass().getClassLoader().getResourceAsStream("properties/item.properties"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public GizmoBall(){
        // 初始化GameFrame顶层窗口
        setTitle("Lab03-弹球游戏");
        setLayout(new FlowLayout());
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        setSize(750, 500);

        // 初始化菜单
        fileHandler = new FileHandler(this);
        filePanel = new FilePanel(fileHandler);
        setJMenuBar(filePanel);

        // 初始化游戏面板
        setGamePane(new GamePanel());

        // 初始化右侧工具面板
        rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(160, 500));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        itemHandler = new ItemHandler(this); // 组件栏
        itemPanel = new ItemPanel(itemHandler);

        operationHandler = new OperationHandler(this); // 操作栏
        operationPanel = new OperationPanel(operationHandler);

        modeHandler = new ModeHandler(this); // 模式栏
        modePanel = new ModePanel(modeHandler);

        rightPanel.add(itemPanel);
        rightPanel.add(operationPanel);
        rightPanel.add(modePanel);

        setBackground(new Color(247, 247, 243, 255));
        add(rightPanel);
        pack();
        setVisible(true);
    }

    public void setNextItemName(String nextItemName) {
        gamePanel.setItemType(nextItemName);
    }

    public GamePanel getGamePane() {
        return gamePanel;
    }

    /**
     * 加载GamePane布局
     */
    public void setGamePane(GamePanel gamePanel) {
        if(this.gamePanel !=null){
            for (Component c:this.gamePanel.getComponents()) {
                gamePanel.remove(c);
            }
            remove(this.gamePanel);
        }
        this.gamePanel = gamePanel;
        add(gamePanel, 0);
    }

    /**
     * 将GamePane写入文件
     */
    public void saveGamePane(File file) {
        try {
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(gamePanel);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException exception) {
            System.err.println("IO异常");
            exception.printStackTrace();
        }
    }

    /**
     * 从文件中重新加载GamePane
     */
    public void loadGamePane(File file) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
            setGamePane((GamePanel)objectInputStream.readObject());
            // Image不能序列化，因此重新加载GamePane时也要重新加载每个item的Image
            for(int i = 0; i< gamePanel.getComponentCount(); i++){
                Item item = ((Item) gamePanel.getComponent(i));
                String imageUrl = item.getImageUrl();
                item.setImage(kv.getImageIcon(imageUrl).getImage());
            }
            gamePanel.repaint();
            objectInputStream.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
