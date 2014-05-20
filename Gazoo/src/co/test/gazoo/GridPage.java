package co.test.gazoo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;

public class GridPage extends ActionBarActivity {
	//View—p
	private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
	private final int MP = ViewGroup.LayoutParams.MATCH_PARENT;

	private String[] animal = {"1","2","3","4","66","12"};
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grid_page);
				
		LinearLayout linearLayout = new LinearLayout(this);
		linearLayout.setOrientation(LinearLayout.HORIZONTAL);
		setContentView(linearLayout);
				
		GridView grid =new GridView(this);
		grid.setNumColumns(3);
		linearLayout.addView(grid, createParam(WC, MP));
				
		ArrayAdapter<String> arrayAdapter 
		= new ArrayAdapter<String>(this, R.layout.row, R.id.textViews, animal);
				
		grid.setAdapter(arrayAdapter);
	}
			
	private LinearLayout.LayoutParams createParam(int w,int h){
		return new LinearLayout.LayoutParams(w, h);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.grid_page, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onIntent(View v){
		this.finish();
	}
	

}
