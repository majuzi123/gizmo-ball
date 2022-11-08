package Handler;

import Item.Item;
import View.GameFrame;
import View.GamePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OperationHandler implements ActionListener {
    private GameFrame gameFrame;

    public OperationHandler(GameFrame gameFrame){
        this.gameFrame=gameFrame;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        GamePanel gamePanel = gameFrame.getGamePane();
        Item item = gamePanel.getCurItem();
        if(item!=null){ // 获取在Board中选中的item进行修改
            switch (e.getActionCommand()){
                case "rotate":
                    item.rotate();
                    System.out.println("旋转item");
                    break;
                case "delete":
                    gamePanel.remove(item);
                    System.out.println("移除item");
                    break;
                case "plus":
                    item.enlarge();
                    System.out.println("放大item");
                    break;
                case "minus":
                    item.reduce();
                    System.out.println("缩减item");
                    break;
            }
            gamePanel.repaint();
        }
    }
}
