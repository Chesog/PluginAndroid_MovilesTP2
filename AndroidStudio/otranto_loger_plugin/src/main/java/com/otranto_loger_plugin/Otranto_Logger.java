package com.otranto_loger_plugin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Otranto_Logger
{
    static final String LOGTAG = "OTRLGG";
    static Otranto_Logger _instance = null;
    private static Activity unityActivity;
    AlertDialog.Builder builder;
    List<String> warnings = new ArrayList<>();
    List <String> errors = new ArrayList<>();
    List <String> debug = new ArrayList<>();

    public static void reciveUnityActivity(Activity uActivity)
    {
        unityActivity = uActivity;
    }

    public void CreateAlert(AlertCallback alertCallback)
    {
        Log.v(LOGTAG,"Android Create Alert");
        builder = new AlertDialog.Builder(unityActivity);
        builder.setMessage("Example Text");
        builder.setCancelable(true);
        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.v(LOGTAG,"Clicked From Pluggin - YES");
                        alertCallback.onPositive("Clicked Yes");
                        dialogInterface.cancel();
                    }
                }
        );
        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.v(LOGTAG,"Clicked From Pluggin - NO");
                        alertCallback.onNegative("Clicked NO");
                        dialogInterface.cancel();
                    }
                }
        );
    }

    public void ShowAlert()
    {
        Log.v(LOGTAG,"Android Show Alert");
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void SendLog(String log)
    {
        this.debug.add(log);
        Log.v(LOGTAG,"Android Added debug Log - " + log);
    }

    public void SendWarning(String warning)
    {
        this.warnings.add(warning);
        Log.v(LOGTAG,"Android Added warning Log - " + warning);
    }

    public void SendError(String error)
    {
        this.errors.add(error);
        Log.v(LOGTAG,"Android Added error Log - " + error);
    }

    public String GetLOGTAG()
    {
        return LOGTAG;
    }

    private void writeToFile(String fileName,String data)
    {
        Context context = unityActivity.getApplicationContext();
        File file = new File(context.getExternalFilesDir(null),fileName);
        Log.v("FileWriter", context.getExternalFilesDir(null).toString());
        try
        {
            FileWriter fileWriter = new FileWriter(file, true);
            for (int i = 0; i < debug.size(); i++)
            {
                fileWriter.append(debug.get(i)).append("\n");
            }
            for (int i = 0; i < warnings.size(); i++)
            {
                fileWriter.append(warnings.get(i)).append("\n");
            }
            for (int i = 0; i < errors.size(); i++)
            {
                fileWriter.append(errors.get(i)).append("\n");
            }
            fileWriter.close();
            Toast.makeText(unityActivity.getApplicationContext(), "write to file - "+ data + " - " + fileName,Toast.LENGTH_SHORT).show();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private String readFromFile(String fileName)
    {
        Context context = unityActivity.getApplicationContext();
        File readFrom = new File(context.getExternalFilesDir(null),fileName);
        Log.v("FileReader", context.getExternalFilesDir(null).toString());
        byte[] content = new byte[(int)readFrom.length()];
        try
        {
            FileInputStream inputStream =  new FileInputStream(readFrom);
            inputStream.read(content);
            return new String(content);
        }
        catch (FileNotFoundException e)
        {
            Log.e("login activity", "File not found: " + e.toString());
            return e.toString();
        } catch (IOException e)
        {
            Log.e("login activity", "Can not read file: " + e.toString());
            return e.toString();
        }
    }


}
