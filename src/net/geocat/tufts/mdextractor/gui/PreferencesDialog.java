package net.geocat.tufts.mdextractor.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.geocat.tufts.guicomponents.ImprovedFormattedTextField;
import net.geocat.tufts.mdextractor.gui.model.PreferencesBean;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.binding.value.BufferedValueModel;
import com.jgoodies.binding.value.Trigger;
import com.jgoodies.forms.builder.ButtonBarBuilder2;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class PreferencesDialog extends JDialog {

	private static final long serialVersionUID = -3274785125608529455L;
	
	private static final String PREF_MINX = "minx";
	private static final String PREF_MINY = "miny";
	private static final String PREF_MAXX = "maxx";
	private static final String PREF_MAXY = "maxy";
	private static final String PREF_EMAIL = "email";
	private static final String PREF_ORGANIZATION = "organization";
	private static final String PREF_DEFAULT_PROJECTION = "defaultProjection";
	private static final String PREF_NAME = "name";
	private static final String PREF_GENERATE_IN_SEPARATE_DIR = "generateInSeparateDir";
	private static final String PREF_OUTPUT_DIR = "outputDir";
	
	private JTextField nameTextField;
	private JTextField organizationTextField;
	private JTextField txtEmail;
	private JTextField txtDefaultprojection;
	private JFormattedTextField txtMinx;
	private JFormattedTextField txtMiny;
	private JFormattedTextField txtMaxx;
	private JFormattedTextField txtMaxy;
	private JLabel lblMinX;
	private JLabel lblMinY;
	private JLabel lblMaxX;
	private JLabel lblMaxY;
	private JPanel buttonsPanel;
	private JLabel lblMetadataPath;
	private JTextField txtOutputpath;
	private JButton btnSelectDir;
	private JCheckBox chckbxGenerateinseparatedir;
	private JLabel lblSeparatedir;
	private JFileChooser fChsrOutputDir;
	private PresentationModel<PreferencesBean> presentationModel;
	private Trigger trigger;
	private JButton btnCancel;
	private JButton btnSave;
	private PreferencesBean preferencesBean;
	/**
	 * @return the preferencesBean
	 */
	public PreferencesBean getPreferencesBean() {
		return preferencesBean;
	}

	private Preferences prefs;

	/**
	 * Create the frame.
	 */
	public PreferencesDialog() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentHidden(ComponentEvent e) {
				trigger.triggerFlush();
				}
		});
		prefs = Preferences.userNodeForPackage(PreferencesDialog.class);
		preferencesBean = new PreferencesBean();
		// Init preferencesBean with values stored in user preferences.
		preferencesBean.setMinx(prefs.getDouble(PREF_MINX, -180.0d));
		preferencesBean.setMaxx(prefs.getDouble(PREF_MAXX, 180.0d));
		preferencesBean.setMiny(prefs.getDouble(PREF_MINY, -90.0d));
		preferencesBean.setMaxy(prefs.getDouble(PREF_MAXY, 90.0d));
		preferencesBean.setEmail(prefs.get(PREF_EMAIL, null));
		preferencesBean.setName(prefs.get(PREF_NAME, null));
		preferencesBean.setOrganization(prefs.get(PREF_ORGANIZATION, null));
		preferencesBean.setDefaultProjection(prefs.get(PREF_DEFAULT_PROJECTION,
				"EPSG:4326"));
		preferencesBean.setGenerateInSeparateDir(prefs.getBoolean(PREF_GENERATE_IN_SEPARATE_DIR, Boolean.FALSE));
		if (prefs.getBoolean(PREF_GENERATE_IN_SEPARATE_DIR, Boolean.FALSE) == true) {
			preferencesBean.setOutputDir(prefs.get(PREF_OUTPUT_DIR, null));
		}
		

		this.trigger = new Trigger();
		this.presentationModel = new PresentationModel<PreferencesBean>(
				preferencesBean, this.trigger);

		// Init frame
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
		btnSave = new JButton();
		btnSave.setAction(new SaveAction("Save"));
		buttonBuilder.addButton(btnSave);
		buttonBuilder.addRelatedGap();
		btnCancel = new JButton();
		btnCancel.setAction(new CancelAction("Cancel"));
		buttonBuilder.addButton(btnCancel);
		buttonsPanel = new JPanel();
		FlowLayout fl_buttonsPanel = (FlowLayout) buttonsPanel.getLayout();
		fl_buttonsPanel.setAlignment(FlowLayout.RIGHT);

		buttonsPanel.add(buttonBuilder.getPanel());

		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
	}

	private JPanel buildMetadataDefaultsPanel() {
		JPanel metadataDefaultsPanel = new JPanel();
		metadataDefaultsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		FormLayout fl_metadataDefaultsPanel = new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("right:default"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.BUTTON_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.BUTTON_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.BUTTON_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.BUTTON_COLSPEC, FormFactory.GLUE_COLSPEC,
				FormFactory.PREF_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, },
				new RowSpec[] { FormFactory.PREF_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.PREF_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.PREF_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.PREF_ROWSPEC,
						FormFactory.UNRELATED_GAP_ROWSPEC,
						FormFactory.PREF_ROWSPEC, FormFactory.LINE_GAP_ROWSPEC,
						FormFactory.PREF_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.PREF_ROWSPEC,
						FormFactory.NARROW_LINE_GAP_ROWSPEC,
						FormFactory.PREF_ROWSPEC,
						FormFactory.PARAGRAPH_GAP_ROWSPEC,
						FormFactory.GLUE_ROWSPEC,
						RowSpec.decode("bottom:max(17dlu;pref)"), });
		metadataDefaultsPanel.setLayout(fl_metadataDefaultsPanel);

		DefaultFormBuilder builder = new DefaultFormBuilder(
				fl_metadataDefaultsPanel);

		JComponent metadataSeparator = builder.addSeparator("Metadata contact",
				CC.xyw(1, 1, 12));
		metadataDefaultsPanel.add(metadataSeparator, CC.xyw(1, 1, 12));

		JLabel lblName = new JLabel("Name");
		metadataDefaultsPanel.add(lblName, "1, 3, right, default");

		nameTextField = new JTextField();
		Bindings.bind(nameTextField, presentationModel
				.getBufferedModel(PreferencesBean.NAME_PROPERTY));
		metadataDefaultsPanel.add(nameTextField, "3, 3, 9, 1, fill, default");
		nameTextField.setColumns(10);

		JLabel lblOrganisation = new JLabel("Organisation");
		metadataDefaultsPanel.add(lblOrganisation, "1, 5, right, default");

		organizationTextField = new JTextField();
		Bindings.bind(organizationTextField, presentationModel
				.getBufferedModel(PreferencesBean.ORGANIZATION_PROPERTY));
		metadataDefaultsPanel.add(organizationTextField,
				"3, 5, 9, 1, fill, default");
		organizationTextField.setColumns(10);

		JLabel lblEmail = new JLabel("E-mail");
		metadataDefaultsPanel.add(lblEmail, "1, 7, right, default");

		txtEmail = new JTextField();
		Bindings.bind(txtEmail, presentationModel
				.getBufferedModel(PreferencesBean.EMAIL_PROPERTY));
		metadataDefaultsPanel.add(txtEmail, "3, 7, 9, 1, fill, default");
		txtEmail.setColumns(10);

		JComponent datasetSeparator = builder.addSeparator("Dataset",
				CC.xyw(1, 9, 12));
		metadataDefaultsPanel.add(datasetSeparator, CC.xyw(1, 9, 12));

		JLabel lblDefaultProjection = new JLabel("Default projection");
		metadataDefaultsPanel
				.add(lblDefaultProjection, "1, 11, right, default");

		txtDefaultprojection = new JTextField();
		Bindings.bind(txtDefaultprojection, presentationModel
				.getBufferedModel(PreferencesBean.DEFAULT_PROYECTION_PROPERTY));
		metadataDefaultsPanel.add(txtDefaultprojection,
				"3, 11, 3, 1, fill, default");
		txtDefaultprojection.setColumns(10);

		lblMinX = new JLabel("Min X");
		metadataDefaultsPanel.add(lblMinX, "3, 13");

		lblMinY = new JLabel("Min Y");
		metadataDefaultsPanel.add(lblMinY, "5, 13");

		lblMaxX = new JLabel("Max X");
		metadataDefaultsPanel.add(lblMaxX, "7, 13");

		lblMaxY = new JLabel("Max Y");
		metadataDefaultsPanel.add(lblMaxY, "9, 13");

		JLabel lblDefaultBoundingBox = new JLabel("Default bounding box");
		metadataDefaultsPanel.add(lblDefaultBoundingBox,
				"1, 15, right, default");

		NumberFormat nf = DecimalFormat.getInstance(Locale.ENGLISH);
		nf.setMinimumFractionDigits(1);
		nf.setMaximumFractionDigits(24);
		nf.setMinimumIntegerDigits(1);
		nf.setMaximumIntegerDigits(3);
		nf.setGroupingUsed(false);
		txtMinx = new ImprovedFormattedTextField(nf);
		Bindings.bind(txtMinx, presentationModel
				.getBufferedModel(PreferencesBean.MINX_PROPERTY));
		txtMinx.setToolTipText("Min X");
		metadataDefaultsPanel.add(txtMinx, "3, 15, fill, default");

		txtMiny = new ImprovedFormattedTextField(nf);
		Bindings.bind(txtMiny, presentationModel
				.getBufferedModel(PreferencesBean.MINY_PROPERTY));
		txtMiny.setToolTipText("Min Y");
		metadataDefaultsPanel.add(txtMiny, "5, 15, fill, default");

		txtMaxx = new ImprovedFormattedTextField(nf);
		Bindings.bind(txtMaxx, presentationModel
				.getBufferedModel(PreferencesBean.MAXX_PROPERTY));
		txtMaxx.setToolTipText("Max X");
		metadataDefaultsPanel.add(txtMaxx, "7, 15, fill, default");

		txtMaxy = new ImprovedFormattedTextField(nf);
		Bindings.bind(txtMaxy, presentationModel
				.getBufferedModel(PreferencesBean.MAXY_PROPERTY));
		txtMaxy.setToolTipText("Max X");
		metadataDefaultsPanel.add(txtMaxy, "9, 15, fill, default");

		// The builder holds the layout container that we now return.
		return metadataDefaultsPanel;

	}

	private Component buildOutputPanel() {
		JPanel outputPanel = new JPanel();
		outputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		fChsrOutputDir = new JFileChooser();
		fChsrOutputDir.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		FormLayout fl_outputPanel = new FormLayout(new ColumnSpec[] {
				FormFactory.PREF_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.GLUE_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.BUTTON_COLSPEC, }, new RowSpec[] {
				FormFactory.PREF_ROWSPEC, FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.GLUE_ROWSPEC, RowSpec.decode("bottom:pref"), });
		outputPanel.setLayout(fl_outputPanel);

		DefaultFormBuilder builder = new DefaultFormBuilder(fl_outputPanel);

		JComponent metadataSeparator = builder.addSeparator("Output details",
				CC.xyw(1, 1, 5));
		outputPanel.add(metadataSeparator, CC.xyw(1, 1, 5));

		chckbxGenerateinseparatedir = new JCheckBox("");
		BufferedValueModel genInSepDirBufferedModel = presentationModel
				.getBufferedModel(PreferencesBean.GENERATE_IN_SEPARATE_DIR_PROPERTY);
		Bindings.bind(chckbxGenerateinseparatedir, genInSepDirBufferedModel);
		
		chckbxGenerateinseparatedir.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					txtOutputpath.setEnabled(true);
					btnSelectDir.setEnabled(true);
				} else {
					txtOutputpath.setEnabled(false);
					btnSelectDir.setEnabled(false);
				}
			}
		});
		
		outputPanel.add(chckbxGenerateinseparatedir, "1, 3, right, default");

		lblSeparatedir = new JLabel("Generate metadata in a separate directory");
		outputPanel.add(lblSeparatedir, "3, 3");

		lblMetadataPath = new JLabel("Metadata path");
		outputPanel.add(lblMetadataPath, "1, 5, right, default");

		txtOutputpath = new JTextField();
		txtOutputpath.setEditable(false);
		final BufferedValueModel outputDirectoryBufferedModel = presentationModel.getBufferedModel(PreferencesBean.OUTPUT_DIR_PROPERTY);
		Bindings.bind(txtOutputpath, outputDirectoryBufferedModel);
		outputPanel.add(txtOutputpath, "3, 5, fill, default");
		txtOutputpath.setColumns(10);

		btnSelectDir = new JButton("Select dir...");
		btnSelectDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String outputDirString = preferencesBean.getOutputDir();
				if (outputDirString != null && outputDirString != "") {
					final File outputDir = new File(outputDirString);
					if (outputDir.exists()) {
						fChsrOutputDir.setCurrentDirectory(outputDir);
					} else {
						fChsrOutputDir.setCurrentDirectory(new File(System.getProperty("user.home")));
					}
				}
				int selected = fChsrOutputDir
						.showOpenDialog(PreferencesDialog.this);
				if (selected == JFileChooser.APPROVE_OPTION) {
					outputDirectoryBufferedModel.setValue(fChsrOutputDir.getSelectedFile()
							.getAbsolutePath());
				}
			}
			
		});
		if (!genInSepDirBufferedModel.booleanValue()) {
			txtOutputpath.setEnabled(false);
			btnSelectDir.setEnabled(false);
		}
		outputPanel.add(btnSelectDir, "5, 5");

		return outputPanel;
	}

	class SaveAction extends AbstractAction {

		private static final long serialVersionUID = -6488510217216397069L;

		public SaveAction(String buttonText) {
			super(buttonText);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			trigger.triggerCommit();
			prefs.putDouble(PREF_MINX,
					preferencesBean.getMinx() == null ? -180.0d
							: preferencesBean.getMinx());
			prefs.putDouble(PREF_MAXX,
					preferencesBean.getMaxx() == null ? 180.0d
							: preferencesBean.getMaxx());
			prefs.putDouble(PREF_MINY,
					preferencesBean.getMiny() == null ? -90.0d
							: preferencesBean.getMiny());
			prefs.putDouble(
					PREF_MAXY,
					preferencesBean.getMaxy() == null ? 90.0d : preferencesBean
							.getMaxy());
			prefs.put(PREF_EMAIL, preferencesBean.getEmail() == null ? ""
					: preferencesBean.getEmail());
			prefs.put(PREF_ORGANIZATION,
					preferencesBean.getOrganization() == null ? ""
							: preferencesBean.getOrganization());
			prefs.put(PREF_DEFAULT_PROJECTION,
					preferencesBean.getDefaultProjection() == null ? ""
							: preferencesBean.getDefaultProjection());
			prefs.put(PREF_NAME, preferencesBean.getName() == null ? ""
					: preferencesBean.getName());
			prefs.putBoolean(PREF_GENERATE_IN_SEPARATE_DIR, preferencesBean.getGenerateInSeparateDir() == null ? false : preferencesBean.getGenerateInSeparateDir());
			prefs.put(PREF_OUTPUT_DIR, preferencesBean.getOutputDir() == null ? "" : preferencesBean.getOutputDir());
			try {
				prefs.flush();
			} catch (BackingStoreException e1) {
				System.err.println("Error saving preferences: "
						+ e1.getMessage());
				e1.printStackTrace(System.err);
			}
			PreferencesDialog.this.setVisible(false);
		}
	}

	class CancelAction extends AbstractAction {
		private static final long serialVersionUID = 5945488874950435558L;

		public CancelAction(String buttonText) {
			super(buttonText);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			trigger.triggerFlush();
			PreferencesDialog.this.setVisible(false);

		}

	}
}
