package valderfields.rjb_admin.view;

import android.app.ProgressDialog;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Observable;
import java.util.Observer;

import valderfields.rjb_admin.R;
import valderfields.rjb_admin.model.NetUtil;
import valderfields.rjb_admin.model.User;
import valderfields.rjb_admin.presenter.MyInfoPresenter;

public class MyInfoActivity extends AppCompatActivity implements Observer{

    TextView aid;
    TextView username;
    ProgressDialog progressDialog;
    MyInfoPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        initView();
    }

    public void initView(){
        aid = (TextView)findViewById(R.id.AID);
        aid.setText(User.getUID());
        username = (TextView)findViewById(R.id.UN);
        username.setText(User.getUsername());
        progressDialog = new ProgressDialog(this);
        presenter = new MyInfoPresenter(this);
        presenter.addObserver(this);
        getSupportActionBar().setTitle("我的信息");
    }
    
    public void changeName(View v){
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_editun,null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();

        final EditText name = (EditText)view.findViewById(R.id.editUsername);
        TextView confirm = (TextView)view.findViewById(R.id.editUsername_yes);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                progressDialog.setMessage("修改用户名");
                progressDialog.setCancelable(false);
                progressDialog.show();
                Log.e("11",name.getText().toString());
                presenter.changeName(name.getText().toString());
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

    public void changePassword(View v){
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_editpw,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();

        final EditText oldpwd = (EditText) view.findViewById(R.id.pwd);
        final EditText newpwd1 = (EditText) view.findViewById(R.id.newpwd1);
        final EditText newpwd2 = (EditText) view.findViewById(R.id.newpwd2);
        TextView confirm = (TextView)view.findViewById(R.id.editPassword_yes);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(oldpwd.getText().toString().equals("")||newpwd1.getText().toString().equals("")
                        ||newpwd2.getText().toString().equals(""))
                {
                    Toast.makeText(MyInfoActivity.this,"输入不能为空",Toast.LENGTH_SHORT).show();
                }
                else if(!newpwd1.getText().toString().equals(newpwd2.getText().toString()))
                {
                    Toast.makeText(MyInfoActivity.this,"新密码不一致",Toast.LENGTH_SHORT).show();
                }
                else if(!User.getPassword().equals(oldpwd.getText().toString()))
                {
                    //原密码错误
                    Toast.makeText(MyInfoActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //提交密码
                    dialog.dismiss();
                    progressDialog.setMessage("修改用户名");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    presenter.changePassword(newpwd1.getText().toString());
                }
            }
        });
        TextView cancel = (TextView)view.findViewById(R.id.editPassword_no);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof MyInfoPresenter)
        {
            progressDialog.dismiss();
            Looper.prepare();
            if(arg.equals("success")){
                Toast.makeText(MyInfoActivity.this,"操作成功",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(MyInfoActivity.this,arg.toString(),Toast.LENGTH_SHORT).show();
            }
            Looper.loop();
        }
    }
}
