package net.geocat.mdextractor.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileFilter;
import java.net.URISyntaxException;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.omg.PortableInterceptor.SUCCESSFUL;

import net.geocat.mdextractor.gui.model.PreferencesBean;
import net.geocat.mdextractor.guicomponents.ImprovedFormattedTextField;

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
import com.sun.org.apache.bcel.internal.classfile.PMGClass;

public class PreferencesDialog extends JDialog {

	private static final long serialVersionUID = -3274785125608529455L;
	private JButton btnCancel;
	private JButton btnCommonProps;
	private JButton btnSave;
	private JButton btnSelectDir;
	private JPanel buttonsPanel;
	private JCheckBox chckbxGenerateinseparatedir;
	private JFileChooser fChsrOutputDir;
	private JLabel label;
	private JLabel lblAdministrativeArea;
	private JLabel lblCity;
	private JLabel lblCommonProperties;
	private JLabel lblCountry;
	private JLabel lblDeliveryPoint;
	private JLabel lblEmail;
	private JLabel lblIndividualName;
	private JLabel lblMaxX;
	private JLabel lblMaxY;
	private JLabel lblMdAdministativeArea;
	private JLabel lblMdCity;
	private JLabel lblMdCountry;
	private JLabel lblMdDeliveryPoint;
	private JLabel lblMdPositionName;
	private JLabel lblMdPostalCode;
	private JLabel lblMdVoice;
	private JLabel lblMetadataPath;
	private JLabel lblMinX;
	private JLabel lblMinY;
	private JLabel lblOrganisation;
	private JLabel lblPostalCode;
	private JLabel lblSeparatedir;
	private JLabel lblVoice;
	private JTextField txtMdOrganisation;
	private PreferencesBean preferencesBean;
	private Preferences prefs;
	private PresentationModel<PreferencesBean> presentationModel;
	private BufferedValueModel resourcesDirBufferedModel;
	private JTextField txtAdministrativeArea;
	private JTextField txtCity;
	private JTextField txtCountry;
	private JTextField txtDeliveryPoint;
	private JTextField txtIndividualName;
	private JTextField txtMdAdministrativeArea;
	private JTextField txtMdCity;
	private JTextField txtMdCountry;
	private JTextField txtMdDeliveryPoint;
	private JTextField txtMdPositionName;
	private JTextField txtMdPostalCode;
	private JTextField txtMdVoice;
	private JTextField txtOrganisation;
	private JTextField txtPosition;
	private JTextField txtPostalCode;
	private JTextField txtVoice;
	private Trigger trigger;
	private JTextField txtCommonProps;
	private JTextField txtDefaultprojection;
	private JTextField txtMdEmail;
	private JFormattedTextField txtMaxx;
	private JFormattedTextField txtMaxy;
	private JFormattedTextField txtMinx;
	private JFormattedTextField txtMiny;
	private JTextField txtMdIndividualName;
	private JTextField txtOutputpath;
	private JTextField txtEmail;

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

		initPreferencesBean(prefs, preferencesBean);

		this.trigger = new Trigger();
		this.presentationModel = new PresentationModel<PreferencesBean>(
				preferencesBean, this.trigger);

		// Init frame
		setResizable(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModal(true);
		setAlwaysOnTop(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				PreferencesDialog.class
						.getResource("/img/16x16/preferences-system.png")));
		setTitle("Preferences");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 600, 590);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.putClientProperty("jgoodies.noContentBorder", Boolean.TRUE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		tabbedPane.addTab("Metadata defaults", buildMetadataDefaultsPanel());
		tabbedPane.addTab("Output", buildOutputPanel());

		ButtonBarBuilder2 buttonBuilder = new ButtonBarBuilder2();
		buttonBuilder.addGlue();
		btnSave = new JButton();
		final SaveAction saveAction = new SaveAction("Save");
		btnSave.setAction(saveAction);
		buttonBuilder.addButton(btnSave);
		buttonBuilder.addRelatedGap();
		btnCancel = new JButton();
		final CancelAction cancelAction = new CancelAction("Cancel");
		btnCancel.setAction(cancelAction);
		buttonBuilder.addButton(btnCancel);
		buttonsPanel = new JPanel();
		FlowLayout fl_buttonsPanel = (FlowLayout) buttonsPanel.getLayout();
		fl_buttonsPanel.setAlignment(FlowLayout.RIGHT);

		buttonsPanel.add(buttonBuilder.getPanel());

		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
	}

	/**
	 * Initializes the PreferencesBean with the data saved in the system
	 * preferences.
	 * 
	 * @param preferences
	 *            system preferences.
	 * @param prefBean
	 *            bean to be initialized.
	 */
	private void initPreferencesBean(Preferences preferences,
			PreferencesBean prefBean) {
		// Init preferencesBean with values stored in user preferences.
		prefBean.setDefaultProjection(preferences.get(
				PreferencesBean.DEFAULT_PROYECTION_PROPERTY, "EPSG:4326"));
		prefBean.setGenerateInSeparateDir(preferences.getBoolean(
				PreferencesBean.GENERATE_IN_SEPARATE_DIR_PROPERTY,
				Boolean.FALSE));
		prefBean.setResourcesDir(preferences.get(
				PreferencesBean.RESOURCES_DIR_PROPERTY,
				getMetadataPropertiesFolder().getAbsolutePath()));
		if (preferences.getBoolean(
				PreferencesBean.GENERATE_IN_SEPARATE_DIR_PROPERTY,
				Boolean.FALSE) == true) {
			prefBean.setOutputDir(preferences.get(
					PreferencesBean.OUTPUT_DIR_PROPERTY, null));
		}
		prefBean.setMinx(preferences.getDouble(PreferencesBean.MINX_PROPERTY,
				-180.0d));
		prefBean.setMaxx(preferences.getDouble(PreferencesBean.MAXX_PROPERTY,
				180.0d));
		prefBean.setMiny(preferences.getDouble(PreferencesBean.MINY_PROPERTY,
				-90.0d));
		prefBean.setMaxy(preferences.getDouble(PreferencesBean.MAXY_PROPERTY,
				90.0d));

		// Metadata properties
		prefBean.setMdIndividualName(preferences.get(
				PreferencesBean.MD_INDIVIDUAL_NAME_PROPERTY, ""));
		prefBean.setMdOrganisation(preferences.get(
				PreferencesBean.MD_ORGANISATION_PROPERTY, ""));
		prefBean.setMdPositionName(preferences.get(
				PreferencesBean.MD_POSITION_NAME_PROPERTY, ""));
		prefBean.setMdVoice(preferences.get(PreferencesBean.MD_VOICE_PROPERTY,
				""));
		prefBean.setMdDeliveryPoint(preferences.get(
				PreferencesBean.MD_DELIVERY_POINT_PROPERTY, ""));
		prefBean.setMdCity(preferences
				.get(PreferencesBean.MD_CITY_PROPERTY, ""));
		prefBean.setMdAdminArea(preferences.get(
				PreferencesBean.MD_ADMIN_AREA_PROPERTY, ""));
		prefBean.setMdPostalCode(preferences.get(
				PreferencesBean.MD_POSTAL_CODE_PROPERTY, ""));
		prefBean.setMdCountry(preferences.get(
				PreferencesBean.MD_COUNTRY_PROPERTY, ""));
		prefBean.setMdEmail(preferences.get(PreferencesBean.MD_EMAIL_PROPERTY,
				""));

		// Data properties
		prefBean.setIndividualName(preferences.get(
				PreferencesBean.INDIVIDUAL_NAME_PROPERTY, ""));
		prefBean.setOrganisation(preferences.get(
				PreferencesBean.ORGANISATION_PROPERTY, ""));
		prefBean.setPositionName(preferences.get(
				PreferencesBean.POSITION_NAME_PROPERTY, ""));
		prefBean.setVoice(preferences.get(PreferencesBean.VOICE_PROPERTY, ""));
		prefBean.setDeliveryPoint(preferences.get(
				PreferencesBean.DELIVERY_POINT_PROPERTY, ""));
		prefBean.setCity(preferences.get(PreferencesBean.CITY_PROPERTY, ""));
		prefBean.setAdminArea(preferences.get(
				PreferencesBean.ADMIN_AREA_PROPERTY, ""));
		prefBean.setPostalCode(preferences.get(
				PreferencesBean.POSTAL_CODE_PROPERTY, ""));
		prefBean.setCountry(preferences.get(PreferencesBean.COUNTRY_PROPERTY,
				""));
		prefBean.setEmail(preferences.get(PreferencesBean.EMAIL_PROPERTY, ""));
	}

	private JPanel buildMetadataDefaultsPanel() {
		JPanel metadataDefaultsPanel = new JPanel();
		metadataDefaultsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		FormLayout fl_metadataDefaultsPanel = new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("right:default"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.GROWING_BUTTON_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.BUTTON_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("right:max(50dlu;pref)"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.GROWING_BUTTON_COLSPEC,
				FormFactory.GROWING_BUTTON_COLSPEC, FormFactory.GLUE_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, }, new RowSpec[] {
				FormFactory.PREF_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC, FormFactory.UNRELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC, FormFactory.UNRELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC, FormFactory.UNRELATED_GAP_ROWSPEC,
				FormFactory.GLUE_ROWSPEC,
				RowSpec.decode("bottom:max(17dlu;pref)"), });
		metadataDefaultsPanel.setLayout(fl_metadataDefaultsPanel);

		DefaultFormBuilder builder = new DefaultFormBuilder(
				fl_metadataDefaultsPanel);

		JComponent commonSeparator = builder.addSeparator("Commont properties",
				CC.xyw(1, 1, 12));
		metadataDefaultsPanel.add(commonSeparator, CC.xyw(1, 1, 12));

		JComponent metadataSeparator = builder.addSeparator("Metadata contact",
				CC.xyw(1, 1, 12));
		metadataDefaultsPanel.add(metadataSeparator, CC.xyw(1, 5, 12));
		JComponent dataSeparator = builder.addSeparator("Data contact",
				CC.xyw(1, 1, 12));
		metadataDefaultsPanel.add(dataSeparator, CC.xyw(1, 17, 12));
		JComponent datasetSeparator = builder.addSeparator("Dataset",
				CC.xyw(1, 9, 12));
		metadataDefaultsPanel.add(datasetSeparator, CC.xyw(1, 29, 12));

		lblCommonProperties = new JLabel("Common properties");
		metadataDefaultsPanel.add(lblCommonProperties, "1, 3, right, default");

		txtCommonProps = new JTextField();
		txtCommonProps.setEditable(false);
		metadataDefaultsPanel.add(txtCommonProps, "3, 3, 7, 1, fill, default");
		txtCommonProps.setColumns(10);

		resourcesDirBufferedModel = presentationModel
				.getBufferedModel(PreferencesBean.RESOURCES_DIR_PROPERTY);
		Bindings.bind(txtCommonProps, resourcesDirBufferedModel);
		btnCommonProps = new JButton("Browse...");
		btnCommonProps.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser;
				if (resourcesDirBufferedModel.getValue() != null
						&& !"".equals(resourcesDirBufferedModel.getValue())) {
					String fileName = ((String) resourcesDirBufferedModel
							.getValue()).replace("\\", "\\\\");
					fileChooser = new JFileChooser(fileName);
				} else {
					File mainJarPath = getMetadataPropertiesFolder();
					fileChooser = new JFileChooser(mainJarPath);
				}
				fileChooser
						.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int selected = fileChooser.showDialog(PreferencesDialog.this,
						"Select Directory");

				if (selected == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					if (!selectedFile.isDirectory()) {
						selectedFile = selectedFile.getParentFile();
					}
					resourcesDirBufferedModel.setValue(selectedFile
							.getAbsolutePath());
				}

			}

		});
		metadataDefaultsPanel.add(btnCommonProps, "10, 3, 2, 1");

		NumberFormat nf = DecimalFormat.getInstance(Locale.ENGLISH);
		nf.setMinimumFractionDigits(1);
		nf.setMaximumFractionDigits(24);
		nf.setMinimumIntegerDigits(1);
		nf.setMaximumIntegerDigits(3);
		nf.setGroupingUsed(false);

		JLabel lblMdIndividualName = new JLabel("Name");
		metadataDefaultsPanel.add(lblMdIndividualName, "1, 7, right, default");

		txtMdIndividualName = new JTextField();
		Bindings.bind(txtMdIndividualName, presentationModel
				.getBufferedModel(PreferencesBean.MD_INDIVIDUAL_NAME_PROPERTY));
		metadataDefaultsPanel.add(txtMdIndividualName,
				"3, 7, 3, 1, fill, default");
		txtMdIndividualName.setColumns(10);

		JLabel lblMdOrganisation = new JLabel("Organisation");
		metadataDefaultsPanel.add(lblMdOrganisation, "7, 7, right, default");

		txtMdOrganisation = new JTextField();
		Bindings.bind(txtMdOrganisation, presentationModel
				.getBufferedModel(PreferencesBean.MD_ORGANISATION_PROPERTY));
		metadataDefaultsPanel.add(txtMdOrganisation,
				"9, 7, 3, 1, fill, default");
		txtMdOrganisation.setColumns(10);

		lblMdPositionName = new JLabel("Position");
		metadataDefaultsPanel.add(lblMdPositionName, "1, 9, right, default");

		txtMdPositionName = new JTextField();
		Bindings.bind(txtMdPositionName, presentationModel
				.getBufferedModel(PreferencesBean.MD_POSITION_NAME_PROPERTY));
		metadataDefaultsPanel.add(txtMdPositionName,
				"3, 9, 3, 1, fill, default");
		txtMdPositionName.setColumns(10);

		JLabel lblMdEmail = new JLabel("E-mail");
		metadataDefaultsPanel.add(lblMdEmail, "7, 9, right, default");

		txtMdEmail = new JTextField();
		Bindings.bind(txtMdEmail, presentationModel
				.getBufferedModel(PreferencesBean.MD_EMAIL_PROPERTY));
		metadataDefaultsPanel.add(txtMdEmail, "9, 9, 3, 1, fill, default");
		txtMdEmail.setColumns(10);

		lblMdVoice = new JLabel("Phone");
		metadataDefaultsPanel.add(lblMdVoice, "1, 11, right, default");

		txtMdVoice = new JTextField();
		Bindings.bind(txtMdVoice, presentationModel
				.getBufferedModel(PreferencesBean.MD_VOICE_PROPERTY));
		metadataDefaultsPanel.add(txtMdVoice, "3, 11, 3, 1, fill, default");
		txtMdVoice.setColumns(10);

		lblMdDeliveryPoint = new JLabel("Address");
		metadataDefaultsPanel.add(lblMdDeliveryPoint, "7, 11, right, default");

		txtMdDeliveryPoint = new JTextField();
		Bindings.bind(txtMdDeliveryPoint, presentationModel
				.getBufferedModel(PreferencesBean.MD_DELIVERY_POINT_PROPERTY));
		metadataDefaultsPanel.add(txtMdDeliveryPoint,
				"9, 11, 3, 1, fill, default");
		txtMdDeliveryPoint.setColumns(10);

		lblMdCity = new JLabel("City");
		metadataDefaultsPanel.add(lblMdCity, "1, 13, right, default");

		txtMdCity = new JTextField();
		Bindings.bind(txtMdCity, presentationModel
				.getBufferedModel(PreferencesBean.MD_CITY_PROPERTY));
		metadataDefaultsPanel.add(txtMdCity, "3, 13, 3, 1, fill, default");
		txtMdCity.setColumns(10);

		lblMdAdministativeArea = new JLabel("Admin. Area");
		metadataDefaultsPanel.add(lblMdAdministativeArea,
				"7, 13, right, default");

		txtMdAdministrativeArea = new JTextField();
		Bindings.bind(txtMdAdministrativeArea, presentationModel
				.getBufferedModel(PreferencesBean.MD_ADMIN_AREA_PROPERTY));
		metadataDefaultsPanel.add(txtMdAdministrativeArea,
				"9, 13, 3, 1, fill, default");
		txtMdAdministrativeArea.setColumns(10);

		lblMdPostalCode = new JLabel("Postal code");
		metadataDefaultsPanel.add(lblMdPostalCode, "1, 15, right, default");

		txtMdPostalCode = new JTextField();
		Bindings.bind(txtPostalCode, presentationModel
				.getBufferedModel(PreferencesBean.MD_POSTAL_CODE_PROPERTY));
		metadataDefaultsPanel
				.add(txtMdPostalCode, "3, 15, 3, 1, fill, default");
		txtMdPostalCode.setColumns(10);

		lblMdCountry = new JLabel("Country");
		metadataDefaultsPanel.add(lblMdCountry, "7, 15, right, default");

		txtMdCountry = new JTextField();
		Bindings.bind(txtMdCountry, presentationModel
				.getBufferedModel(PreferencesBean.MD_COUNTRY_PROPERTY));
		metadataDefaultsPanel.add(txtMdCountry, "9, 15, 3, 1, fill, default");
		txtMdCountry.setColumns(10);

		lblIndividualName = new JLabel("Name");
		metadataDefaultsPanel.add(lblIndividualName, "1, 19, right, default");

		txtIndividualName = new JTextField();
		Bindings.bind(txtIndividualName, presentationModel
				.getBufferedModel(PreferencesBean.INDIVIDUAL_NAME_PROPERTY));
		metadataDefaultsPanel.add(txtIndividualName,
				"3, 19, 3, 1, fill, default");
		txtIndividualName.setColumns(10);

		lblOrganisation = new JLabel("Organisation");
		metadataDefaultsPanel.add(lblOrganisation, "7, 19, right, default");

		txtOrganisation = new JTextField();
		Bindings.bind(txtOrganisation, presentationModel
				.getBufferedModel(PreferencesBean.ORGANISATION_PROPERTY));
		metadataDefaultsPanel
				.add(txtOrganisation, "9, 19, 3, 1, fill, default");
		txtOrganisation.setColumns(10);

		label = new JLabel("Position");
		metadataDefaultsPanel.add(label, "1, 21, right, default");

		txtPosition = new JTextField();
		Bindings.bind(txtPosition, presentationModel
				.getBufferedModel(PreferencesBean.POSITION_NAME_PROPERTY));
		metadataDefaultsPanel.add(txtPosition, "3, 21, 3, 1, fill, default");
		txtPosition.setColumns(10);

		lblEmail = new JLabel("E-mail");
		metadataDefaultsPanel.add(lblEmail, "7, 21, right, default");

		txtEmail = new JTextField();
		Bindings.bind(txtEmail, presentationModel
				.getBufferedModel(PreferencesBean.EMAIL_PROPERTY));
		metadataDefaultsPanel.add(txtEmail, "9, 21, 3, 1, fill, default");
		txtEmail.setColumns(10);

		lblVoice = new JLabel("Phone");
		metadataDefaultsPanel.add(lblVoice, "1, 23, right, default");

		txtVoice = new JTextField();
		Bindings.bind(txtVoice, presentationModel
				.getBufferedModel(PreferencesBean.VOICE_PROPERTY));
		metadataDefaultsPanel.add(txtVoice, "3, 23, 3, 1, fill, default");
		txtVoice.setColumns(10);

		lblDeliveryPoint = new JLabel("Address");
		metadataDefaultsPanel.add(lblDeliveryPoint, "7, 23, right, default");

		txtDeliveryPoint = new JTextField();
		Bindings.bind(txtDeliveryPoint, presentationModel
				.getBufferedModel(PreferencesBean.DELIVERY_POINT_PROPERTY));
		metadataDefaultsPanel.add(txtDeliveryPoint,
				"9, 23, 3, 1, fill, default");
		txtDeliveryPoint.setColumns(10);

		lblCity = new JLabel("City");
		metadataDefaultsPanel.add(lblCity, "1, 25, right, default");

		txtCity = new JTextField();
		Bindings.bind(txtCity, presentationModel
				.getBufferedModel(PreferencesBean.CITY_PROPERTY));
		metadataDefaultsPanel.add(txtCity, "3, 25, 3, 1, fill, default");
		txtCity.setColumns(10);

		lblAdministrativeArea = new JLabel("Admin. Area");
		metadataDefaultsPanel.add(lblAdministrativeArea,
				"7, 25, right, default");

		txtAdministrativeArea = new JTextField();
		Bindings.bind(txtAdministrativeArea, presentationModel
				.getBufferedModel(PreferencesBean.ADMIN_AREA_PROPERTY));
		metadataDefaultsPanel.add(txtAdministrativeArea,
				"9, 25, 3, 1, fill, default");
		txtAdministrativeArea.setColumns(10);

		lblPostalCode = new JLabel("Postal code");
		metadataDefaultsPanel.add(lblPostalCode, "1, 27, right, default");

		txtPostalCode = new JTextField();
		Bindings.bind(txtPostalCode, presentationModel
				.getBufferedModel(PreferencesBean.POSTAL_CODE_PROPERTY));
		txtPostalCode.setColumns(10);
		metadataDefaultsPanel.add(txtPostalCode, "3, 27, 3, 1, fill, default");

		lblCountry = new JLabel("Country");
		metadataDefaultsPanel.add(lblCountry, "7, 27, right, default");

		txtCountry = new JTextField();
		Bindings.bind(txtCountry, presentationModel
				.getBufferedModel(PreferencesBean.COUNTRY_PROPERTY));
		metadataDefaultsPanel.add(txtCountry, "9, 27, 3, 1, fill, default");
		txtCountry.setColumns(10);

		JLabel lblDefaultProjection = new JLabel("Default projection");
		metadataDefaultsPanel
				.add(lblDefaultProjection, "1, 31, right, default");

		txtDefaultprojection = new JTextField();
		Bindings.bind(txtDefaultprojection, presentationModel
				.getBufferedModel(PreferencesBean.DEFAULT_PROYECTION_PROPERTY));
		metadataDefaultsPanel.add(txtDefaultprojection,
				"3, 31, 3, 1, fill, default");
		txtDefaultprojection.setColumns(10);

		JLabel lblDefaultBoundingBox = new JLabel("Default bounding box");
		metadataDefaultsPanel.add(lblDefaultBoundingBox,
				"1, 33, right, default");

		lblMinX = new JLabel("Min X");
		metadataDefaultsPanel.add(lblMinX, "3, 33");

		lblMinY = new JLabel("Min Y");
		metadataDefaultsPanel.add(lblMinY, "5, 33");

		lblMaxX = new JLabel("Max X");
		metadataDefaultsPanel.add(lblMaxX, "7, 33, left, default");

		lblMaxY = new JLabel("Max Y");
		metadataDefaultsPanel.add(lblMaxY, "9, 33");
		txtMinx = new ImprovedFormattedTextField(nf);
		Bindings.bind(txtMinx, presentationModel
				.getBufferedModel(PreferencesBean.MINX_PROPERTY));
		txtMinx.setToolTipText("Min X");
		metadataDefaultsPanel.add(txtMinx, "3, 35, fill, default");

		txtMiny = new ImprovedFormattedTextField(nf);
		Bindings.bind(txtMiny, presentationModel
				.getBufferedModel(PreferencesBean.MINY_PROPERTY));
		txtMiny.setToolTipText("Min Y");
		metadataDefaultsPanel.add(txtMiny, "5, 35, fill, default");

		txtMaxx = new ImprovedFormattedTextField(nf);
		Bindings.bind(txtMaxx, presentationModel
				.getBufferedModel(PreferencesBean.MAXX_PROPERTY));
		txtMaxx.setToolTipText("Max X");
		metadataDefaultsPanel.add(txtMaxx, "7, 35, fill, default");

		txtMaxy = new ImprovedFormattedTextField(nf);
		Bindings.bind(txtMaxy, presentationModel
				.getBufferedModel(PreferencesBean.MAXY_PROPERTY));
		txtMaxy.setToolTipText("Max X");
		metadataDefaultsPanel.add(txtMaxy, "9, 35, fill, default");

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

		JComponent outputDetailsSeparator = builder.addSeparator(
				"Output details", CC.xyw(1, 1, 5));
		outputPanel.add(outputDetailsSeparator, CC.xyw(1, 1, 5));

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
		final BufferedValueModel outputDirectoryBufferedModel = presentationModel
				.getBufferedModel(PreferencesBean.OUTPUT_DIR_PROPERTY);
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
						fChsrOutputDir.setCurrentDirectory(new File(System
								.getProperty("user.home")));
					}
				}
				int selected = fChsrOutputDir
						.showOpenDialog(PreferencesDialog.this);
				if (selected == JFileChooser.APPROVE_OPTION) {
					outputDirectoryBufferedModel.setValue(fChsrOutputDir
							.getSelectedFile().getAbsolutePath());
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

	public File getMetadataPropertiesFolder() {
		File mainJarPath = null;
		try {
			mainJarPath = new File(PreferencesDialog.class
					.getProtectionDomain().getCodeSource().getLocation()
					.toURI().getPath());
			mainJarPath = mainJarPath.getParentFile();
			mainJarPath = new File(mainJarPath.getAbsolutePath()
					+ "/_resources");
			if (!mainJarPath.exists() || !mainJarPath.isDirectory()) {
				mainJarPath = new File(System.getProperty("user.home"));
			}
		} catch (URISyntaxException e1) {
			mainJarPath = new File(System.getProperty("user.home"));
		}
		return mainJarPath;
	}

	/**
	 * @return the preferencesBean
	 */
	public PreferencesBean getPreferencesBean() {
		return preferencesBean;
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

	class SaveAction extends AbstractAction {

		private static final long serialVersionUID = -6488510217216397069L;

		public SaveAction(String buttonText) {
			super(buttonText);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String resourcesDir = (String) resourcesDirBufferedModel.getValue();

			if (resourcesDir == null || resourcesDir.equals("")) {
				resourcesDir = getMetadataPropertiesFolder().getAbsolutePath();
				resourcesDirBufferedModel.setValue(resourcesDir);
			}

			File resourcesDirFile = new File(resourcesDir.replace("\\", "\\\\"));
			boolean error = false;

			if (resourcesDirFile.isDirectory()) {
				File[] result = resourcesDirFile.listFiles(new FileFilter() {

					@Override
					public boolean accept(File pathname) {
						String path = pathname.getAbsolutePath().toLowerCase();
						if (path.endsWith(File.separator
								+ "metadata-properties.csv")) {
							return true;
						} else {
							return false;
						}
					}
				});
				if (result.length == 0) {
					error = true;
				}
			} else {
				error = true;
			}

			if (error) {
				JOptionPane
						.showMessageDialog(
								PreferencesDialog.this,
								"The selected directory doesn't contain the file \nmetadata-properties.csv",
								"Common metadata warning.",
								JOptionPane.WARNING_MESSAGE);
				return;
			}

			trigger.triggerCommit();
			prefs.putDouble(PreferencesBean.MINX_PROPERTY, ObjectUtils
					.defaultIfNull(preferencesBean.getMinx(), -180.0d));
			prefs.putDouble(PreferencesBean.MAXX_PROPERTY, ObjectUtils
					.defaultIfNull(preferencesBean.getMaxx(), 180.0d));
			prefs.putDouble(PreferencesBean.MINY_PROPERTY, ObjectUtils
					.defaultIfNull(preferencesBean.getMiny(), -90.0d));
			prefs.putDouble(PreferencesBean.MAXY_PROPERTY,
					ObjectUtils.defaultIfNull(preferencesBean.getMaxy(), 90.0d));
			prefs.put(PreferencesBean.DEFAULT_PROYECTION_PROPERTY, StringUtils
					.defaultString(preferencesBean.getDefaultProjection()));
			prefs.putBoolean(PreferencesBean.GENERATE_IN_SEPARATE_DIR_PROPERTY,
					ObjectUtils.defaultIfNull(
							preferencesBean.getGenerateInSeparateDir(),
							Boolean.FALSE));
			prefs.put(PreferencesBean.OUTPUT_DIR_PROPERTY,
					StringUtils.defaultString(preferencesBean.getOutputDir()));
			prefs.put(PreferencesBean.RESOURCES_DIR_PROPERTY, StringUtils
					.defaultString(preferencesBean.getResourcesDir()));

			// Metadata properties
			prefs.put(PreferencesBean.MD_INDIVIDUAL_NAME_PROPERTY, StringUtils
					.defaultString(preferencesBean.getMdIndividualName()));
			prefs.put(PreferencesBean.MD_ORGANISATION_PROPERTY, StringUtils
					.defaultString(preferencesBean.getMdOrganisation()));
			prefs.put(PreferencesBean.MD_POSITION_NAME_PROPERTY, StringUtils
					.defaultString(preferencesBean.getMdPositionName()));
			prefs.put(PreferencesBean.MD_VOICE_PROPERTY,
					StringUtils.defaultString(preferencesBean.getMdVoice()));
			prefs.put(PreferencesBean.MD_DELIVERY_POINT_PROPERTY, StringUtils
					.defaultString(preferencesBean.getMdDeliveryPoint()));
			prefs.put(PreferencesBean.MD_CITY_PROPERTY,
					StringUtils.defaultString(preferencesBean.getMdCity()));
			prefs.put(PreferencesBean.MD_ADMIN_AREA_PROPERTY,
					StringUtils.defaultString(preferencesBean.getMdAdminArea()));
			prefs.put(PreferencesBean.MD_POSTAL_CODE_PROPERTY, StringUtils
					.defaultString(preferencesBean.getMdPostalCode()));
			prefs.put(PreferencesBean.MD_COUNTRY_PROPERTY,
					StringUtils.defaultString(preferencesBean.getMdCountry()));
			prefs.put(PreferencesBean.MD_EMAIL_PROPERTY,
					StringUtils.defaultString(preferencesBean.getMdEmail()));

			// Data properties
			prefs.put(PreferencesBean.INDIVIDUAL_NAME_PROPERTY, StringUtils
					.defaultString(preferencesBean.getIndividualName()));
			prefs.put(PreferencesBean.ORGANISATION_PROPERTY, StringUtils
					.defaultString(preferencesBean.getOrganisation()));
			prefs.put(PreferencesBean.POSITION_NAME_PROPERTY, StringUtils
					.defaultString(preferencesBean.getPositionName()));
			prefs.put(PreferencesBean.VOICE_PROPERTY,
					StringUtils.defaultString(preferencesBean.getVoice()));
			prefs.put(PreferencesBean.DELIVERY_POINT_PROPERTY, StringUtils
					.defaultString(preferencesBean.getDeliveryPoint()));
			prefs.put(PreferencesBean.CITY_PROPERTY,
					StringUtils.defaultString(preferencesBean.getCity()));
			prefs.put(PreferencesBean.ADMIN_AREA_PROPERTY,
					StringUtils.defaultString(preferencesBean.getAdminArea()));
			prefs.put(PreferencesBean.POSTAL_CODE_PROPERTY,
					StringUtils.defaultString(preferencesBean.getPostalCode()));
			prefs.put(PreferencesBean.COUNTRY_PROPERTY,
					StringUtils.defaultString(preferencesBean.getCountry()));
			prefs.put(PreferencesBean.EMAIL_PROPERTY,
					StringUtils.defaultString(preferencesBean.getEmail()));

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
}
