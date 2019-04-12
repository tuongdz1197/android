package com.example.chamcong;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
// ket noi csdl.
public class TrangChuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);
        findViewById(R.id.btnDanhsach).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                senDataLogin();
            }
        });
        findViewById(R.id.btnTinhluong).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                senDataTinhLuong();
            }
        });
    }
    private void senDataLogin(){
        Intent i = new Intent(this, quanLyNhanVienActivity.class);
        startActivity(i);
    }
    private void senDataTinhLuong(){
        Intent i = new Intent(this, quanLyChamCongActivity.class);
        startActivity(i);
    }
}
