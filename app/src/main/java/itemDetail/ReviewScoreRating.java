package itemDetail;

import android.content.res.Resources;

import com.sygic.travel.sdkdemo.R;


public class ReviewScoreRating {
	public static final float MIN_REVIEW_SCORE_RATING = 6f;

	private double reviewScore;

	public ReviewScoreRating(double reviewScore) {
		this.reviewScore = reviewScore;
	}

	public String getCategory(Resources resources) {
		String category = getCategoryTag(resources);
		category = category.concat(" - ");
		return category;
	}

	public String getCategoryTag(Resources resources) {
		String category = "";
		if(reviewScore >= 9f){
			category = resources.getString(R.string.review_score_wonderful);
		} else if(reviewScore >= 8f){
			category = resources.getString(R.string.review_score_very_good);
		} else if(reviewScore >= 7f){
			category = resources.getString(R.string.review_score_good);
		} else if(reviewScore >= 6f){
			category = resources.getString(R.string.review_score_pleasant);
		}
		return category;
	}
}
