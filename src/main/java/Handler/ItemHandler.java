package Handler;

import View.GameFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ItemHandler implements ActionListener {
    private GameFrame gameFrame;

    public ItemHandler(GameFrame gameFrame){
        this.gameFrame=gameFrame;
    }
    public void actionPerformed(ActionEvent e) {
        String itemName = e.getActionCommand();
        try {
            gameFrame.setNextItemName(itemName); // 在GamePane中设置想要新建的Item的类别
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
