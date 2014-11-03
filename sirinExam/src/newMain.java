//package com.example.sirinexam;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.sirinexam.R;


public class newMain extends Activity implements OnClickListener{
	
	public static final String DATA_PATH = Environment
			.getExternalStorageDirectory().toString() + "/SirinExam/";
	
	public static final String lang = "eng";

	//private static final String TAG = "SirinExam.java";
	
	protected EditText _field;
	protected String _path;
	
     
    //protected Mat image;
    
    private static int RESULT_LOAD_IMAGE = 1;
    
    // GUI components
    private Button buttonLoadImage;
    private ImageView image;
     
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        
        buttonLoadImage = (Button) findViewById(R.id.buttonLoadPicture);
        image = (ImageView) findViewById (R.id.imgView);
               
        buttonLoadImage.setOnClickListener(this);
        
    }
     
    public void onClick(View v) {
        
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
         
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }
    
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
 
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
 
            String picturePath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));     
            image.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            
            cursor.close();

        }
     }
    
}






