package valderfields.rjb_admin.Model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
}
