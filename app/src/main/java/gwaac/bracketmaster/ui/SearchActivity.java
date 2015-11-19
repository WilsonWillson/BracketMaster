package gwaac.bracketmaster.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import gwaac.bracketmaster.R;

import butterknife.ButterKnife;
import butterknife.Bind;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = SearchActivity.class.getSimpleName();

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
                Intent intent = new Intent(view.getContext(), SearchResultActivity.class);
                switch ((String) mSearchTypeSpinner.getSelectedItem()) {
                    default:
                    case "Title":
                        intent.putExtra("searchType", SearchResultActivity.SEARCH_TYPE_TITLE);
                        break;
                    case "Owner":
                        intent.putExtra("searchType", SearchResultActivity.SEARCH_TYPE_OWNER);
                        break;
                    case "Description":
                        intent.putExtra("searchType", SearchResultActivity.SEARCH_TYPE_DESCRIPTION);
                        break;
                }
                intent.putExtra("searchString", mSearchStringTextView.getEditText().getText().toString());
                startActivity(intent);
            }
        });
    }

}
