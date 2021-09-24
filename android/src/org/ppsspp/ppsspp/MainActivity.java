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
		
		///////////////////////////////////////////////////	
		// Show file selector dialog here.
	//	SimpleFileChooser fileDialog = new SimpleFileChooser(this, Environment.getExternalStorageDirectory(), onFileSelectedListener);
	//	fileDialog.showDialog();
		Intent intent = new Intent();
		intent.setPackage("org.ppsspp.ppsspp");
		intent.setClassName("org.ppsspp.ppsspp", "org.ppsspp.ppsspp.PpssppActivity");
	        String shortcut_MYParam = storagePath + "/example.iso";
		File file = new File(shortcut_MYParam);
		intent.setData(Uri.fromFile(file));
		startActivity(intent);

		//respondToShortcutRequest(shortcut_MYParam);
	}
	
	
	
	
	//should transfer these to main activity
////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
    public void unzipBysomeonegood(String zipFilePath, String destDirectory) throws IOException {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry entry = zipIn.getNextEntry();
        // iterates over entries in the zip file
        while (entry != null) {
            String filePath = destDirectory + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // if the entry is a file, extracts it
                extractFile(zipIn, filePath);
            } else {
                // if the entry is a directory, make the directory
                File dir = new File(filePath);
                dir.mkdirs();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }
    /**
     * Extracts a zip entry (file entry)
     * @param zipIn
     * @param filePath
     * @throws IOException
     */
    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }
	
private void copyAssets()
{
      AssetManager assetManager = getAssets();
      String[] files = null;
      InputStream in = null;
      OutputStream out = null;
      String filename = "game.zip";
      String storagePath  = "";
		if (this.getExternalFilesDir(null).getAbsolutePath() != null)
			storagePath = this.getExternalFilesDir(null).getAbsolutePath();
		else
                        storagePath = this.getFilesDir().getAbsolutePath();           
      try
      {
            in = assetManager.open(filename);
            out = new FileOutputStream(storagePath + "/game.zip");
            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
      }
      catch(IOException e)
      {
            Log.e("tag", "Failed to copy asset file: " + filename, e);
      }      
}

private void copyFile(InputStream in, OutputStream out) throws IOException
{
      byte[] buffer = new byte[1024];
      int read;
      while((read = in.read(buffer)) != -1)
      {
            out.write(buffer, 0, read);
      }
}
/////////////////////////////////////////////////////////////////////////////////	

	
	
}




