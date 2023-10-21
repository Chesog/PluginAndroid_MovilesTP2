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

            _pluginInstance.CallStatic("reciveUnityActivity",_unityActivity);
            
            CreateAlert();
            Debug.Log("Unity Java Class Created");
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
        _pluginInstance.Call("CreateAlert");
    }

    public void ShowAlert()
    {
        Debug.Log("Unity Alert Show");
        _pluginInstance.Call("ShowAlert");
    }
#endif
}
