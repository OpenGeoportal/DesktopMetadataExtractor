package net.geocat.tufts.mdextractor.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import net.geocat.tufts.mdextractor.gui.menu.MetadataExtractorMenu;

public class MetadataExtractorFrame extends JFrame {

	private static final long serialVersionUID = 3046216649220121596L;
	private JPanel contentPane;
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

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(new MetadataExtractorPanel());

	}

}
