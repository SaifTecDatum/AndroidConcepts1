package com.myapps.androidconcepts.Database;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.myapps.androidconcepts.Helpers.Constants;

/*This class took from Hive-AV project, just for an Idea purpose.
* Its all about SharedPreference to store&get the values & here we have custom Static Methods to handle
*  at multiple places allOver App.*/

public class SharedPrefManager {
    public static final String MY_PREFS_NAME = "HIVE_ATLAS";
    public static final String IP = "IP";
    public static final String TCP_IP = "TCP_IP";
    public static final String TCP_PORT = "TCP_PORT";
    public static final String TCP_PASSWORD = "TCP_PASSWORD";
    public static final String URL_IP = "URL_IP";
    public static final String APOLLO_TCP_IP = "APOLLO_TCP_IP";
    public static final String APOLLO_TCP_PORT = "APOLLO_TCP_PORT";


    public static String getIp(Activity activity) {
        if (activity == null) {
            return "";
        }
        SharedPreferences sharedPreferences = activity.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(IP, "");
    }

    public static void storeIp(String ip, Activity activity) {
        if (activity == null) {
            return;
        }
        SharedPreferences.Editor editor = activity.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(IP, ip);
        editor.commit();
    }

    public static void storeTCpIp(String tcpIp, Activity activity) {
        if (activity == null) {
            return;
        }
        SharedPreferences.Editor editor = activity.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(TCP_IP, tcpIp);
        editor.commit();
    }

    public static void storeTCpPort(String port, Activity activity) {
        if (activity == null) {
            return;
        }
        SharedPreferences.Editor editor = activity.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(TCP_PORT, port);
        editor.commit();
    }

    public static void storeApolloTCpIp(String apolloTcpIp, Activity activity) {
        if (activity == null) {
            return;
        }
        SharedPreferences.Editor editor = activity.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(APOLLO_TCP_IP, apolloTcpIp);
        editor.commit();
    }

    public static void storeApolloTCpPort(String apolloPort, Activity activity) {
        if (activity == null) {
            return;
        }
        SharedPreferences.Editor editor = activity.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(APOLLO_TCP_PORT, apolloPort);
        editor.commit();
    }


    public static void storeTCpPassword(String password, Activity activity) {
        if (activity == null) {
            return;
        }
        SharedPreferences.Editor editor = activity.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(TCP_PASSWORD, password);
        editor.commit();
    }

    public static String getTCPPassword(Activity activity) {
        if (activity == null) {
            return "admin";
        }
        SharedPreferences sharedPreferences = activity.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(TCP_PASSWORD, "admin");
    }

    public static String getTCPIp(Activity activity) {
        if (activity == null) {
            return "192.168.1.46";
        }
        SharedPreferences sharedPreferences = activity.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(TCP_IP, "192.168.1.46");
    }

    public static String getTCPPort(Activity activity) {
        if (activity == null) {
            return "80";
        }
        SharedPreferences sharedPreferences = activity.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(TCP_PORT, "80");
    }

    public static String getApolloTCPIp(Activity activity) {
        if (activity == null) {
            return "192.168.0.130";
        }
        SharedPreferences sharedPreferences = activity.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(APOLLO_TCP_IP, "192.168.0.130");
    }


    public static String getApolloTCPPort(Activity activity) {
        if (activity == null) {
            return "23";
        }
        SharedPreferences sharedPreferences = activity.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(APOLLO_TCP_PORT, "23");
    }

    public static Boolean getSettings(String settingKey, Activity activity) {
        if (activity == null) {
            return false;
        }
        SharedPreferences sharedPreferences = activity.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(settingKey, false);
    }

    public static void storeSettings(String settingKey, boolean isChecked, Activity activity) {
        if (activity == null) {
            return;
        }
        SharedPreferences.Editor editor = activity.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean(settingKey, isChecked);
        editor.commit();
    }

    public static void storeVersions(String versionKey, String versionMNum, Activity activity) {
        if (activity == null) {
            return;
        }
        SharedPreferences.Editor editor = activity.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(versionKey, "" + versionMNum);
        editor.commit();
    }

    public static String getVersion(String versionKey, Activity activity) {
        if (activity == null) {
            return "";
        }
        SharedPreferences sharedPreferences = activity.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(versionKey, "");
    }

    public static String getControlPanelActionsLoginToken(String controlPanelTokenKey, Activity activity) {
        if (activity == null) {
            return "";
        }
        SharedPreferences sharedPreferences = activity.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(controlPanelTokenKey, "");
    }

    public static void storeControlPanelActionsLoginToken(String controlPanelTokenKey, String controlPanelTokenValue, Activity activity) {
        if (activity == null) {
            return;
        }
        SharedPreferences.Editor editor = activity.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(controlPanelTokenKey, "" + controlPanelTokenValue);
        editor.commit();
    }

    public static void storeUrlIp(String ip, Activity activity) {
        if (activity == null) {
            return;
        }
        SharedPreferences.Editor editor = activity.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(URL_IP, "" + ip);
        editor.commit();
    }

    public static String getUrlIp(String key, Activity activity) {
        if (activity == null) {
            return "";
        }
        SharedPreferences sharedPreferences = activity.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "" + Constants.URL_IP);
    }

}
