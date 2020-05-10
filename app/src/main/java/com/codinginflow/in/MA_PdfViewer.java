package com.codinginflow.in;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
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

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MA_PdfViewer extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener, OnDrawListener, OnErrorListener, OnPageScrollListener {
    PDFView pdfView;
    private static final String TAG = MA_PdfViewer.class.getSimpleName();
    Integer pageNumber = 0;
     Button pen;
    ToggleButton screen;
    private MA_CanvasView canvasView;

    String pdfFileName;
    Button whiteboard;
    /////////////////////////////////////////
    private static final int REQUEST_CODE = 1000;
    private static final int REQUEST_PERMISSION = 1001;
    private int mScreenDensity;
    private MediaProjectionManager mediaProjectionManager;
    private static int DISPLAY_WIDTH = 720;
    private static int DISPLAY_HEIGHT = 1280;
    private VirtualDisplay virtualDisplay;
    private MediaProjectionCallback mediaProjectionCallback;
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    private MediaRecorder mediaRecorder;
    private MediaProjection mediaProjection;

    static {
        ORIENTATIONS.append(Surface.ROTATION_0,90);
        ORIENTATIONS.append(Surface.ROTATION_90,0);
        ORIENTATIONS.append(Surface.ROTATION_180,270);
        ORIENTATIONS.append(Surface.ROTATION_270,180);
    }

    private RelativeLayout rootLayout;
    private ToggleButton toggleButton;
    private VideoView videoView;
    private String videoUri="";
    ////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ma_pdf_viewer);
        pdfView=findViewById(R.id.ma_pdfView);
        whiteboard = findViewById(R.id.ma_whileboard);
//        canvasView = findViewById(R.id.canvas);
        whiteboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ////////////////////////////////////
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mScreenDensity = metrics.densityDpi;

        mediaRecorder = new MediaRecorder();
        mediaProjectionManager = (MediaProjectionManager)getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        videoView = (VideoView)findViewById(R.id.ma_videoView);
        screen = (ToggleButton)findViewById(R.id.ma_tb_screen);
        rootLayout = (RelativeLayout)findViewById(R.id.ma_rootLayout);
        pen =  findViewById(R.id.ma_tb_pen);
//        pen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    // The toggle is enabled
//
//                } else {
//                    // The toggle is disabled
//                }
//            }
//        });
        pen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in= new Intent(MA_PdfViewer.this, ma_fragment_pen.class);
                startActivity(in);
            }
        });
        DISPLAY_HEIGHT = metrics.heightPixels;
        DISPLAY_WIDTH = metrics.widthPixels;

        screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(MA_PdfViewer.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        + ContextCompat.checkSelfPermission(MA_PdfViewer.this, Manifest.permission.RECORD_AUDIO)
                        != PackageManager.PERMISSION_GRANTED){
                    if(ActivityCompat.shouldShowRequestPermissionRationale(MA_PdfViewer.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            || ActivityCompat.shouldShowRequestPermissionRationale(MA_PdfViewer.this,Manifest.permission.RECORD_AUDIO)){
                        screen.setChecked(false);
                        Snackbar.make(rootLayout,"Permission",Snackbar.LENGTH_INDEFINITE).setAction("ENABLE", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ActivityCompat.requestPermissions(MA_PdfViewer.this,new String[]{
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO
                                },REQUEST_PERMISSION);
                            }
                        }).show();
                        // Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        ActivityCompat.requestPermissions(MA_PdfViewer.this,new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO
                        },REQUEST_PERMISSION);
                    }

                }
                else{
                    toogleScreenShare(v);
                }
            }

            private VirtualDisplay createVirtualDisplay() {
                return mediaProjection.createVirtualDisplay("MA_PdfViewer",DISPLAY_WIDTH,DISPLAY_HEIGHT,mScreenDensity,
                        DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                        mediaRecorder.getSurface(),null,null);
            }

            //hjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjja

        });
        ////////////////////////////


        String filepath=getIntent().getStringExtra("uri");
        pdfFileName=getIntent().getStringExtra("name");

        File file=new File(filepath);     //crash karri h is line pa ,ok dekhte h, path dikkat h vo, m debug karunga, pdf select karna phn se



    pdfView.fromFile(file)
            .defaultPage(pageNumber)
            .enableSwipe(true)
            .swipeHorizontal(false)
            .onPageChange(this)
            .enableAnnotationRendering(true)
            .onLoad(this)
            .scrollHandle(new DefaultScrollHandle(this))
            .load();

        Toast.makeText(this,"Pdf Viewer",Toast.LENGTH_LONG).show();




    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        printBookmarksTree(pdfView.getTableOfContents(), "-");

    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            //  Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }


    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));

    }



    @Override
    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onPageScrolled(int page, float positionOffset) {
    }
//    public void clearCanvas(View view) {
//        canvasView.clearCanvas();
//    }

/////////////////////////////////////////////////////////
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode,resultCode,data);
    if(requestCode!=REQUEST_CODE){
        Toast.makeText(this, "Unk error", Toast.LENGTH_SHORT).show();
        return;
    }
    if(resultCode!=RESULT_OK){
        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        toggleButton.setChecked(false);
        return;
    }
    mediaProjectionCallback = new MediaProjectionCallback();
    mediaProjection = mediaProjectionManager.getMediaProjection(resultCode,data);
    mediaProjection.registerCallback(mediaProjectionCallback,null);
    virtualDisplay = createVirtualDisplay();
    mediaRecorder.start();

}

    private VirtualDisplay createVirtualDisplay() {
        return mediaProjection.createVirtualDisplay("MainActivity",DISPLAY_WIDTH,DISPLAY_HEIGHT,mScreenDensity,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                mediaRecorder.getSurface(),null,null);
    }




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private class MediaProjectionCallback extends MediaProjection.Callback {
        @Override
        public void onStop(){
            if(toggleButton.isChecked()){
                toggleButton.setChecked(false);
                mediaRecorder.stop();
                mediaRecorder.reset();
            }
            mediaProjection= null;
            stopRecordScreen();
            super.onStop();
        }
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        private void stopRecordScreen() {
            if(virtualDisplay==null){
                return;
            }
            virtualDisplay.release();
            destroyMediaProjection();
        }

        private void destroyMediaProjection() {
            if(mediaProjection!=null){
                mediaProjection.unregisterCallback(mediaProjectionCallback);
                mediaProjection.stop();
                mediaProjection=null;
            }
        }




    }

    private void toogleScreenShare(View v) {
        if(((ToggleButton)v).isChecked()){
            initRecorder();
            recordScreen();
        }
        else{
            mediaRecorder.stop();
            mediaRecorder.reset();
            mediaProjectionCallback.stopRecordScreen();
            videoView.setVisibility(View.VISIBLE);
            videoView.setVideoURI(Uri.parse(videoUri));
            videoView.start();
        }
    }

    private void recordScreen() {
        if(mediaProjection == null){
            startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(),REQUEST_CODE);
            return;
        }
        virtualDisplay = createVirtualDisplay();
        mediaRecorder.start();

    }



    private void initRecorder() {
        try{
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            videoUri = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+
                    new StringBuilder("/screenrecord").append(new SimpleDateFormat("dd-mm-yyyy-hh_mm_ss")
                            .format(new Date())).append(".mp4").toString();
            mediaRecorder.setOutputFile(videoUri);
            mediaRecorder.setVideoSize(DISPLAY_WIDTH,DISPLAY_HEIGHT);
            mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setVideoEncodingBitRate(512*1000);
            mediaRecorder.setVideoFrameRate(30);

            int rotation=getWindowManager().getDefaultDisplay().getRotation();
            int orientation = ORIENTATIONS.get(rotation+90);
            mediaRecorder.setOrientationHint(orientation);
            mediaRecorder.prepare();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permission, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode,permission,grantResults);
        switch (requestCode){
            case REQUEST_PERMISSION:
            {
                if((grantResults.length>0)&&(grantResults[0]+grantResults[1]==PackageManager.PERMISSION_GRANTED)){
                    toogleScreenShare(toggleButton);
                }
                else{
                    toggleButton.setChecked(false);
                    Snackbar.make(rootLayout,"Permission",Snackbar.LENGTH_INDEFINITE).setAction("ENABLE", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ActivityCompat.requestPermissions(MA_PdfViewer.this,new String[]{
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO
                            },REQUEST_PERMISSION);
                        }
                    }).show();
                }
                return;
            }
        }
    }
    ////////////////////////


}
