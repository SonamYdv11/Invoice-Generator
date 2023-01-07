package fotl.vyaapar.acq;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    Button b1,b2;
    EditText user,pass;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#01569a")));
        b1=(Button)findViewById(R.id.button);
        b2=(Button)findViewById(R.id.button2);
        user=(EditText)findViewById(R.id.editText);
        pass=(EditText)findViewById(R.id.editText2);
        db = new DatabaseHelper(this);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                ContentValues c=new ContentValues();
//                c.put("Username","Admin");
//                c.put("Password","1234");
//                Boolean isin = db.InsertLogin(c);
//
//                if(isin==true)
//                {
//                    Toast.makeText(getApplicationContext(),"Data Inserted",Toast.LENGTH_LONG).show();
//                }
                Cursor res=db.getLogin();
                if(user.getText().toString().equals("") || pass.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext()," Enter both the details ",Toast.LENGTH_LONG).show();
                }
                while (res.moveToNext())
                {


                    if(res.getString(0).equals(user.getText().toString()) && res.getString(1).equals(pass.getText().toString()))
                    {
                        Toast.makeText(getApplicationContext(),"You have Logged in Successfully ",Toast.LENGTH_LONG).show();
                        Intent i=new Intent(Login.this,Company.class);
                        startActivity(i);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Login Unsuccessful ",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setText("");
                pass.setText("");

            }
        });
    }
}
