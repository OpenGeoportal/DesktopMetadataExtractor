package net.geocat.tufts.mdextractor.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.common.collect.ArrayListModel;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class MetadataExtractorPanel extends JPanel {

	private static final long serialVersionUID = -4180494189643130908L;
	private List<String> datasetList;
	private JFileChooser fileChooser;

	/**
	 * Create the panel.
	 */
	public MetadataExtractorPanel() {
		datasetList = new ArrayListModel<String>();
		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.BUTTON_COLSPEC, },
				new RowSpec[] { FormFactory.DEFAULT_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, FormFactory.GLUE_ROWSPEC,
						RowSpec.decode("bottom:default:grow"), }));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane, "1, 1, 1, 4, fill, fill");
		JList<String> list = new JList<String>();
		final SelectionInList<String> datasetSelectionInList = new SelectionInList<String>(
				datasetList);
		Bindings.bind(list, datasetSelectionInList);

		JButton addDatasetButton = new JButton("Add datasets");
		addDatasetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnValue = fileChooser
						.showOpenDialog(MetadataExtractorPanel.this);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					String filePath = selectedFile.getAbsolutePath();
					if (!datasetList.contains(filePath)) {
						datasetList.add(filePath);
					}
				}
			}
		});
		add(addDatasetButton, "3, 1");

		JButton removeDatasetsButton = new JButton("Remove datasets");
		removeDatasetsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = datasetSelectionInList.getSelectionIndex();
				if (selectedIndex >= 0) {
					datasetList.remove(selectedIndex);
				}
			}
		});
		add(removeDatasetsButton, "3, 2");

		scrollPane.setViewportView(list);

		JButton CreateMetadataButton = new JButton("Create metadata");
		add(CreateMetadataButton, "3, 4");

	}

}
