package com.sygic.travel.sdk.model.place;

import com.google.gson.annotations.SerializedName;
import com.sygic.travel.sdk.model.media.Media;

import java.util.List;

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

public class Detail extends Place { // shall we do this? (extends Place)
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
	private Description description;

	@SerializedName(EMAIL)
	private String email;

	@SerializedName(DURATION)
	private String duration;

	@SerializedName(OPENING_HOURS)
	private String openingHours;

	@SerializedName(PHONE)
	private String phone;

	@SerializedName(MAIN_MEDIA)
	private Media mainMedia;

	@SerializedName(REFERENCES)
	private List<Reference> references;

	@SerializedName(TAGS)
	private List<TagStats> tags;

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

	public Description getDescription() {
		return description;
	}

	public void setDescription(Description description) {
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

	public Media getMainMedia() {
		return mainMedia;
	}

	public void setMainMedia(Media mainMedia) {
		this.mainMedia = mainMedia;
	}

	public List<Reference> getReferences() {
		return references;
	}

	public void setReferences(List<Reference> references) {
		this.references = references;
	}

	public List<TagStats> getTags() {
		return tags;
	}

	public void setTags(List<TagStats> tags) {
		this.tags = tags;
	}
}
