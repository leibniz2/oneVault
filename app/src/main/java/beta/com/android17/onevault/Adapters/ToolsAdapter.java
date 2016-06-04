package beta.com.android17.onevault.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import beta.com.android17.onevault.Menu.Tools;
import beta.com.android17.onevault.R;

import java.util.List;

/**
 */
public class ToolsAdapter extends RecyclerView.Adapter<ToolsAdapter.ViewHolder> {

    private final Tools.ToolsOnListFragmentInteractionListener mListener;
    private final String[] tools;

    public ToolsAdapter(String[] tools, Tools.ToolsOnListFragmentInteractionListener listener) {
        this.tools = tools;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tools_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.mIdView.setText(tools[position]);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tools.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
