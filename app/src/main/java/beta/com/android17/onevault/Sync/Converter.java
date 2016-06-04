package beta.com.android17.onevault.Sync;

import android.content.Context;
import android.os.Environment;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;

import beta.com.android17.onevault.Object.Account;

/**
 * Created by RRosall on 12/2/2015.
 */
public class Converter {

    String filename;
    String dir_name;
    String temp ;
    Calendar c;
    Context context;

    OutputStream outputStream;
    FileOutputStream outPutStream;
    OutputStreamWriter outputStreamWriter;

    public Converter(Context context){
        this.context = context;
        c = Calendar.getInstance();
        filename = "";
        temp = "";
        dir_name = "/JSON_FILES";
    }



    public void convertToJSON_External(ArrayList<Account> list){

        filename = "" + c.getTime();
//        Gson gson = new Gson();
//
//        for (Account a : list) {
//            temp = temp + gson.toJson(a) + "\n";
//        }

        File dir = new File(context.getExternalFilesDir(null).getAbsolutePath() + dir_name);
        if(!dir.exists()){
            dir.mkdirs();
        }

        File file = new File(dir.getPath() , filename);
        try{
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(f);
            pw.print(temp);
            pw.flush();
            pw.close();
            f.close();
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public boolean isExternalStorageWritable(){
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}
