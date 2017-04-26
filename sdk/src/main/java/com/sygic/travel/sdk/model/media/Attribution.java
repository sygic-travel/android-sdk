package com.sygic.travel.sdk.model.media;

import com.google.gson.annotations.SerializedName;

/**
 * <p>Attribution of a medium, contains information about author, license and title.</p>
 */
public class Attribution {
	public static final String AUTHOR = "author";
	public static final String AUTHOR_URL = "author_url";
	public static final String LICENSE = "license";
	public static final String LICENSE_URL = "license_url";
	public static final String OTHER = "other";
	public static final String TITLE = "title";
	public static final String TITLE_URL = "title_url";

	@SerializedName(AUTHOR)
	private String author;
	@SerializedName(AUTHOR_URL)
	private String authorUrl;
	@SerializedName(LICENSE)
	private String license;
	@SerializedName(LICENSE_URL)
	private String licenseUrl;
	@SerializedName(OTHER)
	private String other;
	@SerializedName(TITLE)
	private String title;
	@SerializedName(TITLE_URL)
	private String titleUrl;

	public Attribution() {
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAuthorUrl() {
		return authorUrl;
	}

	public void setAuthorUrl(String authorUrl) {
		this.authorUrl = authorUrl;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getLicenseUrl() {
		return licenseUrl;
	}

	public void setLicenseUrl(String licenseUrl) {
		this.licenseUrl = licenseUrl;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleUrl() {
		return titleUrl;
	}

	public void setTitleUrl(String titleUrl) {
		this.titleUrl = titleUrl;
	}
}
