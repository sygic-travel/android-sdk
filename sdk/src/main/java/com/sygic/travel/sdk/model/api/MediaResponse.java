package com.sygic.travel.sdk.model.api;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.sygic.travel.sdk.Parser;
import com.sygic.travel.sdk.model.media.Medium;

import java.util.List;

/**
 * <p>Response containing a list of place media. Suitable for a gallery.</p>
 */
public class MediaResponse extends StResponse {
	private static final String MEDIA = "media";
	private List<Medium> media;

	private JsonObject data;

	public Object getData() {
		if(media == null && data != null) {
			media = Parser.parseJson(
				data.get(MEDIA).toString(),
				new TypeToken<List<Medium>>(){}.getType()
			);
		}
		return media;
	}
}
