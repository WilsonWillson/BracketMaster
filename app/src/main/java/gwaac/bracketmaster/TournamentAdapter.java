package gwaac.bracketmaster;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Charlie on 10/27/15.
 */
public class TournamentAdapter extends RecyclerView.Adapter<TournamentAdapter.TournamentViewHolder> {

    private List<Tournament> mTournamentData;

    public TournamentAdapter(List<Tournament> tournamentData) {
        mTournamentData = tournamentData;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public TournamentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tournament_card, parent, false);
        TournamentViewHolder tournamentViewHolder = new TournamentViewHolder(v);
        return tournamentViewHolder;
    }

    @Override
    public void onBindViewHolder(TournamentViewHolder holder, int position) {
        holder.tournamentName.setText(mTournamentData.get(position).getName());
//        holder.tournamentDetail.setText(mTournamentData.get(position).getDescription());
//        holder.tournamentOwner.setText(mTournamentData.get(position).getOwner());
        holder.tournamentImage.setImageBitmap(mTournamentData.get(position).getGameImage());
    }

    @Override
    public int getItemCount() {
        return mTournamentData.size();
    }

    public class TournamentViewHolder extends RecyclerView.ViewHolder {
        TextView tournamentName;
        TextView tournamentDetail;
        TextView tournamentOwner;
        ImageView tournamentImage;

        public TournamentViewHolder(View itemView) {
            super(itemView);
            tournamentName = (TextView)itemView.findViewById(R.id.tournament_name);
//            tournamentDetail = (TextView)itemView.findViewById(R.id.row_tournament_detail);
//            tournamentOwner = (TextView)itemView.findViewById(R.id.row_tournament_owner);
            tournamentImage = (ImageView)itemView.findViewById(R.id.tournament_game_image);
        }
    }
}
