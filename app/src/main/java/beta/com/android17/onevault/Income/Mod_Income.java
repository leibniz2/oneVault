package beta.com.android17.onevault.Income;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import beta.com.android17.onevault.Database.DatabaseHandler;
import beta.com.android17.onevault.Database.Utility;
import beta.com.android17.onevault.MainActivity;
import beta.com.android17.onevault.Object.Account;
import beta.com.android17.onevault.Object.Transaction;
import beta.com.android17.onevault.R;
import beta.com.android17.onevault.Utility.RunTime;
import fr.ganfra.materialspinner.MaterialSpinner;
import mehdi.sakout.fancybuttons.FancyButton;

public class Mod_Income extends AppCompatActivity {

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

    Transaction transaction;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mod_income);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar_bottom);
        setSupportActionBar(toolbar);

//        RunTime.Themer(toolbar, toolbar1);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spin_category = (MaterialSpinner) findViewById(R.id.spin_category);
        spin_category.setHighlightColor(Color.parseColor("#3F51B5" ));
        spin_paymethod = (MaterialSpinner) findViewById(R.id.spin_paymethod);
        spin_paymethod.setHighlightColor(Color.parseColor("#3F51B5"));
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

        date = (EditText) findViewById(R.id.field_date);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        FancyButton save = (FancyButton) findViewById(R.id.btn_save);
        save.setText("             UPDATE         " );
        save.setTextColor(Color.WHITE);
        save.setGravity(Gravity.CENTER);
        save.setTextSize(15);
        save.setBackgroundColor(Color.parseColor("#3F51B5"));
        save.setFocusBackgroundColor(Color.parseColor("#303F9F"));

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateData();
            }
        });

        db = new DatabaseHandler(this);

        transaction = db.getIncomeTransaction(getIntent().getIntExtra("id", 0));

        fillFromDB();
    }

    public void fillFromDB(){
        amount = (EditText) findViewById(R.id.field_amount);
        ref_no = (EditText) findViewById(R.id.field_ref);
        tax = (EditText) findViewById(R.id.field_tax);
        payer = (EditText) findViewById(R.id.field_payee);
        desc = (EditText) findViewById(R.id.field_desc);
        quantity = (EditText) findViewById(R.id.field_quantity);

        amount.setText(String.format("%1$,.2f" , transaction.getKEY_AMOUNT()));

        SimpleDateFormat sdf1 = new SimpleDateFormat("EE dd-MM-yyyy");
        String datee = sdf1.format(transaction.getKEY_DATE());
        date.setText(datee);

        ref_no.setText(String.format("%d", transaction.getKEY_REF_CHECK()));
        tax.setText(String.format("%1$,.2f" , transaction.getKEY_TAX()));
        payer.setText(transaction.getKEY_PAYER());
        desc.setText(transaction.getKEY_DESCRIPTION());
        quantity.setText(String.format("%d", transaction.getKEY_QUANTITY()));
        spin_paymethod.setSelection(getSelectedIndex(spin_paymethod, transaction.getKEY_PAYMETHOD()));
        spin_category.setSelection(getSelectedIndex(spin_category, transaction.getKEY_CATEGORY()));
        spin_status.setSelection(getSelectedIndex(spin_status, transaction.getKEY_STATUS()));
        spin_tags.setSelection(getSelectedIndex(spin_tags , transaction.getKEY_TAGS()));


    }

    private int getSelectedIndex(MaterialSpinner payment, String key_paymethod) {
        for (int i = 0; i < payment.getCount()-1 ; i++) {
            if(payment.getItemAtPosition(i).equals(key_paymethod))
                return i;
        }
        return 0;
    }

    public void UpdateData(){
        try {
            String amtString = amount.getText().toString().replaceAll("[^\\d.]+", "");
            String taxtString = tax.getText().toString().replaceAll("[^\\d.]+", "");
            String quantString = quantity.getText().toString().replaceAll("[^\\d.]+", "");
            Double amt = Double.valueOf(amtString);
            if(amt<=0)
                amt = Double.valueOf("");
            Double tx;
            try {
                tx = Double.valueOf(taxtString);
            }catch (NumberFormatException e) {
                tx = 0.00;
            }
            int quant;
            try {
                quant = Integer.parseInt(quantString);
            }catch (NumberFormatException e) {
                quant = 0;
            }
            if(quant<=0)
                quant = Integer.valueOf("");
            int refOrCheck;
            try {
                refOrCheck = Integer.parseInt(ref_no.getText().toString());
            }catch (NumberFormatException e) {
                refOrCheck = 0;
            }
            String description = desc.getText().toString();
            String payerr = payer.getText().toString();
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

            String category = spin_category.getSelectedItem().toString();
            String status = spin_status.getSelectedItem().toString();
            String method = spin_paymethod.getSelectedItem().toString();
            String tags = spin_tags.getSelectedItem().toString();
            Transaction newTransaction = new Transaction
                    ( amt, method,javaSqlDate, refOrCheck, description, tx, quant, payerr, tags, category, status,null, transaction.getFKEY_ID());

            db = new DatabaseHandler(this);
            db.updateIncomeTransaction(newTransaction, transaction.getKEY_ID());
            redirect();
        }
        catch(NumberFormatException e){
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }

        Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(this, MainActivity.class));
    }


    private void transferFunds(int keyIDOfAccountToBeTransferedTo) {

        int i = db.transferTransaction(0,transaction,keyIDOfAccountToBeTransferedTo);
        if(i==1){
            startActivity(new Intent(this, MainActivity.class));
            Toast.makeText(this, "Transfer successfull!", Toast.LENGTH_SHORT).show();
        }
        else if(i==0) {
            Toast.makeText(this, "Transfer successful!", Toast.LENGTH_SHORT).show();
        }
    }

    public void chooser(){
        final Utility utility = new Utility(this);
        final ArrayList<Account> listOfAccounts = db.getAllAccount();
        final ArrayList<String> listOfAccountName = new ArrayList<>();
        for(Account a:listOfAccounts){
            listOfAccountName.add(a.getKEY_NAME());
        }
        final CharSequence[] charSequenceItems = listOfAccountName.toArray(new CharSequence[listOfAccountName.size()]);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose account")
                .setItems(charSequenceItems, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        if (listOfAccounts.get(which).getKEY_ID() == RunTime.ACTIVE_ACCOUNT) {
                            Toast.makeText(Mod_Income.this, "Transaction already in account", Toast.LENGTH_SHORT).show();
                        } else
                            transferFunds(listOfAccounts.get(which).getKEY_ID());
                    }
                });


        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void redirect() {
            startActivity(new Intent(this, MainActivity.class));
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

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String month = monthOfYear<10?"0"+monthOfYear:""+monthOfYear;
            String day = dayOfMonth<10?"0"+dayOfMonth:""+dayOfMonth;

            final Calendar c = new GregorianCalendar(year, monthOfYear, dayOfMonth);
            SimpleDateFormat sdf1 = new SimpleDateFormat("EE dd-MM-yyyy");
            String date = sdf1.format(c.getTime());
            Mod_Income.this.setDate(date);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mod_income, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_transfer){
            chooser(); //TODO: NOT YET DONE
        }

        else if(id == R.id.action_delete){
            delete();
        }

        else if(id == R.id.action_clear){
            clear();
        }

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
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

    private void delete() {
            db.deleteIncomeTransaction(transaction.getKEY_ID());
            Toast.makeText(this, "Transaction Deleted!", Toast.LENGTH_SHORT).show();
            redirect();
    }

}
