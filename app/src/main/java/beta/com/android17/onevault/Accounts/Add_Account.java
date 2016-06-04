package beta.com.android17.onevault.Accounts;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import beta.com.android17.onevault.Database.DatabaseHandler;
import beta.com.android17.onevault.MainActivity;
import beta.com.android17.onevault.Object.Account;
import beta.com.android17.onevault.R;
import fr.ganfra.materialspinner.MaterialSpinner;
import mehdi.sakout.fancybuttons.FancyButton;

public class Add_Account extends AppCompatActivity {

    DatabaseHandler db ;

    EditText name ;
    EditText desc;
    EditText init;
    EditText limit;
    MaterialSpinner currency ;
    RadioButton hasCC ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currency = (MaterialSpinner) findViewById(R.id.spin_currency);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_list, android.R.layout.simple_spinner_item);
        currency.setAdapter(adapter);


        FancyButton save = (FancyButton) findViewById(R.id.btn_save);
        save.setText("             ADD           " );
        save.setTextColor(Color.WHITE);
        save.setGravity(Gravity.CENTER);
        save.setTextSize(15);
        save.setBackgroundColor(Color.parseColor("#3F51B5"));
        save.setFocusBackgroundColor(Color.parseColor("#303F9F"));
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save();
            }
        });

        db = new DatabaseHandler(this);
        name = (EditText) findViewById(R.id.acc_name);
        desc = (EditText) findViewById(R.id.acc_dec);
        init = (EditText) findViewById(R.id.acc_init);
        limit = (EditText) findViewById(R.id.acc_limit);
        hasCC = (RadioButton) findViewById(R.id.acc_hascc);
    }

    private void Save() {
        String namee = name.getText().toString();
        String descc = desc.getText().toString();
        double balance = Double.parseDouble(init.getText().toString());
        String currencyy = currency.getSelectedItem().toString();
        double limitt = 0;
        if(hasCC.isChecked()) {
            limitt = Double.parseDouble(limit.getText().toString()); //TODO: CHECK THIS
        }

        db.addAccount(new Account(namee, descc, currencyy, balance, hasCC.isChecked(), limitt, null, null));

        Toast.makeText(this, "Account Added!", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(this, MainActivity.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_clear){
            clear();
        }

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    private void clear() {
        name.setText("");
        desc.setText("");
        init.setText("");
        limit.setText("");
        hasCC.setSelected(false);
    }

}
