package com.zkaren.springhappenings;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class AppGlobal {

	static ProgressDialog pDialog = null;
	static AlertDialog.Builder alertDialog = null;
	static boolean bShowAlertDialog = false;

	public static void showMsgDialog(final Context context, final String title, final String msg, final String buttonName) {
		if (alertDialog == null) {
			alertDialog = new AlertDialog.Builder(context).setIcon(R.drawable.ic_launcher).setTitle(title).setMessage(msg)
					.setPositiveButton(buttonName, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							alertDialog = null;
							bShowAlertDialog = false;
						}
					});
		}

		if (!bShowAlertDialog) {
			Handler mainHandler = new Handler(context.getMainLooper());
			Runnable myRunnable = new Runnable() {
				@Override
				public void run() {
					alertDialog.show();
					bShowAlertDialog = true;
				}
			};
			mainHandler.post(myRunnable);
		}
	}
//	public static Boolean isConnected(Context context)
//	{
////		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
////		NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
////		if (mWifi.isConnected()) return true;
////		AppGlobal.showMsgDialog(context, R.string.app_name, "网络没有连接", "确认");
//		return false;
//
//	}
	public static boolean isConnectingToInternet(Context _context){
		ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) 
		{
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) 
			{
				for (int i = 0; i < info.length; i++) 
				{
					Log.d("tag", info[i].getTypeName().toString() + " : " + info[i].getState().toString());
					if (info[i].getState() == NetworkInfo.State.CONNECTED)
					{
						return true;
					}
				}
				AppGlobal.showMsgDialog(_context, R.string.app_name, "网络没有连接", "确认");
				return false;
			}
			AppGlobal.showMsgDialog(_context, R.string.app_name, "网络没有连接", "确认");
			return false;
		}
		AppGlobal.showMsgDialog(_context, R.string.app_name, "网络没有连接", "确认");
		return false;
	}
	public static void showMsgDialog(final Context context, final int title, final String msg, final String buttonName) {
		if (alertDialog == null) {
			alertDialog = new AlertDialog.Builder(context).setIcon(R.drawable.ic_launcher).setTitle(title).setMessage(msg)
					.setPositiveButton(buttonName, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							alertDialog = null;
							bShowAlertDialog = false;
						}
					});
		}
		if (!bShowAlertDialog) {
			Handler mainHandler = new Handler(context.getMainLooper());
			Runnable myRunnable = new Runnable() {
				@Override
				public void run() {
					alertDialog.show();
					bShowAlertDialog = true;
				}
			};
			mainHandler.post(myRunnable);
		}
	}

	public static void showMsgDialog(final Context context, final int title, final int msg, final String buttonName) {
		if (alertDialog == null) {
			alertDialog = new AlertDialog.Builder(context).setIcon(R.drawable.ic_launcher).setTitle(title).setMessage(msg)
					.setPositiveButton(buttonName, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							alertDialog = null;
							bShowAlertDialog = false;
						}
					});
		}
		if (!bShowAlertDialog) {
			Handler mainHandler = new Handler(context.getMainLooper());
			Runnable myRunnable = new Runnable() {
				@Override
				public void run() {
					alertDialog.show();
					bShowAlertDialog = true;
				}
			};
			mainHandler.post(myRunnable);
		}
	}

	public static void showMsgDialog(final Context context, final int title, final String msg, final int buttonName) {
		if (alertDialog == null) {
			alertDialog = new AlertDialog.Builder(context).setIcon(R.drawable.ic_launcher).setTitle(title).setMessage(msg)
					.setPositiveButton(buttonName, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							alertDialog = null;
							bShowAlertDialog = false;
						}
					});
		}
		if (!bShowAlertDialog) {
			Handler mainHandler = new Handler(context.getMainLooper());
			Runnable myRunnable = new Runnable() {
				@Override
				public void run() {
					alertDialog.show();
					bShowAlertDialog = true;
				}
			};
			mainHandler.post(myRunnable);
		}
	}

	public static void showMsgDialog(final Context context, final int title, final int msg, final int buttonName) {
		if (alertDialog == null) {
			alertDialog = new AlertDialog.Builder(context).setIcon(R.drawable.ic_launcher).setTitle(title).setMessage(msg)
					.setPositiveButton(buttonName, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							alertDialog = null;
							bShowAlertDialog = false;
						}
					});
		}
		if (!bShowAlertDialog) {
			Handler mainHandler = new Handler(context.getMainLooper());
			Runnable myRunnable = new Runnable() {
				@Override
				public void run() {
					alertDialog.show();
					bShowAlertDialog = true;
				}
			};
			mainHandler.post(myRunnable);
		}
	}

	public static void showMsgDialog(final Context context, final String title, final int msg, final String buttonName) {
		if (alertDialog == null) {
			alertDialog = new AlertDialog.Builder(context).setIcon(R.drawable.ic_launcher).setTitle(title).setMessage(msg)
					.setPositiveButton(buttonName, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							alertDialog = null;
							bShowAlertDialog = false;
						}
					});
		}
		if (!bShowAlertDialog) {
			Handler mainHandler = new Handler(context.getMainLooper());
			Runnable myRunnable = new Runnable() {
				@Override
				public void run() {
					alertDialog.show();
					bShowAlertDialog = true;
				}
			};
			mainHandler.post(myRunnable);
		}
	}

	public static void showMsgDialog(final Context context, final String title, final int msg, final int buttonName) {
		if (alertDialog == null) {
			alertDialog = new AlertDialog.Builder(context).setIcon(R.drawable.ic_launcher).setTitle(title).setMessage(msg)
					.setPositiveButton(buttonName, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							alertDialog = null;
							bShowAlertDialog = false;
						}
					});
		}
		if (!bShowAlertDialog) {
			Handler mainHandler = new Handler(context.getMainLooper());
			Runnable myRunnable = new Runnable() {
				@Override
				public void run() {
					alertDialog.show();
					bShowAlertDialog = true;
				}
			};
			mainHandler.post(myRunnable);
		}
	}

	public static void showMsgDialog(final Context context, final String title, final String msg, final int buttonName) {
		if (alertDialog == null) {
			alertDialog = new AlertDialog.Builder(context).setIcon(R.drawable.ic_launcher).setTitle(title).setMessage(msg)
					.setPositiveButton(buttonName, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							alertDialog = null;
							bShowAlertDialog = false;
						}
					});
		}
		if (!bShowAlertDialog) {
			Handler mainHandler = new Handler(context.getMainLooper());
			Runnable myRunnable = new Runnable() {
				@Override
				public void run() {
					alertDialog.show();
					bShowAlertDialog = true;
				}
			};
			mainHandler.post(myRunnable);
		}
	}

	public static void showProgressDialog(Context context, String string) {
		if (pDialog != null && pDialog.isShowing())
			return;
		pDialog = new ProgressDialog(context);
		if (string == null)
			pDialog.setMessage("Loading info. Please wait...");
		else
			pDialog.setMessage(string);
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(false);
		pDialog.show();
	}

	public static void showProgressDialog(Context context) {
		if (pDialog != null && pDialog.isShowing())
			return;
		pDialog = new ProgressDialog(context);
		pDialog.setMessage("Loading info. Please wait...");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(false);
		pDialog.show();
	}

	public static void hideProgressDialog() {
		if (pDialog.isShowing())
			pDialog.dismiss();
	}

	static Dialog dialog = null;

	

	public static void hideLoadingView() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
			dialog = null;
		}
	}

	public static void showExitAppDiglog(final Activity activity) {
		AlertDialog.Builder alert_confirm = new AlertDialog.Builder(activity);
		alert_confirm.setMessage("Do you really want to quit?").setIcon(R.drawable.ic_launcher).setTitle(R.string.app_name)
				.setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// Intent intent = new Intent(Intent.ACTION_MAIN);
						// intent.addCategory(Intent.CATEGORY_HOME);
						// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						// activity.startActivity(intent);
						activity.moveTaskToBack(true);
						android.os.Process.killProcess(android.os.Process.myPid());
					}
				}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						return;
					}
				});
		AlertDialog alert = alert_confirm.create();
		alert.show();
	}

	public static void showToastView(Context context, CharSequence text) {
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}




}