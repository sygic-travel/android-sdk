package itemDetail.toBeDeleted;

import com.google.gson.JsonElement;

import org.jdeferred.AlwaysCallback;
import org.jdeferred.Deferred;
import org.jdeferred.DeferredManager;
import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import retrofit2.Call;

public class PromisesManager {
	private DeferredManager deferredManager;
	private Promise promise;
	private Deferred<Object, String, Integer> chainDeferred;

	public PromisesManager(DeferredManager deferredManager) {
		this.deferredManager = deferredManager;
	}

	public PromisesManager when(Callable callable) {
		promise = deferredManager.when(callable);
		return this;
	}

	public final PromisesManager when(Call<JsonElement> apiCalls){
		ApiCallable apiCallable = new ApiCallable(apiCalls);
		promise = deferredManager.when(apiCallable);
		return this;
	}

	public final PromisesManager when(List<Call<JsonElement>> apiCalls){
		ApiCallable[] apiCallables = new ApiCallable[apiCalls.size()];
		for(int i = 0; i < apiCalls.size(); i++) {
			apiCallables[i] = new ApiCallable(apiCalls.get(i));
		}

		promise = deferredManager.when(apiCallables);
		return this;
	}

	public final PromisesManager dummyWhen(){
		promise = deferredManager.when(new Runnable() {
			@Override
			public void run() {

			}
		});
		return this;
	}

	public PromisesManager always(AlwaysCallback callback){
		promise = promise.always(callback);
		return this;
	}

	public PromisesManager done(DoneCallback<?> doneCallback){
		promise = promise.done(doneCallback);
		return this;
	}

	public PromisesManager fail(FailCallback<?> failCallback){
		promise = promise.fail(failCallback);
		return this;
	}

	public boolean isPending(){
		return promise.isPending();
	}

	public Promise getPromise() {
		return promise;
	}

	public void initNewChainedCall(){
		chainDeferred = new DeferredObject<>();
	}

	public Promise<Object, String, Integer> getChainPromise(){
		return chainDeferred.promise();
	}
}
