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
import android.widget.RelativeLayout;
import android.widget.Button;
import android.graphics.Paint;
import android.os.AsyncTask;
import java.net.URL;
import java.net.URLConnection;
import android.graphics.Color;
import android.content.res.Resources;
//import org.ppsspp.ppsspp.Decompress;
//new
//tapsell
import ir.tapsell.plus.TapsellPlus;
import ir.tapsell.plus.AdRequestCallback;
import ir.tapsell.plus.AdShowListener;
import ir.tapsell.plus.model.TapsellPlusAdModel;
import ir.tapsell.plus.model.TapsellPlusErrorModel;
//tapsell
/**
 * This class will respond to android.intent.action.CREATE_SHORTCUT intent from launcher homescreen.
 * Register this class in AndroidManifest.xml.
 */
public class MainActivity extends Activity {
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
        
		
//tapsell////////////////////////////////////////////////////////////////////////////////		
	    //inserting tapsell Key  	
            TapsellPlus.initialize(this, "ssfskoqkojqmpdtfpbiidrbamjjitdfhhepjrhepprfppatigbpgrhdnqfgiciefntdahq",
				new TapsellPlusInitListener() {
            @Override
            public void onInitializeSuccess(AdNetworks adNetworks) {
                //Log.d("onInitializeSuccess", adNetworks.name());
            }

            @Override
            public void onInitializeFailed(AdNetworks adNetworks,
						AdNetworkError adNetworkError) {
                //Log.e("onInitializeFailed", "ad network: " + adNetworks.name() + ", error: " +	adNetworkError.getErrorMessage());
            }
        });
//tapsell////////////////////////////////////////////////////////////////////////////////		
		
		
//init first time inv type
File file = new File(storagePath + "/Records.txt");
if(file.exists()){      
//nothing
}else{
	File checkfile = new File(storagePath, "Records.txt");
			try {
FileOutputStream stream = new FileOutputStream(checkfile);
try {
    stream.write("0".getBytes());
    stream.close();
}catch (IOException e) {
             System.out.println("Can't write"); // Or something more intellegent
}		
				} catch (FileNotFoundException e) {
             System.out.println("Can't find"); // Or something more intellegent
             }
}	
//init first time inv type		

		
		//trying to automate ppsspp
		///////////////////////////////////////////////////   should take the permission
		checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
	}
	
	
	
	
	
	
	
	
//all button codes/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void start(View v) {
	    
                String storagePath  = "";
		if (this.getExternalFilesDir(null).getAbsolutePath() != null)
			storagePath = this.getExternalFilesDir(null).getAbsolutePath();
		else
                        storagePath = this.getFilesDir().getAbsolutePath();
	    
	    File GameFile = new File(storagePath + "/example.iso");
	    File GameFileZip = new File(storagePath , "/game.zip");

if(GameFile.exists()){  
	if(GameFileZip.exists()){  
              GameFileZip.delete();
              }
  
	  Intent Myintent = new Intent(this, InterstitialActivity.class);
          startActivity(Myintent);
	
	  //  startActivity(Intent(this@MainActivity, InterstitialActivity::class.java)) //tabligh game!
          //          startActivity(Intent(this@MainActivity, GameActivity::class.java)) //money game

    
    
    } else {

	    
        // Do something in response to button click
        Button start_the_game_button = (Button) findViewById(R.id.start_the_game_button);
        start_the_game_button.setVisibility(View.GONE);
        Button comments = (Button) findViewById(R.id.comments);
        comments.setVisibility(View.GONE);
        Button game_page = (Button) findViewById(R.id.game_page);
        game_page.setVisibility(View.GONE);
        Button exit_button = (Button) findViewById(R.id.exit_button); 
        exit_button.setVisibility(View.GONE);
        Button send_email = (Button) findViewById(R.id.send_email); 
        send_email.setVisibility(View.GONE);
        RelativeLayout relative = (RelativeLayout) findViewById(R.id.relative);
        relative.setBackgroundResource(0);
        relative.setBackgroundColor(Color.parseColor("#000000"));
       
	new Decompress(storagePath + "/game.zip", storagePath, MainActivity.this).execute();
		
        }
    }

    public void sendMsg(View v) {
	
	/*myket*/
String url= "myket://comment?id=com.draco.ludere.captainTusbasanewKickOff";
Intent intent = new Intent();
intent.setAction(Intent.ACTION_VIEW);
intent.setData(Uri.parse(url));
startActivity(intent);
		
	/*bazar*/
	//val openURL = Intent(android.content.Intent.ACTION_EDIT)
        //openURL.data = Uri.parse("bazaar://details?id=com.draco.ludere.captainTusbasanewKickOff")
        //openURL.setPackage("com.farsitel.bazaar")
	
	
    }

    public void sendingEmail(View v) {
	/*myket*/
	String url = "mailto: siavashiranpak@gmail.com";
        /*bazar*/
        //String url = "mailto: 00sohrabiranpak00@gmail.com";		
Intent intent = new Intent();
intent.setAction(Intent.ACTION_SENDTO);
intent.setData(Uri.parse(url));
intent.putExtra(Intent.EXTRA_SUBJECT, "نظر دهی");    
startActivity(intent);
    }

    public void goToPage(View v) {	
	

	/*myket*/
        
	String url= "myket://details?id=com.draco.ludere.captainTusbasanewKickOff";
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
	
	    /*bazar*/
        //openURL.data = Uri.parse("bazaar://details?id=com.draco.ludere.captainTusbasanewKickOff")
        //openURL.setPackage("com.farsitel.bazaar")
      
    }

    public void exit_game(View v) {
	this.finishAffinity();	
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
            //continue (nothing)
        }
    }
 
	
 
	@Override
	public void onRequestPermissionsResult(int requestCode, String [] permissions, int[] grantResults) {
 
 if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //continue (nothing)
            } else {
            //exit the Game!
	    this.finishAffinity();	    
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




