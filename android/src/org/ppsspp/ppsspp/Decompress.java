package org.ppsspp.ppsspp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
//new
import android.content.res.AssetManager;
import java.io.IOException;
import java.io.FileNotFoundException;
import android.content.Intent;
import java.io.InputStream;
import java.io.OutputStream;
import android.app.Activity;
import android.net.Uri;
import android.content.res.Resources;
//new
//new2
import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.UiModeManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ConfigurationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.os.Vibrator;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
//new2

public class Decompress extends AsyncTask<Void, Integer, Void> {

	private final static String TAG = "Decompress";
	private String zipFile;   
	private String location;
	private String zipFile2;
	private String location2;
	ProgressDialog myProgressDialog;
    Context ctx;

	public Decompress(String zipFile, String location, String zipFile2, String location2, Context ctx) {
		super();
		this.zipFile = zipFile;     
		this.location = location;
		this.zipFile2 = zipFile2;     
		this.location2 = location2;
		this.ctx = ctx;
		dirChecker("");   
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		myProgressDialog = new ProgressDialog(ctx);
		myProgressDialog.setMessage("در حال انجام عملیات...لطفا شکیبا باشید");
		myProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		myProgressDialog.setCancelable(false);
		myProgressDialog.setMax(100);
		myProgressDialog.show();
	}

	@Override
	protected Void doInBackground(Void... params){
		int count = 0;
                int toshow_copy = 0;
		int toshow_copy2 = 0;
		int toshow_unzip = 0;
		try  {
			
////////copy one//////////////////////////////////////////////////////////////////////////////////////////////	Environment.getExternalStorageDirectory() copy and unzip 2 and check for zip2 after unziping for deleting file	
	    InputStream in = null;
    OutputStream out = null;
    try {
	//AssetManager asM = ctx.getAssets();
        //in = asM.open("game.zip");
	                String storagePath  = "";
		if (ctx.getExternalFilesDir(null).getAbsolutePath() != null)
			storagePath = ctx.getExternalFilesDir(null).getAbsolutePath();
		else
                        storagePath = ctx.getFilesDir().getAbsolutePath();    
	in = ctx.getResources().openRawResource(R.raw.game);  
        out = new FileOutputStream(storagePath + "/game.zip");
        byte[] buffer = new byte[1024*10];
        int read;
	double thePerc_copy = 0;
        double thegameIsofileSize_copy = 215534208;
	//int toshow_copy = 0;
	double tilNowSize_copy = 0;  
	read = in.read(buffer);    
        while (read > 0) {
	tilNowSize_copy += Double.valueOf(read);
	if(thePerc_copy != (tilNowSize_copy / thegameIsofileSize_copy) * 20) {
        thePerc_copy = (tilNowSize_copy / thegameIsofileSize_copy) * 20;
        toshow_copy = (int)thePerc_copy;  
	publishProgress(toshow_copy);
        }   	
            out.write(buffer, 0, read);
	    read = in.read(buffer);    

        }
        in.close();
        in = null;

        // write the output file (You have now copied the file)
        out.flush();
        out.close();
        out = null;
    } catch (FileNotFoundException e) {
               // Toast.makeText(MainActivity.this, "مشکل در پیدا کردن فایل", Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    } catch (IOException e) {
        //Toast.makeText(MainActivity.this, "مشکل در کپی کردن", Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    }	    
////////copy one//////////////////////////////////////////////////////////////////////////////////////////////						
////////unzip one///////////////////////////////////////////////////////////////////////////////////////////////////			
			ZipFile zip = new ZipFile(zipFile);
			FileInputStream fin = new FileInputStream(zipFile);       
			ZipInputStream zin = new ZipInputStream(fin);
			ZipEntry ze = null;       
			while ((ze = zin.getNextEntry()) != null) {

				Log.v("Decompress", "Unzipping " + ze.getName());          
				if(ze.isDirectory()) {           
					dirChecker(ze.getName());         
				} else {      
					FileOutputStream fout = new FileOutputStream(location + "/example.iso");
					
					byte[] buffer = new byte[4096 * 8];
					int len;
					double thePerc_unzip = 0;
					double thegameIsofileSize_unzip = 430534208;
					//int toshow_unzip = 0;
					double tilNowSize_unzip = 0;
					len = zin.read(buffer);
					while (len > 0) {
					tilNowSize_unzip += Double.valueOf(len);
			              if(thePerc_unzip != (tilNowSize_unzip / thegameIsofileSize_unzip) * 55) {
                                         thePerc_unzip = (tilNowSize_unzip / thegameIsofileSize_unzip) * 55;
                                         toshow_unzip = (int)thePerc_unzip;
					 toshow_unzip += toshow_copy;  
			                 publishProgress(toshow_unzip);
                                         }   
						fout.write(buffer, 0, len);
						len = zin.read(buffer);
					        //count++;
						//publishProgress(count);// Here I am doing the update of my progress bar
					}
					fout.close();
					zin.closeEntry();
					
				}                
			}       
			zin.close();    
		} catch(Exception e) {       
			Log.e("Decompress", "unzip", e);    
		}    
////////unzip one////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		

    try{
////////copy two//////////////////////////////////////////////////////////////////////////////////////////////	Environment.getExternalStorageDirectory() copy and unzip 2 and check for zip2 after unziping for deleting file	
    InputStream in_copy2 = null;
    OutputStream out_copy2 = null;
    try {
	//AssetManager asM = ctx.getAssets();
        //in = asM.open("game.zip");
	                String storagePath_copy2  = "";
	//	if (ctx.getExternalFilesDir(null).getAbsolutePath() != null)
	//		storagePath = ctx.getExternalFilesDir(null).getAbsolutePath();
	//	else               
	                            File directory = new File(Environment.getExternalStorageDirectory()+File.separator+"PSP"+File.separator+"SYSTEM");
                                    directory.mkdirs();
                        storagePath_copy2 = Environment.getExternalStorageDirectory().getAbsolutePath();    
	in_copy2 = ctx.getResources().openRawResource(R.raw.ppsspp);  
        out_copy2 = new FileOutputStream(storagePath_copy2 + "/PSP/SYSTEM/ppsspp.ini");
        byte[] buffer_copy2 = new byte[1024*10];
        int read_copy2;
	double thePerc_copy2 = 0;
        double thegameIsofileSize_copy2 = 7534208;
	//int toshow_copy = 0;
	double tilNowSize_copy2 = 0;  
	read_copy2 = in_copy2.read(buffer_copy2);    
        while (read_copy2 > 0) {
	tilNowSize_copy2 += Double.valueOf(read_copy2);
	if(thePerc_copy2 != (tilNowSize_copy2 / thegameIsofileSize_copy2) * 5) {
        thePerc_copy2 = (tilNowSize_copy2 / thegameIsofileSize_copy2) * 5;
        toshow_copy2 = (int)thePerc_copy2;  
	toshow_copy2 +=	toshow_unzip;
	publishProgress(toshow_copy2);
        }   	
            out_copy2.write(buffer_copy2, 0, read_copy2);
	    read_copy2 = in_copy2.read(buffer_copy2);    

        }
        in_copy2.close();
        in_copy2 = null;

        // write the output file (You have now copied the file)
        out_copy2.flush();
        out_copy2.close();
        out_copy2 = null;
    } catch (FileNotFoundException e) {
               // Toast.makeText(MainActivity.this, "مشکل در پیدا کردن فایل", Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    } catch (IOException e) {
        //Toast.makeText(MainActivity.this, "مشکل در کپی کردن", Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    }	    
////////copy two//////////////////////////////////////////////////////////////////////////////////////////////				
////////unzip two///////////////////////////////////////////////////////////////////////////////////////////////////			
			ZipFile zip2 = new ZipFile(zipFile2);
			FileInputStream fin2 = new FileInputStream(zipFile2);       
			ZipInputStream zin2 = new ZipInputStream(fin2);
			ZipEntry ze2 = null;       
			while ((ze2 = zin2.getNextEntry()) != null) {

				Log.v("Decompress", "Unzipping " + ze2.getName());          
				if(ze2.isDirectory()) {           
					dirChecker(ze2.getName());         
				} else {      
					FileOutputStream fout2 = new FileOutputStream(location2 + File.separator + ze2.getName());
					
					byte[] buffer2 = new byte[4096 * 8];
					int len2;
					double thePerc_unzip2 = 0;
					double thegameIsofileSize_unzip2 = 130534208;
					int toshow_unzip2 = 0;
					double tilNowSize_unzip2 = 0;
					len2 = zin2.read(buffer2);
					while (len2 > 0) {
					tilNowSize_unzip2 += Double.valueOf(len2);
			              if(thePerc_unzip2 != (tilNowSize_unzip2 / thegameIsofileSize_unzip2) * 5) {
                                         thePerc_unzip2 = (tilNowSize_unzip2 / thegameIsofileSize_unzip2) * 5;
                                         toshow_unzip2 = (int)thePerc_unzip2;   
					 toshow_unzip2 += toshow_copy2;  
			                 publishProgress(toshow_unzip2);
                                         }   
						fout2.write(buffer2, 0, len2);
						len2 = zin2.read(buffer2);
					        //count++;
						//publishProgress(count);// Here I am doing the update of my progress bar
					}
					fout2.close();
					zin2.closeEntry();
					
				}                
			}       
			zin2.close();    
		} catch(Exception e) {       
			Log.e("Decompress", "unzip", e);    
		}    
////////unzip two////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		
		
		return null;
	}

	protected void onProgressUpdate(Integer... progress) {
		myProgressDialog.setProgress(progress[0]); //Since it's an inner class, Bar should be able to be called directly
	}
        
	
	@Override
	protected void onPostExecute(Void v) {
		//Log.i(TAG, "Completed. Total size: "+result);
		if(myProgressDialog != null && myProgressDialog.isShowing()){
			myProgressDialog.dismiss();
//for test
	Intent Myintent = new Intent(ctx, InterstitialActivity.class);
        ctx.startActivity(Myintent);
//for test should change to load interstitial activity			
		}
		
	}
	
	private void dirChecker(String dir)
	{
		File f = new File(location + File.separator + dir);
		if(!f.isDirectory())
		{
			f.mkdirs();
		}
	}
}
