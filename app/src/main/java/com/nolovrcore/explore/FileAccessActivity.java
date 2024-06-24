package com.nolovrcore.explore;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nolovrcore.explore.utils.FileUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class FileAccessActivity extends AppCompatActivity {

    private static final String TAG = "_FileAccessActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_file_access);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");


        String uriStr = "content://com.fish.easystorage.easy.fileprovider/inner_app_file/myfile/myTxt.txt";
        Uri uri = Uri.parse(uriStr);
        if (uri != null) {
            Log.d(TAG, "onResume: --------uri = " + uri.getAuthority());


            ContentResolver contentResolver = getContentResolver();
            InputStream inputstream = null;
            try {
                inputstream = contentResolver.openInputStream(uri);
                //读取文件内容

            } catch (FileNotFoundException e) {
                Log.e(TAG, "FileNotFoundException onResume: ", e);
            } finally {
                if (inputstream != null) {
                    try {
                        inputstream.close();
                    } catch (IOException e) {
                        e.printStackTrace();


                    }
                }
            }

        } else {
            Log.d(TAG, "onResume: uri = null");
        }


        String downloadFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        Log.d(TAG, "onResume: downloadFilePath=" + downloadFilePath);

        File pathname = Environment.getExternalStorageDirectory();
        File directory_download = new File(pathname, "Download");
        Log.e(TAG, "directory_download = " + directory_download.getAbsolutePath());

        String targetPath = downloadFilePath + "/" + "gs3d/" + "runtime.config";

        File file = new File(targetPath);
        if (file.exists()) {
            Log.d(TAG, "onResume: ok 1  " + targetPath);

            try {
                String fileContent = FileUtil.getFileContent(file);
                Log.d(TAG, "onResume: fileContent=" + fileContent);
            } catch (Exception e) {
                Log.e(TAG, "onResume: ---》", e);
            }


        } else {
            Log.d(TAG, "onResume: fail 1  " + targetPath);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    public void goFile(View view) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
//        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivityForResult(intent, 10087);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: ");
    }
}