package itemDetail;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.sygic.travel.sdkdemo.R;

import itemDetail.toBeDeleted.Utils;

public class Screen extends AppCompatActivity {
	protected Toolbar toolbar;
	protected ActionBar supportActionBar;


	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

	}

	protected void setToolbar(){
		setToolbar(true);
	}

	protected void setToolbar(boolean updatePadding){
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		if(toolbar != null) {
			setSupportActionBar(toolbar);
			supportActionBar = getSupportActionBar();
		}
		if(updatePadding) {
			toolbar.setPadding(0, Utils.getStatusBarHeight(this), 0, 0);
		}
	}
}
