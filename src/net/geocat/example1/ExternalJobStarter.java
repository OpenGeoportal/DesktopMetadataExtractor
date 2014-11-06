/**
 * 
 */
package net.geocat.example1;

import tufts_metadata_generator.start_0_1.Start;

/**
 * @author JuanLuis
 *
 */
public class ExternalJobStarter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Start talendJob = new Start();
		String[] arguments = new String[] { "--context_param dataDir=C:/Users/JuanLuis/Documents/Projects/tufts/sample-data" };
		talendJob.runJob(arguments);

	}

}
