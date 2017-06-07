package valderfields.rjb_admin.Presenter;

import android.app.ProgressDialog;

import java.util.List;

import valderfields.rjb_admin.Model.AdminBean;
import valderfields.rjb_admin.View.AdminActivity;

/**
 * 管理员管理界面的presenter
 * Created by 11650 on 2017/6/7.
 */

public class AdminPresenter {

    private AdminActivity adminActivity;

    private List<AdminBean> adminBeanList;

    public AdminPresenter(AdminActivity adminActivity)
    {
        this.adminActivity = adminActivity;
    }

    public void initData()
    {
        final ProgressDialog dialog = new ProgressDialog(adminActivity);
        dialog.setMessage("加载用户信息");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
    }
}
