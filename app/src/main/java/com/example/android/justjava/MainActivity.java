package com.example.android.justjava;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.NumberFormat;
/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    public int quantity = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity >= 50){
            Toast.makeText(this, "You cannot have more than 50 cup of coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }
    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1){
            Toast.makeText(this, "You cannot have less than 1 cup of coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameField = findViewById(R.id.name_field);
        String name = nameField.getText().toString();
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        // Figure out if the user wants chocolate topping
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        String priceMessage = createOrderSummary(name , calculatePrice(hasChocolate, hasWhippedCream), hasWhippedCream, hasChocolate);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just java order coffee for " +  name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    /**
     * Calculates the price of the order.
     *@param hasChocolate is whether or not user want Chocolate
     *@param hasWhippedCream is whether or not user want WhippedCream
     * @return total price
     */
    private int calculatePrice(boolean hasChocolate, boolean hasWhippedCream) {
        // price of the cup of coffee
       int basePrice = 5;
       // Add 2$ to the basePrice iof the user want add Chocolate
               if (hasChocolate){
           basePrice = basePrice + 2;
               }
       // Add 1$ to the basePrice if the user want add whippedCream
               if (hasWhippedCream){
                   basePrice = basePrice + 1;
               }
        return quantity * basePrice;
    }
    /**
     * Create summary of the order.
     *@param  name is customer name
     * @param AddWhippedCream is whether or not the user wants whipped cream topping
     * @param AddChocolate    is whether or not the user wants chocolate topping
     * @param price           of the order
     * @return text summary
     */
    public String createOrderSummary(String name, int price, boolean AddWhippedCream, boolean AddChocolate) {
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString( R.string.order_summary_whipped_cream, AddWhippedCream );
        priceMessage += "\n" + getString(R.string.order_summary_choco, AddChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n" + getString(R.string.order_summary_price, NumberFormat.getCurrencyInstance().format(price));
        priceMessage += "\n" + getString(R.string.Thank_you);
        return priceMessage;
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    public void displayQuantity(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


}


