package View;
import Handler.FileHandler;
import Handler.ItemHandler;
import Handler.ModeHandler;
import Handler.OperationHandler;
import Item.Item;
import util.IconUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

/**
 * 顶层Frame类，包含所有的Panel
 */
public class GameFrame extends JFrame {
    IconUtil kv = new IconUtil();
    private GamePanel gamePanel;

    // 静态代码块，初始化加载图片
    {
        try {
            kv.load(this.getClass().getClassLoader().getResourceAsStream("properties/item.properties"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public GameFrame(){
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
        FileHandler fileHandler = new FileHandler(this);
        FilePanel filePanel = new FilePanel(fileHandler);
        setJMenuBar(filePanel);

        // 初始化游戏面板
        setGamePane(new GamePanel());

        // 初始化右侧工具面板
        JPanel rightPane = new JPanel();
        rightPane.setPreferredSize(new Dimension(160, 500));
        rightPane.setLayout(new BoxLayout(rightPane, BoxLayout.Y_AXIS));

        ItemHandler itemHandler = new ItemHandler(this); // 组件栏
        ItemPanel itemPanel = new ItemPanel(itemHandler);

        OperationHandler operationHandler = new OperationHandler(this); // 操作栏
        OperationPanel operationPanel = new OperationPanel(operationHandler);

        ModeHandler modeHandler = new ModeHandler(this); // 模式栏
        ModePanel modePanel = new ModePanel(modeHandler);

        rightPane.add(itemPanel);
        rightPane.add(operationPanel);
        rightPane.add(modePanel);

        setBackground(new Color(247, 247, 243, 255));
        add(rightPane);
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
