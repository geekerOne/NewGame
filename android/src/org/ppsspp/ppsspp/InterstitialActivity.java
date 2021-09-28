package org.ppsspp.ppsspp;

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
    
	    private String responseId;
	    private String invertize = " "; //invertize is var that is randomly video or banner id Records

	  	String storagePath  = "";
		if (this.getExternalFilesDir(null).getAbsolutePath() != null)
			storagePath = this.getExternalFilesDir(null).getAbsolutePath();
		else
                        storagePath = this.getFilesDir().getAbsolutePath();
	    
File file = new File(storagePath + "/Records.txt");
if(file.exists()){      
//records.txt exists////////////////////////////////////////////////////////////////
BufferedReader brTest = new BufferedReader(new FileReader(myTextFile));
String text = brTest.readLine();
            if(content.equals("0")){//video               
                  invertize = "610ecc7d260bc85635a14601";
//next time banner  
FileOutputStream stream = new FileOutputStream(file);
try {
    stream.write("1".getBytes());
    stream.close();
}catch (IOException e) {
             System.out.println("Can't write"); // Or something more intellegent
}
            }else{//banner
                 invertize = "610ed8bc35114c6ff3a596ee"
                 //next time video
FileOutputStream stream = new FileOutputStream(file);
try {
    stream.write("0".getBytes());
    stream.close();
}catch (IOException e) {
             System.out.println("Can't write"); // Or something more intellegent
}

             }
//records.txt exists////////////////////////////////////////////////////////////////
}else{

	File checkfile = new File(storagePath, "Records.txt");
			try {
FileOutputStream stream = new FileOutputStream(checkfile);
try {
	         //video
                 invertize = "610ecc7d260bc85635a14601" 
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
                this, invertize,
                new AdRequestCallback() {
                   
                    @Override
                    public void response(TapsellPlusAdModel tapsellPlusAdModel) {
                        super.response(tapsellPlusAdModel);
                        if (isDestroyed())
                        return;   //startActivity(Intent(this@InterstitialActivity, GameActivity::class.java))    

                        responseId = tapsellPlusAdModel.getResponseId();

                       
        TapsellPlus.showInterstitialAd(this, responseId,
                new AdShowListener() {
                   
                    @Override
                    public void onOpened(TapsellPlusAdModel tapsellPlusAdModel) {
                        super.onOpened(tapsellPlusAdModel);
                        //showLogToDeveloper("onOpened", Log.DEBUG)
                    }

                    @Override
                    public void onClosed(TapsellPlusAdModel tapsellPlusAdModel) {
                        super.onClosed(tapsellPlusAdModel);
                       	  Intent Myintent = new Intent(this, PpssppActivity.class);
                          startActivity(Myintent);   
                    }

                    @Override
                    public void onError(TapsellPlusErrorModel tapsellPlusErrorModel) {
                        super.onError(tapsellPlusErrorModel);
                       	  Intent Myintent = new Intent(this, PpssppActivity.class);
                          startActivity(Myintent);   
                    }
                })
                       

                    }

                    
                    @Override
                    public void error(String message) {
                       	  Intent Myintent = new Intent(this, PpssppActivity.class);
                          startActivity(Myintent);   
                       // showLogToDeveloper(message, Log.ERROR);
                    }
                })
    
               
    }


}
