package org.mobiletrain.food;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.mobiletrain.food.bean.User;

import cn.bmob.v3.listener.SaveListener;

/**
 * 1.注册帐号，后台创建应用
 * 2.应用创建成功之后，点击进入应用，在设置一栏中可以看到ApplicationID
 * 3.下载SDK：http://www.bmob.cn/downloads#android_sdk
 * 4.将SDK导入到项目中，jar文件直接拷贝到libs文件夹中，然后选中所有的jar文件，右键单击，单击Add as library，
 * 对于动态库文件（.so文件），先在项目中创建jniLibs文件夹，然后将局部gradle文件中生成的sourceSets改为下面的内容
 * sourceSets { main { jniLibs.srcDirs = ['src/main/libs'] } }或者sourceSets { main { jniLibs.srcDirs = ['libs'] } }
 * 5.创建MyApplication，继承自Application，在MyApplication中初始化Bmob，然后在清单文件中给application节点添加name属性
 * 6.创建实体类User，继承自BmobObject
 * 7.当用户点击注册时，调用User中的save方法将数据保存到服务端
 */
public class RegActivity extends BaseActivity {

    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
    }

    public void reg(View view) {
        User user = new User();
        user.setPassword(password.getText().toString());
        user.setUsername(username.getText().toString());
        //向服务端保存数据
        user.save(this, new SaveListener() {
            //数据保存成功时回调
            @Override
            public void onSuccess() {
                Toast.makeText(RegActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                RegActivity.this.finish();
            }

            //数据保存失败时回调
            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(RegActivity.this, "注册失败！", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
