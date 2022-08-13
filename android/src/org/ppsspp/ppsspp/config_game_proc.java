package org.ppsspp.ppsspp;
import com.jakewharton.processphoenix.ProcessPhoenix;
import org.ppsspp.ppsspp.R;


import java.io.BufferedOutputStream;
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

public class config_game_proc extends AsyncTask<Void, Integer, Void> {

    private final static String TAG = "Decompress";
    private String file_name;   
    private String location;
    private String file_name_;
	  double size = 0;
    Context ctx;
    ProgressDialog myProgressDialog;

public config_game_proc(String file_name, String file_name_, String file_name_, Context ctx) {
    super();
        this.file_name = file_name;     
        this.file_name_ = file_name_;     
        this.ctx = ctx;
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
      
        AssetManager asM = ctx.getAssets();
        in = asM.open(file_name);
        OutputStream out = null;
			
			                    String storagePath  = "";
        if (ctx.getExternalFilesDir(null).getAbsolutePath() != null)
            storagePath = ctx.getExternalFilesDir(null).getAbsolutePath();
        else
                        storagePath = ctx.getFilesDir().getAbsolutePath();

			
    try {
      if (file_name_.equals("ppsspp.ini")){
      
        File directory = new File(storagePath+File.separator+"PSP"+File.separator+"SYSTEM");    
        directory.mkdirs();    
			
				  out = new FileOutputStream(storagePath + "/PSP/SYSTEM/ppsspp.ini"); 
				  size = 1000000;
			}
      else{
				
        File directory = new File(storagePath+File.separator+"PSP_GAME"+File.separator+"SYSDIR");    
        directory.mkdirs();    
			
          out = new FileOutputStream(storagePath + "/PSP_GAME/SYSDIR/EBOOT.OLD"); 
				  size = 2860000;
      }


	    byte[] buffer = new byte[32 * 1024];
        int read;
    double thePerc_copy = 0;
    double tilNowSize_copy = 0;  
    read = in.read(buffer);    
        while (read > 0) {
    tilNowSize_copy += Double.valueOf(read);
    if(thePerc_copy != (tilNowSize_copy / size) * 85) {
        thePerc_copy = (tilNowSize_copy / size) * 85;
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
        e.printStackTrace();
    } catch (IOException e) {
        //Toast.makeText(MainActivity.this, "مشکل در کپی کردن", Toast.LENGTH_LONG).show();
        e.printStackTrace();
    }       
			
		}
	
	
	
	    protected void onProgressUpdate(Integer... progress) {
        myProgressDialog.setProgress(progress[0]); //Since it's an inner class, Bar should be able to be called directly
    }
        
    
    @Override
    protected void onPostExecute(Void v) {
        //Log.i(TAG, "Completed. Total size: "+result);
        if(myProgressDialog != null && myProgressDialog.isShowing()){
            myProgressDialog.dismiss();
        
	Intent Myintent = new Intent(ctx, MainActivity.class);
        ctx.startActivity(Myintent);
					
        }
        
    }
