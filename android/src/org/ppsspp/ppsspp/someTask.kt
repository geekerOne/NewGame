package org.ppsspp.ppsspp
import com.jakewharton.processphoenix.ProcessPhoenix
import android.content.pm.PackageManager
import android.content.pm.PackageInfo
import android.net.ConnectivityManager
import java.net.URL
import java.net.URLConnection
import android.content.ContentValues.TAG
import android.app.ProgressDialog
import android.util.Log
import android.view.ViewGroup
import android.os.AsyncTask
import android.content.Context
import android.widget.Toast  
import android.graphics.Paint
import android.widget.TextView
import android.widget.ProgressBar
import java.io.*
import java.util.zip.ZipFile
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.io.FileOutputStream
import android.widget.RelativeLayout
import java.util.*
import kotlin.system.exitProcess
import android.net.Uri
import android.content.Intent
import android.app.Activity
import android.app.Service
import android.content.DialogInterface
import android.hardware.input.InputManager
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import android.widget.Button
import kotlinx.coroutines.*
import android.graphics.Color
import android.content.pm.ActivityInfo

        
        /*asynctask new
        */
        class someTask( context:Context , mainActivity: MainActivity ) : AsyncTask<Void, String, String>() {
    
            var context: Context = context 
            val roootView = mainActivity
             val BUFFER_SIZE = 4096 * 8
             val pgsBar = roootView.findViewById(R.id.pBar) as ProgressBar
            val textView = roootView.findViewById(R.id.textview) as TextView
	    	val second_start = roootView.findViewById(R.id.second_start) as Button
	
             val TAG = "MyMessage"
            var current : Double = 0.0
            var current_copy : Double = 0.0
            var prev : Double = -1.0
		var prev_copy : Double = -1.0
	
		//
            var current2 : Double = 0.0
            var current_copy2 : Double = 0.0
            var prev2 : Double = -1.0
	    var prev_copy2 : Double = -1.0
		//
	
	var prev_download : Double = -1.0
		val storagePath: String = (context.getExternalFilesDir(null) ?: context.filesDir).path             
			var ll = 490816696 
		        var ll2 = 680534208 
		        var ll_zip = 550816696
	            	var ll_zip2 = 680534208
		
		
	 	         var ll_download = 1100000000
				        

            var toshoow = 0
		val zipFilePath = File(Environment.getExternalStorageDirectory() + "/PSP/psp.zip")
            val destDirectory = Environment.getExternalStorageDirectory() + "/PSP/"
	
		//new zip2
	val zipFilePath2 = File(storagePath + "/game.zip")
            val destDirectory2 = storagePath 
		//new zip2
		
	        val myProgressDialog = ProgressDialog(context)
//for copy
    //val afile = context.assets.open( "example.zip" )
    //    val afile2 = context.assets.open( "game.zip" )
    
    	val folder2 = Environment.getExternalStorageDirectory()
        val f2 = File(folder2, "PSP")
        f2.mkdir()
    
    val bfile = File(Environment.getExternalStorageDirectory() + "/PSP/psp.zip")	
    val dfile = File(storagePath + "/game.zip")	
            
     override fun onPreExecute() {
        super.onPreExecute()
        Toast.makeText(context,"برای اجرای اولیه نیاز به آماده سازی وجود دارد",Toast.LENGTH_LONG).show()  
      //  pgsBar.setVisibility(View.VISIBLE)
         // ...
	       	myProgressDialog.setMessage("در حال انجام عملیات...لطفا شکیبا باشید")
		myProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
		myProgressDialog.setCancelable(false)
		myProgressDialog.setMax(100)
	        myProgressDialog.show()
		

    }
            
            
            
            override fun doInBackground(vararg params: Void):String? {
	
	           //for testing obb
   	val expansionFile =
        APKExpansionSupport.getAPKExpansionZipFile(context, 111030000, 0)	    
    
		    //copy1
    var inStream: InputStream? = null
    var outStream: OutputStream? = null
    inStream = expansionFile.getInputStream("main/psp.zip")
    outStream = FileOutputStream(bfile)
    val buffer = ByteArray(1024*10)
    var length = inStream.read(buffer)
    while (length    > 0 )
    {
	    current_copy += length.toDouble()
	    		if(prev_copy != current_copy / ll * 20) {
                           prev_copy = current_copy / ll * 20
                           toshoow = prev_copy.toInt()    
			   publishProgress(""+toshoow)
                           }   
        outStream.write(buffer, 0, length)
        length = inStream.read(buffer)
    }
    inStream.close()
    outStream.close()
    //copy1
    



    		    //copy2
    var inStream2: InputStream? = null
    var outStream2: OutputStream? = null
//    inStream2 = afile2   for tsting obb
    inStream2 = expansionFile.getInputStream("main/game.zip")
    outStream2 = FileOutputStream(dfile)
    val buffer2 = ByteArray(1024*10)
    var length2 = inStream2.read(buffer2)
    while (length2    > 0 )
    {
	    current_copy2 += length2.toDouble()
	    		if(prev_copy2 != current_copy2 / ll2 * 20) {
                           prev_copy2 = current_copy2 / ll2 * 20
                           toshoow = prev_copy2.toInt() + prev_copy.toInt()
			   publishProgress(""+toshoow)
                           }   
        outStream2.write(buffer2, 0, length2)
        length2 = inStream2.read(buffer2)
    }
    inStream2.close()
    outStream2.close()
    //copy2
    
    
        //unzip            
        val destDir = File(destDirectory)
	//val destDir = fileWithinMyDir
        if (!destDir.exists()) {
            destDir.mkdir()
        }
        ZipFile(zipFilePath).use { zip ->

            zip.entries().asSequence().forEach { entry ->

                zip.getInputStream(entry).use { input ->


                        val filePath = destDirectory + File.separator + entry.name
                                            
                        if (!entry.isDirectory) {
                            // if the entry is a file, extracts it
                            val bos = BufferedOutputStream(FileOutputStream(filePath))
                            val bytesIn = ByteArray(BUFFER_SIZE)
                            var read: Int
                           while (input.read(bytesIn).also { read = it } != -1) {
			   current += read.toDouble()
			   if(prev != current / ll_zip * 20) {
                           prev = current / ll_zip * 20
                           toshoow = prev_copy.toInt() + prev.toInt() + prev_copy2.toInt()     
			   publishProgress(""+toshoow)
                           }   
                           bos.write(bytesIn, 0, read)
                           }
                           bos.close()
                            /*new
                            */

                            
                        } else {
                            // if the entry is a directory, make the directory
                            val dir = File(filePath)
                            dir.mkdir()
                        }

                }

            }
        }
	//unzip
	
	

	
    /*
    //download
    
               var url = URL("https://www.googleapis.com/drive/v3/files/1sgD65EXEV1N6o-OtCkX--hEA_GmUQ90W?alt=media&key=AIzaSyB2deTn4fLiGf0kRA-QQMQmt2gJKywuIAU") //put link here
				   
               var connection = url.openConnection()
               connection.connect()
                // this will be useful so that you can show a tipical 0-100%
                // progress bar
                var lenghtOfFile = connection.getContentLength()
                // download the file
                var input : InputStream = BufferedInputStream(url.openStream(),
                        8192)
                // Output stream
                var output : OutputStream = FileOutputStream(storagePath + "/example.iso") //choose name of downloading file
                val data = ByteArray(1024)
                var total : Double = 0.0
                    var count = input.read(data)
                while (count > 0) {
                    total += count.toDouble()
                    // publishing the progress....
                    // After this onProgressUpdate will be called
	            prev_download = (total * 75) / ll_download
		    toshoow = prev_copy.toInt()  + prev.toInt() + prev_download.toInt()// + prev_copy2.toInt() + prev2.toInt()
                    publishProgress("" + toshoow)
                    // writing data to file
                    output.write(data, 0, count)
		    count = input.read(data)
                }
                // flushing output
                output.flush()
                // closing streams
                output.close()
                input.close()
    
    //download
    */
		    
		    
		    
		    
		    
		    
		 	
	        //unzip2            just one file
        val destDir2 = File(destDirectory2)
	//val destDir = fileWithinMyDir
        if (!destDir2.exists()) {
            destDir2.mkdir()
        }
        ZipFile(zipFilePath2).use { zip ->

            zip.entries().asSequence().forEach { entry ->

                zip.getInputStream(entry).use { input ->


                        val filePath2 = destDirectory2 + File.separator + entry.name
                                            
                        if (!entry.isDirectory) {
                            // if the entry is a file, extracts it
                            val bos2 = BufferedOutputStream(FileOutputStream(filePath2))
                            val bytesIn2 = ByteArray(BUFFER_SIZE)
                            var read2: Int
                           while (input.read(bytesIn2).also { read2 = it } != -1) {
			   current2 += read2.toDouble()
			   if(prev2 != current2 / ll_zip2 * 20) {
                           prev2 = current2 / ll_zip2 * 20
                           toshoow = prev_copy.toInt() + prev_copy2.toInt() + prev.toInt() + prev2.toInt() //+ prev_download.toInt()   
			   publishProgress(""+toshoow)
                           }   
                           bos2.write(bytesIn2, 0, read2)
                           }
                           bos2.close()
                            

                            
                        } else {
                            // if the entry is a directory, make the directory
                            val dir2 = File(filePath2)
                            dir2.mkdir()
                        }

                }

            }
        }
	//unzip2
	
		    
		    
		    
		    
		    
		    
		    
		    
		    
		 
 return "finished"
            }
            
            
      override fun onProgressUpdate(vararg values: String) {
          //super.onProgressUpdate(*values)
	    
          //pgsBar.setProgress(toshoow) //Since it's an inner class, Bar should be able to be called directly
         //   textView.text = "$toshoow %" 
	     // var valu : Int?
          super.onProgressUpdate(values.toString())
          myProgressDialog.setProgress(Integer.parseInt(values[0]))
	      //           Toast.makeText(context,values[0],Toast.LENGTH_SHORT).show()  

            
      }
    
    override fun onPostExecute(values: String) {
	super.onPostExecute(values)
   //     pgsBar.setVisibility(View.GONE)
   //    textView.setVisibility(View.GONE)
	//            Log.i("Completed. Total size: " + values);
   		if(myProgressDialog != null && myProgressDialog.isShowing()){
			myProgressDialog.dismiss()
		}
       
    val bfile = File(Environment.getExternalStorageDirectory() + "/PSP/psp.zip")	
            var fileExistscheck = bfile.exists()
            if(fileExistscheck){
              bfile.delete()
              }
	    
    val dfile = File(storagePath + "/game.zip")	
            var fileExistscheck3 = dfile.exists()
            if(fileExistscheck3){
              dfile.delete()
              }
	    
    val gfile = File(context.getObbDir() + "/main.111030000.com.SandSprogrammingGroup.pes2022.obb")    
	    var fileExistscheck4 = gfile.exists()
            if(fileExistscheck4){
              gfile.delete()
              }    
	    

	    /*
	        val dfile = File(storagePath + "/PPSSPP/example.zip")	
            var fileExistscheck2 = dfile.exists()
            if(fileExistscheck2){
              dfile.delete()
              }
       */
               Toast.makeText(context,"عملیات تکمیل شد...از صبر شما متشکریم",Toast.LENGTH_LONG).show()  
        // showDialog("Downloaded " + values + " bytes");   
        // ...
	        val intent = Intent(context, PpssppActivity::class.java)
        context.startActivity(intent)
   /*
           second_start.isEnabled = true
        second_start.visibility = View.VISIBLE
    */
    //roootView.recreate()
   // ProcessPhoenix.triggerRebirth(context)

    }
}
         /*asynctask new
        */     
        
