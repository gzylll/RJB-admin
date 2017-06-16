package valderfields.rjb_admin.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public void addAdmin(View v){
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_admin,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.add){
            if(User.getIsRoot())
            {

            }
            else
            {
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
}
