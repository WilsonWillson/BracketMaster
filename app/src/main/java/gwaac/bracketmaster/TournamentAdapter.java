package gwaac.bracketmaster;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

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
        return new TournamentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TournamentViewHolder holder, int position) {
        holder.tournamentName.setText(mTournamentData.get(position).getName());
        holder.tournamentDetail.setText(mTournamentData.get(position).getDescription());
        holder.tournamentDateTimeStart.setText(CalendarHelper.getPrettyDateTime(mTournamentData.get(position).getStartDateTime()));
        holder.tournamentDateTimeEnd.setText(CalendarHelper.getPrettyDateTime(mTournamentData.get(position).getEndDateTime()));
        //holder.tournamentImage.setImageBitmap(mTournamentData.get(position).getGameImage());
    }

    @Override
    public int getItemCount() {
        return mTournamentData.size();
    }

    public class TournamentViewHolder extends RecyclerView.ViewHolder {
<<<<<<< HEAD
        TextView tournamentName;
        TextView tournamentDetail;
        TextView tournamentDateTimeStart;
        TextView tournamentDateTimeEnd;
        ImageView tournamentImage;
        Button tournamentView;

        public TournamentViewHolder(View itemView) {
            super(itemView);
            tournamentName = (TextView)itemView.findViewById(R.id.tournament_name);
            tournamentDetail = (TextView)itemView.findViewById(R.id.tournament_description);
            tournamentDateTimeStart = (TextView)itemView.findViewById(R.id.tournament_datetime_start);
            tournamentDateTimeEnd = (TextView)itemView.findViewById(R.id.tournament_datetime_end);
            tournamentImage = (ImageView)itemView.findViewById(R.id.tournament_game_image);
            tournamentView = (Button)itemView.findViewById(R.id.tournament_view);
=======
        @Bind(R.id.tournament_name) TextView tournamentName;
        @Bind(R.id.tournament_description) TextView tournamentDetail;
        @Bind(R.id.tournament_datetime_start) TextView tournamentDateTimeStart;
        @Bind(R.id.tournament_datetime_end) TextView tournamentDateTimeEnd;
        @Bind(R.id.tournament_game_image) ImageView tournamentImage;

        public TournamentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
>>>>>>> origin/master
        }
    }
    public void viewBracket() {
        segue(BracketActivity.class);
        // Do something in response to button
    }

    private void segue(Class<?> cls) {
        //Intent intent = new Intent(this, cls);
       // startActivity(intent);
    }
}
