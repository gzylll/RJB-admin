package valderfields.rjb_admin.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import valderfields.rjb_admin.R;


public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        getSupportActionBar().hide();
    }
}
