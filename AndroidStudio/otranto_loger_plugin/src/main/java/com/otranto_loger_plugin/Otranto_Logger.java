package com.otranto_loger_plugin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;

import java.util.ArrayList;
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
}
