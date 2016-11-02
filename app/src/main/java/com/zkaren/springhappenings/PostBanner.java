package com.zkaren.springhappenings;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class PostBanner extends Activity {

	Uri fileUri;
	private int CAMERA_REQUEST = 100;
	private int SELECT_PICTURE = 200;
	private int PIC_CROP = 300;
	private int CAMERA_CROP = 400;

	ParseFile pImage;
	ImageView imageview;
	EditText edit1;
	String selectedImagePath;
	ProgressDialog dialog;
	private void performCrop(Uri picUri, int ss) {
		try {

			Intent cropIntent = new Intent("com.android.camera.action.CROP");
			// indicate image type and Uri
			cropIntent.setDataAndType(picUri, "image/*");
			// set crop properties
			cropIntent.putExtra("crop", "true");
			// indicate aspect of desired crop
			cropIntent.putExtra("aspectX", 1);
			cropIntent.putExtra("aspectY", 1);
			// indicate output X and Y
			cropIntent.putExtra("outputX", 300);
			cropIntent.putExtra("outputY", 300);
			// retrieve data on return
			cropIntent.putExtra("return-data", true);
			// start the activity - we handle returning in onActivityResult
			startActivityForResult(cropIntent, ss);
		}
		// respond to users whose devices do not support the crop action
		catch (ActivityNotFoundException anfe) {
			// display an error message
			String errorMessage = "Whoops - your device doesn't support the crop action!";
			Toast toast = Toast
					.makeText(this, errorMessage, Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	private File SaveImage(Bitmap finalBitmap, int n) {

		String root = Environment.getExternalStorageDirectory().toString();
		File myDir = new File(root + "/SpringHappenings");
		myDir.mkdirs();
		String fname = "Image-" + n + ".jpg";
		File file = new File(myDir, fname);
		if (file.exists())
			file.delete();
		try {
			FileOutputStream out = new FileOutputStream(file);
			finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();
			return file;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private int n = 0;
	Bitmap bmp;
	boolean flag;

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and
			// keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}


	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			flag = true;
			if (requestCode == SELECT_PICTURE) {
				Uri selectedImageUri = data.getData();
				selectedImageUri = data.getData();
				if (selectedImageUri == null) {
					Toast.makeText(getApplicationContext(), "Invalid image",
							Toast.LENGTH_SHORT).show();
				} else {
					performCrop(selectedImageUri, PIC_CROP);
				}
			} else if (requestCode == CAMERA_CROP) {
				if (data != null) {
					// get the returned data
					Bundle extras = data.getExtras();
					// get the cropped bitmap
					Bitmap selectedBitmap = extras.getParcelable("data");
					if (selectedBitmap == null) {
						Toast.makeText(getApplicationContext(),
								"Invalid Image", Toast.LENGTH_SHORT).show();
					} else {
						Bitmap finalbit = selectedBitmap;
						bmp = selectedBitmap;
						imageview.setImageBitmap(bmp);
						byte[] dd = Util.bmpToByteArray(bmp, false);
						pImage = new ParseFile("adimage.png", dd);
						// pImage.saveInBackground();
						// img_profile.setImageBitmap(selectedBitmap);
					}
				}

				// b = bout.toByteArray();
				// System.out.println(b);
				// image = Base64.encodeToString(b, Base64.DEFAULT);
			} else if (requestCode == PIC_CROP) {
				if (data != null) {
					// get the returned data
					Bundle extras = data.getExtras();
					// get the cropped bitmap
					Bitmap selectedBitmap = extras.getParcelable("data");
					if (selectedBitmap != null) {
						imageview.setImageBitmap(selectedBitmap);
						bmp = selectedBitmap;
						Bitmap finalbit = bmp;
						if (finalbit != null) {
							// Util.saveBitmapToLocalPhone(bitmap, filename)
							File ff1 = SaveImage(finalbit, n);
							if (ff1 != null) {
								selectedImagePath = ff1.getAbsolutePath();
								System.out.println(selectedImagePath);
							} else {
								System.out.println("rotation pblms");
							}
						} else {
							System.out.println("Not Roatates process doine");
						}
						if (selectedImagePath == null
								&& selectedImagePath.length() <= 0) {
							System.out
									.println("No selected image path is there..");
							Toast.makeText(getApplicationContext(),
									"Invalid image", Toast.LENGTH_SHORT);
						} else {
							BitmapFactory.Options options = new BitmapFactory.Options();
							options.inJustDecodeBounds = true;
							BitmapFactory
									.decodeFile(selectedImagePath, options);
							options.inSampleSize = calculateInSampleSize(
									options, 150, 150);
							options.inJustDecodeBounds = false;

							if (bmp != null) {
								// profilepic.setBackgroundResource(R.drawable.imguploaded);
								System.out.println("sele path "
										+ selectedImagePath);
								ExifInterface ei;
								try {
									ei = new ExifInterface(selectedImagePath);
									int orientation = ei.getAttributeInt(
											ExifInterface.TAG_ORIENTATION,
											ExifInterface.ORIENTATION_NORMAL);

									imageview.setImageBitmap(bmp);
									finalbit = bmp;
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();

								}
								byte[] dd = Util.bmpToByteArray(bmp, false);
								pImage = new ParseFile("adimage.png", dd);

								// pImage.saveInBackground();

								// ByteArrayOutputStream bout = new
								// ByteArrayOutputStream();
								// bmp.compress(Bitmap.CompressFormat.JPEG, 90,
								// bout);
								// b = bout.toByteArray();
								// System.out.println(b);
								// image = Base64.encodeToString(b,
								// Base64.DEFAULT);
							} else {
								selectedImagePath = "";
								Toast.makeText(getApplicationContext(),
										"Invalid image", Toast.LENGTH_SHORT)
										.show();
							}
						}
					} else {
						Toast.makeText(getApplicationContext(),
								"Invalid Image", Toast.LENGTH_SHORT).show();
					}
				}
			}

			else if (requestCode == CAMERA_REQUEST) {
				File f = new File("mnt/sdcard/");
				for (File temp : f.listFiles()) {
					if (temp.getName().equals("temp1.jpg")) {
						f = temp;
						break;
					}
				}
				try {
					Bitmap bitmap;
					BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
					bitmapOptions.inSampleSize = 8;

					// bmp = Util.getSmallBitmap(fileUri.getPath(),
					// img.getWidth(), img.getHeight(), 10);
					if (bmp == null) {
						// Toast.makeText(this, "Error Image",
						// Toast.LENGTH_SHORT).show();
					}
					bitmap = BitmapFactory.decodeFile("mnt/sdcard/temp1.jpg",
							bitmapOptions);
					selectedImagePath = "mnt/sdcard/temp1.jpg";
					// Intent in = new Intent(SignUpActivity.this,
					// CropperActivity.class);
					// in.putExtra(CropperActivity.kCropSourceImage,
					// selectedImagePath);
					// // f.delete();
					// startActivityForResult(in, 101);
					Log.d("path", selectedImagePath);
					File file = new File("mnt/sdcard/temp1.jpg");
					Uri outputFileUri = Uri.fromFile(file);
					System.out.println(outputFileUri);
					if (outputFileUri != null) {
						performCrop(outputFileUri, CAMERA_CROP);
					} else {
						Toast.makeText(getApplicationContext(),
								"Error \n Try Again", Toast.LENGTH_SHORT)
								.show();
					}
				} catch (Exception e) {
				}
				;

			}
		}
	}


	public void selectFromCamera() {
		Intent cameraIntent = new Intent(
				MediaStore.ACTION_IMAGE_CAPTURE);
		File f = new File("mnt/sdcard/temp1.jpg");
		fileUri = Uri.fromFile(f);
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
		startActivityForResult(cameraIntent, CAMERA_REQUEST);
	}

	void showList() {

		AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
		builderSingle.setIcon(R.drawable.ic_launcher);
		builderSingle.setTitle(R.string.app_name);
		final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
				this, android.R.layout.select_dialog_singlechoice);
		int i, size;

		{
			arrayAdapter.add("Select from gallery");
			arrayAdapter.add("Capture from camera");
		}

		builderSingle.setNegativeButton("Cancels",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		builderSingle.setAdapter(arrayAdapter,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, final int which) {
						final String strName = arrayAdapter.getItem(which);
						final String strCode = null;
						// AlertDialog.Builder builderInner = new
						// AlertDialog.Builder(
						// SignUpActivity.this);

						{
							if (which == 0) {
								selectFromGallery();
							} else if (which == 1)
								selectFromCamera();
						}

					}
				});
		builderSingle.show();
	}

	public void selectFromGallery() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(Intent.createChooser(intent, "Select Picture"),
				SELECT_PICTURE);
	}

	public String getPath(Uri uri) {
		// just some safety built in
		if (uri == null) {
			// TODO perform some logging or show user feedback
			return null;
		}
		// try to retrieve the image from the media store first
		// this will only work for images selected from gallery
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		if (cursor != null) {
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		}
		// this is our fallback here
		return uri.getPath();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_banner);
		imageview = (ImageView)findViewById(R.id.imageView1);
		edit1 = (EditText)findViewById(R.id.editText1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.post_banner, menu);
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
	
	public void OnSubmit(View v)
	{
		String url = edit1.getText().toString();
		dialog = new ProgressDialog(this);
		dialog.setMessage("Submitting..");// +
		dialog.show();
		ParseObject po = new ParseObject("ads");
		if (pImage == null)
		{
			AppGlobal.showMsgDialog(this, "Spring Happenings", "Please select ad image!", "OK");
			return;
		}
		try {
			pImage.save();
			po.put(Constant.AD_IMAGE, pImage);
			po.put(Constant.AD_URL, url);
			po.saveInBackground(new SaveCallback() {
				
				@Override
				public void done(ParseException arg0) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					if (arg0 == null)
					{
						AppGlobal.showMsgDialog(PostBanner.this, "Spring Happeinings", "Submit success!", "OK");
					}
					else
					{
						AppGlobal.showMsgDialog(PostBanner.this, "Spring Happeinings", "Submit failed!", "OK");
					}
				}
			});
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
	}
}
