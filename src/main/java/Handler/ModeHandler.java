package Handler;

import Controller.GizmoBall;
import View.GameBoard;
import View.ModePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModeHandler implements ActionListener {
    private GizmoBall gizmoBall;

    public ModeHandler(GizmoBall gizmoBall){
        this.gizmoBall = gizmoBall;
    }

    public void actionPerformed(ActionEvent e) {
        GameBoard gameBoard = gizmoBall.getBoardPanel();
        if(e.getActionCommand()=="Build") { // 切换布局模式
            gameBoard.stop();
            ModePanel.button2.setEnabled(true);
            ModePanel.button1.setEnabled(false);
            gameBoard.addMouseListener(gameBoard.getMyMouseListener());
        }
        else if(e.getActionCommand()=="Play"){ // 切换游玩模式
            ModePanel.button1.setEnabled(true);
            ModePanel.button2.setEnabled(false);
            gameBoard.begin();
            gameBoard.requestFocus();
            gameBoard.removeMouseListener(gameBoard.getMyMouseListener());
            new Thread(gameBoard).start();
        }
    }

}
