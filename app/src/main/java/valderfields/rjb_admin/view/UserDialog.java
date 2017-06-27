package valderfields.rjb_admin.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;
import valderfields.rjb_admin.model.EncodeUtil;
import valderfields.rjb_admin.model.NetUtil;
import valderfields.rjb_admin.model.User;
import valderfields.rjb_admin.model.UserBean;
import valderfields.rjb_admin.R;

/**
 * 用户信息弹出框
 * Created by 11650 on 2017/5/31.
 */

public class UserDialog{

    private Context context;
    private AlertDialog.Builder builder;
    private View view;
    private Boolean canChangePWN;
    private UserBean bean;
    TextView tvUid,tvEmail,tvHobbies,tvPassword;

    public UserDialog(@NonNull Context context,boolean canChangePWN) {
        this.context = context;
        this.canChangePWN = canChangePWN;
        builder = new AlertDialog.Builder(context);
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.dialog_user,null);
        builder.setView(view);
        builder.setTitle("查看用户信息");
        builder.setPositiveButton("确定",listener);
        builder.setNegativeButton("取消",null);
    }

    public void initData(UserBean bean)
    {
        this.bean = bean;
        tvUid = (TextView)view.findViewById(R.id.userDialog_uid);
        TextView username = (TextView)view.findViewById(R.id.userDialog_uname);
        TextView phone = (TextView)view.findViewById(R.id.userDialog_phone);
        tvEmail = (EditText)view.findViewById(R.id.userDialog_email);
        tvPassword = (EditText)view.findViewById(R.id.userDialog_password);
        tvHobbies = (EditText)view.findViewById(R.id.userDialog_hobbies);
        tvUid.setText(bean.getUID());
        username.setText(bean.getUsername());
        phone.setText(bean.getPhone());
        tvEmail.setText(bean.getEmail());
        tvPassword.setText(bean.getPassword());
        if(!canChangePWN)
        {
            tvPassword.setEnabled(false);
            View PWNView = view.findViewById(R.id.password);
            PWNView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"没有权限修改用户密码，请前往设置修改",Toast.LENGTH_SHORT).show();
                }
            });
        }
        tvHobbies.setText(bean.getHobbies());
    }

    public void Show()
    {
        builder.show();
    }

    private DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(final DialogInterface dialog, int which) {
            String uid = tvUid.getText().toString();
            String email = tvEmail.getText().toString();
            String hobby = tvHobbies.getText().toString();
            String password;
            if(canChangePWN)
                password = tvPassword.getText().toString();
            else
                password = bean.getPassword();
            if(email.equals(bean.getEmail())&&hobby.equals(bean.getHobbies())&&password.equals(bean.getPassword())){
                dialog.dismiss();
            }else{
                if(!password.equals(bean.getPassword()))
                    password= EncodeUtil.shaEncode(password);
                dialog.dismiss();
                NetUtil.PersonalOkHttpCilent.newCall(
                        NetUtil.getRequestWithSession(
                                NetUtil.getUpdateUserInfoUrl,
                                new FormBody.Builder()
                                        .add("uid",uid)
                                        .add("email",email)
                                        .add("password", password)
                                        .add("hobbies",hobby)
                                        .build()
                        )
                ).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        showMessage("修改失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if(response.code()==200){
                            String s = response.body().string();
                            Log.e("1",s);
                            if(s.equals("success")){
                                showMessage("修改成功");
                            }else{
                                showMessage("修改失败");
                            }
                        }else{
                            showMessage("修改失败");
                        }
                    }
                });
            }
        }
    };

    public void showMessage(String s){
        Looper.prepare();
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
        Looper.loop();
    }
}
