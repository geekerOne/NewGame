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
public class Decompress extends AsyncTask<Void, Integer, Void> {

	private final static String TAG = "Decompress";
	private String zipFile;   
	private String location;
	
	ProgressDialog myProgressDialog;
    Context ctx;

	public Decompress(String zipFile, String location, Context ctx) {
		super();
		this.zipFile = zipFile;     
		this.location = location;
		this.ctx = ctx;
		dirChecker("");   
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		myProgressDialog = new ProgressDialog(ctx);
		myProgressDialog.setMessage("Please Wait... Unzipping");
		myProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		myProgressDialog.setCancelable(false);
		myProgressDialog.show();
	}

	@Override
	protected Void doInBackground(Void... params){
		int count = 0;
                int toshow_copy = 0;
		try  {
			
////////copy//////////////////////////////////////////////////////////////////////////////////////////////		
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
        int thegameIsofileSize_copy = 7534208;
	//int toshow_copy = 0;
	int tilNowSize_copy = 0;    
        while ((read = in.read(buffer)) != -1) {
	tilNowSize_copy += read;
	if(thePerc_copy != tilNowSize_copy / thegameIsofileSize_copy * 20) {
        thePerc_copy = tilNowSize_copy / thegameIsofileSize_copy * 20;
        toshow_copy = (int)thePerc_copy;  
	publishProgress(toshow_copy);
        }   	
            out.write(buffer, 0, read);
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
////////copy//////////////////////////////////////////////////////////////////////////////////////////////			
			ZipFile zip = new ZipFile(zipFile);
			myProgressDialog.setMax(zip.size());
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
					int thegameIsofileSize_unzip = 130534208;
					int toshow_unzip = 0;
					int tilNowSize_unzip = 0;
					while ((len = zin.read(buffer)) != -1) {
					tilNowSize_unzip += len;
			              if(thePerc_unzip != tilNowSize_unzip / thegameIsofileSize_unzip * 65) {
                                         thePerc_unzip = tilNowSize_unzip / thegameIsofileSize_unzip * 65;
                                         toshow_unzip = (int)thePerc_unzip + toshow_copy;  
			                 publishProgress(toshow_unzip);
                                         }   
						fout.write(buffer, 0, len);
						count++;
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
	Intent Myintent = new Intent(ctx, PpssppActivity.class);
        ctx.startActivity(Myintent);
//for test should change to load interstitial activity			
		}
		
	}
	
	private void dirChecker(String dir)
	{
		File f = new File(location + dir);
		if(!f.isDirectory())
		{
			f.mkdirs();
		}
	}
}
