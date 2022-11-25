package Controller;

import Handler.FileHandler;
import Handler.ModeHandler;
import Handler.OperationHandler;
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
    private GameBoard gameBoard;
    private FileHandler fileHandler;
    private FilePanel filePanel;
    private JPanel rightPanel;
    private ItemPanel itemPanel;
    private ToolPanel toolPanel;
    private OperationHandler operationHandler;
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

        // 加载文件菜单
        fileHandler = new FileHandler(this);
        filePanel = new FilePanel(fileHandler);
        setJMenuBar(filePanel);

        // 加载左侧游戏面板
        setBoardPanel(new GameBoard());

        // 加载右侧工具面板
        setRightPanel();

        setBackground(new Color(247, 247, 243, 255));
        pack();
        setVisible(true);
    }

    /**
     * 加载BoardPanel布局
     */
    public void setBoardPanel(GameBoard gameBoard) {
        if(this.gameBoard !=null){
            for (Component c:this.gameBoard.getComponents()) {
                gameBoard.remove(c);
            }
            remove(this.gameBoard);
        }
        this.gameBoard = gameBoard;
        add(gameBoard, 0);
    }

    /**
     * 加载右侧布局
     */
    public void setRightPanel(){
        rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(160, 500));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        operationHandler = new OperationHandler(this);

        itemPanel = new ItemPanel(operationHandler); // 组件栏

        toolPanel = new ToolPanel(operationHandler); // 工具栏

        modeHandler = new ModeHandler(this); // 模式栏
        modePanel = new ModePanel(modeHandler);

        rightPanel.add(itemPanel);
        rightPanel.add(toolPanel);
        rightPanel.add(modePanel);
        add(rightPanel);
    }

    public GameBoard getBoardPanel() {
        return gameBoard;
    }

}
