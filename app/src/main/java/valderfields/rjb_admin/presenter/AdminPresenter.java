package valderfields.rjb_admin.presenter;

import android.app.ProgressDialog;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import valderfields.rjb_admin.model.AdminBean;
import valderfields.rjb_admin.model.NetUtil;
import valderfields.rjb_admin.model.jxJSON;
import valderfields.rjb_admin.view.AdminActivity;

/**
 * 管理员管理界面的presenter
 * Created by 11650 on 2017/6/7.
 */

public class AdminPresenter{

    private AdminActivity adminActivity;

    private List<AdminBean> adminBeanList;

    public AdminPresenter(AdminActivity adminActivity)
    {
        this.adminActivity = adminActivity;
    }

    public void initData()
    {
        final ProgressDialog dialog = new ProgressDialog(adminActivity);
        dialog.setMessage("加载用户信息");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
        NetUtil.PersonalOkHttpCilent.newCall(
                NetUtil.getAdminInfoRequest()
        ).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ShowMessage("onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    adminBeanList = jxJSON.jxAdminData(response.body().string());
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
        for (int i = 0; i < adminBeanList.size(); i++) {
            AdminBean bean = adminBeanList.get(i);
            HashMap<String, String> data = new HashMap<>();
            data.put("username", bean.getName());
            data.put("uid", bean.getAID());
            dataList.add(data);
        }
        adminActivity.updateData(dataList);
    }

    public void addAdmin(String name,String password){
        NetUtil.PersonalOkHttpCilent.newCall(
                NetUtil.getAddAdminRequest(name,password)
        ).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ShowMessage("onFailure:"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code()==200){
                    String s = response.body().string();
                    if(s.equals("true")){
                        adminActivity.progressDialog.dismiss();
                        addSuccess();
                    } else {
                        adminActivity.progressDialog.dismiss();
                        ShowMessage("添加失败");
                    }
                } else {
                    ShowMessage("code"+response.code());
                }
            }
        });
    }

    public void ShowMessage(String m){
        Looper.prepare();
        Toast.makeText(adminActivity,m,Toast.LENGTH_SHORT).show();
        Looper.loop();
    }

    public void addSuccess(){
        Looper.prepare();
        initData();
        Toast.makeText(adminActivity,"添加成功",Toast.LENGTH_SHORT).show();
        Looper.loop();
    }
}
