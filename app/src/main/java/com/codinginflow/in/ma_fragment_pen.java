package com.codinginflow.in;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Random;

import me.panavtec.drawableview.DrawableView;
import me.panavtec.drawableview.DrawableViewConfig;


public class ma_fragment_pen extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final DrawableView drawableView;
        Button up,down,change,undo,clear,back;
        final DrawableViewConfig config;
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        drawableView = view.findViewById(R.id.ma_paintview);
        up = view.findViewById(R.id.ma_wi);
        down = view.findViewById(R.id.ma_wd);
        change = view.findViewById(R.id.ma_color);
        undo = view.findViewById(R.id.ma_undo);
    back = view.findViewById(R.id.ma_back);
        clear = view.findViewById(R.id.ma_clear);

        config = new DrawableViewConfig();
        config.setStrokeColor(getResources().getColor(android.R.color.black));

        config.setShowCanvasBounds(true);
        config.setMinZoom(1.0f);
        config.setMaxZoom(3.0f);
        config.setCanvasHeight(1920);
        config.setCanvasWidth(1920);

        drawableView.setConfig(config);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                config.setStrokeWidth(config.getStrokeWidth()+5);
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                config.setStrokeWidth(config.getStrokeWidth()-5);
            }

        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                config.setStrokeColor(Color.argb(255,random.nextInt(256),random.nextInt(256),random.nextInt(256)));
            }
        });
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawableView.undo();
            }

        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                drawableView.clear();
                Intent in= new Intent(getActivity(),MA_PdfViewer.class);
                startActivity(in);
            }

        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
//
                Intent in= new Intent(getActivity(),MA_PdfViewer.class);
                startActivity(in);
            }

        });



        return view;
    }
}
