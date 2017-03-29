package com.sygic.travel.sdk.model.place;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by michal.murin on 16.2.2017.
 */

public class Reference {
	public static final String ID = "id";
	public static final String TITLE = "title";
	public static final String TYPE = "type";
	public static final String LANGUAGE_ID = "language_id";
	public static final String URL = "url";
	public static final String OFFLINE_FILE = "offline_file";
	public static final String SUPPLIER = "supplier";
	public static final String PRIORITY = "priority";
	public static final String IS_PREMIUM = "is_premium";
	public static final String CURRENCY = "currency";
	public static final String PRICE = "price";
	public static final String FLAGS = "flags";

	@SerializedName(ID)
	private int id;
	@SerializedName(TITLE)
	private String title;
	@SerializedName(TYPE)
	private String type;
	@SerializedName(LANGUAGE_ID)
	private String languageId;
	@SerializedName(URL)
	private String url;
	@SerializedName(OFFLINE_FILE)
	private String offlineFile;
	@SerializedName(SUPPLIER)
	private String supplier;
	@SerializedName(PRIORITY)
	private int priority;
	@SerializedName(IS_PREMIUM)
	private boolean isPremium;
	@SerializedName(CURRENCY)
	private String currency;
	@SerializedName(PRICE)
	private float price;
	@SerializedName(FLAGS)
	private List<String> flags;

	public Reference() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLanguageId() {
		return languageId;
	}

	public void setLanguageId(String languageId) {
		this.languageId = languageId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOfflineFile() {
		return offlineFile;
	}

	public void setOfflineFile(String offlineFile) {
		this.offlineFile = offlineFile;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public boolean isPremium() {
		return isPremium;
	}

	public void setPremium(boolean premium) {
		isPremium = premium;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public List<String> getFlags() {
		return flags;
	}

	public void setFlags(List<String> flags) {
		this.flags = flags;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
