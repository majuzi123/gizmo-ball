package View;

import Handler.OperationHandler;
import Util.IconUtil;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

/**
 * ToolPanel类，工具栏，展示旋转、删除、放大和缩小工具
 */
public class ToolPanel extends JPanel {
    public ToolPanel(OperationHandler operationHandler){
        Border titleBorder = BorderFactory.createTitledBorder("工具栏");
        setBorder(titleBorder);
        setPreferredSize(new Dimension(100, 125));
        setBackground(new Color(247, 247, 243, 255));
        setLayout(new GridLayout(2, 4));
        try {
            Properties kv = new IconUtil();
            kv.load(this.getClass().getClassLoader().getResourceAsStream("properties/operation.properties"));
            Enumeration keys = kv.keys();
            ButtonGroup group=new ButtonGroup();
            while (keys.hasMoreElements()) {
                String iconName = keys.nextElement().toString();
                ImageIcon icon = new ImageIcon(kv.getProperty(iconName));
                icon.setImage(icon.getImage().getScaledInstance(30, 30,
                        Image.SCALE_AREA_AVERAGING));
                JLabel label = new JLabel();
                label.setIcon(icon);
                JRadioButton button = new JRadioButton();
                button.setBackground(new Color(247, 247, 243, 255));
                button.addActionListener(operationHandler.toolHandler);
                button.setActionCommand(iconName);
                button.setPreferredSize(new Dimension(40, 40));
                button.setMargin(new Insets(5,20,0,10));
                group.add(button);
                add(button);
                add(label);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
