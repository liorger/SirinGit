package com.example.sirinforjunit.test;

import java.util.ArrayList;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.sirinforjunit.MainActivity;

public class SirinTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	private Button OkButton;
	private ImageView image;
	private EditText mEditText;
	private MainActivity activity;
	
	private ArrayList<String> TextList = new ArrayList<>();
	String[] num = {"1","2","3","4","5","6","7"};
			
	public SirinTest(){
		super(MainActivity.class);
	}
	
		
	// runs before any test
	protected void setUp() throws Exception {
		super.setUp();
		
		//Find Views
		activity = getActivity();
		OkButton = (Button) activity.findViewById(com.example.sirinforjunit.R.id.myButton);
		image = (ImageView) activity.findViewById(com.example.sirinforjunit.R.id.imgView);
		mEditText = (EditText) activity.findViewById(com.example.sirinforjunit.R.id.myEditText);
		
		TextList.add("sdg");
		TextList.add("ct a l g stry shrt");
		TextList.add("sgf");
		TextList.add("dFD a");
		TextList.add("Sirin Exam");
		TextList.add("sdg");
		TextList.add("dgRY");
	}
	
	
	
	// runs after any test
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	//tests
	public void testViewsCreated(){
		assertNotNull(activity);
		assertNotNull(OkButton);
		assertNotNull(image);
		assertNotNull(mEditText);
	}
	
	public void testFindText(){
		
		mEditText.clearComposingText();
		String Text = new String();
		
		
		for (int i=0; i < 7; i++){

			TouchUtils.tapView(this, mEditText);
			sendKeys(KeyEvent.KEYCODE_DEL);
			sendKeys(num[i]);
			//sendKeys("1");
			TouchUtils.tapView(this, OkButton);
			
			Text = activity.getReconizedText();
			
			assertEquals(TextList.get(i), Text);
		}
	}
	
	
}
