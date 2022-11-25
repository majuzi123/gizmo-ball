package Handler;

import Item.Item;
import Controller.GizmoBall;
import View.GameBoard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

public class OperationHandler {
    private GizmoBall gizmoBall;

    public OperationHandler(GizmoBall gizmoBall){
        this.gizmoBall = gizmoBall;
    }

    public ItemHandler itemHandler = new ItemHandler();

    public ToolHandler toolHandler = new ToolHandler();

    /**
     * 添加组件
     */
    private class ItemHandler implements ActionListener, Serializable {
        public void actionPerformed(ActionEvent e) {
            String itemName = e.getActionCommand();
            try {
                gizmoBall.getBoardPanel().setItemType(itemName);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * 修改GameBoard中的组件
     */
    private class ToolHandler implements ActionListener, Serializable {
        @Override
        public void actionPerformed(ActionEvent e) {
            GameBoard gameBoard = gizmoBall.getBoardPanel();
            Item item = gameBoard.getCurItem();
            if(item!=null){ // 获取在Board中选中的item进行修改
                switch (e.getActionCommand()){
                    case "rotate":
                        item.rotate();
                        System.out.println("旋转item");
                        break;
                    case "delete":
                        gameBoard.remove(item);
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
                gameBoard.repaint();
            }
        }
    }

}
