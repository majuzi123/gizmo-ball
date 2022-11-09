package View;

import Handler.ModeHandler;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * ModePane类，用于切换模式
 */
public class ModePanel extends JPanel {
    public static final JButton button1 = new JButton("布局模式");
    public static final JButton button2 = new JButton("游玩模式");
    private final ModeHandler modeHandler;
    public ModePanel(ModeHandler handler) {
        this.modeHandler = handler;
        setBackground(new Color(247, 247, 243, 255));
        setLayout(new FlowLayout());
        Border titleBorder = BorderFactory.createTitledBorder("模式栏");
        setBorder(titleBorder);
        setPreferredSize(new Dimension(100, 125));
        button1.setMargin(new Insets(10,0,10,0));
        button1.setPreferredSize(new Dimension(120, 40));
        button1.setBackground(new Color(247, 247, 243));
        button1.setActionCommand("Build");

        button2.setPreferredSize(new Dimension(120, 40));
        button2.setMargin(new Insets(10,0,10,0));
        button2.setBackground(new Color(247, 247, 243));
        button2.setActionCommand("Play");

        button1.addActionListener(modeHandler);
        button2.addActionListener(modeHandler);

        add(button1);
        add(button2);
    }
    /**
     * ModePane对应的Listener，用于切换游戏模式
     *
     private class ModeActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            Controller.GizmoBall gameFrame = (Controller.GizmoBall) getRootPane().getParent();
            GamePane gamePane = gameFrame.getBoardPanel();
            // 布局模式
            if(e.getSource()==button1) {
                // 停止GamePane线程
                button2.setEnabled(true);
                button1.setEnabled(false);
                gamePane.stop();
                // 恢复添加item的监听器
                gamePane.addMouseListener(gamePane.getMyMouseListener());
            }
            //游戏模式
            else if(e.getSource()==button2){
                button1.setEnabled(true);
                button2.setEnabled(false);
                // 设置gamePane线程的循环标志位
                gamePane.begin();
                // 设置gamePane能够获取键盘输入
                gamePane.requestFocus();
                // 移除gamePane的鼠标监听（防止在游玩过程中添加item）
                gamePane.removeMouseListener(gamePane.getMyMouseListener());
                new Thread(gamePane).start();
            }
        }
    }
     */
}
