package valderfields.rjb_admin.model;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 当前登陆的用户
 * Created by 11650 on 2017/4/15.
 */

public class User {

    private static String _AID;
    private static String _name;
    private static String _phone;
    private static String _password;
    private static Boolean _isRoot;
    private static String _session;

    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    public static void init(Context context){
        preferences = context.getSharedPreferences("userData",Context.MODE_PRIVATE);
        editor = preferences.edit();
        _AID = preferences.getString("uid","");
        _name = preferences.getString("username","");
        _password = preferences.getString("password","");
        _isRoot = preferences.getBoolean("isRoot",false);
        _session = preferences.getString("session","");
    }

    public static String getSession() {
        if(_session!=null)
            return _session;
        else
            return preferences.getString("session","");
    }

    public static void setSession(String session){
        _session = session;
        editor.putString("session",session);
        editor.commit();
    }

    public static void setIsRoot(Boolean _isRoot) {
        User._isRoot = _isRoot;
    }

    public static Boolean getIsRoot() {
        return _isRoot;
    }

    public static String getUID() {
        return _AID;
    }

    public static void setUID(String UID) {
        _AID = UID;
        editor.putString("uid",UID);
        editor.commit();
    }

    public static String getUsername() {
        return _name;
    }

    public static void setUsername(String username) {
        _name = username;
        editor.putString("username",username);
        editor.commit();
    }

    public static String getPhone() {
        return _phone;
    }

    public static void setPhone(String phone) {
        _phone = phone;
    }

    public static String getPassword() {
        return _password;
    }

    public static void setPassword(String password) {
        _password = password;
        editor.putString("password",password);
        editor.commit();
    }

    public static void clear(){
        editor.clear();
        editor.commit();
    }
}
