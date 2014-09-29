package net.geocat.example1;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Demonstrates how components can span multiple columns and rows.
 *
 * @author  Karsten Lentzsch
 * @version $Revision: 1.12 $
 */
public class SpanExample {
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.jgoodies.plaf.plastic.PlasticXPLookAndFeel");
        } catch (Exception e) {
            // Likely PlasticXP is not in the class path; ignore.
        }
        JFrame frame = new JFrame();
        frame.setTitle("Forms Tutorial :: Span");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JComponent panel = new SpanExample().buildPanel();
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }


    /**
     * Builds and returns a tabbed pane with tabs for the column span example
     * and the row span example.
     * 
     * @return a tabbed pane that shows horizontal and vertical spans 
     */
    public JComponent buildPanel() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.putClientProperty("jgoodies.noContentBorder", Boolean.TRUE);

        tabbedPane.add("Column Span", buildColumnSpanExample());
        tabbedPane.add("Row Span",    buildRowSpanExample());
        return tabbedPane;
    }
    
    
    /**
     * Builds and returns a panel where a component spans multiple columns.
     * 
     * @return a panel with a component that spans multiple columns
     */
    private JComponent buildColumnSpanExample() {
        FormLayout layout = new FormLayout(
            "pref, 8px, 100px, 4px, 200px",   
            "pref, 6px, pref, 6px, pref, 6px, pref"); 
            
        JPanel panel = new JPanel(layout);
        panel.setBorder(Borders.DIALOG_BORDER);
        CellConstraints cc = new CellConstraints();

        panel.add(new JLabel("Name:"),     cc.xy (1, 1));
        panel.add(new JTextField(),        cc.xyw(3, 1, 3));
        
        panel.add(new JLabel("Phone:"),    cc.xy (1, 3));
        panel.add(new JTextField(),        cc.xyw(3, 3, 3));
        
        panel.add(new JLabel("ZIP/City:"), cc.xy (1, 5));
        panel.add(new JTextField(),        cc.xy (3, 5));
        panel.add(new JTextField(),        cc.xy (5, 5));
        
        panel.add(new JLabel("Country:"),  cc.xy (1, 7));
        panel.add(new JTextField(),        cc.xyw(3, 7, 3));
        
        return panel;
    }
    
    
    /**
     * Builds and returns a panel where a component spans multiple rows.<p>
     * 
     * This demo method is about layout. The default FocusTraversalPolicy
     * will lead to a poor focus traversal order: name, notes, phone, fax;
     * where the order should be: name, phone, fax, notes.
     * The components are added in the same order as they shall be
     * traversed by the focus. 
     * Hence, if you set a ContainerOrderFocusTraversalPolicy,
     * the focus will traverse the fields in the appropriate order.
     * 
     * @return a panel with a component that spans multiple rows
     */
    private JComponent buildRowSpanExample() {
        FormLayout layout = new FormLayout(
            "200px, 25px, 200px",   
            "pref, 2px, pref, 9px, " +
            "pref, 2px, pref, 9px, " +
            "pref, 2px, pref"); 
            
        JPanel panel = new JPanel(layout);
        panel.setBorder(Borders.DIALOG_BORDER);
        CellConstraints cc = new CellConstraints();

        Component addressArea = new JScrollPane(new JTextArea());

        panel.add(new JLabel("Name"),     cc.xy  (1,  1));
        panel.add(new JTextField(),       cc.xy  (1,  3));
        
        panel.add(new JLabel("Phone"),    cc.xy  (1,  5));
        panel.add(new JTextField(),       cc.xy  (1,  7));
        
        panel.add(new JLabel("Fax"),      cc.xy  (1,  9));
        panel.add(new JTextField(),       cc.xy  (1, 11));
        
        panel.add(new JLabel("Notes"),    cc.xy  (3, 1));
        panel.add(addressArea,            cc.xywh(3, 3, 1, 9));
        
        return panel;
    }
    
    
}