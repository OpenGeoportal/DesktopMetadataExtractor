package net.geocat.mdextractor.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker.StateValue;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import net.geocat.mdextractor.gui.model.PreferencesBean;
import net.geocat.mdextractor.guicomponents.TalendWorker;

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
	private PreferencesBean preferencesBean;
	private JButton createMetadataButton;
	private JProgressBar progressBar;
	private SelectionInList<String> datasetSelectionInList;
	private final Action action = new ProcessCancelAction();
	private TalendWorker worker;

	/**
	 * Create the panel.
	 */
	public MetadataExtractorPanel(PreferencesBean preferencesBean) {
		this.preferencesBean = preferencesBean;
		datasetList = new ArrayListModel<String>();
		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.BUTTON_COLSPEC, },
				new RowSpec[] { FormFactory.DEFAULT_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, FormFactory.GLUE_ROWSPEC,
						RowSpec.decode("bottom:default:grow"),
						FormFactory.DEFAULT_ROWSPEC, }));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane, "1, 1, 1, 4, fill, fill");
		JList<String> list = new JList<String>();
		datasetSelectionInList = new SelectionInList<String>(datasetList);
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

		datasetSelectionInList.addListDataListener(new ListDataListener() {

			@Override
			public void intervalRemoved(ListDataEvent e) {
				SelectionInList<?> source = (SelectionInList<?>) e.getSource();
				if (source.getSize() == 0) {
					createMetadataButton.setEnabled(false);
				}

			}

			@Override
			public void intervalAdded(ListDataEvent e) {
				SelectionInList<?> source = (SelectionInList<?>) e.getSource();
				if (source.getSize() != 0) {
					createMetadataButton.setEnabled(true);
				}

			}

			@Override
			public void contentsChanged(ListDataEvent e) {

			}
		});

		createMetadataButton = new JButton("Create metadata");
		createMetadataButton.setAction(action);
		createMetadataButton.setEnabled(false);
		add(createMetadataButton, "3, 4");

		progressBar = new JProgressBar();
		progressBar.setVisible(false);
		progressBar.setStringPainted(true);
		add(progressBar, "1, 5, default, fill");

	}

	private void cancel() {
		worker.cancel(true);
	}

	private void generateMetadata() {

		worker = new TalendWorker(preferencesBean, datasetList, progressBar);

		worker.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				switch (evt.getPropertyName()) {
				case "progress":
					progressBar.setIndeterminate(false);
					progressBar.setValue((Integer) evt.getNewValue());

					break;
				case "state":
					switch ((StateValue) evt.getNewValue()) {
					case DONE:
						progressBar.setVisible(false);
						action.putValue(Action.NAME, "Extract metadata");
						try {
							worker.get();

						} catch (final CancellationException oops) {
							oops.printStackTrace();
						} catch (InterruptedException e) {
							e.printStackTrace();
						} catch (ExecutionException e) {
							e.printStackTrace();
						} finally {
							worker = null;
						}
						JOptionPane.showMessageDialog(
								MetadataExtractorPanel.this,
								"Metadata extraction finished.");
						break;
					case STARTED:
					case PENDING:
						action.putValue(Action.NAME, "Stop");

						progressBar.setVisible(true);
						progressBar.setIndeterminate(true);
						progressBar.setString(null);
						break;
					}

					break;
				}
			}
		});
		worker.execute();

	}

	private class ProcessCancelAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		public ProcessCancelAction() {
			putValue(NAME, "Extract Metadata");
			putValue(SHORT_DESCRIPTION,
					"Generate metadata files using data extracted from geographical files");
		}

		public void actionPerformed(ActionEvent e) {
			if (worker == null) {
				if (preferencesBean.getResourcesDir() == null
						|| "".equals(preferencesBean.getResourcesDir())) {
					JOptionPane
							.showMessageDialog(MetadataExtractorPanel.this,
									"You must set the path to metadata-properties.csv in the Preferences dialog");
				} else {
					generateMetadata();
				}
			} else {
				cancel();
			}
		}
	}
}
