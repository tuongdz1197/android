package com.example.chamcong;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class quanLyNhanVienActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private ListView lvUser;
    private EditText editname, editphone,editsongaylam;
    private ArrayAdapter<User> adapter;
    private ArrayList<User> userlist = new ArrayList<>();
    int idTam = -1 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_nhan_vien);
        initData(); // khoi tao database

        // data

        editname = findViewById(R.id.editName);
        editphone = findViewById(R.id.editPhone);
        editsongaylam= findViewById(R.id.editSogaylam);


        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(idTam<0){
                   insertRow();

               }else{
                   updateRow();
                   idTam=-1;

               }

                loadDataBase();
            }
        });
        lvUser = findViewById(R.id.lvUser);

        // delete

        lvUser.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deleteUser(position);

                return false;
            }
        });
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
        lvUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showInfo(position);
            }
        });


        loadDataBase();

    }

    private void showInfo(int position){
        User u = userlist.get(position);
        editname.setText(u.getName());
        editphone.setText(u.getPhone());
        editsongaylam.setText(u.getSongaylam());

        idTam = u.getId();
//       String i
    }

    private void initData(){
        db = openOrCreateDatabase("chamcong.db",MODE_PRIVATE,null);

        String sql = "CREATE TABLE IF NOT EXISTS tbqlchamcong1 (id integer primary key autoincrement, name text, phone integer, songaylam integer, luong integer)";
        db.execSQL(sql);
    }
    private void insertRow(){
        String name = editname.getText().toString();
        String phone = editphone.getText().toString();


        String sql = "INSERT INTO tbqlchamcong1(name, phone) VALUES('"+ name+"', '"+ phone+"')";
        db.execSQL(sql);
    }
    //update
    public void updateRow(){

        String name = editname.getText().toString();
        String phone = editphone.getText().toString();
        String songay = editsongaylam.getText().toString();

        Integer n = Integer.valueOf(songay);
//        n=n+1;


        String sql ="UPDATE tbqlchamcong1 SET name = '"+name+"',phone = '"+phone+"',songaylam = '"+(n)+"' WHERE id = "+ idTam;
        db.execSQL(sql);
    }
    //delete
    private  void deleteUser(int position){
        int id = userlist.get(position).getId();
        String sql = "DELETE FROM tbqlchamcong1 WHERE id = " +id;
        db.execSQL(sql);
        loadDataBase();
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
}
