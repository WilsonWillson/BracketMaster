package gwaac.bracketmaster;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

/**
 * Created by Adrian on 11/14/2015.
 */

public class BracketAdapter extends RecyclerView.Adapter<BracketAdapter.ViewHolder> {

    private List<Match> mMatchList;
    private Context mContext;

    public BracketAdapter(Context context, List<Match> matchList) {
        mContext = context;
        mMatchList = matchList;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bracket_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Match match = mMatchList.get(position);

        holder.player1Button.setText(match.getPlayer1());
        holder.player2Button.setText(match.getPlayer2());
    }

    @Override
    public int getItemCount() {
        return mMatchList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button player1Button;
        Button player2Button;

        public ViewHolder(View itemView) {
            super(itemView);
            player1Button = (Button)itemView.findViewById(R.id.player1);
            player2Button = (Button)itemView.findViewById(R.id.player2);
        }
    }
}
