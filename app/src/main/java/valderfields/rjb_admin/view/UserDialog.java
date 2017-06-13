package valderfields.rjb_admin.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        TextView uid = (TextView)view.findViewById(R.id.userDialog_uid);
        TextView username = (TextView)view.findViewById(R.id.userDialog_uname);
        TextView phone = (TextView)view.findViewById(R.id.userDialog_phone);
        EditText email = (EditText)view.findViewById(R.id.userDialog_email);
        EditText password = (EditText)view.findViewById(R.id.userDialog_password);
        EditText hobbies = (EditText)view.findViewById(R.id.userDialog_hobbies);
        uid.setText(bean.getUID());
        username.setText(bean.getUsername());
        phone.setText(bean.getPhone());
        email.setText(bean.getEmail());
        password.setText(bean.getPassword());
        if(!canChangePWN)
        {
            password.setEnabled(false);
            View PWNView = view.findViewById(R.id.password);
            PWNView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"没有权限修改用户密码，请前往设置修改",Toast.LENGTH_SHORT).show();
                }
            });
        }
        hobbies.setText(bean.getHobbies());
    }

    public void Show()
    {
        builder.show();
    }

    private DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    };
}
