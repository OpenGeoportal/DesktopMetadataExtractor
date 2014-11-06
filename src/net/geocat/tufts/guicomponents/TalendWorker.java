package net.geocat.tufts.guicomponents;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import net.geocat.tufts.mdextractor.gui.model.PreferencesBean;
import tufts_metadata_generator.start_0_1.Start;

public class TalendWorker extends SwingWorker<String, String> {
	private static final String CONTEXT_PARAM_PREFIX = "--context_param ";
	private static final String PARAM_DATA_DIR = "dataDir";
	private static final String PARAM_DEFAULT_PROJ = "defaultProjection";
	private static final String PARAM_DEFAULT_MINX = "defaultXMin";
	private static final String PARAM_DEFAULT_MINY = "defaultYMin";
	private static final String PARAM_DEFAULT_MAXX = "defaultXMax";
	private static final String PARAM_DEFAULT_MAXY = "defaultYMax";
	private static final String PARAM_GENERATE_MD_IN_SEPARATE_DIR = "generateMetadataInSeparateDir";
	private static final String PARAM_METADATA_DIR = "metadataDir";
	private static final String PARAM_RESOURCE_DIR = "resourceDir";
	private static final String PARAM_MD_NAME = "mdName";
	private static final String PARAM_MD_ORGANIZATION = "mdOrganization";
	private static final String PARAM_MD_EMAIL = "mdEmail";
	private PreferencesBean preferences;
	private List<String> inputDirectoryList;
	private JProgressBar progressBar;

	public TalendWorker(PreferencesBean preferences,
			List<String> inputDirectoryList, JProgressBar progressBar) {
		this.preferences = preferences;
		this.inputDirectoryList = Collections
				.unmodifiableList(inputDirectoryList);
		this.progressBar = progressBar;

	}

	@Override
	protected String doInBackground() throws Exception {
		Start talendJob = new Start();
		List<String> argumentList = new ArrayList<>();
		argumentList.add(CONTEXT_PARAM_PREFIX + PARAM_DEFAULT_PROJ + "="
				+ preferences.getDefaultProjection());
		argumentList.add(CONTEXT_PARAM_PREFIX + PARAM_DEFAULT_MINX + "="
				+ preferences.getMinx());
		argumentList.add(CONTEXT_PARAM_PREFIX + PARAM_DEFAULT_MINY + "="
				+ preferences.getMiny());
		argumentList.add(CONTEXT_PARAM_PREFIX + PARAM_DEFAULT_MAXX + "="
				+ preferences.getMaxx());
		argumentList.add(CONTEXT_PARAM_PREFIX + PARAM_DEFAULT_MAXY + "="
				+ preferences.getMiny());
		argumentList.add(CONTEXT_PARAM_PREFIX + PARAM_MD_EMAIL + "="
				+ preferences.getEmail());
		argumentList.add(CONTEXT_PARAM_PREFIX + PARAM_MD_NAME + "="
				+ preferences.getName());
		argumentList.add(CONTEXT_PARAM_PREFIX + PARAM_MD_ORGANIZATION + "="
				+ preferences.getOrganization());

		String resourceDir = preferences.getResourcesDir();
		resourceDir = resourceDir.replace("\\", "\\\\");
		File resourceDirFile = new File(resourceDir);
		resourceDir = resourceDirFile.getAbsolutePath().replace("\\", "/");
		if (!resourceDir.endsWith("/")) {
			resourceDir = resourceDir + "/";
		}
		argumentList.add(CONTEXT_PARAM_PREFIX + PARAM_RESOURCE_DIR + "="
				+ resourceDir);

		argumentList.add(CONTEXT_PARAM_PREFIX
				+ PARAM_GENERATE_MD_IN_SEPARATE_DIR + "="
				+ preferences.getGenerateInSeparateDir());
		if (preferences.getGenerateInSeparateDir()) {
			String metadataDir = preferences.getOutputDir();
			metadataDir = metadataDir.replace("\\", "\\\\");
			File metadataDirFile = new File(metadataDir);
			metadataDir = metadataDirFile.getAbsolutePath().replace("\\", "/");

			argumentList.add(CONTEXT_PARAM_PREFIX + PARAM_METADATA_DIR + "="
					+ metadataDir);
		}

		int i = 1;
		int percentage = 0;
		for (String directory : inputDirectoryList) {
			if (!isCancelled()) {
				publish(directory);
				String escapedDirectory = directory.replace("\\", "\\\\");
				List<String> arguments = new ArrayList<>(argumentList);
				arguments.add(CONTEXT_PARAM_PREFIX + PARAM_DATA_DIR + "="
						+ escapedDirectory + "/");
				String[][] results = talendJob.runJob(arguments
						.toArray(new String[] {}));
				for (String[] ss : results) {
					for (String s : ss) {
						System.out.println(s);
					}
				}

				percentage = Math.min((i * 100) / argumentList.size(), 100);
				i++;
				setProgress(percentage);
			} else {
				setProgress(100);
				publish("Cancelled by user");
			}
		}

		return "Finished";
	}

	@Override
	protected void process(List<String> chunks) {
		if (chunks.size() > 0) {
			String directory = chunks.get(chunks.size() - 1);
			progressBar.setString(directory);
		}

	}

}
