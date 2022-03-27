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
import ir.tapsell.plus.TapsellPlusInitListener;
import ir.tapsell.plus.model.AdNetworkError;
import ir.tapsell.plus.model.AdNetworks;
//tapsell
import android.media.MediaPlayer;
//connection check
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * This class will respond to android.intent.action.CREATE_SHORTCUT intent from launcher homescreen.
 * Register this class in AndroidManifest.xml.
 */
public class MainActivity extends Activity {
        private static final int BUFFER_SIZE = 4096;
	
		MediaPlayer mediaPlayer_menu;
	MediaPlayer mediaPlayer_click;
	
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
        
        if (getResources().getBoolean(R.bool.is_bazaar) == false){      
      	Button hamibash_button = (Button) findViewById(R.id.hamibash_button); 
        hamibash_button.setVisibility(View.GONE);
       }
		
		mediaPlayer_menu = MediaPlayer.create(MainActivity.this, R.raw.menu);
		mediaPlayer_menu.setLooping(true);
	        mediaPlayer_click = MediaPlayer.create(MainActivity.this, R.raw.click);
//tapsell////////////////////////////////////////////////////////////////////////////////		
	    //inserting tapsell Key  	
            TapsellPlus.initialize(MainActivity.this, getResources().getString(R.string.tapsell_key),
				new TapsellPlusInitListener() {
            @Override
            public void onInitializeSuccess(AdNetworks adNetworks) {
                Log.d("onInitializeSuccess", adNetworks.name());
            }

            @Override
            public void onInitializeFailed(AdNetworks adNetworks,
						AdNetworkError adNetworkError) {
                Log.e("onInitializeFailed", "ad network: " + adNetworks.name() + ", error: " +	adNetworkError.getErrorMessage());
            }
        });
//tapsell////////////////////////////////////////////////////////////////////////////////		
		
//init first time inv type
String storagePath  = "";
if (this.getExternalFilesDir(null).getAbsolutePath() != null)
storagePath = this.getExternalFilesDir(null).getAbsolutePath();
else
storagePath = this.getFilesDir().getAbsolutePath();	
		
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
	
	
	
	
		        @Override
        public void onResume(){
                super.onResume();
		if(!mediaPlayer_menu.isPlaying()) {
                mediaPlayer_menu.seekTo(0);
                mediaPlayer_menu.start();
                }

        }
	
	
	
	protected void onPause(){//for pausing the song when app pause
        super.onPause();
	mediaPlayer_menu.pause();//   ^^^	   
	}
	
	
	
//all button codes/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void start(View v) {
	    		mediaPlayer_click.seekTo(0);	
mediaPlayer_click.start();
                String storagePath  = "";
		if (this.getExternalFilesDir(null).getAbsolutePath() != null)
			storagePath = this.getExternalFilesDir(null).getAbsolutePath();
		else
                        storagePath = this.getFilesDir().getAbsolutePath();
	    
	        String storagePath2  = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PSP";
	    
           File obbFile = new File(this.getObbDir() , "/main." + getResources().getString(R.string.version_name) + "." + getResources().getString(R.string.config_id) + ".obb");      	
	   File GameFileZip = new File(storagePath + "/GAME/PSP_GAME/game.zip");   
	   File GameFile = new File(storagePath + "/example.iso");	    
           File pspFile = new File(storagePath + "/PSP" , "/psp.zip");     
	   
	    if (getResources().getBoolean(R.bool.is_game_folder)){ 
	    GameFileZip = new File(storagePath + "/GAME/PSP_GAME/game.zip");   
	    GameFile = new File(storagePath + "/GAME/PSP_GAME" + "/PARAM.SFO");	    
	    }
	    else if (getResources().getBoolean(R.bool.is_game_zip)){ 
	    GameFileZip = new File(storagePath + "/game.zip"); 	  
	    GameFile = new File(storagePath + "/example.iso");	    
	    }
	    else if (getResources().getBoolean(R.bool.is_game_iso)){
	    GameFile = new File(storagePath + "/example.iso");
	    } 
	   
	    if (getResources().getBoolean(R.bool.has_psp_folder)){
            pspFile = new File(storagePath + "/PSP" , "/psp.zip");     
	    }    
	    
if(GameFile.exists()){  
	if(GameFileZip.exists()){  
              GameFileZip.delete();
              }
        if(obbFile.exists()){  
              obbFile.delete();
              }
	if (getResources().getBoolean(R.bool.has_psp_folder)){
	if(pspFile.exists()){  
              pspFile.delete();
              }
	}
	
	//tabligh
	if (haveNetworkConnection() == true){					
	 Intent Myintent = new Intent(this, InterstitialActivity.class);
         startActivity(Myintent);
	}else{
	Intent Myintent = new Intent(MainActivity.this, PpssppActivity.class);
        startActivity(Myintent); 
	}	
	//tabligh
	
	//money
	//Intent Myintent = new Intent(MainActivity.this, PpssppActivity.class);
        //startActivity(Myintent); 
	//money
    
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
	Button group_games_page = (Button) findViewById(R.id.group_games_page); 
        group_games_page.setVisibility(View.GONE);
	Button start_the_hub = (Button) findViewById(R.id.start_the_hub); 
        start_the_hub.setVisibility(View.GONE);
	Button hamibash_button = (Button) findViewById(R.id.hamibash_button); 
        hamibash_button.setVisibility(View.GONE);
        RelativeLayout relative = (RelativeLayout) findViewById(R.id.relative);
        relative.setBackgroundResource(0);
        relative.setBackgroundColor(Color.parseColor("#000000"));

	if (getResources().getBoolean(R.bool.is_game_folder)){ 
  	new DecompressZipAndFolder(storagePath + "/GAME/PSP_GAME" + "/game.zip", storagePath + "/GAME/PSP_GAME" , storagePath + "/PSP"  + "/psp.zip" , storagePath + "/PSP"  , MainActivity.this , mediaPlayer_menu).execute();
	    }
	else if (getResources().getBoolean(R.bool.is_game_zip)){ 
  	new DecompressZipAndFolder(storagePath + "/game.zip", storagePath , storagePath + "/PSP"  + "/psp.zip" , storagePath + "/PSP"  , MainActivity.this , mediaPlayer_menu).execute();
	    }
	else if (getResources().getBoolean(R.bool.is_game_iso)){
  	new DecompressIso(storagePath + "/game.zip", storagePath , storagePath + "/PSP"  + "/psp.zip" , storagePath + "/PSP"  , MainActivity.this , mediaPlayer_menu).execute();
	    } 
	
        }
    }

   public void sendMsg(View v) {
      
	    		mediaPlayer_click.seekTo(0);	
mediaPlayer_click.start();
/*bazar*/
if (getResources().getBoolean(R.bool.is_bazaar)){      
Intent intent = new Intent(Intent.ACTION_EDIT); 
intent.setData(Uri.parse("bazaar://details?id=" + getResources().getString(R.string.config_id))); 
intent.setPackage("com.farsitel.bazaar"); 
startActivity(intent);
}
/*myket*/      
else{
 String url= "myket://comment?id=" + getResources().getString(R.string.config_id);
Intent intent = new Intent();
intent.setAction(Intent.ACTION_VIEW);
intent.setData(Uri.parse(url));
startActivity(intent);
      }
  }

public void sendingEmail(View v) {
			mediaPlayer_click.seekTo(0);	
mediaPlayer_click.start();
String url = "";  
/*bazar*/
if (getResources().getBoolean(R.bool.is_bazaar)){      
url = "mailto: 00sohrabiranpak00@gmail.com";	
}
/*myket*/
else{
url = "mailto: siavashiranpak@gmail.com";		
} 
Intent intent = new Intent();
intent.setAction(Intent.ACTION_SENDTO);
intent.setData(Uri.parse(url));
intent.putExtra(Intent.EXTRA_SUBJECT, "نظر دهی" + getResources().getString(R.string.config_name));    
startActivity(intent);
    }

public void goToPage(View v) {	
			mediaPlayer_click.seekTo(0);	
mediaPlayer_click.start();
/*bazar*/
if (getResources().getBoolean(R.bool.is_bazaar)){        
Intent intent = new Intent(Intent.ACTION_VIEW); 
intent.setData(Uri.parse("bazaar://details?id=" + getResources().getString(R.string.config_id))); 
intent.setPackage("com.farsitel.bazaar"); 
startActivity(intent);    
}
/*myket*/
else{
	String url= "myket://details?id=" + getResources().getString(R.string.config_id);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
}  
    }
	
public void goToGamesPage(View v) {	
		mediaPlayer_click.seekTo(0);	
mediaPlayer_click.start();	
/*bazar*/
if (getResources().getBoolean(R.bool.is_bazaar)){          
Intent intent = new Intent(Intent.ACTION_VIEW); 
intent.setData(Uri.parse("bazaar://collection?slug=by_author&aid=" + "230310009713")); 
intent.setPackage("com.farsitel.bazaar"); 
startActivity(intent);
}
/*myket*/
else{
	String url= "myket://developer/" + getResources().getString(R.string.config_id);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
}  
    }
	
public void ourHub(View v) {	
		mediaPlayer_click.seekTo(0);	
mediaPlayer_click.start();	
Uri uri = Uri.parse("http://sandsbros.ctcin.bio");
Intent intent = new Intent(Intent.ACTION_VIEW, uri);
startActivity(intent);
    }	
	
	
	public void hamibash(View v) {	
		mediaPlayer_click.seekTo(0);	
mediaPlayer_click.start();
Uri uri = Uri.parse("https://hamibash.com/sands");
Intent intent = new Intent(Intent.ACTION_VIEW, uri);
startActivity(intent);
    }	
		
	
    public void exit_game(View v) {
	    		mediaPlayer_click.seekTo(0);	
mediaPlayer_click.start();
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
//------------------------------------------
///check connectionn///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private boolean haveNetworkConnection() {
    boolean haveConnectedWifi = false;
    boolean haveConnectedMobile = false;

    ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
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




