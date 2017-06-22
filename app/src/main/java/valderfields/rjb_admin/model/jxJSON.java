package valderfields.rjb_admin.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 解析JSOn
 * Created by 11650 on 2017/5/26.
 */

public class jxJSON {

    public static List<UserBean> jxUser(String data)
    {
        List<UserBean> list = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(data);
            JSONArray userList = object.getJSONArray("users");
            for(int i=0;i<userList.length();i++)
            {
                JSONObject jsonObject = userList.getJSONObject(i);
                UserBean bean = new UserBean();
                if(jsonObject.has("email"))
                    bean.setEmail(jsonObject.getString("email"));
                else
                    bean.setEmail("");
                bean.setUsername(jsonObject.getString("username"));
                bean.setPassword(jsonObject.getString("password"));
                if(jsonObject.has("phone"))
                    bean.setPhone(jsonObject.getString("phone"));
                else
                    bean.setPhone("");
                bean.setUID(jsonObject.getString("uid"));
                if(jsonObject.has("hobbies"))
                    bean.setHobbies(jsonObject.getString("hobbies"));
                else
                    bean.setHobbies("");
                Log.i("Json",bean.toString());
                list.add(bean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static boolean jxLoginData(String data){
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONObject object = jsonObject.getJSONObject("admin");
            User.setUID(object.getString("aid"));
            User.setIsRoot(Boolean.parseBoolean(object.getString("isroot")));
            User.setUsername(object.getString("name"));
        } catch (JSONException e) {
            return false;
        }
        return true;
    }

    public static List<AdminBean> jxAdminData(String data){
        List<AdminBean> adminBeen = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject object = jsonArray.getJSONObject(i);
                AdminBean bean = new AdminBean();
                bean.setAID(object.getString("aid"));
                bean.setName(object.getString("name"));
                bean.setRoot(object.getBoolean("isroot"));
                adminBeen.add(bean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return adminBeen;
    }

    public static List<HashMap<String,String>> jxTagsData(String data){
        List<HashMap<String,String>> dataList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray("images");
            for(int i=0;i<jsonArray.length();i++){
                HashMap<String,String> map = new HashMap<>();
                JSONObject object = jsonArray.getJSONObject(i);
                map.put("picture_name",object.getString("picture_name"));
                if(object.has("finish_time"))
                    map.put("finish_time",object.getString("finish_time"));
                else
                    map.put("finish_time","");
                dataList.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataList;
    }
}
