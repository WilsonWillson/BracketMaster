package gwaac.bracketmaster;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Adrian on 11/14/2015.
 */

public class BracketAdapter extends RecyclerView.Adapter<BracketAdapter.BracketViewHolder> {

    private List<Match> mMatchData;

    public BracketAdapter(List<Match> matchData) {
        mMatchData = matchData;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public BracketViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tournament_card, parent, false);
        return new BracketViewHolder(v);
    }

    @Override
    public void onBindViewHolder(BracketViewHolder holder, int position) {
        holder.player1.setText(mMatchData.get(position).getPlayer1());
        holder.player2.setText(mMatchData.get(position).getPlayer2());
    }

    @Override
    public int getItemCount() {
        return mMatchData.size();
    }

    public class BracketViewHolder extends RecyclerView.ViewHolder {
        TextView player1;
        TextView player2;

        public BracketViewHolder(View itemView) {
            super(itemView);
            player1 = (Button)itemView.findViewById(R.id.player1);
            player2 = (Button)itemView.findViewById(R.id.player2);
        }
    }
}
