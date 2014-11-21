package net.geocat.mdextractor.gui.model;

import com.jgoodies.binding.beans.Model;

public class PreferencesBean extends Model {

	private static final long serialVersionUID = -8406239718039626374L;

	/* Metadata properties */
	public static final String MD_INDIVIDUAL_NAME_PROPERTY = "mdIndividualName";
	public static final String MD_ORGANISATION_PROPERTY = "mdOrganisation";
	public static final String MD_POSITION_NAME_PROPERTY = "mdPositionName";
	public static final String MD_VOICE_PROPERTY = "mdVoice";
	public static final String MD_DELIVERY_POINT_PROPERTY = "mdDeliveryPoint";
	public static final String MD_CITY_PROPERTY = "mdCity";
	public static final String MD_ADMIN_AREA_PROPERTY = "mdAdminArea";
	public static final String MD_POSTAL_CODE_PROPERTY = "mdPostalCode";
	public static final String MD_COUNTRY_PROPERTY = "mdCountry";
	public static final String MD_EMAIL_PROPERTY = "mdEmail";

	/* Data properties */
	public static final String INDIVIDUAL_NAME_PROPERTY = "individualName";
	public static final String ORGANISATION_PROPERTY = "organisation";
	public static final String POSITION_NAME_PROPERTY = "positionName";
	public static final String VOICE_PROPERTY = "voice";
	public static final String DELIVERY_POINT_PROPERTY = "deliveryPoint";
	public static final String CITY_PROPERTY = "city";
	public static final String ADMIN_AREA_PROPERTY = "adminArea";
	public static final String POSTAL_CODE_PROPERTY = "postalCode";
	public static final String COUNTRY_PROPERTY = "country";
	public static final String EMAIL_PROPERTY = "email";

	/* Geo properties */
	public static final String DEFAULT_PROYECTION_PROPERTY = "defaultProjection";
	public static final String MINX_PROPERTY = "minx";
	public static final String MINY_PROPERTY = "miny";
	public static final String MAXX_PROPERTY = "maxx";
	public static final String MAXY_PROPERTY = "maxy";
	public static final String GENERATE_IN_SEPARATE_DIR_PROPERTY = "generateInSeparateDir";
	public static final String OUTPUT_DIR_PROPERTY = "outputDir";
	public static final String RESOURCES_DIR_PROPERTY = "resourcesDir";

	/* Metadata properties */
	private String mdIndividualName;
	private String mdOrganisation;
	private String mdPositionName;
	private String mdVoice;
	private String mdDeliveryPoint;
	private String mdCity;
	private String mdAdminArea;
	private String mdPostalCode;
	private String mdCountry;
	private String mdEmail;

	/* Data properties */
	private String individualName;
	private String organisation;
	private String positionName;
	private String voice;
	private String deliveryPoint;
	private String city;
	private String adminArea;
	private String postalCode;
	private String country;
	private String email;

	/* Geo properties */
	private String defaultProjection;
	private Double minx;
	private Double miny;
	private Double maxx;
	private Double maxy;
	private Boolean generateInSeparateDir;
	private String outputDir;
	private String resourcesDir;

	/**
	 * @return the adminArea
	 */
	public String getAdminArea() {
		return adminArea;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @return the defaultProjection
	 */
	public String getDefaultProjection() {
		return defaultProjection;
	}

	/**
	 * @return the deliveryPoint
	 */
	public String getDeliveryPoint() {
		return deliveryPoint;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the generateInSeparateDir
	 */
	public Boolean getGenerateInSeparateDir() {
		return generateInSeparateDir;
	}

	/**
	 * @return the individualName
	 */
	public String getIndividualName() {
		return individualName;
	}

	/**
	 * @return the maxx
	 */
	public Double getMaxx() {
		return maxx;
	}

	/**
	 * @return the maxy
	 */
	public Double getMaxy() {
		return maxy;
	}

	/**
	 * @return the mdAdminArea
	 */
	public String getMdAdminArea() {
		return mdAdminArea;
	}

	/**
	 * @return the mdCity
	 */
	public String getMdCity() {
		return mdCity;
	}

	/**
	 * @return the mdCountry
	 */
	public String getMdCountry() {
		return mdCountry;
	}

	/**
	 * @return the mdDeliveryPoint
	 */
	public String getMdDeliveryPoint() {
		return mdDeliveryPoint;
	}

	/**
	 * @return the mdEmail
	 */
	public String getMdEmail() {
		return mdEmail;
	}

	/**
	 * @return the mdIndividualName
	 */
	public String getMdIndividualName() {
		return mdIndividualName;
	}

	/**
	 * @return the mdOrganisation
	 */
	public String getMdOrganisation() {
		return mdOrganisation;
	}

	/**
	 * @return the mdPositionName
	 */
	public String getMdPositionName() {
		return mdPositionName;
	}

	/**
	 * @return the mdPostalCode
	 */
	public String getMdPostalCode() {
		return mdPostalCode;
	}

	/**
	 * @return the mdVoice
	 */
	public String getMdVoice() {
		return mdVoice;
	}

	/**
	 * @return the minx
	 */
	public Double getMinx() {
		return minx;
	}

	/**
	 * @return the miny
	 */
	public Double getMiny() {
		return miny;
	}

	/**
	 * @return the organisation
	 */
	public String getOrganisation() {
		return organisation;
	}

	/**
	 * @return the outputDir
	 */
	public String getOutputDir() {
		return outputDir;
	}

	/**
	 * @return the positionName
	 */
	public String getPositionName() {
		return positionName;
	}

	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @return the resourcesDir
	 */
	public String getResourcesDir() {
		return resourcesDir;
	}

	/**
	 * @return the voice
	 */
	public String getVoice() {
		return voice;
	}

	/**
	 * @param adminArea
	 *            the adminArea to set
	 */
	public void setAdminArea(String adminArea) {
		String oldValue = this.adminArea;
		this.adminArea = adminArea;
		firePropertyChange(ADMIN_AREA_PROPERTY, oldValue, this.adminArea);
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		String oldValue = this.city;
		this.city = city;
		firePropertyChange(CITY_PROPERTY, oldValue, this.city);
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		String oldValue = this.country;
		this.country = country;
		firePropertyChange(COUNTRY_PROPERTY, oldValue, this.country);
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
	 * @param deliveryPoint
	 *            the deliveryPoint to set
	 */
	public void setDeliveryPoint(String deliveryPoint) {
		String oldValue = this.deliveryPoint;
		this.deliveryPoint = deliveryPoint;
		firePropertyChange(DELIVERY_POINT_PROPERTY, oldValue,
				this.deliveryPoint);
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		String oldValue = this.email;
		this.email = email;
		firePropertyChange(EMAIL_PROPERTY, oldValue, this.email);
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
	 * @param individualName
	 *            the individualName to set
	 */
	public void setIndividualName(String individualName) {
		String oldValue = this.individualName;
		this.individualName = individualName;
		firePropertyChange(INDIVIDUAL_NAME_PROPERTY, oldValue,
				this.individualName);
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
	 * @param maxy
	 *            the maxy to set
	 */
	public void setMaxy(Double maxy) {
		Double oldMaxy = this.maxy;
		this.maxy = maxy;
		firePropertyChange(MAXY_PROPERTY, oldMaxy, this.maxy);
	}

	/**
	 * @param mdAdminArea
	 *            the mdAdminArea to set
	 */
	public void setMdAdminArea(String mdAdminArea) {
		String oldValue = this.adminArea;
		this.mdAdminArea = mdAdminArea;
		firePropertyChange(MD_ADMIN_AREA_PROPERTY, oldValue, this.mdAdminArea);
	}

	/**
	 * @param mdCity
	 *            the mdCity to set
	 */
	public void setMdCity(String mdCity) {
		String oldValue = this.mdCity;
		this.mdCity = mdCity;
		firePropertyChange(MD_CITY_PROPERTY, oldValue, this.mdCity);
	}

	/**
	 * @param mdCountry
	 *            the mdCountry to set
	 */
	public void setMdCountry(String mdCountry) {
		String oldValue = this.mdCountry;
		this.mdCountry = mdCountry;
		firePropertyChange(MD_COUNTRY_PROPERTY, oldValue, this.mdCountry);
	}

	/**
	 * @param mdDeliveryPoint
	 *            the mdDeliveryPoint to set
	 */
	public void setMdDeliveryPoint(String mdDeliveryPoint) {
		String oldValue = this.mdDeliveryPoint;
		this.mdDeliveryPoint = mdDeliveryPoint;
		firePropertyChange(MD_DELIVERY_POINT_PROPERTY, oldValue,
				this.mdDeliveryPoint);
	}

	/**
	 * @param mdEmail
	 *            the mdEmail to set
	 */
	public void setMdEmail(String email) {
		String oldEmail = this.mdEmail;
		this.mdEmail = email;
		firePropertyChange(MD_EMAIL_PROPERTY, oldEmail, this.mdEmail);
	}

	/**
	 * @param mdIndividualName
	 *            the mdIndividualName to set
	 */
	public void setMdIndividualName(String name) {
		String oldName = this.mdIndividualName;
		this.mdIndividualName = name;
		firePropertyChange(MD_INDIVIDUAL_NAME_PROPERTY, oldName,
				this.mdIndividualName);

	}

	/**
	 * @param mdOrganisation
	 *            the mdOrganisation to set
	 */
	public void setMdOrganisation(String organization) {
		String oldOrganization = this.mdOrganisation;
		this.mdOrganisation = organization;
		firePropertyChange(MD_ORGANISATION_PROPERTY, oldOrganization,
				this.mdOrganisation);
	}

	/**
	 * @param mdPositionName
	 *            the mdPositionName to set
	 */
	public void setMdPositionName(String mdPositionName) {
		String oldValue = this.mdPositionName;
		this.mdPositionName = mdPositionName;
		firePropertyChange(MD_POSITION_NAME_PROPERTY, oldValue,
				this.mdPositionName);

	}

	/**
	 * @param mdPostalCode
	 *            the mdPostalCode to set
	 */
	public void setMdPostalCode(String mdPostalCode) {
		String oldValue = this.mdPostalCode;
		this.mdPostalCode = mdPostalCode;
		firePropertyChange(MD_POSTAL_CODE_PROPERTY, oldValue, this.mdPostalCode);
	}

	/**
	 * @param mdVoice
	 *            the mdVoice to set
	 */
	public void setMdVoice(String mdVoice) {
		String oldValue = this.mdVoice;
		this.mdVoice = mdVoice;
		firePropertyChange(MD_VOICE_PROPERTY, oldValue, this.mdVoice);
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
	 * @param miny
	 *            the miny to set
	 */
	public void setMiny(Double miny) {
		Double oldMiny = this.miny;
		this.miny = miny;
		firePropertyChange(MINY_PROPERTY, oldMiny, this.miny);
	}

	/**
	 * @param organisation
	 *            the organisation to set
	 */
	public void setOrganisation(String organisation) {
		String oldValue = this.organisation;
		this.organisation = organisation;
		firePropertyChange(ORGANISATION_PROPERTY, oldValue, this.organisation);
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
	 * @param positionName
	 *            the positionName to set
	 */
	public void setPositionName(String positionName) {
		String oldValue = this.positionName;
		this.positionName = positionName;
		firePropertyChange(POSITION_NAME_PROPERTY, oldValue, this.positionName);
	}

	/**
	 * @param postalCode
	 *            the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		String oldValue = this.postalCode;
		this.postalCode = postalCode;
		firePropertyChange(POSTAL_CODE_PROPERTY, oldValue, this.postalCode);
	}

	public void setResourcesDir(String resourcesDir) {
		String oldResourcesDir = this.resourcesDir;
		this.resourcesDir = resourcesDir;
		firePropertyChange(RESOURCES_DIR_PROPERTY, oldResourcesDir,
				this.resourcesDir);
	}

	/**
	 * @param voice
	 *            the voice to set
	 */
	public void setVoice(String voice) {
		String oldValue = this.voice;
		this.voice = voice;
		firePropertyChange(VOICE_PROPERTY, oldValue, this.voice);
	}

}
