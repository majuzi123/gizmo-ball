package View;

import Handler.OperationHandler;
import Util.IconUtil;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;
import java.util.*;

/**
 * ItemPanel类，组件栏，陈列组件
 */
public class ItemPanel extends JPanel {
    IconUtil kv = new IconUtil();

    {
        try { // 加载图像
            kv.load(this.getClass().getClassLoader().getResourceAsStream("properties/item.properties"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public ItemPanel(OperationHandler operationHandler) {
        Border titleBorder = BorderFactory.createTitledBorder("组件栏");
        setPreferredSize(new Dimension(100, 250));
        setBackground(new Color(247, 247, 243, 255));

        setBorder(titleBorder);
        setLayout(new GridLayout(5, 2));
            Enumeration<Object> keys = kv.keys();
            // 新建ButtonGroup，用于控制单选
            ButtonGroup group = new ButtonGroup();
            // 遍历枚举，将代表每个组件的JButton和图片添加到ItemPanel和ButtonGroup中
            while (keys.hasMoreElements()) {
                String iconName = keys.nextElement().toString();
                ImageIcon icon = kv.getImageIcon(iconName);
                JLabel label = new JLabel();
                label.setIcon(icon);
                JRadioButton button = new JRadioButton();
                button.setActionCommand(iconName);
                button.setBackground(new Color(247, 247, 243, 255));
                button.setPreferredSize(new Dimension(40, 40));
                button.setMargin(new Insets(5,20,0,5));
                button.addActionListener(operationHandler.itemHandler);
                group.add(button);
                add(button);
                add(label);
            }
    }

}
