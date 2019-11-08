package com.ghavinj.mygrocerylistapp.Activities.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.ghavinj.mygrocerylistapp.Activities.Data.DatabaseHandler;
import com.ghavinj.mygrocerylistapp.Activities.Model.Grocery;
import com.ghavinj.mygrocerylistapp.R;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.List;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView itemName;
    private TextView quantity;
    private TextView dateAdded;
    private Button editButton;
    private Button deleteButton;
    private Context context;
    private List<Grocery> groceryItems;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;
    private LayoutInflater inflater;
    private int groceryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        itemName = (TextView)findViewById(R.id.itemNameDet);
        quantity = (TextView)findViewById(R.id.quantityDet);
        dateAdded = (TextView)findViewById(R.id.dateAddedDet);

        editButton = (Button)findViewById(R.id.editButtonDet);
        deleteButton = (Button)findViewById(R.id.deleteButtonDet);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            itemName.setText(bundle.getString("name"));
            quantity.setText(bundle.getString("quantity"));
            dateAdded.setText(bundle.getString("date"));
            groceryId  = bundle.getInt("id");
        }

        editButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
           case R.id.editButton:
                Toast.makeText(context,"you clicked me", Toast.LENGTH_LONG).show();
               //int position = this.groceryId;
               //Grocery grocery = groceryItems.get(groceryId);
               //editItem(grocery);

               break;
            case R.id.deleteButton:
               // position = this.groceryId;
               // grocery = groceryItems.get(position);
               // deleteItem(grocery.getId());

                break;



        }

    }

    public void deleteItem(final int id){
        //create an Alert Dialog
        alertDialogBuilder = new AlertDialog.Builder(context);

        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.confirmation_dialog, null);

        Button noButton = (Button)view.findViewById(R.id.noButton);
        Button yesButton = (Button)view.findViewById(R.id.yesButton);

        alertDialogBuilder.setView(view);
        dialog = alertDialogBuilder.create();
        dialog.show();

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete the item.
                DatabaseHandler db = new DatabaseHandler(context);
                //delete item
                db.deleteGrocery(id);
                groceryItems.remove(this);


                dialog.dismiss();

            }
        });



    }

    public void editItem(final Grocery grocery){

        //create an Alert Dialog
        alertDialogBuilder = new AlertDialog.Builder(context);

        inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.popup, null);

        final EditText groceryItem = (EditText)view.findViewById(R.id.groceryItem);
        final EditText quantity = (EditText) view.findViewById(R.id.groceryQty);
        final TextView text = (TextView) view.findViewById(R.id.tile);
        Button saveButton = (Button)view.findViewById(R.id.saveButton);

        text.setText("Edit Grocery Item");

        alertDialogBuilder.setView(view);
        dialog = alertDialogBuilder.create();
        dialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler db = new DatabaseHandler(context);

                //update item.
                grocery.setName(groceryItem.getText().toString());
                grocery.setQuantity(quantity.getText().toString());

                if (!groceryItem.getText().toString().isEmpty() && !quantity.getText().toString().isEmpty()){
                    db.updateGrocery(grocery);
                    dialog.dismiss();


                }else{
                    Snackbar.make(view, "Add Grocery and Quantity", Snackbar.LENGTH_LONG).show();
                }
            }
        });


    }
}
