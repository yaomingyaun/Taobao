package com.bw.ymy.taobao.homepage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bw.ymy.taobao.Circle.activity.Circle;
import com.bw.ymy.taobao.R;
import com.bw.ymy.taobao.home.activity.Home;
import com.bw.ymy.taobao.my.activity.My;
import com.bw.ymy.taobao.order.activity.Order;
import com.bw.ymy.taobao.shoppingtrolley.activity.Shoppingtrolley;

import java.util.ArrayList;
import java.util.List;

public class ShowActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private RadioGroup radioGroup;
   private List<Fragment> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        //获取资源id
        viewPager=findViewById(R.id.show_viewpage);
        radioGroup=findViewById(R.id.show_radio);
        list=new ArrayList<>();
        list.add(new Home());
        list.add(new Circle());
        list.add(new Shoppingtrolley());
        list.add(new Order());
        list.add(new My());
        //创建适配器
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return list.get(i);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
        //滑动+点击
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case  R.id.show_home:
                        viewPager.setCurrentItem(0);
                        break;
                    case  R.id.show_circle:
                        viewPager.setCurrentItem(1);
                        break;
                    case  R.id.show_shopping:
                        viewPager.setCurrentItem(2);
                        break;
                    case  R.id.show_ccoder:
                        viewPager.setCurrentItem(3);
                        break;
                    case  R.id.show_my:
                        viewPager.setCurrentItem(4);
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
