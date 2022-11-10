package Handler;

import Controller.GizmoBall;
import Item.Item;
import Util.IconUtil;
import View.BoardPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class FileHandler implements ActionListener {
    private GizmoBall gizmoBall;
    private IconUtil kv = new IconUtil();
    public FileHandler(GizmoBall gizmoBall){
        this.gizmoBall = gizmoBall;
    }

    {
        try { // 加载图像
            kv.load(this.getClass().getClassLoader().getResourceAsStream("properties/item.properties"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    public void actionPerformed(ActionEvent e) {
        String menuItemName = e.getActionCommand();
        // 处理不同的menu事件
        switch (menuItemName){
            case "new":
                newGame();
                break;
            case "save":
                saveGame();
                break;
            case "load":
                loadGame();
                break;
        }
    }
    public  void newGame() {
        BoardPanel boardPanel = gizmoBall.getBoardPanel();
        boardPanel.stop();
        // 删除之前的所有组件
        for (Component c: boardPanel.getComponents())
            boardPanel.remove(c);
        // 需要手动立即更新UI，否则删除的组件仍会显示
        boardPanel.updateUI();
    }

    public  void saveGame(){
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(".")); // 打开当前文件所在目录·
        int option = chooser.showSaveDialog(null);
        // 文件保存逻辑
        if(option==JFileChooser.APPROVE_OPTION){	// 假如用户选择了保存
            File file = chooser.getSelectedFile();
            String fName = chooser.getName(file);	// 从文件名输入框中获取文件名
            if(!fName.contains(".gizmo")){
                file = new File(chooser.getCurrentDirectory(),fName+".gizmo");
                System.out.println("renamed");
                System.out.println(file.getName());
            }
            saveBoardPanel(file);
            System.out.println("保存文件成功");
        }
    }

    public  void loadGame(){
        JFileChooser chooser = new JFileChooser(); // 设置选择器
        chooser.setCurrentDirectory(new File(".")); // 打开当前文件所在目录·
        chooser.setMultiSelectionEnabled(false); // 设为单选
        int returnVal = chooser.showOpenDialog(gizmoBall); // 判断是否打开文件选择框
        if (returnVal == JFileChooser.APPROVE_OPTION) { // 如果符合文件类型
            File file = chooser.getSelectedFile();
            loadBoardPanel(file);
            System.out.println("加载文件成功");
        }
        else{
            System.out.println("打开了错误的文件类型");
        }
    }

    public void saveBoardPanel(File file) {
        try {
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(gizmoBall.getBoardPanel());
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException exception) {
            System.err.println("IO异常");
            exception.printStackTrace();
        }
    }

    public void loadBoardPanel(File file) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
            gizmoBall.setBoardPanel((BoardPanel)objectInputStream.readObject());
            // Image不能序列化，因此重新加载BoardPanel时也要重新加载每个item的Image
            for(int i = 0; i< gizmoBall.getBoardPanel().getComponentCount(); i++){
                Item item = ((Item) gizmoBall.getBoardPanel().getComponent(i));
                String imageUrl = item.getImageUrl();
                item.setImage(kv.getImageIcon(imageUrl).getImage());
            }
            gizmoBall.getBoardPanel().repaint();
            objectInputStream.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
