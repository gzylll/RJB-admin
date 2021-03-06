package valderfields.rjb_admin.view;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import android.view.View;
import android.widget.EditText;

import valderfields.rjb_admin.R;
import valderfields.rjb_admin.model.User;
import valderfields.rjb_admin.presenter.AdminPresenter;


public class AdminActivity extends AppCompatActivity {

    private AdminPresenter presenter;
    private ListView adminList;
    private SimpleAdapter adapter;
    //list中显示的用户数据
    private List<HashMap<String,String>> dataList = new ArrayList<>();
    public ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        presenter = new AdminPresenter(this);
        getSupportActionBar().setTitle("管理员");
        initView();
        presenter.initData();
    }

    private void initView()
    {
        progressDialog = new ProgressDialog(this);
        adminList = (ListView)findViewById(R.id.adminList);
        //生成适配器
        adapter = new SimpleAdapter(this,
                dataList,//数据来源
                R.layout.item_listview,//ListItem的XML实现
                new String[] {"uid", "username"},
                new int[] {R.id.userUID,R.id.userName});
        adminList.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.addadmin_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void addAdmin(){
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_editadmin,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();

        final EditText username = (EditText) view.findViewById(R.id.username);
        final EditText pwd1 = (EditText) view.findViewById(R.id.pwd);
        final EditText pwd2 = (EditText) view.findViewById(R.id.pwd_confirm);
        TextView confirm = (TextView)view.findViewById(R.id.editAdmin_yes);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equals("")||pwd1.getText().toString().equals("")
                        ||pwd2.getText().toString().equals(""))
                {
                    Toast.makeText(AdminActivity.this,"输入不能为空",Toast.LENGTH_SHORT).show();
                }
                else if(!pwd1.getText().toString().equals(pwd2.getText().toString()))
                {
                    Toast.makeText(AdminActivity.this,"密码不一致",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //提交密码
                    dialog.dismiss();
                    progressDialog.setMessage("增加新管理员");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    presenter.addAdmin(username.getText().toString(),pwd1.getText().toString());
                }
            }
        });
        TextView cancel = (TextView)view.findViewById(R.id.editAdmin_no);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.add){
            if(User.getIsRoot()) {
                addAdmin();
            } else {
                Toast.makeText(this,"您没有权限添加管理员",Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 刷新数据显示
     * @param dataList 数据
     */
    public void updateData(List<HashMap<String,String>> dataList)
    {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void update(){
        presenter.initData();
    }
}
