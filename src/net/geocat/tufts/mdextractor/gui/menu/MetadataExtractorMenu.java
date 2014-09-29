package net.geocat.tufts.mdextractor.gui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import net.geocat.tufts.mdextractor.gui.PreferencesDialog;

public class MetadataExtractorMenu extends JMenuBar {
	private static final long serialVersionUID = 6647356239315428741L;
	private PreferencesDialog preferencesFrame;

	public MetadataExtractorMenu() {
		initMenuBar();
	}

	private void initMenuBar() {
		JMenu fileMenu = initFileMenu();
		JMenu configMenu = initConfigMenu();
		JMenu helpMenu = initHelpMenu();

		add(fileMenu);
		add(configMenu);
		add(helpMenu);

	}


	private JMenu initFileMenu() {
		JMenu fileMenu = new JMenu("File");

		fileMenu.setMnemonic(KeyEvent.VK_F);
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.setMnemonic(KeyEvent.VK_E);
		exitItem.setToolTipText("Exit application");
		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}
		});
		exitItem.setIcon(createImageIcon("/img/16x16/system-log-out.png", "Exit"));
		
		fileMenu.add(exitItem);
		return fileMenu;
	}

	private JMenu initConfigMenu() {
		JMenu configMenu = new JMenu("Configuration");
		configMenu.setMnemonic(KeyEvent.VK_C);

		JMenuItem preferencesItem = new JMenuItem("Preferences...");
		preferencesItem.setMnemonic(KeyEvent.VK_P);
		preferencesItem
				.setToolTipText("Set default metadata values and other options");
		preferencesItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				preferencesFrame.setVisible(true);

			}
		});
		preferencesItem.setIcon(createImageIcon("/img/16x16/preferences-system.png", "Preferences"));
		configMenu.add(preferencesItem);

		return configMenu;
	}
	
	
	private JMenu initHelpMenu() {
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		
		JMenuItem helpContentsItem = new JMenuItem("Help contents");
		helpContentsItem.setMnemonic(KeyEvent.VK_H);
		helpContentsItem.setToolTipText("Show help window");
		helpContentsItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Open help
				
			}
		});
		helpContentsItem.setIcon(createImageIcon("/img/16x16/help-browser.png", "Help"));
		
		helpMenu.add(helpContentsItem);
		
		return helpMenu;
	}
	
	/**
    * Creates an ImageIcon if the path is valid.
    * @param String - resource path
    * @param String - description of the file
    */
   protected ImageIcon createImageIcon(String path,
           String description) {
       java.net.URL imgURL = getClass().getResource(path);
       if (imgURL != null) {
           return new ImageIcon(imgURL, description);
       } else {
           System.err.println("Couldn't find file: " + path);
           return null;
       }
   }

	public void setPreferencesFrame(PreferencesDialog preferencesFrame) {
		this.preferencesFrame = preferencesFrame;
		
		
	}
}
