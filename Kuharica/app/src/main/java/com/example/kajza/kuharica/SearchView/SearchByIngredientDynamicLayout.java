package com.example.kajza.kuharica.SearchView;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.kajza.kuharica.R;

import java.util.ArrayList;
import java.util.List;


public class SearchByIngredientDynamicLayout extends SearchByIngredient {




/*
    public static void search(final Activity activity, Button btn)


    {
        btn.setOnClickListener(new View.OnClickListener() {

            String searchQuery1, searchQuery2, searchQuery3;



            @Override
            public void onClick(View v) {
                // fetch layout surrounding editTexts
                LinearLayout scrollViewlinerLayout = (LinearLayout) activity.findViewById(R.id.linearLayoutForm);
                // prepare list to collect ingredients from existing editTexts
                List<String> searchQueries = new ArrayList<String>();
                // go through each scrollViewLinearLayout child to fetch existing EditTexts
                for (int i = 0; i < scrollViewlinerLayout.getChildCount(); i++) {
                    LinearLayout editTextContainer = (LinearLayout) scrollViewlinerLayout.getChildAt(i);
                    EditText edit = (EditText) editTextContainer.findViewById(R.id.editRow);
                    // fetch entered value from EditText field and save it to the ingredients
                    // TODO add check to see if there is any input - add to list only if there is text in the EditText
                    searchQueries.add(edit.getText().toString());
                }

                // initiate call to server
                DohvatPoSastojku dohvat = new SearchByIngredient().new DohvatPoSastojku(searchQueries);
                dohvat.execute();
                new DohvatPoSastojku(searchQueries);
            }

        });
    }

    */

    public  static void add(final Activity activity, final ImageButton btn)
    {
        final LinearLayout linearLayoutForm = (LinearLayout) activity.findViewById(R.id.linearLayoutForm);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(linearLayoutForm.getChildCount() == 2){
                    btn.setEnabled(false);
                }

                for (int i = 0; i < linearLayoutForm.getChildCount(); i++) {
                    LinearLayout editTextContainer = (LinearLayout) linearLayoutForm.getChildAt(i);
                    AutoCompleteTextView edit = (AutoCompleteTextView) editTextContainer.findViewById(R.id.editRow);
                    edit.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {


                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                }
                final LinearLayout newView = (LinearLayout)activity.getLayoutInflater().inflate(R.layout.test_row, null);
                newView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                ImageButton btnRemove = (ImageButton) newView.findViewById(R.id.btnRemove);
                btnRemove.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        linearLayoutForm.removeView(newView);
                        btn.setEnabled(true);
                    }
                });
                linearLayoutForm.addView(newView);

            }
        });
    }
}

