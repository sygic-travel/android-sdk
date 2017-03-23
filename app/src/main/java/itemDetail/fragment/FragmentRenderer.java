package itemDetail.fragment;

import android.app.Activity;
import android.widget.LinearLayout;

import com.sygic.travel.sdkdemo.R;

import itemDetail.RenderModel;
import itemDetail.RenderModelEntry;
import itemDetail.subviews.AttributionLayoutController;
import itemDetail.subviews.BookingController;
import itemDetail.subviews.ExpandableElementController;
import itemDetail.subviews.ExternalLinksExpandableController;
import itemDetail.subviews.ItemDetailSubviewModel;
import itemDetail.subviews.MainInfoController;
import itemDetail.subviews.MultipleReferencesController;
import itemDetail.subviews.ReferenceController;
import itemDetail.subviews.SimpleLinkController;
import itemDetail.subviews.TagsController;

import com.sygic.travel.sdk.model.place.Reference;


public class FragmentRenderer {
	private final int DIVIDER_STATE_ADDED = 0;
	private final int DIVIDER_STATE_LAST = 1;
	private final int DIVIDER_STATE_NOT = 2;

	private MainInfoController mainInfoController;
	private int dividerState = DIVIDER_STATE_NOT;
	private SimpleLinkController addNoteController, durationController;
	private ItemDetailFragmentFactories factories;



	public Runnable getRenderContent(
		final Activity activity,
		final RenderModel renderModel,
		final ItemDetailFragmentFactories factories,
		final LinearLayout llContent
	){
		this.factories = factories;

		return new Runnable() {
			@Override
			public void run() {
				llContent.removeAllViews();

				for(RenderModelEntry renderModelEntry : renderModel.getData()){
					if(dividerState == DIVIDER_STATE_ADDED){
						dividerState = DIVIDER_STATE_LAST;
					} else if(dividerState == DIVIDER_STATE_LAST){
						dividerState = DIVIDER_STATE_NOT;
					}


					switch(renderModelEntry.getKey()){
						case RenderModel.REFERENCE:{
							new ReferenceController(renderModelEntry.getValue()).render(
								llContent,
								activity,
								factories
							);
							break;
						}

						case RenderModel.MULTIPLE_REFERENCES:{
							new MultipleReferencesController(renderModelEntry.getValue()).render(
								llContent,
								activity,
								factories
							);
							break;
						}

						case RenderModel.PACKED_REFERENCES:
						case RenderModel.LINK:{
							int type = renderModelEntry.getValue().getType();
							SimpleLinkController simpleLinkController = new SimpleLinkController(renderModelEntry.getValue());
							if(type == ItemDetailSubviewModel.ADD_NOTE){
								addNoteController = simpleLinkController;
							} else if(type == ItemDetailSubviewModel.DURATION){
								durationController = simpleLinkController;
							}

							simpleLinkController.render(
								llContent,
								activity,
								factories
							);
							break;
						}

						case RenderModel.EXTERNAL_LINKS:{
							new ExternalLinksExpandableController(renderModelEntry.getValue()).render(
								llContent,
								activity,
								factories
							);
							break;
						}

						case RenderModel.EXPANDABLE_ELEMENT:{
							new ExpandableElementController(renderModelEntry.getValue()).render(
								llContent,
								activity,
								factories
							);
							break;
						}

						case RenderModel.MAIN_LAYOUT:{
							mainInfoController = new MainInfoController(renderModelEntry.getValue());
							mainInfoController.render(
								llContent,
								activity,
								factories
							);
							break;
						}

						case RenderModel.TAGS_LAYOUT:{
							new TagsController(renderModelEntry.getValue()).render(
								llContent,
								activity,
								factories
							);
							break;
						}

						case RenderModel.BOOKING:{
							new BookingController(renderModelEntry.getValue()).render(
								llContent,
								activity,
								factories
							);
							break;
						}

						case RenderModel.ATTRIBUTION:{
							new AttributionLayoutController(renderModelEntry.getValue()).render(
								llContent,
								activity,
								factories
							);
							break;
						}

						case RenderModel.DIVIDER:{
							if(dividerState == DIVIDER_STATE_NOT) {
								llContent.addView(activity.getLayoutInflater().inflate(R.layout.thick_divider, null));
							}
							dividerState = DIVIDER_STATE_ADDED;
							break;
						}
					}
				}

				factories.getAfterRenderRunnable().run();
			}
		};
	}
}