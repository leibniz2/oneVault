package beta.com.android17.onevault.Menu;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

import java.util.ArrayList;

import beta.com.android17.onevault.Adapters.TransactionAdapter;
import beta.com.android17.onevault.Database.DatabaseHandler;
import beta.com.android17.onevault.Income.Add_Income;
import beta.com.android17.onevault.Income.Mod_Income;
import beta.com.android17.onevault.MainActivity;
import beta.com.android17.onevault.Object.Transaction;
import beta.com.android17.onevault.R;
import beta.com.android17.onevault.Utility.RunTime;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class Income extends Fragment implements ObservableScrollViewCallbacks {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */

    String[] amounts ;
    String[] dates ;
    String[] payer ;
    String[] status ;
    int[] id;

    FloatingActionMenu menu;

    boolean isEmpty = true;

    public Income() {
    }

    @SuppressWarnings("unused")
    public static Income newInstance(int columnCount) {
        Income fragment = new Income();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }


        DatabaseHandler db = new DatabaseHandler(getActivity());
        ArrayList<Transaction> transactions;
        transactions = db.getAllIncomeTransactions();
        System.out.println("Transaction Size: " + transactions.size());


        if(!transactions.isEmpty()){
            dates= new String[transactions.size()];
            amounts = new String[transactions.size()];
            payer = new String[transactions.size()];
            status = new String[transactions.size()];
            id = new int[transactions.size()];
            for (int i = 0; i < transactions.size() ; i++) {
                dates[i] = ""+transactions.get(i).getKEY_DATE();
                amounts[i] = String.format("%.2f" , transactions.get(i).getKEY_AMOUNT());
                payer[i] = transactions.get(i).getKEY_PAYER();
                status[i] = transactions.get(i).getKEY_STATUS();
                id[i] = transactions.get(i).getKEY_ID();
            }
            isEmpty = false;
        }
        else{
            isEmpty = true;
            dates= new String[1];
            amounts = new String[1];
            payer = new String[1];
            status = new String[1];
            id = new int[1];
            dates[0] = "No transaction at the moment";
            amounts[0] = "";
            payer[0] = "";
            status[0] = "";
            id[0] = 0;
        }

        RunTime.ThemeChooser();

        getActivity().setTitle("Income");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income, container, false);

        ObservableRecyclerView list_view = (ObservableRecyclerView) view.findViewById(R.id.list);
        list_view.setScrollViewCallbacks(this);
        list_view.addItemDecoration(new RunTime.SimpleDividerItemDecoration(getActivity().getApplicationContext()));
        list_view.setAdapter(new TransactionAdapter(dates, amounts, payer, status, id, mListener));
        list_view.addOnItemTouchListener(new Overview.RecyclerItemClickListener(getActivity().getApplicationContext(),
                new Overview.RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getActivity(), Mod_Income.class);
                        intent.putExtra("id" , id[position]);
                        startActivity(intent);
                    }
                }));


//        ((MainActivity)getActivity()).Hide(0, false);

        menu = (FloatingActionMenu) getActivity().findViewById(R.id.menu);
        menu.setAnimationDelayPerItem(125); // in ms

        com.github.clans.fab.FloatingActionButton repeating =
                (com.github.clans.fab.FloatingActionButton) getActivity().findViewById(R.id.menu_expense);
        repeating.setLabelText("Repeating");

        com.github.clans.fab.FloatingActionButton oneTime =
                (com.github.clans.fab.FloatingActionButton) getActivity().findViewById(R.id.menu_income);
        oneTime.setLabelText("One-Time");

        repeating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Redirect to Recurrence Income!", Toast.LENGTH_SHORT).show();
            }
        });

        oneTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity() , Add_Income.class));
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ToolsOnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        ActionBar ab = getActivity().getActionBar();
        if (scrollState == ScrollState.UP) {
            if (ab.isShowing()) {
                ab.hide();
            }
        } else if (scrollState == ScrollState.DOWN) {
            if (!ab.isShowing()) {
                ab.show();
            }
        }

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void IncomeonListFragmentInteraction();
    }
}
