package gwaac.bracketmaster.ui;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import gwaac.bracketmaster.R;

import butterknife.ButterKnife;
import butterknife.Bind;

public class SearchActivity extends AppCompatActivity {

    @Bind(R.id.search_type_spinner) Spinner mSearchTypeSpinner;
    @Bind(R.id.search_text_layout) TextInputLayout mSearchStringTextView;
    @Bind(R.id.search_button) Button mSearchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.search_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSearchTypeSpinner.setAdapter(adapter);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * collect search type and search string and send in intent to SearchResultActivity
                 */
            }
        });
    }

}
