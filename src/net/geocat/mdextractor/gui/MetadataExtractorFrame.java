package net.geocat.mdextractor.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import net.geocat.mdextractor.gui.menu.MetadataExtractorMenu;

public class MetadataExtractorFrame extends JFrame {

	private static final long serialVersionUID = 3046216649220121596L;
	private PreferencesDialog preferencesFrame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
        String osName=System.getProperty("os.name");
				try {
          if (osName.startsWith("Windows")) {
            UIManager.setLookAndFeel("com.jgoodies.looks.windows.WindowsLookAndFeel");
          } else if (osName.startsWith("Mac")) {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
          } else {
            UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticLookAndFeel");
          }
					MetadataExtractorFrame frame = new MetadataExtractorFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MetadataExtractorFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				MetadataExtractorFrame.class
						.getResource("/img/48x48/OGPLogo.png")));
		setTitle("Metadata extractor");
		preferencesFrame = new PreferencesDialog();
		initUI();

	}

	private void initUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setBounds(100, 100, 600, 400);

		MetadataExtractorMenu menuBar = new MetadataExtractorMenu();
		menuBar.setPreferencesFrame(preferencesFrame);
		setJMenuBar(menuBar);
		JPanel pane = new JPanel();
		pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		pane.setLayout(new BorderLayout(0, 0));
		JPanel metadataExtractorPane = new MetadataExtractorPanel(
				preferencesFrame.getPreferencesBean());
		pane.add(metadataExtractorPane);
		setContentPane(pane);
	}

}
