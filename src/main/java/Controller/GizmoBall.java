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
 * 顶层游戏类，创建并管理所有的组件
 */
public class GizmoBall extends JFrame {
    IconUtil kv = new IconUtil();
    private BoardPanel boardPanel;
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
        setTitle("Lab03-弹球游戏");
        setLayout(new FlowLayout());
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        setSize(750, 500);

        // 加载菜单
        fileHandler = new FileHandler(this);
        filePanel = new FilePanel(fileHandler);
        setJMenuBar(filePanel);

        // 加载左侧游戏面板
        setBoardPanel(new BoardPanel());

        // 加载右侧工具面板
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

    public BoardPanel getGamePanel() {
        return boardPanel;
    }
    /**
     * 加载BoardPanel布局
     */
    public void setBoardPanel(BoardPanel boardPanel) {
        if(this.boardPanel !=null){
            for (Component c:this.boardPanel.getComponents()) {
                boardPanel.remove(c);
            }
            remove(this.boardPanel);
        }
        this.boardPanel = boardPanel;
        add(boardPanel, 0);
    }

    /**
     * 将BoardPanel写入文件
     */
    public void saveGamePanel(File file) {
        try {
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(boardPanel);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException exception) {
            System.err.println("IO异常");
            exception.printStackTrace();
        }
    }

    /**
     * 从文件中重新加载BoardPanel
     */
    public void loadGamePane(File file) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
            setBoardPanel((BoardPanel)objectInputStream.readObject());
            // Image不能序列化，因此重新加载BoardPanel时也要重新加载每个item的Image
            for(int i = 0; i< boardPanel.getComponentCount(); i++){
                Item item = ((Item) boardPanel.getComponent(i));
                String imageUrl = item.getImageUrl();
                item.setImage(kv.getImageIcon(imageUrl).getImage());
            }
            boardPanel.repaint();
            objectInputStream.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
