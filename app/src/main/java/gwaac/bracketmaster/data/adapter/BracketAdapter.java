package gwaac.bracketmaster.data.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.client.Firebase;

import java.util.List;

import gwaac.bracketmaster.R;
import gwaac.bracketmaster.data.model.Match;
import gwaac.bracketmaster.ui.BracketActivity;

/**
 * Created by Adrian on 11/14/2015.
 */

public class BracketAdapter extends RecyclerView.Adapter<BracketAdapter.ViewHolder> {

    private List<Match> mMatchList;
    private Context mContext;
    private boolean mIsOwner;

    public BracketAdapter(Context context, List<Match> matchList, boolean isOwner) {
        mContext = context;
        mMatchList = matchList;
        mIsOwner = isOwner;
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Match match = mMatchList.get(position);

        holder.player1Button.setText(match.getPlayer1());
        holder.player2Button.setText(match.getPlayer2());
        if (mIsOwner) {
            holder.player1Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(view.getContext())
                            .setTitle("Set Winner?")
                            .setMessage("Set " + match.getPlayer1() + " as the winner of the match?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    holder.player2Button.setTextColor(mContext.getResources().getColor(R.color.colorGrey));
                                    holder.player1Button.setClickable(false);
                                    holder.player2Button.setClickable(false);
                                    ((BracketActivity)mContext).handleMatchWinner(match.getPlayer1());
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).show();
                }
            });
            holder.player2Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(view.getContext())
                            .setTitle("Set Winner?")
                            .setMessage("Set " + match.getPlayer2() + " as the winner of the match?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    holder.player1Button.setTextColor(mContext.getResources().getColor(R.color.colorGrey));
                                    holder.player1Button.setClickable(false);
                                    holder.player2Button.setClickable(false);
                                    ((BracketActivity)mContext).handleMatchWinner(match.getPlayer2());
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mMatchList.size();
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        holder.player1Button.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        holder.player2Button.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button player1Button;
        Button player2Button;

        public ViewHolder(View itemView) {
            super(itemView);
            player1Button = (Button) itemView.findViewById(R.id.player1);
            player2Button = (Button) itemView.findViewById(R.id.player2);
        }
    }
}
