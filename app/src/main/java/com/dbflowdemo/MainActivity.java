package com.dbflowdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.OrderBy;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private View add;
    private View backup;
    private View restore;
    private EditText name;
    private RadioGroup genderGroup;
    private EditText age;
    private EditText intro;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText) findViewById(R.id.name);
        genderGroup = (RadioGroup) findViewById(R.id.genderGroup);
        age = (EditText) findViewById(R.id.age);
        intro = (EditText) findViewById(R.id.intro);
        add = findViewById(R.id.add);
        add.setOnClickListener(this);
        backup = findViewById(R.id.backup);
        backup.setOnClickListener(this);
        restore = findViewById(R.id.restore);
        restore.setOnClickListener(this);
        list = (ListView) findViewById(R.id.list);

        loadData();
    }

    @Override
    public void onClick(View view) {
        if(view==add){
            User user = new User();
            user.name = name.getText().toString();
            user.gender = genderGroup.getCheckedRadioButtonId() == R.id.male?0:1;
            user.age = Integer.parseInt(age.getText().toString());
            user.intro = intro.getText().toString();
            user.state = 1;
            user.save();

            name.setText("");
            loadData();
            Toast.makeText(this,"添加成功",Toast.LENGTH_SHORT).show();
        } else if(view == backup) {
            FlowManager.getDatabase(AppDatabase.class).getHelper().backupDB();
        } else if(view == restore){
            FlowManager.getDatabase(AppDatabase.class).getHelper().performRestoreFromBackup();
            loadData();
        }
    }

    private void loadData(){
        List<User> users = new Select().from(User.class).orderBy(OrderBy.fromString("id desc")).queryList();
        if(users!=null){
            List<String> datas = new ArrayList<>();
            for (int i=0;i<users.size();i++){
                User user = users.get(i);
                StringBuffer buffer = new StringBuffer();
                buffer.append("user:");
                buffer.append(user.id);
                buffer.append(" name:");
                buffer.append(user.name);
                buffer.append(" is:");
                buffer.append(user.gender==0?"male":"female");
                buffer.append(" age:");
                buffer.append(user.age);
                buffer.append(" state:");
                buffer.append(user.state);
                buffer.append(" \nintro:");
                buffer.append(user.intro);
                datas.add(buffer.toString());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.test_label,datas);
            list.setAdapter(adapter);
        }
    }
}
