package net.geocat.mdextractor.guicomponents;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

import metadata_generator.rasterjob_0_1.RasterJob;
import metadata_generator.start_0_1.Start;
import metadata_generator.vectorjob_0_1.VectorJob;
import net.geocat.mdextractor.gui.model.PreferencesBean;

import org.apache.commons.lang3.StringUtils;

public class TalendWorker extends SwingWorker<String, String> {
	private static final String CONTEXT_PARAM_PREFIX = "--context_param ";
	private static final String PARAM_DATA_DIR = "dataDir";
	private static final String PARAM_FILE = "file";
	private static final String PARAM_DEFAULT_PROJ = "defaultProjection";
	private static final String PARAM_DEFAULT_MINX = "defaultXMin";
	private static final String PARAM_DEFAULT_MINY = "defaultYMin";
	private static final String PARAM_DEFAULT_MAXX = "defaultXMax";
	private static final String PARAM_DEFAULT_MAXY = "defaultYMax";
	private static final String PARAM_GENERATE_MD_IN_SEPARATE_DIR = "generateMetadataInSeparateDir";
	private static final String PARAM_METADATA_DIR = "metadataDir";
	private static final String PARAM_RESOURCE_DIR = "resourceDir";
	private static final String PARAM_MD_INDIVIDUAL_NAME = "mdIndividualName";
	private static final String PARAM_MD_ORGANISATION = "mdOrganisation";
	private static final String PARAM_MD_POSITION_NAME = "mdPositionName";
	private static final String PARAM_MD_VOICE = "mdVoice";
	private static final String PARAM_MD_DELIVERY_POINT = "mdDeliveryPoint";
	private static final String PARAM_MD_CITY = "mdCity";
	private static final String PARAM_MD_ADMINISTRATIVE_AREA = "mdAdministrativeArea";
	private static final String PARAM_MD_POSTAL_CODE = "mdPostalCode";
	private static final String PARAM_MD_COUNTRY = "mdCountry";
	private static final String PARAM_MD_EMAIL = "mdEmail";

	private static final String PARAM_INDIVIDUAL_NAME = "individualName";
	private static final String PARAM_ORGANISATION = "organisationName";
	private static final String PARAM_POSITION_NAME = "positionName";
	private static final String PARAM_VOICE = "voice";
	private static final String PARAM_DELIVERY_POINT = "deliveryPoint";
	private static final String PARAM_CITY = "city";
	private static final String PARAM_ADMINISTRATIVE_AREA = "administrativeArea";
	private static final String PARAM_POSTAL_CODE = "postalCode";
	private static final String PARAM_COUNTRY = "country";
	private static final String PARAM_EMAIL = "email";

	private PreferencesBean preferences;
	private List<String> inputList;
	private JProgressBar progressBar;
	private FileNameExtensionFilter rasterFilter;
	private FileNameExtensionFilter vectorFilter;

	public TalendWorker(PreferencesBean preferences,
			List<String> inputDirectoryList, JProgressBar progressBar,
			String[] rasterExtensions, String[] vectorExtensions) {
		this.preferences = preferences;
		this.inputList = Collections.unmodifiableList(inputDirectoryList);
		this.progressBar = progressBar;
		rasterFilter = new FileNameExtensionFilter("Raster files",
				rasterExtensions);
		vectorFilter = new FileNameExtensionFilter("Raster files",
				vectorExtensions);

	}

	@Override
	protected String doInBackground() throws Exception {

		List<String> argumentList = new ArrayList<>();
		addParameter(argumentList, PARAM_DEFAULT_PROJ,
				preferences.getDefaultProjection());
		addParameter(argumentList, PARAM_DEFAULT_MINX, preferences.getMinx());
		addParameter(argumentList, PARAM_DEFAULT_MINY, preferences.getMiny());
		addParameter(argumentList, PARAM_DEFAULT_MAXX, preferences.getMaxx());
		addParameter(argumentList, PARAM_DEFAULT_MAXY, preferences.getMiny());

		// Metadata parameters
		addParameter(argumentList, PARAM_MD_INDIVIDUAL_NAME,
				preferences.getMdIndividualName());
		addParameter(argumentList, PARAM_MD_ORGANISATION,
				preferences.getMdOrganisation());
		addParameter(argumentList, PARAM_MD_POSITION_NAME,
				preferences.getMdPositionName());
		addParameter(argumentList, PARAM_MD_VOICE, preferences.getMdVoice());
		addParameter(argumentList, PARAM_MD_DELIVERY_POINT,
				preferences.getMdDeliveryPoint());
		addParameter(argumentList, PARAM_MD_CITY, preferences.getMdCity());
		addParameter(argumentList, PARAM_MD_ADMINISTRATIVE_AREA,
				preferences.getMdAdminArea());
		addParameter(argumentList, PARAM_MD_POSTAL_CODE,
				preferences.getMdPostalCode());
		addParameter(argumentList, PARAM_MD_COUNTRY, preferences.getMdCountry());
		addParameter(argumentList, PARAM_MD_EMAIL, preferences.getMdEmail());

		// Data parameters
		addParameter(argumentList, PARAM_INDIVIDUAL_NAME,
				preferences.getIndividualName());
		addParameter(argumentList, PARAM_ORGANISATION,
				preferences.getOrganisation());
		addParameter(argumentList, PARAM_POSITION_NAME,
				preferences.getPositionName());
		addParameter(argumentList, PARAM_VOICE, preferences.getVoice());
		addParameter(argumentList, PARAM_DELIVERY_POINT,
				preferences.getDeliveryPoint());
		addParameter(argumentList, PARAM_CITY, preferences.getCity());
		addParameter(argumentList, PARAM_ADMINISTRATIVE_AREA,
				preferences.getAdminArea());
		addParameter(argumentList, PARAM_POSTAL_CODE,
				preferences.getPostalCode());
		addParameter(argumentList, PARAM_COUNTRY, preferences.getCountry());
		addParameter(argumentList, PARAM_EMAIL, preferences.getEmail());

		String resourceDir = preferences.getResourcesDir();
		resourceDir = resourceDir.replace("\\", "\\\\");
		File resourceDirFile = new File(resourceDir);
		resourceDir = resourceDirFile.getAbsolutePath().replace("\\", "/");
		if (!resourceDir.endsWith("/")) {
			resourceDir = resourceDir + "/";
		}
		addParameter(argumentList, PARAM_RESOURCE_DIR, resourceDir);
		addParameter(argumentList, PARAM_GENERATE_MD_IN_SEPARATE_DIR,
				preferences.getGenerateInSeparateDir());
		if (preferences.getGenerateInSeparateDir()) {
			String metadataDir = preferences.getOutputDir();
			metadataDir = metadataDir.replace("\\", "\\\\");
			File metadataDirFile = new File(metadataDir);
			metadataDir = metadataDirFile.getAbsolutePath().replace("\\", "/");

			addParameter(argumentList, PARAM_METADATA_DIR, metadataDir);
		}

		int i = 1;
		int percentage = 0;
		for (String currentElement : inputList) {
			if (!isCancelled()) {
				publish(currentElement);
				String escapedFilename = currentElement.replace("\\", "\\\\");
				File currentElementAsFile = new File(currentElement);
				// Copy common arguments into a new list
				List<String> arguments = new ArrayList<>(argumentList);
				if (currentElementAsFile.isDirectory()) {
					// First try to process the entire folder as a Vector and Raster format
//					processVector(escapedFilename, arguments);
//					processRaster(escapedFilename, arguments);
					
					// Process directory recursively with the start job.
					addParameter(arguments, PARAM_DATA_DIR, escapedFilename
							+ "/");

					Start folderJob = new Start();
					String[][] results = folderJob.runJob(arguments
							.toArray(new String[] {}));
					for (String[] ss : results) {
						for (String s : ss) {
							System.out.println(String.format(
									"Processed  %s folder with return values",
									escapedFilename, s));
						}
					}
				} else if (vectorFilter.accept(currentElementAsFile)) {
					processVector(escapedFilename, arguments);
				} else if (rasterFilter.accept(currentElementAsFile)) {
					// Process raster file
					processRaster(escapedFilename, arguments);
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

	private void processRaster(String escapedFilename, List<String> arguments) {
		addParameter(arguments, PARAM_FILE, escapedFilename);
		RasterJob rasterJob = new RasterJob();
		String[][] results = rasterJob.runJob(arguments
				.toArray(new String[] {}));
		for (String[] ss : results) {
			for (String s : ss) {
				System.out
						.println(String
								.format("Processed raster file %s with return values %s",
										escapedFilename, s));
			}
		}
	}

	private void processVector(String escapedFilename, List<String> arguments) {
		// Process vector file
		addParameter(arguments, PARAM_FILE, escapedFilename);
		VectorJob vectorJob = new VectorJob();
		String[][] results = vectorJob.runJob(arguments
				.toArray(new String[] {}));
		for (String[] ss : results) {
			for (String s : ss) {
				System.out
						.println(String
								.format("Processed vector file %s with return values %s",
										escapedFilename, s));
			}
		}
	}

	@Override
	protected void process(List<String> chunks) {
		if (chunks.size() > 0) {
			String directory = chunks.get(chunks.size() - 1);
			progressBar.setString(directory);
		}

	}

	/**
	 * Add parameter to the collection only if value is not null or blank.
	 * 
	 * @param parameters
	 *            collection of parameters.
	 * @param key
	 *            name of the parameter.
	 * @param value
	 *            value of the parameter.
	 */
	private void addParameter(Collection<String> parameters, String key,
			Object value) {
		if (value instanceof String && StringUtils.isNotBlank((String) value)) {
			parameters.add(CONTEXT_PARAM_PREFIX + key + "=" + value);

		} else if (!(value instanceof String) && value != null) {
			parameters.add(CONTEXT_PARAM_PREFIX + key + "=" + value);
		}
	}

}
