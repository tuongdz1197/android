package com.example.chamcong;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class InfoNVActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private TextView tvInfoName, tvInfoPhone,tvInfoSongaylam,tvId;
    private ArrayAdapter<User> adapter;
    private ArrayList<User> userlist = new ArrayList<>();
    String idUpdate ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_nv);
        initData(); // khoi tao database
        tvInfoName = findViewById(R.id.tvInfoName);
        tvInfoPhone = findViewById(R.id.tvInfoPhone);
        tvInfoSongaylam = findViewById(R.id.tvInfoSongaylam);
        tvId = findViewById(R.id.tvID);


        //nhan dulieu tu inten


        String name = getIntent().getStringExtra("name");
        String phone = getIntent().getStringExtra("phone");
        String songaylam = getIntent().getStringExtra("songaylam");
        String id = getIntent().getStringExtra("id");

        idUpdate = id;


    //    Toast.makeText(this, id, Toast.LENGTH_SHORT).show();


        tvInfoName.setText(name);
        tvInfoPhone.setText(phone);
        tvInfoSongaylam.setText(String.valueOf(songaylam));
        tvId.setText(id);

        //button diem danh

        findViewById(R.id.btnDiemDanh).setOnClickListener(new View.OnClickListener() {
            @Override

            //cong them 1 khi an vao item
            public void onClick(View v) {
               checkDD();
               loadData();
            }
        });
    }
    private void initData(){
        db = openOrCreateDatabase("chamcong.db",MODE_PRIVATE,null);

        String sql = "CREATE TABLE IF NOT EXISTS tbqlchamcong1 (id integer primary key autoincrement, name text, phone integer, songaylam integer, luong integer)";
        db.execSQL(sql);
    }
    public void checkDD() {
//

        Integer io = Integer.valueOf(idUpdate); //id =io

//        Toast.makeText(this,String.valueOf(io), Toast.LENGTH_SHORT).show();
//        Integer io = Integer.valueOf(idUpdate);
//
        String songaylam = getIntent().getStringExtra("songaylam");
        String soaz = songaylam;
        Integer solam = Integer.valueOf(soaz);
        solam = solam+1;

//        Toast.makeText(this,String.valueOf(solam), Toast.LENGTH_SHORT).show();

//        String songay = editsongaylam.getText().toString();
//
//        Integer n = Integer.valueOf(songay);
//        n=n+1;


        String sql ="UPDATE tbqlchamcong1 SET songaylam = '"+(solam)+"' WHERE id = "+ idUpdate;
        db.execSQL(sql);
    }
    public void loadData(){
        userlist.clear();

        String sql = "SELECT * FROM tbqlchamcong1";
        Cursor cursor =  db.rawQuery(sql, null); // tra ve cho cung ta cuirsor nhu con tro

        cursor.moveToFirst(); // tro den cai dau tien
        // tro den cai cuoi cung.. trong khi chua tro den cai cuoi cung thi lay data
        while (!cursor.isAfterLast()){
//            int id = cursor.getInt(0);
//            String name = cursor.getString(1);
//            String phone = cursor.getString(2);
            String songaylam = cursor.getString(3);
            User u = new User();
//            u.setId(id);
//            u.setName(name);
//            u.setPhone(phone);
            u.setSongaylam(songaylam);

            userlist.add(u);

            cursor.moveToNext();
        }
        adapter.notifyDataSetChanged();



    }

}
