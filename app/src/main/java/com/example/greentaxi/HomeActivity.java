package com.example.greentaxi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.greentaxi.mlkitbarcodescan.BarcodeScanner.BarcodeScannerActivity;
import com.example.greentaxi.mlkitbarcodescan.ListingSetup.CustomItemClickListener;
import com.example.greentaxi.mlkitbarcodescan.LocalData.ContactDetail;
import com.example.greentaxi.mlkitbarcodescan.LocalData.DBHandler;
import com.example.greentaxi.mlkitbarcodescan.Util.AlertDialog.AlertDialogHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    String TAG = "HomeActivity";
    DBHandler dbHandler;
    @BindView(R.id.rvContactsList)
    RecyclerView rvContactsList;
    @BindView(R.id.fabAdd)
    FloatingActionButton fabAdd;

    ArrayList<ContactDetail> contactDetailArrayList;
    LinearLayoutManager layoutManager;

    private DatabaseReference databaseReference;


    private ListView listView;
    private ArrayAdapter<String> adapter;
    List<Object> Array = new ArrayList<>();

    AlertDialogHelper alertDialogHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        dbHandler = new DBHandler(this);
        alertDialogHelper=new AlertDialogHelper(this);
        listSetup();
        listView = findViewById(R.id.message_list);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<String>());
        listView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("message").child(autoLogin.getUserName(HomeActivity.this));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adapter.clear();

                for (DataSnapshot data : dataSnapshot.getChildren()){

                    String msg = data.getValue().toString();
                    Array.add(msg);
                    adapter.add(msg);
                }
                adapter.notifyDataSetChanged();
                listView.setSelection(adapter.getCount()-1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    public void listSetup()
    {
        contactDetailArrayList= (ArrayList<ContactDetail>) dbHandler.getAccountsList();
        Log.d(TAG, "listSetup: "+contactDetailArrayList.size());
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvContactsList.setLayoutManager(layoutManager);

    }

    public void loadAdapter(final ArrayList<ContactDetail> itemsList) {

        rvContactsList.post(() -> {
            rvContactsList.getRecycledViewPool().clear();

        });
    }

    @OnClick(R.id.fabAdd)
    public void onViewClicked() {
        Intent barcodeScanner=new Intent(this, BarcodeScannerActivity.class);
        startActivity(barcodeScanner);
    }

    public CustomItemClickListener getItemCLickListener()
    {
        CustomItemClickListener customItemClickListener=new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

            }

        };

        return customItemClickListener;
    }
}
