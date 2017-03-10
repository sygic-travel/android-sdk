package com.sygic.travel.sdk.model.media;

import com.google.gson.annotations.SerializedName;

/**
 * Created by michal.murin on 16.2.2017.
 */

public class Original {
	public static final String WIDTH = "width";
	public static final String HEIGHT = "height";
	public static final String SIZE = "size";

	@SerializedName(WIDTH)
	private int width;

	@SerializedName(HEIGHT)
	private int height;

	@SerializedName(SIZE)
	private int size;

	public Original() {
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
