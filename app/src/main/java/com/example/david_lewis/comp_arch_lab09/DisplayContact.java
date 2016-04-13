package com.example.david_lewis.comp_arch_lab09;

import android.content.Intent;
import android.database.Cursor;
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

import static android.R.attr.name;

public class DisplayContact extends AppCompatActivity {


    private EditText Ename;
    private EditText Ephone;
    private EditText Eemail;
    private EditText Estreet;
    private EditText Ecity;
    private DBHelper myDb;
    private Button SaveContact;
    private Button Droptable;

    // String representations of it's editText counterpart. These will be passed to the database
    private String name;
    private String phone;
    private String email;
    private String street;
    private String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar(); // this snippet of code is how to change the title of your toolbar
        if (actionBar != null) // just some error checking for a potential null reference.
            actionBar.setTitle("Display Contacts");

        // These two lines adds a back arrow button in your toolbar to go back to the previous page.
        // Arrow is there by default and is hidden by default. (No icon needed)
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Ename = (EditText) findViewById(R.id.editTxtName);
        Ephone = (EditText) findViewById(R.id.editTxtPhone);
        Eemail = (EditText) findViewById(R.id.editTxtEmail);
        Estreet = (EditText) findViewById(R.id.editTxtStreet);
        Ecity = (EditText) findViewById(R.id.editTxtCity);
        SaveContact = (Button) findViewById(R.id.btnSave);
        Droptable = (Button) findViewById(R.id.btnDrop);
        myDb = new DBHelper(this);


        Bundle listViewPosition = getIntent().getExtras(); // this is the data i'm passing to this Class from MainActivity onItemClick fucntion

        if(listViewPosition != null) // CHECK TO MAKE SURE EXTRAS(or Position) is not null OR GUESS WHAT..... YEP, AN ERROR(NULL POINTER).
            showContactData(listViewPosition);                                                                 //if check isn't here, you'll get the error when you add a new contact

        SaveContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Grab the data
                name = Ename.getText().toString();
                phone = Ephone.getText().toString();
                email = Eemail.getText().toString();
                street = Estreet.getText().toString();
                city = Ecity.getText().toString();

                Bundle extras =getIntent().getExtras();
                if (extras == null) // I think this means, that this is a new contact. so let's add it to the DB
                {
                    if(!name.isEmpty()) // add this check soo you can't just enter a blank contact. *At the very least, there must be a name
                    {
                        Contact contact = new Contact(name, phone, email, street, city);
                        myDb.insertContact(contact); // add the contact to the database
                        startActivity(new Intent(getApplicationContext(), MainActivity.class)); // take us back to the main Activity where hopefully! i'll see a new contact
                    }
                    else // code for if they do enter an empty name
                        Toast.makeText(getApplicationContext(),"Can't have a contact without a name!",Toast.LENGTH_SHORT).show();
                }
                else // code for if it's not a new contact but they want to update one in the database already
                {
                    int id_to_update = extras.getInt("position");
                    Contact contact = new Contact(id_to_update,name, phone, email, street, city); // have to use constructor with id so the db can know which id to update.
                    myDb.updateContact(contact);
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }

            }
        });

        Droptable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Delete all the contacts and then clear the text fields.
                myDb.deleteAllContacts();
                Ename.getText().clear();
                Ephone.getText().clear();
                Eemail.getText().clear();
                Ecity.getText().clear();
                Estreet.getText().clear();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));

            }
        });

    }


    public boolean onOptionsItemSelected(MenuItem item) { // this navigates back to MainActivity from backArrow button in top left of toolBar.
        int id = item.getItemId();

        // i didn't define R.id.home  That id is created by default from android for the home button.
        if (id == android.R.id.home)
            NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class)); // this is how you go back up to the main class
        // you could also use .NavigateUpTask if the mainActivity is the parent activity
        //make the main Activity the Parent activity in the AndroidManifext.xml file

        return super.onOptionsItemSelected(item);
    }

    private void showContactData(Bundle extras)
    {
        int id = extras.getInt("position"); // I'm able to obtain this cause of the ItemClickFunction in MainActivity. I stored an Int variable(and called it "position"). Now i'm retrieving it.
        Contact tempContact = myDb.getContact(id); // Grab the Contact based on the position that was passed from listView in MainActivity

        if(tempContact != null) // check for null JUST TO BE SAFE!(myDb.getContact() returns null if it doesn't find a contact)
        {
            // Now let's update the TextFields....
            Ename.setText(tempContact.getName());
            Ephone.setText(tempContact.getPhone());
            Estreet.setText(tempContact.getStreet());
            Eemail.setText(tempContact.getEmail());
            Ecity.setText(tempContact.getCity());
        }
        else
        {
            //NOT SURE HOW THIS IS POSSIBLE BUT ERROR!
        }
    }
}
