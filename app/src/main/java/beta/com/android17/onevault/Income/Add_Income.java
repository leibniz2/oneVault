package beta.com.android17.onevault.Income;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import beta.com.android17.onevault.Database.DatabaseHandler;
import beta.com.android17.onevault.Database.Utility;
import beta.com.android17.onevault.MainActivity;
import beta.com.android17.onevault.Object.Transaction;
import beta.com.android17.onevault.R;
import beta.com.android17.onevault.Utility.RunTime;
import fr.ganfra.materialspinner.MaterialSpinner;
import mehdi.sakout.fancybuttons.FancyButton;


public class Add_Income extends AppCompatActivity {

    MaterialSpinner spin_status;
    MaterialSpinner spin_paymethod;
    MaterialSpinner spin_category;
    MaterialSpinner spin_tags;

    EditText amount;
    EditText date;
    EditText ref_no ;
    EditText tax;
    EditText payer;
    EditText desc ;
    EditText quantity;

    DatabaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_income);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar_bottom);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        RunTime.delayThemer(toolbar, toolbar1);

        spin_category = (MaterialSpinner) findViewById(R.id.spin_category);
        spin_category.setHighlightColor(RunTime.CURRENT_THEME);
        spin_paymethod = (MaterialSpinner) findViewById(R.id.spin_paymethod);
        spin_paymethod.setHighlightColor(RunTime.CURRENT_THEME);
        spin_status = ( MaterialSpinner) findViewById(R.id.spin_status);
        spin_status.setHighlightColor(Color.parseColor("#3F51B5"));
        spin_tags = (MaterialSpinner) findViewById(R.id.spin_tags);
        spin_tags.setHighlightColor(Color.parseColor("#3F51B5"));

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.paymethods, android.R.layout.simple_spinner_item);
        spin_paymethod.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.category_income, android.R.layout.simple_spinner_item);
        spin_category.setAdapter(adapter1);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.status, android.R.layout.simple_spinner_item);
        spin_status.setAdapter(adapter2);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.tags, android.R.layout.simple_spinner_item);
        spin_tags.setAdapter(adapter3);

        EditText field_date = (EditText) findViewById(R.id.field_date);
        field_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });


//        TODO: LINK: https://github.com/wasabeef/awesome-android-ui

        FancyButton save = (FancyButton) findViewById(R.id.btn_save);
        save.setText("              SAVE         ");
        save.setTextColor(Color.WHITE);
        save.setGravity(Gravity.CENTER);
        save.setTextSize(15);
        save.setBackgroundColor(RunTime.CURRENT_THEME);
        save.setFocusBackgroundColor(Color.parseColor("#303F9F"));

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveData();
            }
        });

        db = new DatabaseHandler(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_income, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       if( id == R.id.action_clear){
            clear();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void SaveData(){
        try {
            Utility utility = new Utility(this);

            amount = (EditText) findViewById(R.id.field_amount);
            date = (EditText) findViewById(R.id.field_date);
            ref_no = (EditText) findViewById(R.id.field_ref);
            tax = (EditText) findViewById(R.id.field_tax);
            payer = (EditText) findViewById(R.id.field_payee);
            desc = (EditText) findViewById(R.id.field_desc);
            quantity = (EditText) findViewById(R.id.field_quantity);
            Double amt = Double.valueOf(amount.getText().toString());
            if(amt<=0)
                amt = Double.valueOf("");
            String method = spin_paymethod.getSelectedItem().toString();
            String category = spin_category.getSelectedItem().toString();
            String status = spin_status.getSelectedItem().toString();
            int refOrCheck;
            try {
                refOrCheck = Integer.parseInt(ref_no.getText().toString());
            }catch (NumberFormatException e) {
                refOrCheck = 0;
            }
            String description = desc.getText().toString();
            Double tx;
            try {
                tx = Double.valueOf(tax.getText().toString());
            }catch (NumberFormatException e) {
                tx = 0.00;
            }
            int quant;
            try {
                quant = Integer.parseInt(quantity.getText().toString());
            }catch (NumberFormatException e) {
                quant = 0;
            }
            if(quant<=0)
                quant = Integer.valueOf("");
            String payerr = payer.getText().toString();
            String tags = spin_tags.getSelectedItem().toString();;
            String datee = date.getText().toString();

            java.util.Date javaUtilDate = new java.util.Date();
            java.sql.Date javaSqlDate = new java.sql.Date(javaUtilDate.getTime());
            SimpleDateFormat sdf1 = new SimpleDateFormat("EE dd-MM-yyyy");
            try {
                javaUtilDate = sdf1.parse(datee);
                javaSqlDate = new java.sql.Date(javaUtilDate.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Transaction newTransaction = new Transaction
                    ( amt, method,javaSqlDate, refOrCheck, description, tx, quant, payerr, tags,
                            category, status,null, RunTime.ACTIVE_ACCOUNT );

            db.addIncome(newTransaction);
            Toast.makeText(this, "Transaction Added!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));

        }
        catch (NumberFormatException e) {
            Toast.makeText(this, R.string.form_error, Toast.LENGTH_SHORT).show();
        }

    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");

    }

    public void setDate(String datePicker){
        EditText date = (EditText) findViewById(R.id.field_date);
        date.setText(datePicker);
    }

    public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        int year;
        int month;
        int day;

        @Override
        public Dialog onCreateDialog(Bundle savedInstance){
            //use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(Add_Income.this, this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String month = monthOfYear<10?"0"+monthOfYear:""+monthOfYear;
            String day = dayOfMonth<10?"0"+dayOfMonth:""+dayOfMonth;

            final Calendar c = new GregorianCalendar(year, Integer.parseInt(month), Integer.parseInt(day));
            SimpleDateFormat sdf1 = new SimpleDateFormat("EE dd-MM-yyyy");
            String date = sdf1.format(c.getTime());
            Add_Income.this.setDate(date);
        }
    }

    private void clear() {
        ref_no.setText("");
        tax.setText("");
        payer.setText("");
        desc.setText("");
        quantity.setText("");
        spin_paymethod.setSelection(0);
        spin_category.setSelection(0);
        spin_status.setSelection(0);
        spin_tags.setSelection(0);
    }
}
