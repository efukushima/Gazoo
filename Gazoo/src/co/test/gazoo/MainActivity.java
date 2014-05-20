package co.test.gazoo;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	private static final int REQUEST_IMAGE_CAPTURE = 0;
	private static final String TAG = null;
	private static Uri mImageUri = null;
	private static Uri mTmpImageUri = null;
	private static DatePickerDialog datePickerDialog; 
	
	DatePickerDialog.OnDateSetListener DateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(android.widget.DatePicker datePicker, int year,
				int monthOfYear, int dayOfMonth){
			Toast.makeText(co.test.gazoo.MainActivity.this,
					"year:" + year + "monthOfYear:" + monthOfYear
							+ "dayOfMonth:" + dayOfMonth, Toast.LENGTH_SHORT)
					.show();
			Log.d("DatePicker", "year:" + year + "monthOfYear:" + monthOfYear
					+ "dayOfMonth:" + dayOfMonth);
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if(mImageUri!=null){
			this.setPhoto();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
		
	public void onSelectDate(View v){
		//日付の選択
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int monthOfYear = calendar.get(Calendar.MONTH);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		
		datePickerDialog = new DatePickerDialog(this,
				android.R.style.Theme_Black_NoTitleBar, DateSetListener,
				year, monthOfYear, dayOfMonth);
		datePickerDialog.show();
	}
	
	public void onClickToCamera(View v){
		//画像をUriで取得する
		mTmpImageUri = mImageUri;
		mImageUri = getPhotoUri();
		Intent intent = new Intent();
		intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
		startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == REQUEST_IMAGE_CAPTURE) {
	        Log.d(TAG, "onActivityResult");
	        if (resultCode == RESULT_OK) {
	        	this.setPhoto();
	        }else{
	        	mImageUri = mTmpImageUri;
	        	this.setPhoto();
	        }
	    }
	}
	
	/**
	 * 画像の表示
	 */
	public void setPhoto(){
		ImageView imageView = (ImageView)findViewById(R.id.imageView);
    	imageView.setImageURI(mImageUri);		
	}
	
	
	/**
     * 画像のディレクトリパスの取得
     * @return
     */
    private String getDirPath() {
        String dirPath = "";
        File photoDir = null;
        File extStorageDir = Environment.getExternalStorageDirectory();
        if (extStorageDir.canWrite()) {
            photoDir = new File(extStorageDir.getPath() + "/" + getPackageName());
        }
        if (photoDir != null) {
            if (!photoDir.exists()) {
                photoDir.mkdirs();
            }
            if (photoDir.canWrite()) {
                dirPath = photoDir.getPath();
            }
        }
        return dirPath;
    }
 
    /**
     * 画像のUriの取得
     * @return
     */
    private Uri getPhotoUri() {
        long currentTimeMillis = System.currentTimeMillis();
        Date today = new Date(currentTimeMillis);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String title = dateFormat.format(today);
        String dirPath = getDirPath();
        String fileName = "samplecameraintent_" + title + ".jpg";
        String path = dirPath + "/" + fileName;
        File file = new File(path);
        ContentValues values = new ContentValues();
        values.put(Images.Media.TITLE, title);
        values.put(Images.Media.DISPLAY_NAME, fileName);
        values.put(Images.Media.MIME_TYPE, "image/jpeg");
        values.put(Images.Media.DATA, path);
        values.put(Images.Media.DATE_TAKEN, currentTimeMillis);
        if (file.exists()) {
            values.put(Images.Media.SIZE, file.length());
        }
        Uri uri = getContentResolver().insert(Images.Media.EXTERNAL_CONTENT_URI, values);
        return uri;
    }

}

