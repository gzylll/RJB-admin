package valderfields.rjb_admin.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import valderfields.rjb_admin.R;

public class MyInfoActivity extends AppCompatActivity {


    private EditText name;
    private EditText oldpwd;
    private EditText newpwd1;
    private EditText newpwd2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
    }

    public void setOnClick1(View v){
        /*//创建对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //设置标题
        builder.setTitle("修改用户名");

        v = LayoutInflater.from(MyInfoActivity.this).inflate(R.layout.username,null);

        name = (EditText) v.findViewById(R.id.edit_text);

        builder.setView(v);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

        builder.setCancelable(false);*/
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_editun,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);

        final AlertDialog dialog = builder.create();
        dialog.show();
        TextView confirm = (TextView)view.findViewById(R.id.editUsername_yes);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        TextView cancel = (TextView)view.findViewById(R.id.editUsername_no);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    public void setOnClick2(View v){
        //创建对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //设置标题
        builder.setTitle("修改密码");

        v = LayoutInflater.from(MyInfoActivity.this).inflate(R.layout.pwd,null);

        oldpwd = (EditText) v.findViewById(R.id.oldpwd);
        newpwd1 = (EditText) v.findViewById(R.id.newpwd1);
        newpwd2 = (EditText) v.findViewById(R.id.newpwd2);


        builder.setView(v);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

        builder.setCancelable(false);

    }

}
