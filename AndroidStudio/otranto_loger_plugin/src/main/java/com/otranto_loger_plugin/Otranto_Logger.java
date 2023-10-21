package com.otranto_loger_plugin;
import java.util.ArrayList;
import java.util.List;

public class Otranto_Logger
{
    static final String LOGTAG = "OTRLGG";
    List<String> warnings = new ArrayList<>();
    List <String> errors = new ArrayList<>();
    List <String> debug = new ArrayList<>();
    static Otranto_Logger _instance = null;

    public static Otranto_Logger GetInstance()
    {
        if (_instance == null)
            _instance = new Otranto_Logger();
        return _instance;
    }

    public void SendLog(String log)
    {
        this.debug.add(log);
    }

    public void SendWarning(String warning)
    {
        this.warnings.add(warning);
    }

    public void SendError(String error)
    {
        this.errors.add(error);
    }

    public String GetLOGTAG()
    {
        return LOGTAG;
    }
}
