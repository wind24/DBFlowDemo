package com.dbflowdemo;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by android-dev on 31/8/16.
 */
@Table(database =  AppDatabase.class)
public class User extends BaseModel {

    @PrimaryKey(autoincrement = true)
    public long id;
    @Column
    public String name;
    @Column
    public int gender;
    @Column
    public int age;
    @Column
    public String intro;
    @Column
    public int state;

}
