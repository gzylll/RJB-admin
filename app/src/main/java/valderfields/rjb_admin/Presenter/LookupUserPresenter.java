package valderfields.rjb_admin.Presenter;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import valderfields.rjb_admin.Model.NetUtil;
import valderfields.rjb_admin.Model.UserBean;
import valderfields.rjb_admin.Model.jxJSON;
import valderfields.rjb_admin.View.LookupUserActivity;

/**
 * 查看用户信息
 * Created by 11650 on 2017/5/26.
 */

public class LookupUserPresenter {

    private LookupUserActivity activity;
    //用户数据
    private List<UserBean> userBeanList = new ArrayList<>();

    public LookupUserPresenter(LookupUserActivity activity) {
        this.activity = activity;
    }

    public void initData() {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setMessage("加载用户信息");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
        NetUtil.PersonalOkHttpCilent.newCall(
                NetUtil.getUserRequest("admin", "admin")
        ).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(activity, "onFailure", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    userBeanList = jxJSON.jxUser(response.body().string());
                    dialog.dismiss();
                    updataListData();
                } else {
                    Toast.makeText(activity, "获取失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void updataListData() {
        List<HashMap<String, String>> dataList = new ArrayList<>();
        for (int i = 0; i < userBeanList.size(); i++) {
            UserBean bean = userBeanList.get(i);
            HashMap<String, String> data = new HashMap<>();
            data.put("username", bean.getUsername());
            data.put("uid", bean.getUID());
            dataList.add(data);
        }
        Log.i("1", dataList.toString());
        activity.updateData(dataList);
    }

    public UserBean getUserBeanDataAt(int position)
    {
        return userBeanList.get(position);
    }
}
