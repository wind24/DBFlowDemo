package com.dbflowdemo;

import android.app.Application;

import com.raizlabs.android.dbflow.config.DatabaseConfig;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.database.DatabaseHelperListener;
import com.raizlabs.android.dbflow.structure.database.OpenHelper;

/**
 * Created by android-dev on 31/8/16.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FlowConfig.Builder flowBuilder = new FlowConfig.Builder(this);
        FlowManager.init(flowBuilder.addDatabaseConfig(new DatabaseConfig.Builder(AppDatabase.class).openHelper(new DatabaseConfig.OpenHelperCreator() {
            @Override
            public OpenHelper createHelper(DatabaseDefinition databaseDefinition, DatabaseHelperListener helperListener) {
                return new CustomOpenHelper(databaseDefinition,helperListener);
            }
        }).build()).build());
    }
}
