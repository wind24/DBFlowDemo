package com.dbflowdemo;

import android.util.Log;

import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.sql.SQLiteType;
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration;
import com.raizlabs.android.dbflow.sql.migration.BaseMigration;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;

/**
 * Created by android-dev on 31/8/16.
 */
@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION,backupEnabled = true)
public class AppDatabase {
    public static final String NAME = "AppDatabase"; // we will add the .db extension

    public static final int VERSION = 5;

    /**
     * 升级数据库时,可以按照每个版本编写一个BaseMigration,在里面执行升级语句
     *
     *
     * 也可以针对每个表编写AlterTableMigration
     */
    @Migration(version = 4, database = AppDatabase.class)
    public static class Version4Migration extends BaseMigration {

        @Override
        public void migrate(DatabaseWrapper database) {
            Log.d("Test","update database version 4");
            database.execSQL("alter table user add state integer default 0");
        }
    }

    /**
     * 升级数据库时,需要针对每个需要升级的表编写一个AlterTableMigration类,
     * 比如给User表增加一个age字段,
     *    首先在User对象增加一个age字段
     *    然后在AppDatabase修改version
     *    然后编译项目,通过apt更新User_Table
     *    然后添加如下AlterTableMigration类
     *
     * 每个表每升级一个版本就新加一个AlterTableMigration,已添加的AlterTableMigration不删除,DBFlow会按照版本从低到高,逐步执行对应的AlterTableMigration.
     */
    @Migration(version = 2, database = AppDatabase.class)
    public static class AddAgeToUserMigration extends AlterTableMigration<User> {

        public AddAgeToUserMigration(Class<User> table) {
            super(table);
        }

        @Override
        public void onPreMigrate() {
            super.onPreMigrate();
            Log.d("Test","update database version 2");
            addColumn(SQLiteType.TEXT, User_Table.age.getNameAlias().getNameAsKey());
        }
    }

    @Migration(version = 3, database = AppDatabase.class)
    public static class AddIntroToUserMigration extends AlterTableMigration<User> {

        public AddIntroToUserMigration(Class<User> table) {
            super(table);
        }

        @Override
        public void onPreMigrate() {
            super.onPreMigrate();
            Log.d("Test","update database version 3");
            addColumn(SQLiteType.TEXT, User_Table.intro.getNameAlias().getNameAsKey());
        }
    }
}
