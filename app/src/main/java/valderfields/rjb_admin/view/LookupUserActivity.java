package valderfields.rjb_admin.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import valderfields.rjb_admin.presenter.LookupUserPresenter;
import valderfields.rjb_admin.R;

public class LookupUserActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView userList;
    private LookupUserPresenter presenter;
    private SimpleAdapter adapter;
    //list中显示的用户数据
    private List<HashMap<String,String>> dataList = new ArrayList<>();
    private Boolean canChangePWN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lookup_user);
        presenter = new LookupUserPresenter(this);
        initView();
        presenter.initData();
        getSupportActionBar().setTitle("查看用户");
    }

    private void initView()
    {
        SharedPreferences sp = getSharedPreferences("setting",MODE_PRIVATE);
        canChangePWN = sp.getBoolean("cPWN",false);
        userList = (ListView)findViewById(R.id.userList);
        //生成适配器
        adapter = new SimpleAdapter(this,
                dataList,//数据来源
                R.layout.item_listview,//ListItem的XML实现
                new String[] {"uid", "username"},
                new int[] {R.id.userUID,R.id.userName});
        userList.setAdapter(adapter);
        userList.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.lookup_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.refresh:
                presenter.initData();
                break;
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        UserDialog userDialog = new UserDialog(this,canChangePWN);
        userDialog.initData(presenter.getUserBeanDataAt(position));
        userDialog.Show();
    }
}
