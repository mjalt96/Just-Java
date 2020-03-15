package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    //Global variables
    int order = 0;
    int quantity=2;
    int price = 0;
    boolean hasWhippedCream = false;
    boolean hasChocolate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment(View view) {
        if (quantity < 50) {
            displayQuantity(++quantity);
            //Calculate and display price
            price = calculatePrice();
            displayPrice(price);
        }
        else {
            //Create and display Toast Message
            CharSequence message = "You can't order more than 50 coffees.";
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(getApplicationContext(), message, duration).show();
        }
    }

    public void decrement(View view) {
        if (quantity > 1) {
            displayQuantity(--quantity);
            //Calculate and display price
            price = calculatePrice();
            displayPrice(price);
        }
        else {
            //Create and display Toast Message
            CharSequence message = "You can't order less than 1 coffee.";
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(getApplicationContext(), message, duration).show();
        }
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        order+=1;
        //Check for checked toppings
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_check_box);
        hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_check_box);
        hasChocolate = chocolateCheckBox.isChecked();
        //Get name
        EditText nameView = (EditText) findViewById(R.id.name_field);
        String name = nameView.getText().toString();

        //Calculate and display price
        price = calculatePrice();
        displayPrice(price);

        //Create summary and e-mail using an intent
        String subject = "Just-Java Order n" + order + " for " + name;
        Intent composeEmail = new Intent(Intent.ACTION_SENDTO);
        composeEmail.setData(Uri.parse("mailto:"));
        composeEmail.putExtra(Intent.EXTRA_SUBJECT, subject);
        composeEmail.putExtra(Intent.EXTRA_TEXT, createOrderSummary(name));
        if (composeEmail.resolveActivity(getPackageManager()) != null) {
            startActivity(composeEmail);
        }
    }

    /**
     * Create summary for the order
     * @return
     */
    private String createOrderSummary(String name) {
        String message = "Name: " + name + ("\nAdd whipped cream? " + hasWhippedCream)
                + ("\nAdd chocolate? " + hasChocolate) + ("\nQuantity: " + quantity)
                + ("\nTotal: $" + price) + "\nThank you!";
        return message;
    }

    /**
     * This method calculates the price of the order
     * @return
     */
    public int calculatePrice(){
        price = 5;

        if(hasWhippedCream) {
            price += 1;
        }

        if(hasChocolate) {
            price += 2;
        }

        return (price * quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    public void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayPrice(int price) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(price + " $");
    }
}
