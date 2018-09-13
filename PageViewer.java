import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.Insets;

/**
 * Write a description of class PageViewer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class PageViewer extends CardLayout
    {
        @Override
        public Dimension preferredLayoutSize(Container parent)
        {
            Component current = findCurrentComponent(parent);
            if (current != null)
            {
                Insets insets = parent.getInsets();
                Dimension pref = current.getPreferredSize();
                pref.width += insets.left + insets.right;
                pref.height += insets.top + insets.bottom;
                return pref;
            }
            return super.preferredLayoutSize(parent);
        }

        public Component findCurrentComponent(Container parent)
        {
            for (Component comp : parent.getComponents())
            {
                if (comp.isVisible())
                {
                    return comp;
                }
            }
            return null;
        }
    }