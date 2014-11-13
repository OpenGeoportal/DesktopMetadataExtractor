package net.geocat.mdextractor.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import tufts_metadata_generator.start_0_1.Start;

public class MainPanel extends JPanel {

	private static final long serialVersionUID = -2005927078107969949L;
	private JTextField textField;
	private JFileChooser inputChooser;
	private File inputDir;
	private File outputDir;
	private String[][] retValue = null;
	private JTextField outputTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame frame = new JFrame("Test metadata generator");
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setBounds(100, 100, 600, 400);
					frame.getContentPane().add(new MainPanel(),
							BorderLayout.CENTER);

					// Display the window
					// frame.pack();
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
	public MainPanel() {
		super(new GridLayout(1, 1));
		JTabbedPane tabbedPane = new JTabbedPane();
		JComponent panel1 = makePanelInput();
		tabbedPane.addTab("Input", panel1);
		add(tabbedPane);
		// The following line enables to use scrolling tabs.
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}

	private JComponent makePanelInput() {
		final JPanel panel = new JPanel(true);
		final JButton generateMetadataOutput = new JButton("Generate metadata");
		generateMetadataOutput.setEnabled(false);
		panel.setLayout(null);

		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(94, 6, 284, 20);
		panel.add(textField);
		textField.setColumns(10);

		JLabel lblInputDir = new JLabel("Input dir");
		lblInputDir.setBounds(10, 9, 74, 14);
		panel.add(lblInputDir);

		JButton btnSelectDirectory = new JButton("Select directory...");
		btnSelectDirectory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int returnVal = inputChooser.showOpenDialog(panel);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					inputDir = inputChooser.getSelectedFile();
					textField.setText(inputDir.getAbsolutePath());
					generateMetadataOutput.setEnabled(true);
				} else {
					inputDir = null;
					textField.setText(null);
					generateMetadataOutput.setEnabled(false);

				}

			}
		});
		btnSelectDirectory.setBounds(388, 5, 147, 23);
		panel.add(btnSelectDirectory);

		generateMetadataOutput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Thread worker = new Thread() {
					@Override
					public void run() {
						try {
							Start talendJob = new Start();
							List<String> argumentsList = new ArrayList<String>();
							argumentsList.add("--context_param dataDir="
									+ inputDir.getAbsolutePath().replace("\\",
											"/"));
							if (outputDir != null) {
								argumentsList
										.add("--context_param metadataDir="
												+ outputDir.getAbsolutePath()
														.replace("\\", "/"));
							}
							retValue = talendJob.runJob(argumentsList
									.toArray(new String[] {}));
						} catch (Exception e) {
							System.out.println("Exception: " + e.getMessage());
							e.printStackTrace();
						}

						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								String message;
								if (retValue != null
										&& retValue[0][0].equals("0")) {
									message = "Metadata generation finished successfully";
								} else if (retValue != null) {
									message = "There was an error while generating metadata. Exit code "
											+ retValue[0][0];
								} else {
									message = "An exception has occurred while generating metadata";
								}

								JOptionPane.showMessageDialog(panel, message);
								generateMetadataOutput.setEnabled(true);

							}
						});
					}
				};
				worker.start();

			}
		});
		generateMetadataOutput.setBounds(388, 238, 147, 23);
		panel.add(generateMetadataOutput);

		JLabel lblOutputDir = new JLabel("Output dir");
		lblOutputDir.setBounds(10, 34, 74, 14);
		panel.add(lblOutputDir);

		outputTextField = new JTextField();
		outputTextField.setEditable(false);
		outputTextField.setBounds(94, 31, 284, 20);
		panel.add(outputTextField);
		outputTextField.setColumns(10);

		JButton btnNewButton = new JButton("Select directory...");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int returnVal = inputChooser.showOpenDialog(panel);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					outputDir = inputChooser.getSelectedFile();
					outputTextField.setText(outputDir.getAbsolutePath());
					;
				} else {
					outputDir = null;
					outputTextField.setText(null);
				}
			}
		});
		btnNewButton.setBounds(388, 30, 147, 23);
		panel.add(btnNewButton);

		inputChooser = new JFileChooser();
		inputChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		return panel;
	}
}
