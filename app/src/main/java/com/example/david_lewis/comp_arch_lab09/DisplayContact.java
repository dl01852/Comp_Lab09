package com.example.david_lewis.comp_arch_lab09;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.w3c.dom.Text;

public class DisplayContact extends AppCompatActivity {


    EditText name;
    EditText phone;
    EditText email;
    EditText street;
    EditText city;
    DBHelper myDb;
    Button SaveContact;
    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar(); // this snippet of code is how to change the title of your toolbar
        if(actionBar != null) // just some error checking for a potential null reference.
            actionBar.setTitle("Display Contacts");

        // These two lines adds a back arrow button in your toolbar to go back to the previous page.
        // Arrow is there by default and is hidden by default. (No icon needed)
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        name = (EditText) findViewById(R.id.editTxtName);
        phone = (EditText) findViewById(R.id.editTxtPhone);
        email = (EditText) findViewById(R.id.editTxtEmail);
        street = (EditText) findViewById(R.id.editTxtStreet);
        city = (EditText) findViewById(R.id.editTxtCity);
        SaveContact = (Button) findViewById(R.id.btnSave);
        myDb = new DBHelper(this);


        SaveContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name.getText().clear();
        }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        // i didn't define R.id.home  That id is created by default from android for the home button.
        if(id == android.R.id.home)
            NavUtils.navigateUpTo(this, new Intent(this,MainActivity.class)); // this is how you go back up to the main class
                                                                             // you could also use .NavigateUpTask if the mainActivity is the parent activity.
                                                                             // make the main Activity the Parent activity in the AndroidManifext.xml file
        return super.onOptionsItemSelected(item);
    }

//    public void run(View view)
//                {
//                    Bundle extras = getIntent().getExtras();
//                    if(extras != null)
//                    {
//                        int value = extras.getInt("id");
//                        if(value > 0)
//                        {
//                            if(myDb.updateContact(id_To_Update,name.getText().toString(),phone.getText().toString(),street.getText().toString(),email.getText().toString(),city.getText().toString()))
//                            {
//                                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                                startActivity(intent);
//                            }
//                            else
//                            {
//                                Toast.makeText(getApplicationContext(),"Not updated", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                        else
//                        {
//                            if(myDb.insertContact(name.getText().toString(),phone.getText().toString(),street.getText().toString(),email.getText().toString(),city.getText().toString()))
//                                Toast.makeText(getApplicationContext(),"Contact created", Toast.LENGTH_SHORT).show();
//                            else
//                                Toast.makeText(getApplicationContext(),"Contact wasn't created", Toast.LENGTH_SHORT).show();
//
//                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
//            }
//        }
//    }

}
