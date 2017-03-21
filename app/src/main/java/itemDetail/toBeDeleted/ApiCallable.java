package itemDetail.toBeDeleted;

import com.google.gson.JsonElement;

import org.jdeferred.DeferredCallable;
import org.jdeferred.DeferredManager;

import retrofit2.Call;
import retrofit2.Response;

public class ApiCallable extends DeferredCallable<JsonElement, Double> {
	private static final String DATA = "data";
	private Call<JsonElement> apiCall;

	public ApiCallable(Call<JsonElement> apiCall) {
		this.apiCall = apiCall;
	}

	public ApiCallable(DeferredManager.StartPolicy startPolicy, Call<JsonElement> apiCall) {
		super(startPolicy);
		this.apiCall = apiCall;
	}

	@Override
	public JsonElement call() throws Exception {
		Response<JsonElement> response = apiCall.execute();
		if(response.isSuccessful()) {
			if(response.body() != null) {
				return response.body().getAsJsonObject().get(DATA);
			}
		}
		return null;
	}
}
