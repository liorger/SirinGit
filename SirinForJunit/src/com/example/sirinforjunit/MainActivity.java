package com.example.sirinforjunit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{
	//public class MainActivity extends Activity{

	public static final String DATA_PATH = Environment.getExternalStorageDirectory().toString() + "/SirinExam/";
	public static final String FolderPath = "/storage/emulated/0/Pictures/TextImages/";
	public static final String lang = "eng";
	private static final String TAG = "SirinExam.java";
	protected TextView _field;
	protected Mat imgMAT;
	protected String mPicturePath;
	protected Bitmap bitmap;
	protected String recognizedText;

	private GetText TextProvider;
	private PrepareImage prep;

	// GUI components
	private Button OkButton;
	private ImageView image;
	private EditText mEditText;

	private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
		@Override
		public void onManagerConnected(int status) {
			switch (status) {
			case LoaderCallbackInterface.SUCCESS:
			{
				Log.i(TAG, "OpenCV loaded successfully");
			} break;
			default:
			{
				super.onManagerConnected(status);
			} break;
			}
		}
	};


	@Override
	public void onCreate(Bundle savedInstanceState) {

		String[] paths = new String[] { DATA_PATH, DATA_PATH + "tessdata/" };

		for (String path : paths) {
			File dir = new File(path);
			if (!dir.exists()) {
				if (!dir.mkdirs()) {
					Log.v(TAG, "ERROR: Creation of directory " + path + " on sdcard failed");
					return;
				} else {
					Log.v(TAG, "Created directory " + path + " on sdcard");
				}
			}

		}

		if (!(new File(DATA_PATH + "tessdata/" + lang + ".traineddata")).exists()) {
			try {

				AssetManager assetManager = getAssets();
				InputStream in = assetManager.open("tessdata/" + lang + ".traineddata");
				OutputStream out = new FileOutputStream(DATA_PATH
						+ "tessdata/" + lang + ".traineddata");

				// Transfer bytes from in to out
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				in.close();
				out.close();

				Log.v(TAG, "Copied " + lang + " traineddata");
			} catch (IOException e) {
				Log.e(TAG, "Was unable to copy " + lang + " traineddata " + e.toString());
			}
		}

		super.onCreate(savedInstanceState); 	
		setContentView(R.layout.activity_main);

		OkButton = (Button)findViewById(R.id.myButton);
		image = (ImageView) findViewById (R.id.imgView);
		_field = (TextView)findViewById(R.id.myTextView);
		mEditText = (EditText)findViewById(R.id.myEditText);

		OkButton.setOnClickListener(this);

		prep = new PrepareImage(this);
		TextProvider = new GetText(this);

	}


	public void onClick(View v) {
		CharSequence edit_text_value = mEditText.getText();
		InputMethodManager imm = (InputMethodManager)getSystemService(
		Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
		mPicturePath =FolderPath + edit_text_value+".png";
		image.setImageBitmap(BitmapFactory.decodeFile(mPicturePath));
		
		onPhotoChosen();
	}



	protected void onPhotoChosen() {

		prep.PrepAndRotate();

		imgMAT = new Mat();

		TextProvider.ProvidText();

		if ( recognizedText.length() != 0 ) {
			_field.setText(recognizedText);
		}
		else{
			_field.setText("did not find any text");
		}
		// Cycle done.
	}

	public String getReconizedText(){
		if (recognizedText != null){
			return recognizedText;
		}
		else{
			return null;
		}
	}

	public void onResume() {
		super.onResume();
		OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this, mLoaderCallback);
	}

	public void onDestroy() {
		super.onDestroy();
	}

}




