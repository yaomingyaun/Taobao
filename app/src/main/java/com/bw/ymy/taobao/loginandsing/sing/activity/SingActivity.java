package com.bw.ymy.taobao.loginandsing.sing.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.ymy.taobao.Api.Api;
import com.bw.ymy.taobao.MainActivity;
import com.bw.ymy.taobao.R;
import com.bw.ymy.taobao.homepage.ShowActivity;
import com.bw.ymy.taobao.loginandsing.login.bean.LoginBean;
import com.bw.ymy.taobao.loginandsing.sing.bean.SingBean;
import com.bw.ymy.taobao.mvp.prenster.IPrenserlpl;
import com.bw.ymy.taobao.mvp.view.IView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

public class SingActivity extends AppCompatActivity implements IView {
    @BindView(R.id.sing_phone)
    EditText sing_phone;

    @BindView(R.id.sing_pass)
    EditText sing_pass;

    @BindView(R.id.sing_login)
    TextView sing_login;

    @BindView(R.id.sing)
    Button sing;

    @BindView(R.id.sing_hide)
    ImageView sing_hide;

    private IPrenserlpl iPrenserlpl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing);
        //注册
        ButterKnife.bind(this);
        //
        iPrenserlpl=new IPrenserlpl(this);
    }

        //点击注册
    @OnClick(R.id.sing)
        public  void singclick()
            {
                    String phone=sing_phone.getText().toString();

                    String pwd=sing_pass.getText().toString();
                    if(phone.length()<11)
                    {
                        Toast.makeText(SingActivity.this,"请输入正确的账号长度",Toast.LENGTH_LONG).show();

                    }else if(pwd.length()<5)
                    {
                        Toast.makeText(SingActivity.this,"请输入正确密码长度",Toast.LENGTH_LONG).show();
                    }
                        Map<String,String> map=new HashMap<>();

                        map.put("phone",phone+"");

                        map.put("pwd",pwd+"");

                        iPrenserlpl.getRequest(Api.sing,map,SingBean.class);

            }


    //点击到登录页面
    @OnClick(R.id.sing_login)
    public  void  kszcOnclick()
    {
        Intent intent=new Intent(SingActivity.this,MainActivity.class);
        startActivity(intent);
    }
    //密码显示或隐藏
        @OnTouch(R.id.sing_hide)
        public  boolean setLogin_pass(View v, MotionEvent event)
        {
            if(event.getAction()==MotionEvent.ACTION_DOWN)
            {
                sing_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

            }else if(event.getAction()==MotionEvent.ACTION_UP)
            {
                sing_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            return true;
        }
    @Override
    public void onsuccess(Object data) {
        if(data instanceof SingBean)
        {
            SingBean singBean= (SingBean) data;
         if(singBean.getMessage().equals("注册成功"))
                {
                    Toast.makeText(SingActivity.this,"注册成功",Toast.LENGTH_LONG).show();

                }
         else
                 {
                     Toast.makeText(SingActivity.this,"注册失败",Toast.LENGTH_LONG).show();
                 }

        }

    }
}
