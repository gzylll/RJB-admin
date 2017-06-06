package valderfields.rjb_admin.Model;

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
    private static Boolean _isRemember;
    private static Boolean _isAuto;
    private static Boolean _isRoot;

    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    public static void init(Context context){
        preferences = context.getSharedPreferences("userData",Context.MODE_PRIVATE);
        editor = preferences.edit();
        _AID = preferences.getString("uid","");
        _name = preferences.getString("username","");
        _password = preferences.getString("password","");
        _isRemember = preferences.getBoolean("isR",false);
        _isAuto = preferences.getBoolean("isA",false);
        _isRoot = preferences.getBoolean("isRoot",false);
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

    public static Boolean getRemember() {
        return _isRemember;
    }

    public static void setRemember(Boolean remember) {
        _isRemember = remember;
        editor.putBoolean("isR",remember);
        editor.commit();
    }

    public static Boolean getAuto() {
        return _isAuto;
    }

    public static void setAuto(Boolean auto) {
        _isAuto = auto;
        editor.putBoolean("isA",auto);
        editor.commit();
    }

    public static void clear(){
        editor.clear();
        editor.commit();
    }
}
