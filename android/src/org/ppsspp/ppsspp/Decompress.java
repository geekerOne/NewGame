package org.ppsspp.ppsspp;
import com.jakewharton.processphoenix.ProcessPhoenix;
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

public class Decompress extends AsyncTask<Void, Integer, Void> {

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
//    public Decompress(String zipFile, String location, String zipFile2, String location2, String zipFile3, String location3, String zipFile4, String location4,String zipFile5, String location5, Context ctx, MediaPlayer mediaPlayer_menu) {
//public Decompress(String zipFile, String location, String zipFile2, String location2,Context ctx, MediaPlayer mediaPlayer_menu) {
public Decompress(String zipFile, String location,Context ctx, MediaPlayer mediaPlayer_menu) {
    super();
        this.zipFile = zipFile;     
        this.location = location;
       // this.zipFile2 = zipFile2;     
       // this.location2 = location2;
       // this.zipFile3 = zipFile3;     
       // this.location3 = location3;
       // this.zipFile4 = zipFile4;     
       // this.location4 = location4;
       // this.zipFile5 = zipFile5;     
       // this.location5 = location5;
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
      //try  {
    //commenet abov try if you want to just copy and not unzip and comment unzip one        
////////copy one//////////////////////////////////////////////////////////////////////////////////////////////  Environment.getExternalStorageDirectory() copy and unzip 2 and check for zip2 after unziping for deleting file  
    InputStream in = null;
    OutputStream out = null;
    try {
    //AssetManager asM = ctx.getAssets();
        //in = asM.open("game.zip");
    ZipResourceFile expansionFile = APKExpansionSupport.getAPKExpansionZipFile(ctx, 311030000, 0);
    
        
        
                    String storagePath  = "";
        if (ctx.getExternalFilesDir(null).getAbsolutePath() != null)
            storagePath = ctx.getExternalFilesDir(null).getAbsolutePath();
        else
                        storagePath = ctx.getFilesDir().getAbsolutePath();
        
        
       //                       File directory = new File(storagePath+File.separator+"PSP_GAME");
       //                       directory.mkdirs();
        
    //with unzip after    
    //   in = expansionFile.getInputStream("main/game.zip");
    //   out = new FileOutputStream(storagePath + "/PSP_GAME/game.zip");
        //without unzip after    
    in = expansionFile.getInputStream("main/example.iso");
        out = new FileOutputStream(storagePath + "/example.iso");    
        byte[] buffer = new byte[1024*10];
        int read;
    double thePerc_copy = 0;
        double thegameIsofileSize_copy = 1350534208;
    //int toshow_copy = 0;
    double tilNowSize_copy = 0;  
    read = in.read(buffer);    
        while (read > 0) {
    tilNowSize_copy += Double.valueOf(read);
    if(thePerc_copy != (tilNowSize_copy / thegameIsofileSize_copy) * 10) {
        thePerc_copy = (tilNowSize_copy / thegameIsofileSize_copy) * 10;
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
/*          
            ZipFile zip = new ZipFile(zipFile);
            FileInputStream fin = new FileInputStream(zipFile);       
            ZipInputStream zin = new ZipInputStream(fin);
            ZipEntry ze = null;     
            
            
                    double thePerc_unzip = 0;
                    double thegameIsofileSize_unzip = 650534208;
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
                          if(thePerc_unzip != (tilNowSize_unzip / thegameIsofileSize_unzip) * 20) {
                                         thePerc_unzip = (tilNowSize_unzip / thegameIsofileSize_unzip) * 20;
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
 */   
////////unzip one////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////       

    try{
////////copy two//////////////////////////////////////////////////////////////////////////////////////////////  Environment.getExternalStorageDirectory() copy and unzip 2 and check for zip2 after unziping for deleting file  
    
    InputStream in_copy2 = null;
    OutputStream out_copy2 = null;
    try {
                    String storagePath_copy2  = "";
        ZipResourceFile expansionFile = APKExpansionSupport.getAPKExpansionZipFile(ctx, 311030000, 0);
//normal use                            
//  File directory = new File(Environment.getExternalStorageDirectory()+File.separator+"PSP");    
//specific use            ->    "/TEXTURES/ULUS10112/psp.zip"             
  File directory = new File(Environment.getExternalStorageDirectory()+File.separator+"PSP"+File.separator+"TEXTURES"+File.separator+"FIFA01590");   
        directory.mkdirs();
                        storagePath_copy2 = Environment.getExternalStorageDirectory().getAbsolutePath();    
        in_copy2 = expansionFile.getInputStream("main/psp.zip");
        //normall use
	    //out_copy2 = new FileOutputStream(storagePath_copy2 + "/PSP/psp.zip");            
       //specific use
        out_copy2 = new FileOutputStream(storagePath_copy2 + "/PSP/TEXTURES/FIFA01590/psp.zip");
	    
	    byte[] buffer_copy2 = new byte[1024*10];
        int read_copy2;
    double thePerc_copy2 = 0;
        double thegameIsofileSize_copy2 = 40534208;
    //int toshow_copy = 0;
    double tilNowSize_copy2 = 0;  
    read_copy2 = in_copy2.read(buffer_copy2);    
        while (read_copy2 > 0) {
    tilNowSize_copy2 += Double.valueOf(read_copy2);
    if(thePerc_copy2 != (tilNowSize_copy2 / thegameIsofileSize_copy2) * 10) {
        thePerc_copy2 = (tilNowSize_copy2 / thegameIsofileSize_copy2) * 10;
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
                    double thegameIsofileSize_unzip2 = 40534208;
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
                          if(thePerc_unzip2 != (tilNowSize_unzip2 / thegameIsofileSize_unzip2) * 10) {
                                         thePerc_unzip2 = (tilNowSize_unzip2 / thegameIsofileSize_unzip2) * 10;
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

    try{
////////copy three//////////////////////////////////////////////////////////////////////////////////////////////    Environment.getExternalStorageDirectory() copy and unzip 2 and check for zip3 after unziping for deleting file  
   
    InputStream in_copy3 = null;
    OutputStream out_copy3 = null;
    try {
                    String storagePath_copy3  = "";
        ZipResourceFile expansionFile = APKExpansionSupport.getAPKExpansionZipFile(ctx, 311030000, 0);
//normal use                            
//  File directory = new File(Environment.getExternalStorageDirectory()+File.separator+"PSP");    
//specific use            ->    "/TEXTURES/ULUS10112/psp.zip"             
  File directory = new File(Environment.getExternalStorageDirectory()+File.separator+"PSP"+File.separator+"TEXTURES"+File.separator+"FIFA01590");   
        directory.mkdirs();
                        storagePath_copy3 = Environment.getExternalStorageDirectory().getAbsolutePath();    
        in_copy3 = expansionFile.getInputStream("main/psp2.zip");
        out_copy3 = new FileOutputStream(storagePath_copy3 + "/PSP/TEXTURES/FIFA01590/psp2.zip");           
        byte[] buffer_copy3 = new byte[1024*10];
        int read_copy3;
    double thePerc_copy3 = 0;
        double thegameIsofileSize_copy3 = 160534208;
    //int toshow_copy = 0;
    double tilNowSize_copy3 = 0;  
    read_copy3 = in_copy3.read(buffer_copy3);    
        while (read_copy3 > 0) {
    tilNowSize_copy3 += Double.valueOf(read_copy3);
    if(thePerc_copy3 != (tilNowSize_copy3 / thegameIsofileSize_copy3) * 5) {
        thePerc_copy3 = (tilNowSize_copy3 / thegameIsofileSize_copy3) * 5;
        toshow_copy3 = (int)thePerc_copy3;  
        //if have unzip
    toshow_copy3 += toshow_unzip2; 
    //if have'nt unzip
    //toshow_copy3 += toshow_copy;  
    publishProgress(toshow_copy3);
        }       
            out_copy3.write(buffer_copy3, 0, read_copy3);
        read_copy3 = in_copy3.read(buffer_copy3);    

        }
        in_copy3.close();
        in_copy3 = null;

        // write the output file (You have now copied the file)
        out_copy3.flush();
        out_copy3.close();
        out_copy3 = null;
    } catch (FileNotFoundException e) {
               // Toast.makeText(MainActivity.this, "مشکل در پیدا کردن فایل", Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    } catch (IOException e) {
        //Toast.makeText(MainActivity.this, "مشکل در کپی کردن", Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    }       
    
////////copy three//////////////////////////////////////////////////////////////////////////////////////////////                
////////unzip three///////////////////////////////////////////////////////////////////////////////////////////////////          
     
        ZipFile zip3 = new ZipFile(zipFile3);
            FileInputStream fin3 = new FileInputStream(zipFile3);       
            ZipInputStream zin3 = new ZipInputStream(fin3);
            ZipEntry ze3 = null;     
        
        
                            double thePerc_unzip3 = 0;
                    double thegameIsofileSize_unzip3 = 160534208;
                    double tilNowSize_unzip3 = 0;
        
            while ((ze3 = zin3.getNextEntry()) != null) {

                Log.v("Decompress", "Unzipping " + ze3.getName());          
                if(ze3.isDirectory()) {           
                    dirChecker(ze3.getName(),location3);         
                } else {      
                    FileOutputStream fout3 = new FileOutputStream(location3 +File.separator+ ze3.getName());
                    
                    byte[] buffer3 = new byte[2048];
                    int len3;
                    while ((len3 = zin3.read(buffer3)) != -1) {
                    tilNowSize_unzip3 += Double.valueOf(len3);
                          if(thePerc_unzip3 != (tilNowSize_unzip3 / thegameIsofileSize_unzip3) * 10) {
                                         thePerc_unzip3 = (tilNowSize_unzip3 / thegameIsofileSize_unzip3) * 10;
                                         toshow_unzip3 = (int)thePerc_unzip3;   
                     toshow_unzip3 += toshow_copy3;  
                             publishProgress(toshow_unzip3);
                                         }   
                        fout3.write(buffer3, 0, len3);
                            //count++;
                        //publishProgress(count);// Here I am doing the update of my progress bar
                    }
                    fout3.close();
                    zin3.closeEntry();
                    
                }                
            }       
        zin3.close();    

        
        } catch(Exception e) {       
            Log.e("Decompress", "unzip", e);    
        }    
        
////////unzip three////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////     

    try{
////////copy four////////////////////////////////////////////////////////////////////////////////////////////// Environment.getExternalStorageDirectory() copy and unzip 4 and check for zip4 after unziping for deleting file  
   
    InputStream in_copy4 = null;
    OutputStream out_copy4 = null;
    try {
                    String storagePath_copy4  = "";
        ZipResourceFile expansionFile = APKExpansionSupport.getAPKExpansionZipFile(ctx, 311030000, 0);
//normal use                            
//  File directory = new File(Environment.getExternalStorageDirectory()+File.separator+"PSP");    
//specific use            ->    "/TEXTURES/ULUS10112/psp.zip"             
  File directory = new File(Environment.getExternalStorageDirectory()+File.separator+"PSP"+File.separator+"TEXTURES"+File.separator+"FIFA01590"+File.separator+"Faces");    
        directory.mkdirs();
                        storagePath_copy4 = Environment.getExternalStorageDirectory().getAbsolutePath();    
        in_copy4 = expansionFile.getInputStream("main/Faces.zip");
        out_copy4 = new FileOutputStream(storagePath_copy4 + "/PSP/TEXTURES/FIFA01590/Faces/Faces.zip");            
        byte[] buffer_copy4 = new byte[1024*10];
        int read_copy4;
    double thePerc_copy4 = 0;
        double thegameIsofileSize_copy4 = 20534208;
    //int toshow_copy = 0;
    double tilNowSize_copy4 = 0;  
    read_copy4 = in_copy4.read(buffer_copy4);    
        while (read_copy4 > 0) {
    tilNowSize_copy4 += Double.valueOf(read_copy4);
    if(thePerc_copy4 != (tilNowSize_copy4 / thegameIsofileSize_copy4) * 10) {
        thePerc_copy4 = (tilNowSize_copy4 / thegameIsofileSize_copy4) * 10;
        toshow_copy4 = (int)thePerc_copy4;  
        //if have unzip
    toshow_copy4 += toshow_unzip3; 
    //if have'nt unzip
    //toshow_copy4 += toshow_copy;  
    publishProgress(toshow_copy4);
        }       
            out_copy4.write(buffer_copy4, 0, read_copy4);
        read_copy4 = in_copy4.read(buffer_copy4);    

        }
        in_copy4.close();
        in_copy4 = null;

        // write the output file (You have now copied the file)
        out_copy4.flush();
        out_copy4.close();
        out_copy4 = null;
    } catch (FileNotFoundException e) {
               // Toast.makeText(MainActivity.this, "مشکل در پیدا کردن فایل", Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    } catch (IOException e) {
        //Toast.makeText(MainActivity.this, "مشکل در کپی کردن", Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    }       
    
////////copy four//////////////////////////////////////////////////////////////////////////////////////////////             
////////unzip four///////////////////////////////////////////////////////////////////////////////////////////////////           
      
        ZipFile zip4 = new ZipFile(zipFile4);
            FileInputStream fin4 = new FileInputStream(zipFile4);       
            ZipInputStream zin4 = new ZipInputStream(fin4);
            ZipEntry ze4 = null;     
        
        
                            double thePerc_unzip4 = 0;
                    double thegameIsofileSize_unzip4 = 20534208;
                    double tilNowSize_unzip4 = 0;
        
            while ((ze4 = zin4.getNextEntry()) != null) {

                Log.v("Decompress", "Unzipping " + ze4.getName());          
                if(ze4.isDirectory()) {           
                    dirChecker(ze4.getName(),location4);         
                } else {      
                    FileOutputStream fout4 = new FileOutputStream(location4 +File.separator+ ze4.getName());
                    
                    byte[] buffer4 = new byte[2048];
                    int len4;
                    while ((len4 = zin4.read(buffer4)) != -1) {
                    tilNowSize_unzip4 += Double.valueOf(len4);
                          if(thePerc_unzip4 != (tilNowSize_unzip4 / thegameIsofileSize_unzip4) * 10) {
                                         thePerc_unzip4 = (tilNowSize_unzip4 / thegameIsofileSize_unzip4) * 10;
                                         toshow_unzip4 = (int)thePerc_unzip4;   
                     toshow_unzip4 += toshow_copy4;  
                             publishProgress(toshow_unzip4);
                                         }   
                        fout4.write(buffer4, 0, len4);
                            //count++;
                        //publishProgress(count);// Here I am doing the update of my progress bar
                    }
                    fout4.close();
                    zin4.closeEntry();
                    
                }                
            }       
        zin4.close();    

        
        } catch(Exception e) {       
            Log.e("Decompress", "unzip", e);    
        }    
        
////////unzip four////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////      
        
    try{
////////copy five////////////////////////////////////////////////////////////////////////////////////////////// Environment.getExternalStorageDirectory() copy and unzip 4 and check for zip5 after unziping for deleting file  
   
    InputStream in_copy5 = null;
    OutputStream out_copy5 = null;
    try {
                    String storagePath_copy5  = "";
        ZipResourceFile expansionFile = APKExpansionSupport.getAPKExpansionZipFile(ctx, 311030000, 0);
//normal use                            
//  File directory = new File(Environment.getExternalStorageDirectory()+File.separator+"PSP");    
//specific use            ->    "/TEXTURES/ULUS10112/psp.zip"             
  File directory = new File(Environment.getExternalStorageDirectory()+File.separator+"PSP"+File.separator+"TEXTURES"+File.separator+"FIFA01590"+File.separator+"Faces");    
        directory.mkdirs();
                        storagePath_copy5 = Environment.getExternalStorageDirectory().getAbsolutePath();    
        in_copy5 = expansionFile.getInputStream("main/Faces2.zip");
        out_copy5 = new FileOutputStream(storagePath_copy5 + "/PSP/TEXTURES/FIFA01590/Faces/Faces2.zip");            
        byte[] buffer_copy5 = new byte[1024*10];
        int read_copy5;
    double thePerc_copy5 = 0;
        double thegameIsofileSize_copy5 = 20534208;
    //int toshow_copy = 0;
    double tilNowSize_copy5 = 0;  
    read_copy5 = in_copy5.read(buffer_copy5);    
        while (read_copy5 > 0) {
    tilNowSize_copy5 += Double.valueOf(read_copy5);
    if(thePerc_copy5 != (tilNowSize_copy5 / thegameIsofileSize_copy5) * 10) {
        thePerc_copy5 = (tilNowSize_copy5 / thegameIsofileSize_copy5) * 10;
        toshow_copy5 = (int)thePerc_copy5;  
        //if have unzip
    toshow_copy5 += toshow_unzip4; 
    //if have'nt unzip
    //toshow_copy5 += toshow_copy;  
    publishProgress(toshow_copy5);
        }       
            out_copy5.write(buffer_copy5, 0, read_copy5);
        read_copy5 = in_copy5.read(buffer_copy5);    

        }
        in_copy5.close();
        in_copy5 = null;

        // write the output file (You have now copied the file)
        out_copy5.flush();
        out_copy5.close();
        out_copy5 = null;
    } catch (FileNotFoundException e) {
               // Toast.makeText(MainActivity.this, "مشکل در پیدا کردن فایل", Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    } catch (IOException e) {
        //Toast.makeText(MainActivity.this, "مشکل در کپی کردن", Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    }       
    
////////copy five//////////////////////////////////////////////////////////////////////////////////////////////             
////////unzip five///////////////////////////////////////////////////////////////////////////////////////////////////           
       
        ZipFile zip5 = new ZipFile(zipFile5);
            FileInputStream fin5 = new FileInputStream(zipFile5);       
            ZipInputStream zin5 = new ZipInputStream(fin5);
            ZipEntry ze5 = null;     
        
        
                            double thePerc_unzip5 = 0;
                    double thegameIsofileSize_unzip5 = 20534208;
                    double tilNowSize_unzip5 = 0;
        
            while ((ze5 = zin5.getNextEntry()) != null) {

                Log.v("Decompress", "Unzipping " + ze5.getName());          
                if(ze5.isDirectory()) {           
                    dirChecker(ze5.getName(),location5);         
                } else {      
                    FileOutputStream fout5 = new FileOutputStream(location4 +File.separator+ ze5.getName());
                    
                    byte[] buffer5 = new byte[2048];
                    int len5;
                    while ((len5 = zin5.read(buffer5)) != -1) {
                    tilNowSize_unzip5 += Double.valueOf(len5);
                          if(thePerc_unzip5 != (tilNowSize_unzip5 / thegameIsofileSize_unzip5) * 5) {
                                         thePerc_unzip5 = (tilNowSize_unzip5 / thegameIsofileSize_unzip5) * 5;
                                         toshow_unzip5 = (int)thePerc_unzip5;   
                     toshow_unzip5 += toshow_copy5;  
                             publishProgress(toshow_unzip5);
                                         }   
                        fout5.write(buffer5, 0, len5);
                            //count++;
                        //publishProgress(count);// Here I am doing the update of my progress bar
                    }
                    fout5.close();
                    zin5.closeEntry();
                    
                }                
            }       
        zin5.close();    

        
        } catch(Exception e) {       
            Log.e("Decompress", "unzip", e);    
        }    
    
////////unzip five////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////      
        
//just for folder games		
////////copy UMD_DATA.BIN const func//////////////////////////////////////////////////////////////////////////////////////////////	
    /*
    InputStream in_bin = null;
    OutputStream out_bin = null;
    try {
	    
	    	                String storagePath_bin  = "";
		if (ctx.getExternalFilesDir(null).getAbsolutePath() != null)
			storagePath_bin = ctx.getExternalFilesDir(null).getAbsolutePath();
		else
                        storagePath_bin = ctx.getFilesDir().getAbsolutePath();  

	in_bin = ctx.getResources().openRawResource(R.raw.umd);  
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
    */
////////copy UMD_DATA.BIN const func//////////////////////////////////////////////////////////////////////////////////////////////	

////////copy ppsspp.ini const func//////////////////////////////////////////////////////////////////////////////////////////////	
    InputStream in_ppssppIni = null;
    OutputStream out_ppssppIni = null;
    try {
	                String storagePath_ppssppIni  = "";
	                            File directory = new File(Environment.getExternalStorageDirectory()+File.separator+"PSP"+File.separator+"SYSTEM");
                                    directory.mkdirs();
                        storagePath_ppssppIni = Environment.getExternalStorageDirectory().getAbsolutePath();    
	in_ppssppIni = ctx.getResources().openRawResource(R.raw.ppsspp);  
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
	                            File directory3 = new File(Environment.getExternalStorageDirectory()+File.separator+"PSP"+File.separator+"SYSTEM");
                                    directory3.mkdirs();
                        storagePath_controlsIni = Environment.getExternalStorageDirectory().getAbsolutePath();    
	in_controlsIni = ctx.getResources().openRawResource(R.raw.controls);  
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
                                File directory = new File(Environment.getExternalStorageDirectory()+File.separator+"PSP"+File.separator+"Cheats");
                                    directory.mkdirs();
                        storagePath_Cheats = Environment.getExternalStorageDirectory().getAbsolutePath();    
    in_Cheats = ctx.getResources().openRawResource(R.raw.cheat);  
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

        File GameFileZip = new File(storagePath+"/PSP_GAME" , "/game.zip"); 
        if(GameFileZip.exists()){  
                GameFileZip.delete();
                }
        File obbFile = new File(ctx.getObbDir() ,"/main.311030000.com.draco.ludere.JeyRideJETPACK.obb");
        if(obbFile.exists()){  
                obbFile.delete();
                }   
        File pspFileZip = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/PSP/TEXTURES/FIFA01590"  , "/psp.zip");    
        if(pspFileZip.exists()){  
                pspFileZip.delete();
                }   
        File pspFileZip2 = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/PSP/TEXTURES/FIFA01590"  , "/psp2.zip");  
        if(pspFileZip2.exists()){  
                pspFileZip2.delete();
                }   
        File pspFileZip3 = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/PSP/TEXTURES/FIFA01590/Faces"  , "/Faces.zip");   
        if(pspFileZip3.exists()){  
                pspFileZip3.delete();
                }       
        File pspFileZip4 = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/PSP/TEXTURES/FIFA01590/Faces"  , "/Faces2.zip");  
        if(pspFileZip4.exists()){  
                pspFileZip4.delete();
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
	
//for money
	//Intent Myintent = new Intent(ctx, PpssppActivity.class);
        //ctx.startActivity(Myintent); 		
//for money
            
//restarting the game preventing force close            
//ProcessPhoenix.triggerRebirth(ctx);           
//restarting the game preventing force close            
            
            
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
