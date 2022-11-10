package Handler;

import Item.Item;
import Controller.GizmoBall;
import View.BoardPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.Serializable;

public class OperationHandler {
    private GizmoBall gizmoBall;

    public OperationHandler(GizmoBall gizmoBall){
        this.gizmoBall = gizmoBall;
    }

    public ItemHandler itemHandler = new ItemHandler();

    public ToolHandler toolHandler = new ToolHandler();

    private class ItemHandler implements ActionListener, Serializable {
        public void actionPerformed(ActionEvent e) {
            String itemName = e.getActionCommand();
            try {
                gizmoBall.getBoardPanel().setItemType(itemName);// 在BoardPanel中设置想要新建的Item的类别
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private class ToolHandler implements ActionListener, Serializable {
        @Override
        public void actionPerformed(ActionEvent e) {
            BoardPanel boardPanel = gizmoBall.getBoardPanel();
            Item item = boardPanel.getCurItem();
            if(item!=null){ // 获取在Board中选中的item进行修改
                switch (e.getActionCommand()){
                    case "rotate":
                        item.rotate();
                        System.out.println("旋转item");
                        break;
                    case "delete":
                        boardPanel.remove(item);
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
                boardPanel.repaint();
            }
        }
    }

}
