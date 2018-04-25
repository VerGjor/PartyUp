package com.vergjor.android.partyup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class OwnerRegister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_register);

        final Button submit = (Button) findViewById(R.id.btnOwner);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                EditText txtname = (EditText)findViewById(R.id.txtName);

                String bname=txtname.getText().toString().trim();
                if (bname.length()==0)
                    return ;


                EditText txtphone = (EditText)findViewById(R.id.txtPhone);

                String phone=txtphone.getText().toString().trim();
                if (phone.length()==0)
                    return ;


                EditText txttax = (EditText)findViewById(R.id.txtTax);

                String tax=txtname.getText().toString().trim();
                if (tax.length()==0)
                    return ;



                EditText txtaddr = (EditText)findViewById(R.id.txtAdress);

                String addr=txtname.getText().toString().trim();
                if (addr.length()==0)
                    return ;




            }
        });

    }
}
