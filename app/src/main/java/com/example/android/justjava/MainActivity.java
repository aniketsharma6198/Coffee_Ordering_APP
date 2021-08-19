package com.example.android.justjava;

import android.content.ActivityNotFoundException;
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

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        //stores the name of the customer.
        EditText customer_name = (EditText) findViewById(R.id.name_text_field);
        String customer_Name = customer_name.getText().toString();

        //checks whether user wants chocolate or not.
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkBox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        //checks whether user wants whipped cream or not.
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.cream_checkBox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        // stores the total price of the order.
        int price = calculatePrice(hasWhippedCream, hasChocolate);

        //String subject = "Coffee order for " + customer_Name;
        String message = createOrderSummary(customer_Name, hasWhippedCream, hasChocolate, price);

        //Intent part
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order for " + customer_Name);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(),"No application can handle the task", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(getApplicationContext(), "Can't order more than 100 Coffee", Toast.LENGTH_SHORT).show();
            return;
        }

        quantity += 1;
        display(quantity);
    }

    /**
     * This method is called when the + button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(getApplicationContext(), "Can't Order Less than 1 Coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity -= 1;
        display(quantity);
    }


    /**
     * This method calculates the total price of coffee.
     *
     * @param whippedCream deterimines customer wants whippedcream or not
     * @param chocolate    deterimines customer wants chocolate or not
     * @return total price
     */
    private int calculatePrice(boolean whippedCream, boolean chocolate) {
        int basePrice = 50;
        if (whippedCream) {
            basePrice += 10;
        }

        if (chocolate) {
            basePrice += 20;
        }
        return basePrice * quantity;
    }

    /**
     * This method creayes the order summary.
     *
     * @param customer_name   stores the name of customer.
     * @param addWhippedCream to check the status of whipped cream checkbox.
     * @param addChocolate    to check the status of chocolate checkbox.
     * @param price           stores the price of the order.
     * @return order summary.
     */
    private String createOrderSummary(String customer_name, boolean addWhippedCream, boolean addChocolate, int price) {
        String message = "Name: " + customer_name;
        message += "\nAddWhippedCream? " + addWhippedCream;
        message += "\nAddChocolate? " + addChocolate;
        message += "\nQunatity: " + quantity;
        message += "\nTotal: ₹" + price;
        message += "\nThank You!";
        return message;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}