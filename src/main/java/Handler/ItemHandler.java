package Handler;

import Controller.GizmoBall;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ItemHandler implements ActionListener {
    private GizmoBall gizmoBall;

    public ItemHandler(GizmoBall gizmoBall){
        this.gizmoBall = gizmoBall;
    }
    public void actionPerformed(ActionEvent e) {
        String itemName = e.getActionCommand();
        try {
            gizmoBall.getGamePanel().setItemType(itemName);// 在BoardPanel中设置想要新建的Item的类别
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
