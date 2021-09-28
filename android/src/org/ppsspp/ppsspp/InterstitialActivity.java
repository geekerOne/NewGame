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

class InterstitialActivity : Activity() {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
    
	    private String responseId;
	    
        super.onCreate(savedInstanceState);

	  //  val storagePath: String = (this.getExternalFilesDir(null) ?: this.filesDir).path
    //    val file = File(storagePath + "Records.txt")
	//var invertize = " "  //invertize is var that is randomly video or banner id
   //     var fileExists = file.exists()
  //       if(fileExists){

    //         var content:String = file.readText()
             
     //        if(content.equals("0")){//video
               
        //          invertize = "61496da714d2fc6e7183737b"
                  //next time banner
		//  file.writeText("1") 

      //       }
       //      else{//banner
                 
         //        invertize = "61496db79adf3f2fd896d848"
                 //next time video
//		 file//.writeText("0")

   //          }
         
         
   //     }else{ //video
	          
    //              invertize = "61496da714d2fc6e7183737b" 
                 //next time banner
	//	 file.writeText("1")  
        
	// }//init the first time invertisement randomly





//invertize is var that is randomly video or banner id
	//    
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
                        startActivity(Intent(this@InterstitialActivity, GameActivity::class.java))    
                    }

                    @Override
                    public void onError(TapsellPlusErrorModel tapsellPlusErrorModel) {
                        super.onError(tapsellPlusErrorModel);
                       startActivity(Intent(this@InterstitialActivity, GameActivity::class.java))    
                    }
                })
                       

                    }

                    
                    @Override
                    public void error(String message) {
                        startActivity(Intent(this@InterstitialActivity, GameActivity::class.java))
                       // showLogToDeveloper(message, Log.ERROR);
                    }
                })
    
               
    }


}
