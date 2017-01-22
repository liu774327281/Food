package org.mobiletrain.food;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.mobiletrain.food.bean.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class LoginActivity extends BaseActivity {

    private EditText username;
    private EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

    }

    public void login(View view) {
        BmobQuery<User> query = new BmobQuery<>();
        //添加查询条件
        final String usernameStr = username.getText().toString();
        //select * from user where username=usernameStr and password=passwordStr
        query.addWhereEqualTo("username", usernameStr);
        final String passwordStr = password.getText().toString();
        query.addWhereEqualTo("password", passwordStr);
        //执行查询操作
        query.findObjects(this, new FindListener<User>() {
            //方法调用成功
            @Override
            public void onSuccess(List<User> list) {
//                Toast.makeText(LoginActivity.this, "查询成功！" + list.size(), Toast.LENGTH_SHORT).show();
                //表示服务端有该条数据
                if (list != null && list.size() == 1) {
                    getSharedPreferences("loginInfo", MODE_PRIVATE).edit()
                            .putBoolean("isLogin", true)
                            .putString("username", usernameStr)
                            .putString("password", passwordStr).commit();
                    Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                    LoginActivity.this.finish();
                } else {
                    Toast.makeText(LoginActivity.this, "用户名或密码输入错误！", Toast.LENGTH_SHORT).show();
                }
            }

            //方法调用失败
            @Override
            public void onError(int i, String s) {
                Toast.makeText(LoginActivity.this, "查询失败！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void reg(View view) {
        startActivity(new Intent(LoginActivity.this, RegActivity.class));
    }
}
