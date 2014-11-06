package net.geocat.tufts.mdextractor.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import net.geocat.tufts.mdextractor.gui.menu.MetadataExtractorMenu;

public class MetadataExtractorFrame extends JFrame {

	private static final long serialVersionUID = 3046216649220121596L;
	private PreferencesDialog preferencesFrame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager
							.setLookAndFeel("com.jgoodies.looks.windows.WindowsLookAndFeel");
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
		setBounds(100, 100, 450, 300);

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
