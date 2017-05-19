package com.sygic.travel.sdk.model.place;

import com.google.gson.annotations.SerializedName;
import com.sygic.travel.sdk.model.media.MainMedia;

import java.util.List;

/**
 * <p>Place detailed information.</p>
 */
public class Detail {
	private static final String TAGS = "tags";
	private static final String DESCRIPTION = "description";
	private static final String ADDRESS = "address";
	private static final String ADMISSION = "admission";
	private static final String DURATION = "duration";
	private static final String EMAIL = "email";
	private static final String OPENING_HOURS = "opening_hours";
	private static final String PHONE = "phone";
	private static final String MAIN_MEDIA = "main_media";
	private static final String REFERENCES = "references";

	@SerializedName(TAGS)
	private List<TagStats> tags;

	@SerializedName(DESCRIPTION)
	private Description description;

	@SerializedName(ADDRESS)
	private String address;

	@SerializedName(ADMISSION)
	private String admission;

	@SerializedName(DURATION)
	private int duration;

	@SerializedName(EMAIL)
	private String email;

	@SerializedName(OPENING_HOURS)
	private String openingHours;

	@SerializedName(PHONE)
	private String phone;

	@SerializedName(MAIN_MEDIA)
	private MainMedia mainMedia;

	@SerializedName(REFERENCES)
	private List<Reference> references;

	public Detail() {
	}

	public List<TagStats> getTags() {
		return tags;
	}

	public void setTags(List<TagStats> tags) {
		this.tags = tags;
	}

	public Description getDescription() {
		return description;
	}

	public void setDescription(Description description) {
		this.description = description;
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

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public MainMedia getMainMedia() {
		return mainMedia;
	}

	public void setMainMedia(MainMedia mainMedia) {
		this.mainMedia = mainMedia;
	}

	public List<Reference> getReferences() {
		return references;
	}

	public void setReferences(List<Reference> references) {
		this.references = references;
	}
}
