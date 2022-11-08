package Handler;

import Controller.GizmoBall;
import View.GamePanel;
import View.ModePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModeHandler implements ActionListener {
    private GizmoBall gizmoBall;

    public ModeHandler(GizmoBall gizmoBall){
        this.gizmoBall = gizmoBall;
    }

    public void actionPerformed(ActionEvent e) {
        GamePanel gamePanel = gizmoBall.getGamePane();
        if(e.getActionCommand()=="Build") {
            // 停止GamePane线程
            ModePanel.button2.setEnabled(true);
            ModePanel.button1.setEnabled(false);
            gamePanel.stop();
            // 恢复添加item的监听器
            gamePanel.addMouseListener(gamePanel.getMyMouseListener());
        }
        else if(e.getActionCommand()=="Play"){
            ModePanel.button1.setEnabled(true);
            ModePanel.button2.setEnabled(false);
            // 设置gamePane线程的循环标志位
            gamePanel.begin();
            // 设置gamePane能够获取键盘输入
            gamePanel.requestFocus();
            // 移除gamePane的鼠标监听（防止在游玩过程中添加item）
            gamePanel.removeMouseListener(gamePanel.getMyMouseListener());
            new Thread(gamePanel).start();
        }
    }

}
