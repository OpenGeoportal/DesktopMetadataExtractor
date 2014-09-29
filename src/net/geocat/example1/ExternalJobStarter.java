/**
 * 
 */
package net.geocat.example1;

import tufts_metadata_generator.start_0_1.start;

/**
 * @author JuanLuis
 *
 */
public class ExternalJobStarter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		start talendJob = new start();
		String[] arguments = new String[]{
				"--context_param dataDir=C:/Users/JuanLuis/Documents/Projects/tufts/sample-data"
		};
		talendJob.runJob(arguments);

	}

}
