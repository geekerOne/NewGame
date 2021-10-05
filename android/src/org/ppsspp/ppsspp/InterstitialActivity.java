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
import android.os.Environment;
import android.os.PowerManager;
import android.os.Vibrator;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import androidx.documentfile.provider.DocumentFile;
import android.text.InputType;
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
import java.lang.reflect.Field;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.widget.Button;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import ir.tapsell.plus.AdRequestCallback;
import ir.tapsell.plus.AdShowListener;
import ir.tapsell.plus.TapsellPlus;
import ir.tapsell.plus.model.TapsellPlusAdModel;
import ir.tapsell.plus.model.TapsellPlusErrorModel;
import android.content.Intent;
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
import java.io.BufferedReader;

import com.jakewharton.processphoenix.ProcessPhoenix;
//new

public class InterstitialActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    
	     String invertize = " "; //invertize is var that is randomly video or banner id Records

	  	String storagePath  = "";
		if (this.getExternalFilesDir(null).getAbsolutePath() != null)
			storagePath = this.getExternalFilesDir(null).getAbsolutePath();
		else
                        storagePath = this.getFilesDir().getAbsolutePath();
	    
File file = new File(storagePath + "/Records.txt");
if(file.exists()){      
//records.txt exists////////////////////////////////////////////////////////////////
try {
BufferedReader brTest = new BufferedReader(new FileReader(file));
String text = brTest.readLine();
            if(text.equals("0")){//video               
                  invertize = "615be6f4e3fe321708e1277e";
//next time banner  
FileOutputStream stream = new FileOutputStream(file);

    stream.write("1".getBytes());
    stream.close();
            }else{//banner
                 invertize = "615be6fffddfdf39359ad5c0";
	//next time video
	
FileOutputStream stream = new FileOutputStream(file);

    stream.write("0".getBytes());
    stream.close();
}
}catch (IOException e) {//| FileNotFoundException
             System.out.println("Can't write"); // Or something more intellegent
}

          
//records.txt exists////////////////////////////////////////////////////////////////
}else{

	File checkfile = new File(storagePath, "Records.txt");
			try {
FileOutputStream stream = new FileOutputStream(checkfile);
try {
	         //video
                 invertize = "615be6f4e3fe321708e1277e";
                 //next time banner
    stream.write("1".getBytes());
    stream.close();
	
	
}catch (IOException e) {
             System.out.println("Can't write"); // Or something more intellegent
}		
				} catch (FileNotFoundException e) {
             System.out.println("Can't find"); // Or something more intellegent
             }
}	





//invertize is var that is randomly video or banner id
        TapsellPlus.requestInterstitialAd(
                InterstitialActivity.this, invertize,
                new AdRequestCallback() {
                   
                    @Override
                    public void response(TapsellPlusAdModel tapsellPlusAdModel) {
                        super.response(tapsellPlusAdModel);
                        if (isDestroyed())
                        return;   //startActivity(Intent(this@InterstitialActivity, GameActivity::class.java))    

                      String responseId = tapsellPlusAdModel.getResponseId();

                       
        TapsellPlus.showInterstitialAd(InterstitialActivity.this, responseId,
                new AdShowListener() {
                   
                    @Override
                    public void onOpened(TapsellPlusAdModel tapsellPlusAdModel) {
                        super.onOpened(tapsellPlusAdModel);
                        //showLogToDeveloper("onOpened", Log.DEBUG)
                    }

                    @Override
                    public void onClosed(TapsellPlusAdModel tapsellPlusAdModel) {
                        super.onClosed(tapsellPlusAdModel);
                       	  Intent Myintent = new Intent(InterstitialActivity.this, PpssppActivity.class);
                          startActivity(Myintent);   
                    }

                    @Override
                    public void onError(TapsellPlusErrorModel tapsellPlusErrorModel) {
                        super.onError(tapsellPlusErrorModel);
                       	  Intent Myintent = new Intent(InterstitialActivity.this, PpssppActivity.class);
                          startActivity(Myintent);   
                    }
                });
                       

                    }

                    
                    @Override
                    public void error(String message) {
                       	  Intent Myintent = new Intent(InterstitialActivity.this, PpssppActivity.class);
                          startActivity(Myintent);   
                       // showLogToDeveloper(message, Log.ERROR);
                    }
                });
    
               
    }


}
