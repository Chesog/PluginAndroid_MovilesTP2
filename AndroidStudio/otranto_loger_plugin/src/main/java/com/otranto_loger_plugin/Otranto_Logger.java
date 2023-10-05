package com.otranto_loger_plugin;

public class Otranto_Logger
{
    static final String LOGTAG = "OTRLGG";
    static Otranto_Logger _instance = null;

    public static Otranto_Logger GetInstance()
    {
        if (_instance == null)
            _instance = new Otranto_Logger();
        return _instance;
    }

    public String GetLOGTAG()
    {
        return LOGTAG;
    }
}
