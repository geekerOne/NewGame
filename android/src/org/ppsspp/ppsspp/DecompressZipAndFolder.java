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
//new2
import android.media.MediaPlayer;
//connection check
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class DecompressZipAndFolder extends AsyncTask<Void, Integer, Void> {

    private final static String TAG = "Decompress";
    private String zipFile;   
    private String location;
    private String zipFile2;
    private String location2;
    private String zipFile3;   
    private String location3;
    private String zipFile4;
    private String location4;
    private String zipFile5;
    private String location5;
    ProgressDialog myProgressDialog;
    Context ctx;
    MediaPlayer mediaPlayer_menu;

public DecompressZipAndFolder(String zipFile, String location, String zipFile2, String location2, Context ctx, MediaPlayer mediaPlayer_menu) {
    super();
        this.zipFile = zipFile;     
        this.location = location;
        this.zipFile2 = zipFile2;     
        this.location2 = location2;
  //      this.zipFile3 = zipFile3;     
  //      this.location3 = location3;
  //     this.zipFile4 = zipFile4;     
  //      this.location4 = location4;
  //      this.zipFile5 = zipFile5;     
  //      this.location5 = location5;
        this.ctx = ctx;
        this.mediaPlayer_menu = mediaPlayer_menu;
        dirChecker("","");   
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
        int toshow_copy3 = 0;
        int toshow_copy4 = 0;
        int toshow_copy5 = 0;
        int toshow_unzip = 0;
        int toshow_unzip2 = 0;
        int toshow_unzip3 = 0;
        int toshow_unzip4 = 0;
        int toshow_unzip5 = 0;
      try  {
    //commenet abov try if you want to just copy and not unzip and comment unzip one        
////////copy one//////////////////////////////////////////////////////////////////////////////////////////////  Environment.getExternalStorageDirectory() copy and unzip 2 and check for zip2 after unziping for deleting file  
    InputStream in = null;
    OutputStream out = null;
    try {
      if (ctx.getResources().getBoolean(R.bool.is_bazaar)){
    ZipResourceFile expansionFile = APKExpansionSupport.getAPKExpansionZipFile(ctx, ctx.getResources().getInteger(R.integer.version_code), 0);
              in = expansionFile.getInputStream("main/game.zip");
      }
      else{
        AssetManager asM = ctx.getAssets();
        in = asM.open("game.zip");
      }
                    String storagePath  = "";
        if (ctx.getExternalFilesDir(null).getAbsolutePath() != null)
            storagePath = ctx.getExternalFilesDir(null).getAbsolutePath();
        else
                        storagePath = ctx.getFilesDir().getAbsolutePath();
       
	 File directory = new File(storagePath+File.separator+"PSP_GAME");    
        directory.mkdirs();
	    
	  if (ctx.getResources().getBoolean(R.bool.is_game_zip))  
          out = new FileOutputStream(storagePath + "/game.zip");
	 else if (ctx.getResources().getBoolean(R.bool.is_game_folder))
          out = new FileOutputStream(storagePath + "/PSP_GAME/game.zip");
	  
	byte[] buffer = new byte[32 * 1024];
        int read;
    double thePerc_copy = 0;
    double thegameIsofileSize_copy = Double.valueOf(ctx.getResources().getInteger(R.integer.game_zip_size_byte));
    //int toshow_copy = 0;
    double tilNowSize_copy = 0;  
    read = in.read(buffer);    
        while (read > 0) {
    tilNowSize_copy += Double.valueOf(read);
    if(thePerc_copy != (tilNowSize_copy / thegameIsofileSize_copy) * ctx.getResources().getInteger(R.integer.percent_game_zip_size_byte)) {
        thePerc_copy = (tilNowSize_copy / thegameIsofileSize_copy) * ctx.getResources().getInteger(R.integer.percent_game_zip_size_byte);
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
            
            
                    double thePerc_unzip = 0;
                    double thegameIsofileSize_unzip = Double.valueOf(ctx.getResources().getInteger(R.integer.game_size_byte));
                    //int toshow_unzip = 0;
                    double tilNowSize_unzip = 0;
            
            while ((ze = zin.getNextEntry()) != null) {
                Log.v("Decompress", "Unzipping " + ze.getName());          
                if(ze.isDirectory()) {           
                    dirChecker(ze.getName(),location);         
                } else {      
                    FileOutputStream fout = new FileOutputStream(location +File.separator+ ze.getName());
                    
                    byte[] buffer = new byte[4096 * 8];
                    int len;
                    while ((len = zin.read(buffer)) != -1) {
                    tilNowSize_unzip += Double.valueOf(len);
                          if(thePerc_unzip != (tilNowSize_unzip / thegameIsofileSize_unzip) * ctx.getResources().getInteger(R.integer.percent_game_size_byte)) {
                                         thePerc_unzip = (tilNowSize_unzip / thegameIsofileSize_unzip) * ctx.getResources().getInteger(R.integer.percent_game_size_byte);
                                         toshow_unzip = (int)thePerc_unzip;
                     toshow_unzip += toshow_copy;  
                             publishProgress(toshow_unzip);
                                         }   
                        fout.write(buffer, 0, len);
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

	    
if (ctx.getResources().getBoolean(R.bool.has_psp_folder)){	    
    try{
////////copy two//////////////////////////////////////////////////////////////////////////////////////////////  Environment.getExternalStorageDirectory() copy and unzip 2 and check for zip2 after unziping for deleting file  
    
    InputStream in_copy2 = null;
    OutputStream out_copy2 = null;
    try {

	String storagePath_copy2  = "";
        if (ctx.getExternalFilesDir(null).getAbsolutePath() != null)
            storagePath_copy2 = ctx.getExternalFilesDir(null).getAbsolutePath();
        else
            storagePath_copy2 = ctx.getFilesDir().getAbsolutePath();
	    
      if (ctx.getResources().getBoolean(R.bool.is_bazaar)){
       ZipResourceFile expansionFile = APKExpansionSupport.getAPKExpansionZipFile(ctx, ctx.getResources().getInteger(R.integer.version_code), 0);
              in_copy2 = expansionFile.getInputStream("main/psp.zip");
      }
      else{
        AssetManager asM = ctx.getAssets();
        in_copy2 = asM.open("psp.zip");
      }
	    
	File directory = new File(storagePath_copy2+File.separator+"PSP");    
        directory.mkdirs();
	    
	out_copy2 = new FileOutputStream(storagePath_copy2 + "/PSP/psp.zip");            
	    
	    byte[] buffer_copy2 = new byte[1024*10];
        int read_copy2;
    double thePerc_copy2 = 0;
    double thegameIsofileSize_copy2 = Double.valueOf(ctx.getResources().getInteger(R.integer.psp_folder_zip_size_byte));
    double tilNowSize_copy2 = 0;  
    read_copy2 = in_copy2.read(buffer_copy2);    
        while (read_copy2 > 0) {
    tilNowSize_copy2 += Double.valueOf(read_copy2);
    if(thePerc_copy2 != (tilNowSize_copy2 / thegameIsofileSize_copy2) * ctx.getResources().getInteger(R.integer.percent_psp_folder_zip_size_byte)) {
        thePerc_copy2 = (tilNowSize_copy2 / thegameIsofileSize_copy2) * ctx.getResources().getInteger(R.integer.percent_psp_folder_zip_size_byte);
        toshow_copy2 = (int)thePerc_copy2;
	    
        //if have unzip
    //toshow_copy2 += toshow_unzip; 
    //if have'nt unzip
    toshow_copy2 += toshow_copy;    
	    
	    
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
        
        
                            double thePerc_unzip2 = 0;
                    double thegameIsofileSize_unzip2 = 700534208;
                    double tilNowSize_unzip2 = 0;
        
            while ((ze2 = zin2.getNextEntry()) != null) {

                Log.v("Decompress", "Unzipping " + ze2.getName());          
                if(ze2.isDirectory()) {           
                    dirChecker(ze2.getName(),location2);         
                } else {      
                    FileOutputStream fout2 = new FileOutputStream(location2 +File.separator+ ze2.getName());
                    
                    byte[] buffer2 = new byte[2048 * 9];
                    int len2;
                    while ((len2 = zin2.read(buffer2)) != -1) {
                    tilNowSize_unzip2 += Double.valueOf(len2);
                          if(thePerc_unzip2 != (tilNowSize_unzip2 / thegameIsofileSize_unzip2) * ctx.getResources().getInteger(R.integer.percent_psp_folder_size_byte)) {
                                         thePerc_unzip2 = (tilNowSize_unzip2 / thegameIsofileSize_unzip2) * ctx.getResources().getInteger(R.integer.percent_psp_folder_size_byte);
                                         toshow_unzip2 = (int)thePerc_unzip2;   
                     toshow_unzip2 += toshow_copy2;  
                             publishProgress(toshow_unzip2);
                                         }   
                        fout2.write(buffer2, 0, len2);
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
}//if (ctx.getResources().getBoolean(R.bool.has_psp_folder))
	
	    
////////copy ppsspp.ini const func//////////////////////////////////////////////////////////////////////////////////////////////	
    InputStream in_ppssppIni = null;
    OutputStream out_ppssppIni = null;
    try {
	                String storagePath_ppssppIni  = "";
	    		            if (ctx.getExternalFilesDir(null).getAbsolutePath() != null)
            storagePath_ppssppIni = ctx.getExternalFilesDir(null).getAbsolutePath();
        else
                        storagePath_ppssppIni = ctx.getFilesDir().getAbsolutePath();

	AssetManager asM = ctx.getAssets();
        in_ppssppIni = asM.open("ppsspp.ini");
        File directory = new File(storagePath_ppssppIni+File.separator+"PSP"+File.separator+"SYSTEM");    
        directory.mkdirs();    
        out_ppssppIni = new FileOutputStream(storagePath_ppssppIni + "/PSP/SYSTEM/ppsspp.ini");
        byte[] buffer_ppssppIni = new byte[1024*10];
        int read_ppssppIni;
	double thePerc_copy2 = 0;
	read_ppssppIni = in_ppssppIni.read(buffer_ppssppIni);    
        while (read_ppssppIni > 0) {
            out_ppssppIni.write(buffer_ppssppIni, 0, read_ppssppIni);
	    read_ppssppIni = in_ppssppIni.read(buffer_ppssppIni);    
        }
        in_ppssppIni.close();
        in_ppssppIni = null;

        // write the output file (You have now copied the file)
        out_ppssppIni.flush();
        out_ppssppIni.close();
        out_ppssppIni = null;
    } catch (FileNotFoundException e) {
               // Toast.makeText(MainActivity.this, "مشکل در پیدا کردن فایل", Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    } catch (IOException e) {
        //Toast.makeText(MainActivity.this, "مشکل در کپی کردن", Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    }
    
////////copy ppsspp.ini const func//////////////////////////////////////////////////////////////////////////////////////////////	

////////copy controls.ini const func//////////////////////////////////////////////////////////////////////////////////////////////	
    InputStream in_controlsIni = null;
    OutputStream out_controlsIni = null;
    try {
	                String storagePath_controlsIni  = "";
	    	    		            if (ctx.getExternalFilesDir(null).getAbsolutePath() != null)
            storagePath_controlsIni = ctx.getExternalFilesDir(null).getAbsolutePath();
        else
                        storagePath_controlsIni = ctx.getFilesDir().getAbsolutePath();

	AssetManager asM = ctx.getAssets();
        in_controlsIni = asM.open("controls.ini");
	File directory = new File(storagePath_controlsIni+File.separator+"PSP"+File.separator+"SYSTEM");    
        directory.mkdirs();
        out_controlsIni = new FileOutputStream(storagePath_controlsIni + "/PSP/SYSTEM/controls.ini");
        byte[] buffer_controlsIni = new byte[1024*10];
        int read_controlsIni;
	read_controlsIni = in_controlsIni.read(buffer_controlsIni);    
        while (read_controlsIni > 0) {
            out_controlsIni.write(buffer_controlsIni, 0, read_controlsIni);
	    read_controlsIni = in_controlsIni.read(buffer_controlsIni);    
        }
        in_controlsIni.close();
        in_controlsIni = null;

        // write the output file (You have now copied the file)
        out_controlsIni.flush();
        out_controlsIni.close();
        out_controlsIni = null;
    } catch (FileNotFoundException e) {
               // Toast.makeText(MainActivity.this, "مشکل در پیدا کردن فایل", Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    } catch (IOException e) {
        //Toast.makeText(MainActivity.this, "مشکل در کپی کردن", Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    }
////////copy controls.ini const func//////////////////////////////////////////////////////////////////////////////////////////////	
			        
////////copy Cheats const func//////////////////////////////////////////////////////////////////////////////////////////////    
    InputStream in_Cheats = null;
    OutputStream out_Cheats = null;
    try {
                    String storagePath_Cheats  = ""; 
	if (ctx.getExternalFilesDir(null).getAbsolutePath() != null)
            storagePath_Cheats = ctx.getExternalFilesDir(null).getAbsolutePath();
        else
                        storagePath_Cheats = ctx.getFilesDir().getAbsolutePath();
	    
    in_Cheats = ctx.getResources().openRawResource(R.raw.cheat);  
	    	File directory = new File(storagePath_Cheats+File.separator+"PSP"+File.separator+"Cheats");    
        directory.mkdirs();
        out_Cheats = new FileOutputStream(storagePath_Cheats + "/PSP/Cheats/cheat.db");
        byte[] buffer_Cheats = new byte[1024*10];
        int read_Cheats;
    double thePerc_copy2 = 0;
    read_Cheats = in_Cheats.read(buffer_Cheats);    
        while (read_Cheats > 0) {
            out_Cheats.write(buffer_Cheats, 0, read_Cheats);
        read_Cheats = in_Cheats.read(buffer_Cheats);    
        }
        in_Cheats.close();
        in_Cheats = null;
        // write the output file (You have now copied the file)
        out_Cheats.flush();
        out_Cheats.close();
        out_Cheats = null;
    } catch (FileNotFoundException e) {
               // Toast.makeText(MainActivity.this, "مشکل در پیدا کردن فایل", Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    } catch (IOException e) {
        //Toast.makeText(MainActivity.this, "مشکل در کپی کردن", Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    }
////////copy Cheats const func//////////////////////////////////////////////////////////////////////////////////////////////   

if (ctx.getResources().getBoolean(R.bool.is_game_folder)){ 	    
////////copy UMD_DATA.BIN const func//////////////////////////////////////////////////////////////////////////////////////////////	
    InputStream in_bin = null;
    OutputStream out_bin = null;
    try {
	    
	    	                String storagePath_bin  = "";
		if (ctx.getExternalFilesDir(null).getAbsolutePath() != null)
			storagePath_bin = ctx.getExternalFilesDir(null).getAbsolutePath();
		else
                        storagePath_bin = ctx.getFilesDir().getAbsolutePath();  
	    	AssetManager asM = ctx.getAssets();
        in_bin = asM.open("umd.bin");
        out_bin = new FileOutputStream(storagePath_bin + "/UMD_DATA.BIN");
        byte[] buffer_bin = new byte[1024*10];
        int read_bin;
	read_bin = in_bin.read(buffer_bin);    
        while (read_bin > 0) {
            out_bin.write(buffer_bin, 0, read_bin);
	    read_bin = in_bin.read(buffer_bin);    
        }
        in_bin.close();
        in_bin = null;
        // write the output file (You have now copied the file)
        out_bin.flush();
        out_bin.close();
        out_bin = null;
    } catch (FileNotFoundException e) {
               // Toast.makeText(MainActivity.this, "مشکل در پیدا کردن فایل", Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    } catch (IOException e) {
        //Toast.makeText(MainActivity.this, "مشکل در کپی کردن", Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    }
////////copy UMD_DATA.BIN const func//////////////////////////////////////////////////////////////////////////////////////////////	        	    
}//if (getResources().getBoolean(R.bool.is_game_folder))
	    
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
        
            
            
            String storagePath  = "";
        if (ctx.getExternalFilesDir(null).getAbsolutePath() != null)
            storagePath = ctx.getExternalFilesDir(null).getAbsolutePath();
        else
                        storagePath = ctx.getFilesDir().getAbsolutePath();

        //File GameFileZip = new File(storagePath+"/PSP_GAME" , "/game.zip"); 
 
          
          File GameFileZip = new File(storagePath , "/game.zip"); 
        
	if(GameFileZip.exists()){  
                GameFileZip.delete();
                }
        File obbFile = new File(ctx.getObbDir() , "/main." + ctx.getResources().getString(R.string.version_name) + "." + ctx.getResources().getString(R.string.config_id) + ".obb");
        if(obbFile.exists()){  
                obbFile.delete();
                }   
        File pspFileZip = new File(storagePath+"/PSP"  , "/psp.zip");    
        if(pspFileZip.exists()){  
                pspFileZip.delete();
                }            
            
            
            mediaPlayer_menu.pause();
            
//for tabligh
	if (haveNetworkConnection() == true){					
	Intent Myintent = new Intent(ctx, InterstitialActivity.class);
        ctx.startActivity(Myintent);
	}else{
	Intent Myintent = new Intent(ctx, PpssppActivity.class);
        ctx.startActivity(Myintent); 			
	}	
//for tabligh activity	
          
        }
        
    }
    
    private void dirChecker(String dir , String loc)
    {
        File f = new File(loc +File.separator+ dir);
        if(!f.isDirectory())
        {
            f.mkdirs();
        }
    }
	
	
	
		//check connectionn
	private boolean haveNetworkConnection() {
    boolean haveConnectedWifi = false;
    boolean haveConnectedMobile = false;

    ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(ctx.CONNECTIVITY_SERVICE);
    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
    for (NetworkInfo ni : netInfo) {
        if (ni.getTypeName().equalsIgnoreCase("WIFI"))
            if (ni.isConnected())
                haveConnectedWifi = true;
        if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
            if (ni.isConnected())
                haveConnectedMobile = true;
    }
    return haveConnectedWifi || haveConnectedMobile;
}

	
	
}
