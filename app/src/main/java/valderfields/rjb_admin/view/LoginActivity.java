package valderfields.rjb_admin.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

import valderfields.rjb_admin.R;
import valderfields.rjb_admin.model.User;
import valderfields.rjb_admin.presenter.LoginPresenter;

/**
 * 登录和注册操作
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener,Observer{

    String TAG = "LoginActivity";
    //login
    private Button bLogin;
    private EditText username_Login;
    private EditText password_Login;
    private CheckBox remPWD,autoLogin;

    private LoginPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        initLoginView();
        presenter = new LoginPresenter(this);
        presenter.addObserver(this);
    }

    private void initLoginView(){
        bLogin = (Button)findViewById(R.id.dl);
        bLogin.setOnClickListener(this);
        username_Login = (EditText)findViewById(R.id.username);
        password_Login = (EditText)findViewById(R.id.password);
        username_Login.setText(User.getUsername());
        password_Login.setText(User.getPassword());
    }

    private void Login(){
        String un = username_Login.getText().toString().trim();
        String pw = password_Login.getText().toString().trim();
        //pw=(pw.length()==40)?pw:EncodeUtil.shaEncode(pw);
        if(!un.equals("")&&!pw.equals("")){
                User.setUsername(un);
                User.setPassword(pw);
            //登录
            presenter.Login(un,pw);
        }
        else{
            Toast.makeText(this,"用户名和密码不能为空",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dl:
                Login();
                break;
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof LoginPresenter){
            Log.e("Re",arg.toString());
            if(arg.equals("Login Success"))
            {
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

}
