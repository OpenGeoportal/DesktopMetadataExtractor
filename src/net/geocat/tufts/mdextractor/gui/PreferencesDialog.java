package net.geocat.tufts.mdextractor.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.jgoodies.forms.builder.ButtonBarBuilder2;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.JPanel;
import javax.swing.text.DefaultFormatterFactory;

import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JLabel;

import java.awt.Font;
import java.awt.FlowLayout;

import javax.swing.JCheckBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class PreferencesDialog extends JDialog {

	private static final long serialVersionUID = -3274785125608529455L;
	private JTextField nameTextField;
	private JTextField organisationTextField;
	private JTextField txtEmail;
	private JTextField txtDefaultprojection;
	private JTextField txtMinx;
	private JTextField txtMiny;
	private JTextField txtMaxx;
	private JTextField txtMaxy;
	private JLabel lblMinX;
	private JLabel lblMinY;
	private JLabel lblMaxX;
	private JLabel lblMaxY;
	private JPanel buttonPanel;
	private JLabel lblMetadataPath;
	private JTextField txtOutputpath;
	private JButton btnSelectDir;
	private JCheckBox chckbxGenerateinseparatedir;
	private JLabel lblSeparatedir;
	private JFileChooser fChsrOutputDir;

	/**
	 * Create the frame.
	 */
	public PreferencesDialog() {
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModal(true);
		setAlwaysOnTop(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				PreferencesDialog.class
						.getResource("/img/16x16/preferences-system.png")));
		setTitle("Preferences");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 535, 359);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.putClientProperty("jgoodies.noContentBorder", Boolean.TRUE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		tabbedPane.addTab("Metadata defaults", buildMetadataDefaultsPanel());
		tabbedPane.addTab("Output", buildOutputPanel());
		
		ButtonBarBuilder2 buttonBuilder = new ButtonBarBuilder2();
		buttonBuilder.addGlue();
		buttonBuilder.addButton(new JButton("Save"));
		buttonBuilder.addRelatedGap();
		buttonBuilder.addButton(new JButton("Cancel"));
		buttonPanel = new JPanel();
		FlowLayout fl_buttonPanel2 = (FlowLayout) buttonPanel.getLayout();
		fl_buttonPanel2.setAlignment(FlowLayout.RIGHT);

		buttonPanel.add(buttonBuilder.getPanel());
		

		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);

	}


	private JPanel buildMetadataDefaultsPanel() {
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 10, 10, 10) );
		FormLayout layout = new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("right:default"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.BUTTON_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.BUTTON_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.BUTTON_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.BUTTON_COLSPEC,
				FormFactory.GLUE_COLSPEC,
				FormFactory.PREF_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.PREF_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC,
				FormFactory.UNRELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC,
				FormFactory.NARROW_LINE_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC,
				FormFactory.PARAGRAPH_GAP_ROWSPEC,
				FormFactory.GLUE_ROWSPEC,
				RowSpec.decode("bottom:max(17dlu;pref)"),});
		panel.setLayout(layout);

		DefaultFormBuilder builder = new DefaultFormBuilder(layout);

		JComponent metadataSeparator = builder.addSeparator("Metadata contact",
				CC.xyw(1, 1, 12));
		panel.add(metadataSeparator, CC.xyw(1, 1, 12));

		JLabel lblName = new JLabel("Name");
		panel.add(lblName, "1, 3, right, default");

		nameTextField = new JTextField();
		panel.add(nameTextField, "3, 3, 9, 1, fill, default");
		nameTextField.setColumns(10);

		JLabel lblOrganisation = new JLabel("Organisation");
		panel.add(lblOrganisation, "1, 5, right, default");

		organisationTextField = new JTextField();
		panel.add(organisationTextField, "3, 5, 9, 1, fill, default");
		organisationTextField.setColumns(10);

		JLabel lblEmail = new JLabel("E-mail");
		panel.add(lblEmail, "1, 7, right, default");

		txtEmail = new JTextField();
		panel.add(txtEmail, "3, 7, 9, 1, fill, default");
		txtEmail.setColumns(10);

		JComponent datasetSeparator = builder.addSeparator("Dataset",
				CC.xyw(1, 9, 12));
		panel.add(datasetSeparator, CC.xyw(1, 9, 12));

		JLabel lblDefaultProjection = new JLabel("Default projection");
		panel.add(lblDefaultProjection, "1, 11, right, default");

		txtDefaultprojection = new JTextField();
		panel.add(txtDefaultprojection, "3, 11, 3, 1, fill, default");
		txtDefaultprojection.setColumns(10);

		lblMinX = new JLabel("Min X");
		panel.add(lblMinX, "3, 13");

		lblMinY = new JLabel("Min Y");
		panel.add(lblMinY, "5, 13");

		lblMaxX = new JLabel("Max X");
		panel.add(lblMaxX, "7, 13");

		lblMaxY = new JLabel("Max Y");
		panel.add(lblMaxY, "9, 13");

		JLabel lblDefaultBoundingBox = new JLabel("Default bounding box");
		panel.add(lblDefaultBoundingBox, "1, 15, right, default");

		txtMinx = new JTextField();
		txtMinx.setToolTipText("Min X");
		panel.add(txtMinx, "3, 15, fill, default");

		txtMiny = new JTextField();
		txtMiny.setToolTipText("Min Y");
		panel.add(txtMiny, "5, 15, fill, default");

		txtMaxx = new JTextField();
		txtMaxx.setToolTipText("Max X");
		panel.add(txtMaxx, "7, 15, fill, default");

		txtMaxy = new JTextField();
		txtMaxy.setToolTipText("Max X");
		panel.add(txtMaxy, "9, 15, fill, default");




		// The builder holds the layout container that we now return.
		return panel;

	}
	private Component buildOutputPanel() {
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 10, 10, 10) );
		
		fChsrOutputDir = new JFileChooser();
		fChsrOutputDir.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		
		FormLayout layout = new FormLayout(new ColumnSpec[] {
				FormFactory.PREF_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.GLUE_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.BUTTON_COLSPEC,},
			new RowSpec[] {
				FormFactory.PREF_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.GLUE_ROWSPEC,
				RowSpec.decode("bottom:pref"),});
		panel.setLayout(layout);
		
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);

		JComponent metadataSeparator = builder.addSeparator("Output details",
				CC.xyw(1, 1, 5));
		panel.add(metadataSeparator, CC.xyw(1, 1, 5));
		
		chckbxGenerateinseparatedir = new JCheckBox("");
		chckbxGenerateinseparatedir.setSelected(true);
		chckbxGenerateinseparatedir.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (chckbxGenerateinseparatedir.isSelected()) {
					txtOutputpath.setEnabled(true);
					btnSelectDir.setEnabled(true);
				} else {
					txtOutputpath.setEnabled(false);
					btnSelectDir.setEnabled(false);
				}
			}
		});
		panel.add(chckbxGenerateinseparatedir, "1, 3, right, default");
		
		lblSeparatedir = new JLabel("Generate metadata in a separate directory");
		panel.add(lblSeparatedir, "3, 3");
		
		lblMetadataPath = new JLabel("Metadata path");
		panel.add(lblMetadataPath, "1, 5, right, default");
		
		txtOutputpath = new JTextField();
		panel.add(txtOutputpath, "3, 5, fill, default");
		txtOutputpath.setColumns(10);
		
		btnSelectDir = new JButton("Select dir...");
		btnSelectDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selected = fChsrOutputDir.showOpenDialog(PreferencesDialog.this);
				if (selected == JFileChooser.APPROVE_OPTION) {
					txtOutputpath.setText(fChsrOutputDir.getSelectedFile().getAbsolutePath());
				}
			}
		});
		panel.add(btnSelectDir, "5, 5");

		
		return panel;
	}
}
