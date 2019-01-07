package com.bw.ymy.taobao;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.ymy.taobao.Api.Api;
import com.bw.ymy.taobao.homepage.ShowActivity;
import com.bw.ymy.taobao.loginandsing.login.bean.LoginBean;
import com.bw.ymy.taobao.loginandsing.sing.activity.SingActivity;
import com.bw.ymy.taobao.mvp.prenster.IPrenserlpl;
import com.bw.ymy.taobao.mvp.view.IView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

public class MainActivity extends AppCompatActivity implements IView {
    @BindView(R.id.kszc)
     TextView kszc;

    @BindView(R.id.login_pass)
     ImageView login_pass;

    @BindView(R.id.et_pass)
     EditText et_pass;

    @BindView(R.id.et_phone)
    EditText et_phone;

    @BindView(R.id.jz_pass)
    CheckBox jz_pass;

    @BindView(R.id.login)
    Button login;
    IPrenserlpl iPrenserlpl;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //注册 findview
        ButterKnife.bind(this);
        iPrenserlpl=new IPrenserlpl(this);
        //shaer
        sharedPreferences=getSharedPreferences("User",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        //记住密码
        boolean check_pass=sharedPreferences.getBoolean("check_pass",false);
        if(check_pass)
        {
            //先把值取出来
            String  phone=sharedPreferences.getString("phone",null);
            String  pwd=sharedPreferences.getString("pwd",null);
            //把值设置进去
            et_phone.setText(phone);
            et_pass.setText(pwd);
            jz_pass.setChecked(true);
        }



}

    //点击登录
    @OnClick(R.id.login)
    public  void  loginOnclick()
    {
        String phone=et_phone.getText().toString();
        String  pwd=et_pass.getText().toString();
        //判断是否记住密码
        if(jz_pass.isChecked())
        {
                //将值存入
            editor.putString("phone",phone);
            editor.putString("pwd",pwd);
            //存入勾选状态
            editor.putBoolean("check_pass",true);
            //提交
            editor.commit();
        }else
        {
            //清空
            editor.clear();
            //提交
            editor.commit();
        }
        Map<String,String> map=new HashMap<>();
        map.put("phone",phone+"");
        map.put("pwd",pwd+"");
        iPrenserlpl.getRequest(Api.login,map,LoginBean.class);
    }
    //点击调到注册页面
    @OnClick(R.id.kszc)
    public  void  kszcOnclick()
    {
        Intent intent=new Intent(MainActivity.this,SingActivity.class);
        startActivity(intent);
    }

    //密码显示或隐藏
    @OnTouch(R.id.login_pass)
    public  boolean setLogin_pass(View v,MotionEvent event)
    {
        if(event.getAction()==MotionEvent.ACTION_DOWN)
        {
            et_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

        }else if(event.getAction()==MotionEvent.ACTION_UP)
        {
            et_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        return true;
    }


    @Override
    public void onsuccess(Object data) {
        if(data instanceof LoginBean)
        {
            LoginBean loginBean= (LoginBean) data;
          if(loginBean.getMessage().equals("登录成功"))
          {
              Intent intent=new Intent(MainActivity.this,ShowActivity.class);
              startActivity(intent);
          }else
          {
              Toast.makeText(MainActivity.this,loginBean.getMessage()+"",Toast.LENGTH_LONG).show();
          }
        }

    }
}
