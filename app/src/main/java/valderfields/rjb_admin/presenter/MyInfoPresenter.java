package valderfields.rjb_admin.presenter;

import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.Observable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import valderfields.rjb_admin.model.NetUtil;
import valderfields.rjb_admin.model.User;
import valderfields.rjb_admin.view.MyInfoActivity;

/**
 * Created by 11650 on 2017/6/13.
 */

public class MyInfoPresenter extends Observable {

    private MyInfoActivity activity;

    public MyInfoPresenter(MyInfoActivity activity)
    {
        this.activity = activity;
    }

    public void changeName(final String name)
    {
        NetUtil.PersonalOkHttpCilent.newCall(
                NetUtil.getUpdateMyInfoRequest(name,User.getUID(),User.getPassword())
        ).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                setChanged();
                notifyObservers("Request Failure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code()==200){
                    String s = response.body().string();
                    if(s.equals("success")) {
                        User.setUsername(name);
                        setChanged();
                        notifyObservers("success");
                    }
                    else{
                        setChanged();
                        notifyObservers("修改失败");
                    }
                }
                else{
                    setChanged();
                    notifyObservers("网络出错");
                }
            }
        });
    }

    public void changePassword(final String password)
    {
        NetUtil.PersonalOkHttpCilent.newCall(
                NetUtil.getUpdateMyInfoRequest(User.getUsername(),User.getUID(), password)
        ).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                setChanged();
                notifyObservers("Request Failure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code()==200){
                    String s = response.body().string();
                    if(s.equals("success")) {
                        User.setPassword(password);
                        setChanged();
                        notifyObservers("success");
                    }
                    else{
                        setChanged();
                        notifyObservers("修改失败");
                    }
                }
                else{
                    setChanged();
                    notifyObservers("网络出错");
                }
            }
        });
    }
}
