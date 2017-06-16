package valderfields.rjb_admin.presenter;

import android.app.ProgressDialog;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import valderfields.rjb_admin.model.NetUtil;
import valderfields.rjb_admin.model.UserBean;
import valderfields.rjb_admin.model.jxJSON;
import valderfields.rjb_admin.view.LookupUserActivity;

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
                NetUtil.getUserRequest()
        ).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ShowMessage("onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    userBeanList = jxJSON.jxUser(response.body().string());
                    dialog.dismiss();
                    updataListData();
                } else {
                    ShowMessage("获取失败");
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

    public void ShowMessage(String m){
        Looper.prepare();
        Toast.makeText(activity,m,Toast.LENGTH_SHORT).show();
        Looper.loop();
    }
}
