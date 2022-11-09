package View;

import Handler.FileHandler;

import javax.swing.*;

/**
 * FilePanel类，用于新建、保存和读取游戏
 */
public class FilePanel extends JMenuBar {
    private final FileHandler fileHandler;
    public FilePanel(FileHandler handler){
        this.fileHandler = handler;
        JMenu menu = new JMenu("文件");
        JMenuItem menuItem1 = new JMenuItem("新建游戏");
        menuItem1.setActionCommand("new");
        JMenuItem menuItem2 = new JMenuItem("保存游戏");
        menuItem2.setActionCommand("save");
        JMenuItem menuItem3 = new JMenuItem("读取游戏");
        menuItem3.setActionCommand("load");

        menuItem1.addActionListener(fileHandler);
        menuItem2.addActionListener(fileHandler);
        menuItem3.addActionListener(fileHandler);
        menu.add(menuItem1);
        menu.add(menuItem2);
        menu.add(menuItem3);
        add(menu);
    }

}
