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
//new
public class Decompress extends AsyncTask<Void, Integer, Integer> {

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
	protected Integer doInBackground(Void... params){
		int count = 0;

		try  {
			
////////copy//////////////////////////////////////////////////////////////////////////////////////////////		
    InputStream in = null;
    OutputStream out = null;
    try {
	AssetManager asM = ctx.getAssets();
        in = asM.open("game.zip");
        out = new FileOutputStream(location + "/game.zip");
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
        in.close();
        in = null;

        // write the output file (You have now copied the file)
        out.flush();
        out.close();
        out = null;
    } catch (FileNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (IOException e) {
        // TODO Auto-generated catch block
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
					FileOutputStream fout = new FileOutputStream(location +ze.getName());
					
					byte[] buffer = new byte[8192];
					int len;
					while ((len = zin.read(buffer)) != -1) {
						fout.write(buffer, 0, len);
						count++;
						publishProgress(count);// Here I am doing the update of my progress bar
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

	protected void onPostExecute(Integer... result) {
		Log.i(TAG, "Completed. Total size: "+result);
		if(myProgressDialog != null && myProgressDialog.isShowing()){
			myProgressDialog.dismiss();
	
		//for test 
		String storagePath  = "";
		if (ctx.getExternalFilesDir(null).getAbsolutePath() != null)
			storagePath = ctx.getExternalFilesDir(null).getAbsolutePath();
		else
                        storagePath = ctx.getFilesDir().getAbsolutePath();
	    
	        File GameFile = new File(storagePath + "/example.iso");	
		Intent intent = new Intent();
		intent.setPackage("org.ppsspp.ppsspp");
		intent.setClassName("org.ppsspp.ppsspp", "org.ppsspp.ppsspp.PpssppActivity");
		intent.setData(Uri.fromFile(GameFile));
		ctx.startActivity(intent);
	        //for test
			
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
