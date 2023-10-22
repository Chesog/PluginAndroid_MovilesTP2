using System;
using TMPro;
using UnityEngine;

public class TestLoggerPlugin : MonoBehaviour
{
    private const string packName = "com.otranto_loger_plugin";
    private const string className = packName + ".Otranto_Logger";
#if UNITY_ANDROID
    

    private AndroidJavaClass _pluginClass;
    private AndroidJavaClass _pluginUnityClass;
    private AndroidJavaObject _pluginInstance;
    private AndroidJavaObject _unityActivity;
    public TextMeshProUGUI _label;

    private void Start()
    {
        if (Application.platform == RuntimePlatform.Android)
        {
            _pluginClass = new AndroidJavaClass(className);
            _pluginUnityClass = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
            _unityActivity = _pluginUnityClass.GetStatic<AndroidJavaObject>("currentActivity");
            _pluginInstance = new AndroidJavaObject(className);
            if (_pluginInstance == null)
            {
                Debug.Log("Plugin Instance is NULL");
                return;
            }
            Application.logMessageReceived += Application_logMessageReceived;
            _pluginInstance.CallStatic("reciveUnityActivity",_unityActivity);
            
            CreateAlert();
            Debug.Log("Unity Java Class Created");
        }
    }

    private void Application_logMessageReceived(string condition, string stacktrace, LogType type)
    {
        switch (type)
        {
            case LogType.Error:
                _pluginInstance.Call("SendError",condition);
                break;
            case LogType.Assert:
                Debug.Log("AssertLog");
                break;
            case LogType.Warning:
                _pluginInstance.Call("SendWarning",condition);
                break;
            case LogType.Log:
                _pluginInstance.Call("SendLog",condition);
                break;
            case LogType.Exception:
                Debug.Log("ExceptionLog");
                break;
            default:
                throw new ArgumentOutOfRangeException(nameof(type), type, null);
        }
    }

    public void RunPlugin()
    {
        Debug.Log("Runing Plugin");
        if (Application.platform == RuntimePlatform.Android){_label.text = _pluginInstance.Call<string>("GetLOGTAG");}
     
    }

    public void CreateAlert()
    {
        Debug.Log("Unity Alert Created");
        _pluginInstance.Call("CreateAlert",new AndroidPluginCallback{});
    }

    public void ShowAlert()
    {
        Debug.Log("Unity Alert Show");
        _pluginInstance.Call("ShowAlert");
    }
#endif
    public void PrintErrorMessage() {Debug.LogError("Error Test"); }
    public void PrintWarningMessage() {Debug.LogError("Warnig Test"); }
    public void PrintDebugMessage() {Debug.LogError("Debug Test"); }
}

public class AndroidPluginCallback : AndroidJavaProxy
{
    public AndroidPluginCallback() : base("com.otranto_loger_plugin.AlertCallback") { }

    public void onPositive(String message)
    {
     Debug.Log("On Unity Positive - " + message);   
    }

    public void onNegative(String message)
    {
        Debug.Log("On Unity Negative - " + message); 
    }
}
