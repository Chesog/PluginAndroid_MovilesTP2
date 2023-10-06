using System;
using TMPro;
using Unity.VisualScripting;
using UnityEngine;

public class TestLoggerPlugin : MonoBehaviour
{
    private const string packName = "com.otranto_loger_plugin";
    private const string className = packName + ".Otranto_Logger";
#if UNITY_ANDROID
    

    private AndroidJavaClass _pluginClass;
    private AndroidJavaObject _pluginInstance;
    public TextMeshProUGUI _label;

    private void Start()
    {
        if (Application.platform == RuntimePlatform.Android)
        {
            _pluginClass = new AndroidJavaClass(className);
            _pluginInstance = _pluginClass.CallStatic<AndroidJavaObject>("GetInstance");
        }
    }

    public void RunPlugin()
    {
        Debug.Log("Runing Plugin");
        if (Application.platform == RuntimePlatform.Android){_label.text = _pluginInstance.Call<string>("GetLOGTAG");}
     
    }
#endif
}
