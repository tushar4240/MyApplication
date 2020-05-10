package com.codinginflow.in;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.database.Cursor;

import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;

import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.VideoView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MA_LectureActivity extends AppCompatActivity {
    private Uri uri;
    private String name, path;



//    private static final int REQUEST_CODE = 1000;
//    private static final int REQUEST_PERMISSION = 1001;
//    private int mScreenDensity;
//    private MediaProjectionManager mediaProjectionManager;
//    private static final int DISPLAY_WIDTH = 720;
//    private static final int DISPLAY_HEIGHT = 1280;
//    private VirtualDisplay virtualDisplay;
//    private MediaProjectionCallback mediaProjectionCallback;
//    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
//    private MediaRecorder mediaRecorder;
//    private MediaProjection mediaProjection;
//
//    static {
//        ORIENTATIONS.append(Surface.ROTATION_0,90);
//        ORIENTATIONS.append(Surface.ROTATION_90,0);
//        ORIENTATIONS.append(Surface.ROTATION_180,270);
//        ORIENTATIONS.append(Surface.ROTATION_270,180);
//    }
//
//    private RelativeLayout rootLayout;
//    private ToggleButton toggleButton;
//    private VideoView videoView;
//    private String videoUri="";
    //    private StorageReference mStorageRef;
    Button pdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ma_lecture);

//        DisplayMetrics metrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        mScreenDensity = metrics.densityDpi;
//
//        mediaRecorder = new MediaRecorder();
//        mediaProjectionManager = (MediaProjectionManager)getSystemService(Context.MEDIA_PROJECTION_SERVICE);
//        videoView = (VideoView)findViewById(R.id.videoView);
//        toggleButton = (ToggleButton)findViewById(R.id.toggleButton);
//        rootLayout = (RelativeLayout)findViewById(R.id.rootLayout);
//        toggleButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                        + ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO)
//                        != PackageManager.PERMISSION_GRANTED){
//                    if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                            || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.RECORD_AUDIO)){
//                        toggleButton.setChecked(false);
//                        Snackbar.make(rootLayout,"Permission",Snackbar.LENGTH_INDEFINITE).setAction("ENABLE", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                ActivityCompat.requestPermissions(MainActivity.this,new String[]{
//                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO
//                                },REQUEST_PERMISSION);
//                            }
//                        }).show();
//                        // Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
//                    }
//                    else{
//                        ActivityCompat.requestPermissions(MainActivity.this,new String[]{
//                                Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO
//                        },REQUEST_PERMISSION);
//                    }
//
//                }
//                else{
//                    toogleScreenShare(v);
//                }
//            }

//            private VirtualDisplay createVirtualDisplay() {
//                return mediaProjection.createVirtualDisplay("MainActivity",DISPLAY_WIDTH,DISPLAY_HEIGHT,mScreenDensity,
//                        DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
//                        mediaRecorder.getSurface(),null,null);
//            }

            //hjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjja

//        });


        ///////////////////////////////
        pdf = (Button) findViewById(R.id.ma_openpdf);

        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Choose"), 1);
            }
        });
    }

    /////////


//    private VirtualDisplay createVirtualDisplay() {
//        return mediaProjection.createVirtualDisplay("MainActivity",DISPLAY_WIDTH,DISPLAY_HEIGHT,mScreenDensity,
//                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
//                mediaRecorder.getSurface(),null,null);
//    }


//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    private class MediaProjectionCallback extends MediaProjection.Callback {
//        @Override
//        public void onStop(){
//            if(toggleButton.isChecked()){
//                toggleButton.setChecked(false);
//                mediaRecorder.stop();
//                mediaRecorder.reset();
//            }
//            mediaProjection= null;
//            stopRecordScreen();
//            super.onStop();
//        }
//        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//        private void stopRecordScreen() {
//            if(virtualDisplay==null){
//                return;
//            }
//            virtualDisplay.release();
//            destroyMediaProjection();
//        }
//
//        private void destroyMediaProjection() {
//            if(mediaProjection!=null){
//                mediaProjection.unregisterCallback(mediaProjectionCallback);
//                mediaProjection.stop();
//                mediaProjection=null;
//            }
//        }
//
//
//
//
//    }
//
//    private void toogleScreenShare(View v) {
//        if(((ToggleButton)v).isChecked()){
//            initRecorder();
//            recordScreen();
//        }
//        else{
//            mediaRecorder.stop();
//            mediaRecorder.reset();
//            mediaProjectionCallback.stopRecordScreen();
//            videoView.setVisibility(View.VISIBLE);
//            videoView.setVideoURI(Uri.parse(videoUri));
//            videoView.start();
//        }
//    }
//
//    private void recordScreen() {
//        if(mediaProjection == null){
//            startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(),REQUEST_CODE);
//            return;
//        }
//        virtualDisplay = createVirtualDisplay();
//        mediaRecorder.start();
//
//    }
//
//
//
//    private void initRecorder() {
//        try{
//            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//            mediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
//            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//            videoUri = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+
//                    new StringBuilder("/EDMTRecord_").append(new SimpleDateFormat("dd-mm-yyyy-hh_mm_ss")
//                            .format(new Date())).append(".mp4").toString();
//            mediaRecorder.setOutputFile(videoUri);
//            mediaRecorder.setVideoSize(DISPLAY_WIDTH,DISPLAY_HEIGHT);
//            mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
//            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//            mediaRecorder.setVideoEncodingBitRate(512*1000);
//            mediaRecorder.setVideoFrameRate(30);
//
//            int rotation=getWindowManager().getDefaultDisplay().getRotation();
//            int orientation = ORIENTATIONS.get(rotation+90);
//            mediaRecorder.setOrientationHint(orientation);
//            mediaRecorder.prepare();
//        }
//        catch (IOException e){
//            e.printStackTrace();
//        }
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permission, @NonNull int[] grantResults){
//        super.onRequestPermissionsResult(requestCode,permission,grantResults);
//        switch (requestCode){
//            case REQUEST_PERMISSION:
//            {
//                if((grantResults.length>0)&&(grantResults[0]+grantResults[1]==PackageManager.PERMISSION_GRANTED)){
//                    toogleScreenShare(toggleButton);
//                }
//                else{
//                    toggleButton.setChecked(false);
//                    Snackbar.make(rootLayout,"Permission",Snackbar.LENGTH_INDEFINITE).setAction("ENABLE", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
//                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO
//                            },REQUEST_PERMISSION);
//                        }
//                    }).show();
//                }
//                return;
//            }
//        }
//    }

    ////////////////////////////////
    private static final int FILE_SELECT_CODE = 0;

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_CANCELED) {
//            // action cancelled
//        }



        if (resultCode == RESULT_OK && requestCode == 1 && data != null) {
            uri = data.getData();
            name = getFileName(uri); //name yahi h?
//            pdf.setText(name);//haaa
            // path="/storage/emulated/0/Download/"+name;
            path = getPathFromUri(MA_LectureActivity.this, uri);
            int p = 0;
//            path = getRealPathFromURI(uri);
//            String add = Environment.getExternalStorageDirectory().getAbsolutePath().toString();
//            path = add;
//            int i = 0;

            Intent intent = new Intent(getApplicationContext(), MA_PdfViewer.class);
            intent.putExtra("name", name);
            intent.putExtra("uri", path);
            startActivity(intent);
        }
//        if(requestCode!=REQUEST_CODE){
//            Toast.makeText(this, "Unk error", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if(resultCode!=RESULT_OK){
//            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
//            toggleButton.setChecked(false);
//            return;
//        }

//        mediaProjectionCallback = new MediaProjectionCallback();
//        mediaProjection = mediaProjectionManager.getMediaProjection(resultCode,data);
//        mediaProjection.registerCallback(mediaProjectionCallback,null);
//        virtualDisplay = createVirtualDisplay();
//        mediaRecorder.start();
    }

    public static String getPathFromUri(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
