package valderfields.rjb_admin.presenter;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.Observable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import valderfields.rjb_admin.model.NetUtil;
import valderfields.rjb_admin.model.jxJSON;
import valderfields.rjb_admin.view.LoginActivity;

/**
 * Created by 11650 on 2017/5/13.
 */

public class LoginPresenter extends Observable{

    private LoginActivity activity;

    public LoginPresenter(LoginActivity activity) {
        this.activity = activity;
    }

    public void Login(final String un, final String pw) {
        new Thread() {
            public void run() {
                Request request = NetUtil.getLoginRequest(un, pw);
                NetUtil.PersonalOkHttpCilent.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("Login", "Failure");
                        Log.i("Login", e.getMessage());
                        ShowMessage("Login Request onFailure！");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if(response.code()!=200){
                            ShowMessage("网络错误！");
                        }else{
                            String s = response.body().string();
                            Log.e("return",s);
                            if(jxJSON.jxLoginData(s)){
                                setChanged();
                                notifyObservers("Login Success");
                            }else{
                                ShowMessage(s);
                            }
                        }
                    }
                });
            }
        }.start();
    }


    public void ShowMessage(String m){
        Looper.prepare();
        Toast.makeText(activity,m,Toast.LENGTH_SHORT).show();
        Looper.loop();
    }

}
