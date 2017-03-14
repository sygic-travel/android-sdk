package com.sygic.travel.sdk.model.place;

import com.google.gson.annotations.SerializedName;

/**
 * Created by michal.murin on 16.2.2017.
 */

/*
class Detail {
    address: string
    admission: string
    description: Description
    email: string
    duration: int
    openingHours: string
    phone: string
    mainMedia: array<Medium>
    references: array<Reference>
    tags: array<string>
}
*/

public class Detail extends Place{ // shall we do this? (extends Place)
	public static final String ADDRESS = "address";
	public static final String ADMISSION = "admission";
	public static final String DESCRIPTION = "description";
	public static final String EMAIL = "email";
	public static final String DURATION = "duration";
	public static final String OPENING_HOURS = "opening_hours";
	public static final String PHONE = "phone";
	public static final String MAIN_MEDIA = "main_media";
	public static final String REFERENCES = "references";
	public static final String TAGS = "tags";

	@SerializedName(ADDRESS)
	private String address;

	@SerializedName(ADMISSION)
	private String admission;

	@SerializedName(DESCRIPTION)
	private String description;

	@SerializedName(EMAIL)
	private String email;

	@SerializedName(DURATION)
	private String duration;

	@SerializedName(OPENING_HOURS)
	private String openingHours;

	@SerializedName(PHONE)
	private String phone;

	@SerializedName(MAIN_MEDIA)
	private String mainMedia;

	@SerializedName(REFERENCES)
	private String references;

	@SerializedName(TAGS)
	private String tags;

	public Detail() {
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAdmission() {
		return admission;
	}

	public void setAdmission(String admission) {
		this.admission = admission;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getOpeningHours() {
		return openingHours;
	}

	public void setOpeningHours(String openingHours) {
		this.openingHours = openingHours;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMainMedia() {
		return mainMedia;
	}

	public void setMainMedia(String mainMedia) {
		this.mainMedia = mainMedia;
	}

	public String getReferences() {
		return references;
	}

	public void setReferences(String references) {
		this.references = references;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
}
