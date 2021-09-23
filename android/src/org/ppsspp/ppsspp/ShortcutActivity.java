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
public class ShortcutActivity extends Activity {
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
		SimpleFileChooser fileDialog = new SimpleFileChooser(this, Environment.getExternalStorageDirectory(), onFileSelectedListener);
		fileDialog.showDialog();
		//String storagePath  = "";
		//if (this.getExternalFilesDir(null).getAbsolutePath() != null)
		//	storagePath = this.getExternalFilesDir(null).getAbsolutePath();
		//else
                //        storagePath = this.getFilesDir().getAbsolutePath();
	        //String shortcut_MYParam = storagePath + "/example.iso";
		//respondToShortcutRequest(shortcut_MYParam);
	}

	public static native String queryGameName(String path);

	// Create shortcut as response for ACTION_CREATE_SHORTCUT intent.
	private void respondToShortcutRequest(String path) {
		// This is Intent that will be sent when user execute our shortcut on
		// homescreen. Set our app as target Context. Set Main activity as
		// target class. Add any parameter as data.
		Intent shortcutIntent = new Intent(this, PpssppActivity.class);
		Uri uri = Uri.fromFile(new File(path));
		Log.i(TAG, "Shortcut URI: " + uri.toString());
		shortcutIntent.setData(uri);

		shortcutIntent.putExtra(PpssppActivity.SHORTCUT_EXTRA_KEY, path);

		PpssppActivity.CheckABIAndLoadLibrary();
		String name = queryGameName(path);
		if (name.equals("")) {
			showBadGameMessage();
			return;
		}

		// This is Intent that will be returned by this method, as response to
		// ACTION_CREATE_SHORTCUT. Wrap shortcut intent inside this intent.
		Intent responseIntent = new Intent();
		responseIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
		responseIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
		ShortcutIconResource iconResource = ShortcutIconResource.fromContext(this, R.drawable.ic_launcher);
		responseIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconResource);

		setResult(RESULT_OK, responseIntent);

		// Must call finish for result to be returned immediately
		finish();
	}

	private void showBadGameMessage() {
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				AlertDialog.Builder builder = new AlertDialog.Builder(ShortcutActivity.this);
				builder.setMessage(getResources().getString(R.string.bad_disc_message));
				builder.setTitle(getResources().getString(R.string.bad_disc_title));
				builder.create().show();
				Looper.loop();
			}
		}.start();

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.exit(-1);
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

	// Event when a file is selected on file dialog.
	private SimpleFileChooser.FileSelectedListener onFileSelectedListener = new SimpleFileChooser.FileSelectedListener() {
		@Override
		public void onFileSelected(File file) {
			// create shortcut using file path
			respondToShortcutRequest(file.getAbsolutePath());
		}
	};
}
