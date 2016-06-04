package beta.com.android17.onevault;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;

import beta.com.android17.onevault.Accounts.Add_Account;
import beta.com.android17.onevault.Accounts.Profile;
import beta.com.android17.onevault.Database.DatabaseHandler;
import beta.com.android17.onevault.FileChooser.FileChooserDialog;
import beta.com.android17.onevault.Income.Add_Income;
import beta.com.android17.onevault.Menu.Income;
import beta.com.android17.onevault.Menu.Legal;
import beta.com.android17.onevault.Menu.Overview;
import beta.com.android17.onevault.Menu.Tools;
import beta.com.android17.onevault.Object.Account;
import beta.com.android17.onevault.Sync.Converter;
import beta.com.android17.onevault.Sync.GoogleDrive;
import beta.com.android17.onevault.Utility.RunTime;

/*
    NOTES:
        (1) NOT GOOD TO PASS CONTEXT TO NON-ACTIVITY CLASS = MEMORY LEAKS
        TODO: FAB IN MAIN HAS INCOME/EXPENSE -> ACTIVITY INCOME/EXPENSE , FAB HERE CONTAINS ONE-TIME OR REPEATING
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    Drawer result;
    ArrayList<Account> list;

    FloatingActionMenu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        menu = (FloatingActionMenu) findViewById(R.id.menu);
        menu.setAnimationDelayPerItem(125); // in ms

        com.github.clans.fab.FloatingActionButton income =
                (com.github.clans.fab.FloatingActionButton) findViewById(R.id.menu_income);
        com.github.clans.fab.FloatingActionButton expense =
                (com.github.clans.fab.FloatingActionButton) findViewById(R.id.menu_expense);

        income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Income");
                menu.close(true);
                startActivity(new Intent(MainActivity.this, Add_Income.class));
            }
        });


        expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Expense");
                menu.close(true);
                FileChooserDialog dialog = new FileChooserDialog(MainActivity.this);
                dialog.show();
            }
        });

        Account account = new DatabaseHandler(this).getActive_Account();
        list = new DatabaseHandler(this).getAllAccount(); //chage to list.remove
        System.out.println("Account count: " + list.size());

        ProfileDrawerItem[] profiles = new ProfileDrawerItem[list.size()];

        int i;
        for (i = 0; i < list.size(); i++) {
            if (list.get(i).getKEY_NAME().equals(account.getKEY_NAME())) {
                RunTime.ACTIVE_ACCOUNT = account.getKEY_ID();
                profiles[0] = new ProfileDrawerItem()
                        .withEmail(account.getKEY_NAME())
                        .withName("")
                        .withIcon(R.mipmap.default_dp);
                break;
            }
        }

        list.remove(i);

            final AccountHeader headerResult = new AccountHeaderBuilder().withActivity(this)
                    .withTextColor(Color.BLACK)
                    .withCompactStyle(true)
//                .withHeaderBackground(R.drawable.header)
                    .addProfiles(profiles[0])
                    .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                        @Override
                        public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                            //TODO: QUERY ACCOUNT BY ACCOUNT_NAME AND SET ACTIVE
                            //NOTE: QUERY USING ACCOUNT_NAME (getEmail)

                            if (profile.getName().toString().equals("Add Account")) {
                                startActivity(new Intent(MainActivity.this, Add_Account.class));
                            } else if (profile.getName().toString().equals("Delete Account")) {
//                                startActivity(new Intent(MainActivity.this, Profile.class));
                            }
                            else{
                                startActivity(new Intent(MainActivity.this, Profile.class));
                            }
                            //TODO: CLICK ACCOUNT
                            return true;
                        }
                    })
                    .build();


        for (i = 1; i <= list.size(); i++) {
            headerResult.addProfiles(
                    new ProfileDrawerItem()
                            .withEmail(list.get(i - 1).getKEY_NAME()).withIdentifier(i)
                            .withName("")
                            .withIcon(R.mipmap.default_dp)
                            .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                @Override
                                public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                    startActivity(new Intent(MainActivity.this, Profile.class));
                                    return true;
                                }
                            })
            );

        }

        headerResult.addProfiles(
                    new ProfileSettingDrawerItem().withIdentifier(69)
                            .withName("Add Account")
                            .withEmail("")
                            .withIcon(R.mipmap.icon_add)
        );

        headerResult.addProfiles(
                    new ProfileSettingDrawerItem().withIdentifier(88)
                            .withName("Delete Account")
                            .withEmail("")
                            .withIcon(R.mipmap.icon_recurr)
            );


            result = new DrawerBuilder().withActivity(this)
                    .withToolbar(toolbar)
                    .withDisplayBelowStatusBar(true)
                    .withActionBarDrawerToggleAnimated(true)
                    .withAccountHeader(headerResult)
                    .addDrawerItems(
                            new PrimaryDrawerItem().withIdentifier(0)
                                    .withName(R.string.drawer_item_summary)
                                    .withIcon(R.mipmap.icon_summary),
                            new PrimaryDrawerItem()
                                    .withName(R.string.drawer_item_income).withIdentifier(1)
                                    .withIcon(R.mipmap.icon_income),
                            new PrimaryDrawerItem()
                                    .withName(R.string.drawer_item_expense).withIdentifier(2)
                                    .withIcon(R.mipmap.icon_expense),
                            new PrimaryDrawerItem()
                                    .withName(R.string.drawer_item_recurrence).withIdentifier(3)
                                    .withIcon(R.mipmap.icon_recurr),
                            new PrimaryDrawerItem()
                                    .withName(R.string.drawer_item_tools).withIdentifier(4)
                                    .withIcon(R.mipmap.icon_tools),
                            new DividerDrawerItem(),
                            new PrimaryDrawerItem()
                                    .withName(R.string.drawer_item_ie).withIdentifier(5)
                                    .withIcon(R.mipmap.icon_ie),
                            new PrimaryDrawerItem()
                                    .withName(R.string.drawer_item_sync).withIdentifier(6)
                                    .withIcon(R.mipmap.icon_sync),
                            new DividerDrawerItem(),
                            new PrimaryDrawerItem()
                                    .withName(R.string.drawer_item_legal).withIdentifier(7),
                            new PrimaryDrawerItem()
                                    .withName(R.string.drawer_item_about).withIdentifier(8)
                    )
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                            Fragment fragment = new Overview();

                            switch (drawerItem.getIdentifier()) {
                                case 0: // summary
                                    System.out.println("Pressed menu " + (drawerItem.getIdentifier() + 1));
                                    fragment = new Overview();
                                    break;
                                case 1: // income
                                    System.out.println("Pressed menu " + (drawerItem.getIdentifier() + 1));
                                    fragment = new Income();
                                    break;
                                case 2: // expense
                                    System.out.println("Pressed menu " + (drawerItem.getIdentifier() + 1));
                                    break;
                                case 3: // recurrence
                                    System.out.println("Pressed menu RECURRENCE " + (drawerItem.getIdentifier() + 1));
                                    break;
                                case 4: // tools
                                    System.out.println("Pressed menu TOOLS " + (drawerItem.getIdentifier() + 1));
                                    fragment = new Tools();
                                    break;
                                case 5: // ie
                                    System.out.println("Pressed menu " + (drawerItem.getIdentifier() + 1));
                                    //TODO: IMPORT/EXPORT
                                    Converter converter = new Converter(MainActivity.this);
                                    if (!converter.isExternalStorageWritable()) {
                                        Toast.makeText(MainActivity.this, "No external storage found! Internal not yet supported",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        converter.convertToJSON_External(list);
                                    }
                                    break;
                                case 6: // sync
                                    System.out.println("Pressed menu " + (drawerItem.getIdentifier() + 1));
                                    startActivity(new Intent(MainActivity.this, GoogleDrive.class));
                                    break;
                                case 7:
                                    fragment = new Legal();
                                    break;
                                default:
                                    System.out.println("Weird: " + drawerItem.getIdentifier());
                            }

                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.content_main, fragment).commit();
                            result.closeDrawer();

                            return true;
                        }
                    })
                    .withOnDrawerListener(new Drawer.OnDrawerListener() {
                        @Override
                        public void onDrawerOpened(View drawerView) {
                            if (menu.isOpened()) {
                                menu.close(true);
                            }
                        }

                        @Override
                        public void onDrawerClosed(View drawerView) {
                            if (menu.isOpened()) {
                                menu.close(true);
                            }
                        }

                        @Override
                        public void onDrawerSlide(View drawerView, float slideOffset) {
                            if (menu.isOpened()) {
                                menu.close(true);
                            }
                        }
                    })
                    .build();


            result.setSelection(0);

            RunTime.ThemeChooser();

            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    toolbar.setBackgroundColor(RunTime.THEME_COLOR);
                    menu.setMenuButtonColorNormal(RunTime.THEME_COLOR);
                }
            }, 1500);

        }

    @Override
    public void onStart(){
        super.onStart();
    }


    @Override
    public void onBackPressed() {
////        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
        if(result.isDrawerOpen()) result.closeDrawer();
        else super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(menu.isOpened()){
            menu.close(true);
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        return true;
    }


    public void Hide(int time , final boolean animate){
        menu.postDelayed(new Runnable() {
            @Override
            public void run() {
                menu.hideMenu(animate);
            }
        } , time);

    }

    public void Show(int time , final boolean animate){
        menu.postDelayed(new Runnable() {
            @Override
            public void run() {
                menu.showMenu(animate);
            }
        } , time);

    }

}
