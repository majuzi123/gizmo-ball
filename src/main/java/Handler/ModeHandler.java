package Handler;

import Controller.GizmoBall;
import View.BoardPanel;
import View.ModePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModeHandler implements ActionListener {
    private GizmoBall gizmoBall;

    public ModeHandler(GizmoBall gizmoBall){
        this.gizmoBall = gizmoBall;
    }

    public void actionPerformed(ActionEvent e) {
        BoardPanel boardPanel = gizmoBall.getGamePanel();
        if(e.getActionCommand()=="Build") {
            boardPanel.stop(); // 停止BoardPanel线程
            ModePanel.button2.setEnabled(true);
            ModePanel.button1.setEnabled(false);
            boardPanel.addMouseListener(boardPanel.getMyMouseListener());
        }
        else if(e.getActionCommand()=="Play"){
            ModePanel.button1.setEnabled(true);
            ModePanel.button2.setEnabled(false);
            boardPanel.begin();
            boardPanel.requestFocus();
            boardPanel.removeMouseListener(boardPanel.getMyMouseListener()); // 移除BoardPanel的鼠标监听（防止在游玩过程中添加item）
            new Thread(boardPanel).start();
        }
    }

}
