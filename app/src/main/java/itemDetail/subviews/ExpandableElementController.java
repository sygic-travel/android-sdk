package itemDetail.subviews;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sygic.travel.sdkdemo.R;

import itemDetail.fragment.PlaceDetailFragmentFactories;


public class ExpandableElementController implements PlaceDetailSubview {
	protected View rootView;
	protected TextView tvTitle, expandedText;
	protected LinearLayout expandableView;
	protected ImageView ivIcon;
	protected boolean open = false;
	protected PlaceDetailSubviewModel dependencies;

	public ExpandableElementController(PlaceDetailSubviewModel dependencies) {
		this.dependencies = dependencies;
	}

	protected void initUiElements(Activity activity){
		rootView = activity.getLayoutInflater().inflate(R.layout.expandable_element_layout, null);
		tvTitle = (TextView) rootView.findViewById(R.id.tv_link_title);
		ivIcon = (ImageView) rootView.findViewById(R.id.sdv_link_icon);
		expandedText = (TextView)rootView.findViewById(R.id.tv_expanded_text);
		expandableView = (LinearLayout)rootView.findViewById(R.id.ll_expendable_part);
	}

	@Override
	public void render(LinearLayout llContent, Activity activity, PlaceDetailFragmentFactories factories) {
		initUiElements(activity);

		BasicExpandableElement basicExpandableElement = (BasicExpandableElement)dependencies;


		tvTitle.setText(basicExpandableElement.getTitle());
		ivIcon.setImageResource(basicExpandableElement.getIconId());
		expandedText.setText(basicExpandableElement.getExpandableText());
		expandableView.setVisibility(View.GONE);

		rootView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				openClose();
			}
		});

		llContent.addView(rootView);
	}

	protected void openClose(){
		if(open){
			expandableView.setVisibility(View.GONE);
			open = false;
		} else {
			expandableView.setVisibility(View.VISIBLE);
			open = true;
		}
	}
}
