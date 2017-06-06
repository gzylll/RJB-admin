package valderfields.rjb_admin.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import valderfields.rjb_admin.R;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{

    private SharedPreferences sp;
    private View imgCountView;
    private View changePwnView;
    private TextView imgCount;
    private TextView changePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        intiView();
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setTitle("设置");
        }
    }

    private void intiView()
    {
        sp = getSharedPreferences("setting",MODE_PRIVATE);
        imgCountView = findViewById(R.id.uploadIMGCount);
        imgCountView.setOnClickListener(this);
        changePwnView = findViewById(R.id.changePassword);
        changePwnView.setOnClickListener(this);
        imgCount = (TextView)findViewById(R.id.imgCount);
        imgCount.setText(String.valueOf(sp.getInt("imgCount",9)));
        changePassword = (TextView)findViewById(R.id.cPwd);
        changePassword.setText(sp.getBoolean("cPWN",false)?"是":"否");
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.uploadIMGCount:
                showImgCountDialog();
                break;
            case R.id.changePassword:
                showchangePasswordDialog();
                break;
        }
    }

    public void showImgCountDialog(){
        final EditText et = new EditText(this);
        et.setInputType(InputType.TYPE_CLASS_NUMBER);
        new AlertDialog.Builder(this)
                .setTitle("设置图片数量(1-30)")
                .setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int i = Integer.parseInt(et.getText().toString().trim());
                        if(i>0&&i<=30)
                        {
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putInt("imgCount",i);
                            imgCount.setText(String.valueOf(i));
                            editor.apply();
                        }
                        else
                        {
                            Toast.makeText(SettingActivity.this,"输入数字超出范围",Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("取消",null)
                .show();
    }

    public void showchangePasswordDialog(){
        final View view = getLayoutInflater().inflate(R.layout.dialog_cpwn,null);
        new AlertDialog.Builder(this)
                .setTitle("是否可以修改用户密码")
                .setNegativeButton("取消",null)
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RadioGroup rg = (RadioGroup)view.findViewById(R.id.radioGroup);
                        SharedPreferences.Editor editor = sp.edit();
                        switch (rg.getCheckedRadioButtonId())
                        {
                            case R.id.radioYes:
                                editor.putBoolean("cPWN",true);
                                break;
                            case R.id.radioNo:
                                editor.putBoolean("cPWN",false);
                                break;
                        }
                        editor.apply();
                        changePassword.setText(sp.getBoolean("cPWN",false)?"是":"否");
                    }
                })
                .show();

    }
}
