package org.ppsspp.ppsspp;
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
		int toshow_unzip = 0;
		int toshow_unzip2 = 0;
		int toshow_unzip3 = 0;
		int toshow_unzip4 = 0;
	// 	try  {
	//commenet abov try if you want to just copy and not unzip and comment unzip one		
////////copy one//////////////////////////////////////////////////////////////////////////////////////////////	Environment.getExternalStorageDirectory() copy and unzip 2 and check for zip2 after unziping for deleting file	
    InputStream in = null;
    OutputStream out = null;
    try {
	//AssetManager asM = ctx.getAssets();
        //in = asM.open("game.zip");
	ZipResourceFile expansionFile = APKExpansionSupport.getAPKExpansionZipFile(ctx, 111030000, 0);
    
	    
	    
	                String storagePath  = "";
		if (ctx.getExternalFilesDir(null).getAbsolutePath() != null)
			storagePath = ctx.getExternalFilesDir(null).getAbsolutePath();
		else
                        storagePath = ctx.getFilesDir().getAbsolutePath();
	    
	    
	                        //  File directory = new File(storagePath+File.separator+"PSP_GAME");
	                        //  directory.mkdirs();
	    
	//with unzip after    
	//in = expansionFile.getInputStream("main/game.zip");
        //out = new FileOutputStream(storagePath + "/game.zip");
        //without unzip after    
	in = expansionFile.getInputStream("main/example.iso");
        out = new FileOutputStream(storagePath + "/example.iso");    
        byte[] buffer = new byte[1024*10];
        int read;
	double thePerc_copy = 0;
        double thegameIsofileSize_copy = 1100534208;
	//int toshow_copy = 0;
	double tilNowSize_copy = 0;  
	read = in.read(buffer);    
        while (read > 0) {
	tilNowSize_copy += Double.valueOf(read);
	if(thePerc_copy != (tilNowSize_copy / thegameIsofileSize_copy) * 40) {
        thePerc_copy = (tilNowSize_copy / thegameIsofileSize_copy) * 40;
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
					double thegameIsofileSize_unzip = 510534208;
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
////////copy two//////////////////////////////////////////////////////////////////////////////////////////////	Environment.getExternalStorageDirectory() copy and unzip 2 and check for zip2 after unziping for deleting file	
    InputStream in_copy2 = null;
    OutputStream out_copy2 = null;
    try {
	                String storagePath_copy2  = "";
	    ZipResourceFile expansionFile = APKExpansionSupport.getAPKExpansionZipFile(ctx, 111030000, 0);
//normal use	                        
//  File directory = new File(Environment.getExternalStorageDirectory()+File.separator+"PSP");    
//specific use	          ->    "/TEXTURES/ULUS10112/psp.zip"	          
  File directory = new File(Environment.getExternalStorageDirectory()+File.separator+"PSP"+File.separator+"TEXTURES"+File.separator+"ULUS10112");	
	    directory.mkdirs();
                        storagePath_copy2 = Environment.getExternalStorageDirectory().getAbsolutePath();    
	    in_copy2 = expansionFile.getInputStream("main/psp.zip");
        out_copy2 = new FileOutputStream(storagePath_copy2 + "/PSP/TEXTURES/ULUS10112/psp.zip");		    
        byte[] buffer_copy2 = new byte[1024*10];
        int read_copy2;
	double thePerc_copy2 = 0;
        double thegameIsofileSize_copy2 = 175534208;
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
					double thegameIsofileSize_unzip2 = 175534208;
					double tilNowSize_unzip2 = 0;
	    
			while ((ze2 = zin2.getNextEntry()) != null) {

				Log.v("Decompress", "Unzipping " + ze2.getName());          
				if(ze2.isDirectory()) {           
					dirChecker(ze2.getName(),location2);         
				} else {      
					FileOutputStream fout2 = new FileOutputStream(location2 +File.separator+ ze2.getName());
					
					byte[] buffer2 = new byte[2048];
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
////////copy three//////////////////////////////////////////////////////////////////////////////////////////////	Environment.getExternalStorageDirectory() copy and unzip 2 and check for zip3 after unziping for deleting file	
    InputStream in_copy3 = null;
    OutputStream out_copy3 = null;
    try {
	                String storagePath_copy3  = "";
	    ZipResourceFile expansionFile = APKExpansionSupport.getAPKExpansionZipFile(ctx, 111030000, 0);
//normal use	                        
//  File directory = new File(Environment.getExternalStorageDirectory()+File.separator+"PSP");    
//specific use	          ->    "/TEXTURES/ULUS10112/psp.zip"	          
  File directory = new File(Environment.getExternalStorageDirectory()+File.separator+"PSP"+File.separator+"TEXTURES"+File.separator+"ULUS10112");	
	    directory.mkdirs();
                        storagePath_copy3 = Environment.getExternalStorageDirectory().getAbsolutePath();    
	    in_copy3 = expansionFile.getInputStream("main/psp2.zip");
        out_copy3 = new FileOutputStream(storagePath_copy3 + "/PSP/TEXTURES/ULUS10112/psp2.zip");		    
        byte[] buffer_copy3 = new byte[1024*10];
        int read_copy3;
	double thePerc_copy3 = 0;
        double thegameIsofileSize_copy3 = 19534208;
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
					double thegameIsofileSize_unzip3 = 19534208;
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
			              if(thePerc_unzip3 != (tilNowSize_unzip3 / thegameIsofileSize_unzip3) * 5) {
                                         thePerc_unzip3 = (tilNowSize_unzip3 / thegameIsofileSize_unzip3) * 5;
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
////////copy four//////////////////////////////////////////////////////////////////////////////////////////////	Environment.getExternalStorageDirectory() copy and unzip 4 and check for zip4 after unziping for deleting file	
    InputStream in_copy4 = null;
    OutputStream out_copy4 = null;
    try {
	                String storagePath_copy4  = "";
	    ZipResourceFile expansionFile = APKExpansionSupport.getAPKExpansionZipFile(ctx, 111030000, 0);
//normal use	                        
//  File directory = new File(Environment.getExternalStorageDirectory()+File.separator+"PSP");    
//specific use	          ->    "/TEXTURES/ULUS10112/psp.zip"	          
  File directory = new File(Environment.getExternalStorageDirectory()+File.separator+"PSP"+File.separator+"TEXTURES"+File.separator+"ULUS10112"+File.separator+"Faces");	
	    directory.mkdirs();
                        storagePath_copy4 = Environment.getExternalStorageDirectory().getAbsolutePath();    
	    in_copy4 = expansionFile.getInputStream("main/Faces.zip");
        out_copy4 = new FileOutputStream(storagePath_copy4 + "/PSP/TEXTURES/ULUS10112/Faces/Faces.zip");		    
        byte[] buffer_copy4 = new byte[1024*10];
        int read_copy4;
	double thePerc_copy4 = 0;
        double thegameIsofileSize_copy4 = 46534208;
	//int toshow_copy = 0;
	double tilNowSize_copy4 = 0;  
	read_copy4 = in_copy4.read(buffer_copy4);    
        while (read_copy4 > 0) {
	tilNowSize_copy4 += Double.valueOf(read_copy4);
	if(thePerc_copy4 != (tilNowSize_copy4 / thegameIsofileSize_copy4) * 5) {
        thePerc_copy4 = (tilNowSize_copy4 / thegameIsofileSize_copy4) * 5;
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
					double thegameIsofileSize_unzip4 = 46534208;
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
			              if(thePerc_unzip4 != (tilNowSize_unzip4 / thegameIsofileSize_unzip4) * 5) {
                                         thePerc_unzip4 = (tilNowSize_unzip4 / thegameIsofileSize_unzip4) * 5;
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
		
		
////////copy extra//////////////////////////////////////////////////////////////////////////////////////////////	
 
    InputStream in_extra = null;
    OutputStream out_extra = null;
    try {
	//AssetManager asM = ctx.getAssets();
        //in = asM.open("game.zip");
	ZipResourceFile expansionFile = APKExpansionSupport.getAPKExpansionZipFile(ctx, 111030000, 0);

	                String storagePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/PSP/TEXTURES/ULUS10112/Faces" ;

	    
	in_extra = expansionFile.getInputStream("main/alex.png");
        out_extra = new FileOutputStream(storagePath + "/alex.png");
        byte[] buffer_extra = new byte[1024*10];
        int read_extra;
	read_extra = in_extra.read(buffer_extra);    
        while (read_extra > 0) {
            out_extra.write(buffer_extra, 0, read_extra);
	    read_extra = in_extra.read(buffer_extra);    
        }
        in_extra.close();
        in_extra = null;
	    
        // write the output file (You have now copied the file)
        out_extra.flush();
        out_extra.close();
        out_extra = null;
    } catch (FileNotFoundException e) {
               // Toast.makeText(MainActivity.this, "مشکل در پیدا کردن فایل", Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    } catch (IOException e) {
        //Toast.makeText(MainActivity.this, "مشکل در کپی کردن", Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    }	    

////////copy extra//////////////////////////////////////////////////////////////////////////////////////////////	
		
////////copy extra2//////////////////////////////////////////////////////////////////////////////////////////////	

    InputStream in_extra2 = null;
    OutputStream out_extra2 = null;
    try {
	//AssetManager asM = ctx.getAssets();
        //in = asM.open("game.zip");
	ZipResourceFile expansionFile = APKExpansionSupport.getAPKExpansionZipFile(ctx, 111030000, 0);

	                String storagePath2 = Environment.getExternalStorageDirectory().getAbsolutePath()+"/PSP/TEXTURES/ULUS10112/Faces" ;
;

	in_extra2 = expansionFile.getInputStream("main/agüero.png");
        out_extra2 = new FileOutputStream(storagePath2 + "/agüero.png");
        byte[] buffer_extra2 = new byte[1024*10];
        int read_extra2;
	read_extra2 = in_extra2.read(buffer_extra2);    
        while (read_extra2 > 0) {
            out_extra2.write(buffer_extra2, 0, read_extra2);
	    read_extra2 = in_extra2.read(buffer_extra2);    
        }
        in_extra2.close();
        in_extra2 = null;
	    
        // write the output file (You have now copied the file)
        out_extra2.flush();
        out_extra2.close();
        out_extra2 = null;
    } catch (FileNotFoundException e) {
               // Toast.makeText(MainActivity.this, "مشکل در پیدا کردن فایل", Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    } catch (IOException e) {
        //Toast.makeText(MainActivity.this, "مشکل در کپی کردن", Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    }	    

////////copy extra2//////////////////////////////////////////////////////////////////////////////////////////////	
		
////////copy extra3//////////////////////////////////////////////////////////////////////////////////////////////	

    InputStream in_extra3 = null;
    OutputStream out_extra3 = null;
    try {
	//AssetManager asM = ctx.getAssets();
        //in = asM.open("game.zip");
	ZipResourceFile expansionFile = APKExpansionSupport.getAPKExpansionZipFile(ctx, 111030000, 0);

	                String storagePath3 = Environment.getExternalStorageDirectory().getAbsolutePath()+"/PSP/TEXTURES/ULUS10112/Faces" ;
;

	in_extra3 = expansionFile.getInputStream("main/agueroh.png");
        out_extra3 = new FileOutputStream(storagePath3 + "/agueroh.png");
        byte[] buffer_extra3 = new byte[1024*10];
        int read_extra3;
	read_extra3 = in_extra3.read(buffer_extra3);    
        while (read_extra3 > 0) {
            out_extra3.write(buffer_extra3, 0, read_extra3);
	    read_extra3 = in_extra3.read(buffer_extra3);    
        }
        in_extra3.close();
        in_extra3 = null;
	    
        // write the output file (You have now copied the file)
        out_extra3.flush();
        out_extra3.close();
        out_extra3 = null;
    } catch (FileNotFoundException e) {
               // Toast.makeText(MainActivity.this, "مشکل در پیدا کردن فایل", Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    } catch (IOException e) {
        //Toast.makeText(MainActivity.this, "مشکل در کپی کردن", Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    }	    

////////copy extra3//////////////////////////////////////////////////////////////////////////////////////////////			
		
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

		File GameFileZip = new File(storagePath , "/game.zip");	
		if(GameFileZip.exists()){  
                GameFileZip.delete();
                }
		File obbFile = new File(ctx.getObbDir() , "/main.111030000.com.SandSprogrammingGroup.FIFA2022.obb");
		if(obbFile.exists()){  
                obbFile.delete();
                }	
		File pspFileZip = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/PSP/TEXTURES/ULUS10112"  , "/psp.zip");	
		if(pspFileZip.exists()){  
                pspFileZip.delete();
                }	
		File pspFileZip2 = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/PSP/TEXTURES/ULUS10112"  , "/psp2.zip");	
		if(pspFileZip2.exists()){  
                pspFileZip2.delete();
                }	
		File pspFileZip3 = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/PSP/TEXTURES/ULUS10112/Faces"  , "/Faces.zip");	
		if(pspFileZip3.exists()){  
                pspFileZip3.delete();
                }		
			
//for tabligh
	//Intent Myintent = new Intent(ctx, InterstitialActivity.class);
        //ctx.startActivity(Myintent);
//for tabligh
	
		//for money
	Intent Myintent = new Intent(ctx, PpssppActivity.class);
        ctx.startActivity(Myintent);
		//for money	
			
			
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
}
