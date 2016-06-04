package beta.com.android17.onevault.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import beta.com.android17.onevault.Menu.Income.OnListFragmentInteractionListener;
import beta.com.android17.onevault.R;
import beta.com.android17.onevault.Utility.RunTime;

/**
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private final String[] dates;
    private final String[] amount;
    private final String[] payer;
    private final String[] status;
    private final int[] id;


    private final OnListFragmentInteractionListener mListener;

    public TransactionAdapter(String[] dates, String[] amount, String[] payer,
                              String[] status, int[] id, OnListFragmentInteractionListener listener) {
        this.dates = dates;
        this.amount = amount;
        this.payer = payer;
        this.status = status;
        this.id = id;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_income_contents, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.dateView.setText(dates[position]);
        holder.amountView.setText(amount[position]);
        holder.payerView.setText(payer[position].equals("")?"":payer[position]);
        holder.statusView.setText(status[position].equals("Select Transaction Status")?"":status[position]);
        holder.id = id[position];

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
//                    mListener.IncomeonListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dates.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public int id;
        public final TextView dateView;
        public final TextView amountView;
        public final TextView payerView;
        public final TextView statusView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            id = 0;
            dateView = (TextView) view.findViewById(R.id.id);
            dateView.setHighlightColor(RunTime.CURRENT_THEME);
            amountView = (TextView) view.findViewById(R.id.content);
            payerView = (TextView) view.findViewById(R.id.payer);
            statusView = (TextView) view.findViewById(R.id.status);
            init();
        }

        public void init(){
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.height = 80;

            dateView.setLayoutParams(params);
            dateView.setClickable(true);
//            dateView.setFocusable(true);
//            dateView.setFocusableInTouchMode(true);

            amountView.setLayoutParams(params);
            amountView.setClickable(true);
//            amountView.setFocusable(true);
//            amountView.setFocusableInTouchMode(true);

            payerView.setLayoutParams(params);
            payerView.setClickable(true);
//            payerView.setFocusable(true);
//            payerView.setFocusableInTouchMode(true);

            statusView.setLayoutParams(params);
            statusView.setClickable(true);
//            statusView.setFocusable(true);
//            statusView.setFocusableInTouchMode(true);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + amountView.getText() + "'";
        }
    }
}
