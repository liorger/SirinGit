package com.example.sirinforjunit;

import org.opencv.android.Utils;
import org.opencv.imgproc.Imgproc;

import com.googlecode.tesseract.android.TessBaseAPI;

public class GetText {
	
	private MainActivity activity;
	
	public GetText(MainActivity activity){
		this.activity = activity;
	}
	
	public void ProvidText(){
		TessBaseAPI baseApi = new TessBaseAPI();
		baseApi.setDebug(true);
		baseApi.init(MainActivity.DATA_PATH, MainActivity.lang);
		
		Utils.bitmapToMat(activity.bitmap, activity.imgMAT);
		
		// pre processing
		Imgproc.cvtColor(activity.imgMAT, activity.imgMAT, Imgproc.COLOR_BGR2GRAY); // convert to grayscale
		//Imgproc.medianBlur(imgMAT, imgMAT, 3);
		Imgproc.threshold(activity.imgMAT, activity.imgMAT, 0, 255, Imgproc.THRESH_OTSU); //apply threshold
		
		
		Utils.matToBitmap(activity.imgMAT,activity.bitmap);
		
		
		baseApi.setImage(activity.bitmap);
		
		
		activity.recognizedText = baseApi.getUTF8Text();
				
		baseApi.end();
		
		// You now have the text in recognizedText var, you can do anything with it.
		// We will display a stripped out trimmed alpha-numeric version of it (if lang is eng)
		// so that garbage doesn't make it to the display.

		//Log.v(TAG, "OCRED TEXT: " + recognizedText);

		if ( MainActivity.lang.equalsIgnoreCase("eng") ) {
			activity.recognizedText = activity.recognizedText.replaceAll("[^a-zA-Z0-9]+", " ");
		}
		
		activity.recognizedText = activity.recognizedText.trim();
		
	}
	
	
}
