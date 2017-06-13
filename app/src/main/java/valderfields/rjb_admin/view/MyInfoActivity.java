package valderfields.rjb_admin.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import valderfields.rjb_admin.R;

public class MyInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
    }

    public void setOnClick1(View v){
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
                else if(false)
                {
                    //原密码错误
                    Toast.makeText(MyInfoActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //提交密码
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

}
