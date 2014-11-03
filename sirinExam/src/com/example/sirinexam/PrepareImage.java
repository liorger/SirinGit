package com.example.sirinexam;

import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;

public class PrepareImage  {
	
	private MainActivity activity;
	private static final String TAG = "SirinExam.java";

	public PrepareImage(MainActivity activity){
		this.activity = activity;
	}
	
	public void PrepAndRotate(){
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 4;

		activity.bitmap = BitmapFactory.decodeFile(activity.mPicturePath, options);

		try {
			ExifInterface exif = new ExifInterface(activity.mPicturePath);
			int exifOrientation = exif.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);

			Log.v(TAG, "Orient: " + exifOrientation);

			int rotate = 0;

			switch (exifOrientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				rotate = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				rotate = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				rotate = 270;
				break;
			}

			Log.v(TAG, "Rotation: " + rotate);

			if (rotate != 0) {

				// Getting width & height of the given image.
				int w = activity.bitmap.getWidth();
				int h = activity.bitmap.getHeight();

				// Setting pre rotate
				Matrix mtx = new Matrix();
				mtx.preRotate(rotate);

				// Rotating Bitmap
				activity.bitmap = Bitmap.createBitmap(activity.bitmap, 0, 0, w, h, mtx, false);
			}

			// Convert to ARGB_8888, required by tess
			activity.bitmap = activity.bitmap.copy(Bitmap.Config.ARGB_8888, true);

		} catch (IOException e) {
			Log.e(TAG, "Couldn't correct orientation: " + e.toString());
		}
		
		Log.v(TAG, "Before baseApi");
	}

}
