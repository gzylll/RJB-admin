package valderfields.rjb_admin.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import valderfields.rjb_admin.R;
import valderfields.rjb_admin.model.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private View userMessage;
    private View uploadIMG;
    private View setting;
    private View lookupUser;
    private View addAdmin;
    private TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    public void exit(View v){
        AlertDialog.Builder isExit = new AlertDialog.Builder(this);
        isExit.setTitle("提示");
        isExit.setMessage("确定要退出登录吗？");
        isExit.setPositiveButton("确定",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,LoginActivity.class);
                MainActivity.this.startActivity(intent);
                finish();
            }
        });
        isExit.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = isExit.create();
        isExit.show();
    }

    private void initView(){
        userMessage = findViewById(R.id.userMessage);
        userMessage.setOnClickListener(this);
        username = (TextView)findViewById(R.id.username);
        username.setText(User.getUsername());
        uploadIMG = findViewById(R.id.uploadIMG);
        uploadIMG.setOnClickListener(this);
        setting = findViewById(R.id.Setting);
        setting.setOnClickListener(this);
        lookupUser = findViewById(R.id.lookupUser);
        lookupUser.setOnClickListener(this);
        addAdmin = findViewById(R.id.administrator);
        addAdmin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.uploadIMG:
                intent = new Intent(this,UploadIMGActivity.class);
                startActivity(intent);
                break;
            case R.id.lookupUser:
                intent = new Intent(this,LookupUserActivity.class);
                startActivity(intent);
                break;
            case R.id.Setting:
                intent = new Intent(this,SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.userMessage:
                intent = new Intent(this,MyInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.administrator:
                intent = new Intent(this,AdminActivity.class);
                startActivity(intent);
                break;
        }
    }
}
