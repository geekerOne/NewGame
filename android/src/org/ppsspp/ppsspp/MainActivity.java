package org.ppsspp.ppsspp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import java.io.File;
//new
import android.content.res.AssetManager;
import java.io.*;
import java.util.zip.ZipFile;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
//new

/**
 * This class will respond to android.intent.action.CREATE_SHORTCUT intent from launcher homescreen.
 * Register this class in AndroidManifest.xml.
 */
public class MainActivity extends Activity {
	private static final String TAG = "PPSSPP";
        private static final int BUFFER_SIZE = 4096;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
                //trying to automate ppsspp
		///////////////////////////////////////////////////   sdcard0/SandS/example.iso
		/*
		copyAssets();
		
                String storagePath  = "";
		if (this.getExternalFilesDir(null).getAbsolutePath() != null)
			storagePath = this.getExternalFilesDir(null).getAbsolutePath();
		else
                        storagePath = this.getFilesDir().getAbsolutePath();
		
		try {
		unzipBysomeonegood(storagePath + "/game.zip" , storagePath);
             } catch (IOException e) {
             System.out.println("Can't unzip"); // Or something more intellegent
             }
	        respondToShortcutRequest(storagePath + "/example.iso");//path of unziped game in MainActivity
		*/
		///////////////////////////////////////////////////	
		// Show file selector dialog here.
	//	SimpleFileChooser fileDialog = new SimpleFileChooser(this, Environment.getExternalStorageDirectory(), onFileSelectedListener);
	//	fileDialog.showDialog();
		Intent intent = new Intent();
		intent.setPackage("org.ppsspp.ppsspp");
		intent.setClassName("org.ppsspp.ppsspp", "org.ppsspp.ppsspp.PpssppActivity");

		String storagePath  = "";
		if (this.getExternalFilesDir(null).getAbsolutePath() != null)
			storagePath = this.getExternalFilesDir(null).getAbsolutePath();
		else
                        storagePath = this.getFilesDir().getAbsolutePath();
	        String shortcut_MYParam = storagePath + "/example.iso";
		File file = new File(shortcut_MYParam);
		intent.setData(Uri.fromFile(file));
		startActivity(intent);

		//respondToShortcutRequest(shortcut_MYParam);
	}
