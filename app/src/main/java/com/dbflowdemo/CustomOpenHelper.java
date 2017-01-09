package com.dbflowdemo;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.database.DatabaseHelperListener;
import com.raizlabs.android.dbflow.structure.database.FlowSQLiteOpenHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by android-dev on 16/12/16.
 */

public class CustomOpenHelper extends FlowSQLiteOpenHelper {

    private DatabaseDefinition databaseDefinition;

    public CustomOpenHelper(DatabaseDefinition databaseDefinition, DatabaseHelperListener listener) {
        super(databaseDefinition, listener);
        this.databaseDefinition = databaseDefinition;
    }

    @Override
    public void backupDB() {
//        super.backupDB();
        Log.d("CustomOpenHelper", "backupDB");
        Context context = FlowManager.getContext();
        File existing = context.getDatabasePath(databaseDefinition.getDatabaseFileName());
        File backuped = new File(Environment.getExternalStorageDirectory(), "temp_dbflow_backup.db");

        writeToDB(existing, backuped);
    }

    @Override
    public void performRestoreFromBackup() {
//        super.performRestoreFromBackup();
        Log.d("CustomOpenHelper", "performRestoreFromBackup");
        File backuped = new File(Environment.getExternalStorageDirectory(), "temp_dbflow_backup.db");
        if (!backuped.exists())
            return;
        File dbPath = FlowManager.getContext().getDatabasePath(databaseDefinition.getDatabaseFileName());
//        File temp = new File(dbPath.getParent(), "temp.db");
//        writeToDB(dbPath,temp);
//        dbPath.delete();
        writeToDB(backuped, dbPath);
    }

    private void writeToDB(File src, File dst) {
        try {
            FileInputStream fis = new FileInputStream(src);
            FileOutputStream fos = new FileOutputStream(dst);

            byte[] buffer = new byte[1024];
            int ret = 0;
            while ((ret = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, ret);
            }

            fos.flush();
            fos.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
