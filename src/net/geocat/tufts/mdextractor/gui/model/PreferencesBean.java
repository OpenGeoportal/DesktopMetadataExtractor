package net.geocat.tufts.mdextractor.gui.model;

import com.jgoodies.binding.beans.Model;

public class PreferencesBean extends Model {

	private static final long serialVersionUID = -8406239718039626374L;

	public static final String NAME_PROPERTY = "name";
	public static final String ORGANIZATION_PROPERTY = "organization";
	public static final String EMAIL_PROPERTY = "email";
	public static final String DEFAULT_PROYECTION_PROPERTY = "defaultProjection";
	public static final String MINX_PROPERTY = "minx";
	public static final String MINY_PROPERTY = "miny";
	public static final String MAXX_PROPERTY = "maxx";
	public static final String MAXY_PROPERTY = "maxy";
	public static final String GENERATE_IN_SEPARATE_DIR_PROPERTY = "generateInSeparateDir";
	public static final String OUTPUT_DIR_PROPERTY = "outputDir";

	private String name;
	private String organization;
	private String email;
	private String defaultProjection;
	private Double minx;
	private Double miny;
	private Double maxx;
	private Double maxy;
	private Boolean generateInSeparateDir;
	private String outputDir;

	/**
	 * @return the generateInSeparateDir
	 */
	public Boolean getGenerateInSeparateDir() {
		return generateInSeparateDir;
	}

	/**
	 * @param generateInSeparateDir
	 *            the generateInSeparateDir to set
	 */
	public void setGenerateInSeparateDir(Boolean generateInSeparateDir) {
		Boolean oldGenerateInSeparateDir = this.generateInSeparateDir;
		this.generateInSeparateDir = generateInSeparateDir;
		firePropertyChange(GENERATE_IN_SEPARATE_DIR_PROPERTY,
				oldGenerateInSeparateDir, this.generateInSeparateDir);
	}

	/**
	 * @return the outputDir
	 */
	public String getOutputDir() {
		return outputDir;
	}

	/**
	 * @param outputDir
	 *            the outputDir to set
	 */
	public void setOutputDir(String outputDir) {
		String oldDir = this.outputDir;
		this.outputDir = outputDir;
		firePropertyChange(OUTPUT_DIR_PROPERTY, oldDir, this.outputDir);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		String oldName = this.name;
		this.name = name;
		firePropertyChange(NAME_PROPERTY, oldName, this.name);

	}

	/**
	 * @return the organization
	 */
	public String getOrganization() {
		return organization;
	}

	/**
	 * @param organization
	 *            the organization to set
	 */
	public void setOrganization(String organization) {
		String oldOrganization = this.organization;
		this.organization = organization;
		firePropertyChange(ORGANIZATION_PROPERTY, oldOrganization,
				this.organization);
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		String oldEmail = this.email;
		this.email = email;
		firePropertyChange(EMAIL_PROPERTY, oldEmail, this.email);
	}

	/**
	 * @return the defaultProjection
	 */
	public String getDefaultProjection() {
		return defaultProjection;
	}

	/**
	 * @param defaultProjection
	 *            the defaultProjection to set
	 */
	public void setDefaultProjection(String defaultProjection) {
		String oldProjection = this.defaultProjection;
		this.defaultProjection = defaultProjection;
		firePropertyChange(DEFAULT_PROYECTION_PROPERTY, oldProjection,
				this.defaultProjection);
	}

	/**
	 * @return the minx
	 */
	public Double getMinx() {
		return minx;
	}

	/**
	 * @param minx
	 *            the minx to set
	 */
	public void setMinx(Double minx) {
		Double oldMinx = this.minx;
		this.minx = minx;
		firePropertyChange(MINX_PROPERTY, oldMinx, this.minx);
	}

	/**
	 * @return the miny
	 */
	public Double getMiny() {
		return miny;
	}

	/**
	 * @param miny
	 *            the miny to set
	 */
	public void setMiny(Double miny) {
		Double oldMiny = this.miny;
		this.miny = miny;
		firePropertyChange(MINY_PROPERTY, oldMiny, this.miny);
	}

	/**
	 * @return the maxx
	 */
	public Double getMaxx() {
		return maxx;
	}

	/**
	 * @param maxx
	 *            the maxx to set
	 */
	public void setMaxx(Double maxx) {
		Double oldMaxx = this.maxx;
		this.maxx = maxx;
		firePropertyChange(MAXX_PROPERTY, oldMaxx, this.maxx);
	}

	/**
	 * @return the maxy
	 */
	public Double getMaxy() {
		return maxy;
	}

	/**
	 * @param maxy
	 *            the maxy to set
	 */
	public void setMaxy(Double maxy) {
		Double oldMaxy = this.maxy;
		this.maxy = maxy;
		firePropertyChange(MAXY_PROPERTY, oldMaxy, this.maxy);
	}

}
