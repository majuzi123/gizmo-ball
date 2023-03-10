package Util;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * 工具类，用来获取Item对应的图标
 */
public class IconUtil extends Properties {
        private final LinkedHashSet<Object> keys = new LinkedHashSet<>();

        @Override
        public Enumeration<Object> keys() {
            return Collections.enumeration(keys);
        }

        @Override
        public Object put(Object key, Object value) {
            keys.add(key);
            return super.put(key, value);
        }

        @Override
        public Set<String> stringPropertyNames() {
            Set<String> set = new LinkedHashSet<>();
            for (Object key : this.keys) {
                set.add((String) key);
            }
            return set;
        }

        @Override
        public Set<Object> keySet() {
            return keys;
        }

        @Override
        public Enumeration<?> propertyNames() {
            return Collections.enumeration(keys);
        }

    /**
     * 通过iconName获取对应的ImageIcon
     */
    public ImageIcon getImageIcon(String iconName){
            ImageIcon icon = new ImageIcon(getProperty(iconName));
            icon.setImage(icon.getImage().getScaledInstance(30, 30,
                    Image.SCALE_AREA_AVERAGING));
            return icon;
        }
}
