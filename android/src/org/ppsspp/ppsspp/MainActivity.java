package org.ppsspp.ppsspp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.UiModeManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import androidx.documentfile.provider.DocumentFile;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.HapticFeedbackConstants;
import android.view.InputDevice;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;
import java.io.File;
import java.lang.reflect.Field;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import com.jakewharton.processphoenix.ProcessPhoenix;
//new

/**
 * This class will respond to android.intent.action.CREATE_SHORTCUT intent from launcher homescreen.
 * Register this class in AndroidManifest.xml.
 */
public class MainActivity extends Activity {
	private static final String TAG = "PPSSPP";
        private static final int BUFFER_SIZE = 4096;
	
    // Defining Permission codes.
    // We can give any value
    // but unique for each permission.
    private static final int STORAGE_PERMISSION_CODE = 1;
	private static final String[] permissionsForStorage = {
		Manifest.permission.WRITE_EXTERNAL_STORAGE,
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
                //trying to automate ppsspp
		///////////////////////////////////////////////////   sdcard0/SandS/example.iso
		checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
	}
	
	
	
	
	
	
	
	
//all button codes/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void start(view: View) {
	    
	   val storagePath: String = (this.getExternalFilesDir(null) ?: this.filesDir).path
        val cfile = File(storagePath + "/example.nds")//diffrent for each game
        var fileExists = cfile.exists()
    val bfile = File(storagePath + "/system/PPSSPP/example.zip")
        var fileExistscheck = bfile.exists()
	 val dfile = File(storagePath + "/game.zip")
        var fileExistscheck2 = dfile.exists()
            
    if(fileExists){
            if(fileExistscheck){
              bfile.delete()
              }
	                if(fileExistscheck2){
                 dfile.delete()
              }
    
    
	
	    startActivity(Intent(this@MainActivity, InterstitialActivity::class.java))
          //          startActivity(Intent(this@MainActivity, GameActivity::class.java))

    
    
    } else {

	    
        // Do something in response to button click
        val start_the_game_button = findViewById(R.id.start_the_game_button) as Button
        start_the_game_button.isEnabled = false
        start_the_game_button.visibility = View.INVISIBLE
        val comments = findViewById(R.id.comments) as Button
        comments.isEnabled = false
        comments.visibility = View.GONE
        val game_page = findViewById(R.id.game_page) as Button
        game_page.isEnabled = false
        game_page.visibility = View.GONE
        val exit_button = findViewById(R.id.exit_button) as Button
        exit_button.isEnabled = false
        exit_button.visibility = View.GONE
        val send_email = findViewById(R.id.send_email) as Button
        send_email.isEnabled = false
        send_email.visibility = View.GONE
        val relative = findViewById(R.id.relative) as RelativeLayout
        relative.setBackgroundResource(0)
        relative.setBackgroundColor(Color.parseColor("#000000"))
        
			

				        someTask(this,this).execute()
		
        }
    }

    public void sendMsg(view: View) {
	
	/*myket*/
	val openURL = Intent(android.content.Intent.ACTION_VIEW)	    
        openURL.data = Uri.parse("myket://comment?id=com.draco.ludere.captainTusbasanewKickOff")
        
	/*bazar*/
	//val openURL = Intent(android.content.Intent.ACTION_EDIT)
        //openURL.data = Uri.parse("bazaar://details?id=com.draco.ludere.captainTusbasanewKickOff")
        //openURL.setPackage("com.farsitel.bazaar")
	
	
        startActivity(openURL)
    }

    public void sendingEmail(view: View) {

        val intent = Intent(Intent.ACTION_SENDTO)
        
	    
	/*myket*/
	intent.data = Uri.parse("mailto: siavashiranpak@gmail.com")
        /*bazar*/
        //intent.data = Uri.parse("mailto: 00sohrabiranpak00@gmail.com")        
	
	    
	    
	intent.putExtra(Intent.EXTRA_SUBJECT, "نظر دهی")
        startActivity(intent)

    }

    public void goToPage(view: View) {
        
	//for both
	val openURL = Intent(android.content.Intent.ACTION_VIEW)
	
	
	/*myket*/
        openURL.data = Uri.parse("myket://details?id=com.draco.ludere.captainTusbasanewKickOff")
	
	/*bazar*/
        //openURL.data = Uri.parse("bazaar://details?id=com.draco.ludere.captainTusbasanewKickOff")
        //openURL.setPackage("com.farsitel.bazaar")
	    
	    
	startActivity(openURL)
    }

    public void exit_game(view: View) {
        this@MainActivity.finish()
        exitProcess(0)
    }
//all button codes/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
		
	
//###################################################################	
	
	
// Function to check and request permission.//////////////////////////////////////////////////////////////////////////////////////
    public void checkPermission(String permission, int requestCode)
    {
        if (this.checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED) {
 
            // Requesting the permission
            this.requestPermissions(permissionsForStorage , requestCode);
        }
        else {
            Toast.makeText(MainActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }
 
	
 
	@Override
	public void onRequestPermissionsResult(int requestCode, String [] permissions, int[] grantResults) {
 
 if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
		//		NativeApp.sendMessage("permission_granted", "storage");
		    
	 Toast.makeText(MainActivity.this, "Permission granted", Toast.LENGTH_SHORT).show();
   
		                    String storagePath  = "";
		if (this.getExternalFilesDir(null).getAbsolutePath() != null)
			storagePath = this.getExternalFilesDir(null).getAbsolutePath();
		else
                        storagePath = this.getFilesDir().getAbsolutePath();
		
		    
		Intent intent = new Intent();
		intent.setPackage("org.ppsspp.ppsspp");
		intent.setClassName("org.ppsspp.ppsspp", "org.ppsspp.ppsspp.PpssppActivity");
	        String shortcut_MYParam = storagePath + "/example.iso";
		File file = new File(shortcut_MYParam);
		intent.setData(Uri.fromFile(file));
		startActivity(intent);

		    
            } else {
				NativeApp.sendMessage("permission_denied", "storage");
            }
        }
    }
// Function to check and request permission.//////////////////////////////////////////////////////////////////////////////////////
	
	
	
//###################################################################	
	
	
///copy and unzip assets///////////////////////////////////////////////////////////////////////////////////////////////////////////	
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
            in = getAssets().open("game.zip");
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
      byte[] buffer = new byte[1024*4];
      int read;
      while((read = in.read(buffer)) != -1)
      {
            out.write(buffer, 0, read);
      }
}
///copy and unzip assets///////////////////////////////////////////////////////////////////////////////////////////////////////////		

	
	
}




