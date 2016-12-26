package com.lxm.ss.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxm.ss.R;
import com.lxm.ss.http.CwyyHttpPost;
import com.lxm.ss.util.Zlog;

import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.btn_ceshi1)
    Button btnCeshi1;
    @BindView(R.id.btn_ceshi2)
    Button btnCeshi2;
    @BindView(R.id.btn_ceshi3)
    Button btnCeshi3;
    @BindView(R.id.btn_ceshi4)
    Button btnCeshi4;
    @BindView(R.id.btn_ceshi5)
    Button btnCeshi5;
    @BindView(R.id.txt_show)
    TextView txtShow;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);


        //这里有{}和return 以及 ;
        Runnable r = () -> {
            System.out.println("hello world");
        };

        //这里不需要{}和return
        Comparator<String> c = (String s1, String s2) -> s2.length() - s1.length();

        r.run();
        System.out.println(c.compare("s1", "12323"));
    }

    @OnClick({R.id.btn_ceshi1, R.id.btn_ceshi2, R.id.btn_ceshi3, R.id.btn_ceshi4, R.id.btn_ceshi5})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ceshi1:

                inidata();

                break;
            case R.id.btn_ceshi2:
                break;
            case R.id.btn_ceshi3:
                break;
            case R.id.btn_ceshi4:
                break;
            case R.id.btn_ceshi5:
                break;
        }
    }

    private void inidata() {
        CwyyHttpPost.getInstance().addDic(MainActivity.this ,null);
        Zlog.ii("lxm ss initdata");
    }
}
