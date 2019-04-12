package com.example.chamcong;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class quanLyChamCongActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private ListView lvUser;

    private ArrayAdapter<User> adapter;
    private ArrayList<User> userlist = new ArrayList<>();


    //data

    private TextView tvData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_cham_cong);



        initData(); // khoi tao database



//

        lvUser = findViewById(R.id.lvUser);

        adapter = new ArrayAdapter<User>(this,0,userlist){




            public View getView(int position,  View convertView,  ViewGroup parent) {
                LayoutInflater inflater  = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE); //  = getSystemSe+cast
                convertView = inflater.inflate(R.layout.data_item,null);

                TextView tvName = convertView.findViewById(R.id.tvName);
                TextView tvPhone = convertView.findViewById(R.id.tvPhone);
                TextView tvSongaylam = convertView.findViewById(R.id.tvSongaylam);

                User u = userlist.get(position);

                tvName.setText(u.getName());
                tvPhone.setText(u.getPhone());
                tvSongaylam.setText(u.getSongaylam());
                return convertView;
            }
        };

        lvUser.setAdapter(adapter);

        loadDataBase();
// truyen item qua man hinh moi
        lvUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showInfoNV(position);

            }

        });
    }



    private void initData(){
        db = openOrCreateDatabase("chamcong.db",MODE_PRIVATE,null);

      String sql = "CREATE TABLE IF NOT EXISTS tbqlchamcong1 (id integer primary key autoincrement, name text, phone integer, songaylam integer, luong integer)";
       db.execSQL(sql);
    }

    //load data
    private void loadDataBase(){
        userlist.clear();

        String sql = "SELECT * FROM tbqlchamcong1";
        Cursor cursor =  db.rawQuery(sql, null); // tra ve cho cung ta cuirsor nhu con tro

        cursor.moveToFirst(); // tro den cai dau tien
        // tro den cai cuoi cung.. trong khi chua tro den cai cuoi cung thi lay data
        while (!cursor.isAfterLast()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String phone = cursor.getString(2);
            String songaylam = cursor.getString(3);


            User u = new User();

            u.setId(id);
            u.setName(name);
            u.setPhone(phone);
            u.setSongaylam(songaylam);


            userlist.add(u);

            cursor.moveToNext();
        }
        adapter.notifyDataSetChanged();

    }
    private void showInfoNV(int position){
        User u = userlist.get(position);
        Intent i = new Intent(this,InfoNVActivity.class);
        //TRUYEN DU LIEU QUA InTEN
        i.putExtra("name",u.getName());
        i.putExtra("phone",u.getPhone());
        i.putExtra("songaylam",u.getSongaylam());
        String o = String.valueOf(u.getId());
        i.putExtra("id",o);


//        Toast.makeText(this,o, Toast.LENGTH_SHORT).show();
//
        startActivity(i);
    }





    //load data

}
