package itemDetail.referenceList;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sygic.travel.sdk.model.place.Reference;
import com.sygic.travel.sdkdemo.R;

import java.util.List;

import itemDetail.PlaceDetailReferenceUtils;

public class ReferencesListAdapter extends RecyclerView.Adapter<ReferencesListAdapter.ReferenceViewHolder> {
	private List<Reference> references;
	private ReferenceViewHolder.ReferenceClick referenceClick;
	private Resources resources;

	public ReferencesListAdapter(
		List<Reference> references,
		ReferenceViewHolder.ReferenceClick referenceClick,
		Resources resources
	) {
		this.references = references;
		this.referenceClick = referenceClick;
		this.resources = resources;
	}

	@Override
	public ReferenceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View vItem = LayoutInflater.from(parent.getContext())
			.inflate(R.layout.reference_list_item, parent, false);

		return new ReferenceViewHolder(vItem);
	}

	@Override
	public int getItemCount() {
		return references.size();
	}

	@Override
	public void onBindViewHolder(ReferenceViewHolder holder, int position) {
		final Reference reference = references.get(position);

		if(reference.getPrice() == 0){
			holder.llPrice.setVisibility(View.GONE);
		}
		holder.tvTitle.setText(reference.getTitle());
		holder.tvPrice.setText(reference.getPrice() + "");
		PlaceDetailReferenceUtils.renderReferenceFlags(reference, holder.tvFlags, resources);
		holder.root.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				referenceClick.referenceClicked(reference);
			}
		});

	}

	public static class ReferenceViewHolder extends RecyclerView.ViewHolder{
		public TextView tvTitle, tvPrice, tvFlags;
		public LinearLayout llPrice;
		public View root;

		public ReferenceViewHolder(View root){
			super(root);
			this.root = root;
			llPrice = (LinearLayout) root.findViewById(R.id.ll_price);
			tvTitle = (TextView) root.findViewById(R.id.tv_reference_title);
			tvPrice = (TextView) root.findViewById(R.id.tv_reference_price);
			tvFlags = (TextView) root.findViewById(R.id.tv_reference_flags);
		}

		public interface ReferenceClick{
			void referenceClicked(Reference reference);
		}
	}
}
