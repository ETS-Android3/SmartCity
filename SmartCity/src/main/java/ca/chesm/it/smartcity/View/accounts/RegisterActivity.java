/*
    Name:Dung Ly N01327929
    Course: CENG322-RND
    Purpose: Control register user with firebase
    Last updated:  09 Oct 2021
*/

package ca.chesm.it.smartcity.View.accounts;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ca.chesm.it.smartcity.R;
import ca.chesm.it.smartcity.models.AESCrypt;
import ca.chesm.it.smartcity.models.User;

public class RegisterActivity extends AppCompatActivity
{

    private EditText editTextfullname, editTextpassword, editTextemail, editTextphone;
    String password, fullname, phone, email;
    private Button bntSubmit;
    private DatabaseReference reff;
    private FirebaseAuth mAuth;
    private User user;
    long maxid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        reff = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();
        user = new User();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channen1 = new NotificationChannel("Smart City","Smart City",NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channen1);
        }
        regid();
        //Use to control the submit button on Register form
        bntSubmit.setOnClickListener(view ->
        {
            AESCrypt aesCrypt = new AESCrypt();

            fullname = editTextfullname.getText().toString().trim();
            password = editTextpassword.getText().toString().trim();
            email = editTextemail.getText().toString().trim();
            phone = editTextphone.getText().toString().trim();
            if (fullname.isEmpty())
            {
                editTextfullname.setError("This field can not be blank !");
                return;
            }
            if (email.isEmpty())
            {
                editTextemail.setError("This field can not be blank !");
                return;
            }
            if (password.isEmpty())
            {
                editTextpassword.setError("This field can not be blank !");
                return;
            }
            if (phone.isEmpty())
            {
                editTextphone.setError("This field can not be blank !");
                return;
            }
            else if (phone.length() < 8)
            {
                editTextphone.setError("Incorrect Phone number !");
                return;
            }
                if (!isEmailValid(email))
                {
                    editTextemail.setError("The email type not correct !");
                    return;
                }
                user.setEmail(email);
                user.setFullname(fullname);
                try
                {
                    user.setPassword(aesCrypt.encrypt(password));
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                user.setPhoneNo(phone);
                emailreg(user);

        });

    }

    //This method use to create User on method login with email when user register complete!
    private void emailreg(User user)
    {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {

                if (task.isSuccessful())
                {
                    reff.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if (task.isSuccessful())
                            {
                                Toast register_success = Toast.makeText(getApplicationContext(),"Register Successfully", Toast.LENGTH_SHORT);
                                register_success.show();
                                notibuild("Registered Successed !");
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }

                        }
                    });
                } else
                {
                    AlertDialog.Builder dialog = Dialogb(task.getException().getMessage());
                    dialog.show();
                }
            }
        });
    }

    private  void notibuild (String mess)
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(RegisterActivity.this,"Smart City");
        builder.setContentTitle("Smart City");
        builder.setContentText(mess);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(RegisterActivity.this);
        managerCompat.notify(1,builder.build());

    }
    //Method use to show dialog with custom mess
    private AlertDialog.Builder Dialogb(String mess)
    {
        AlertDialog.Builder builderd = new AlertDialog.Builder(RegisterActivity.this);
        builderd.setCancelable(true);
        builderd.setMessage(mess);
        builderd.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                dialogInterface.cancel();
            }
        });
        return builderd;
    }

    //Method use to check email is correct type or not
    private static boolean isEmailValid(String email)
    {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    //Method use to find ID of each variable need to set on xml
    private void regid()
    {
        editTextfullname = (EditText) findViewById(R.id.txteditregfullname);
        editTextpassword = (EditText) findViewById(R.id.txteditregpassword);
        editTextemail = (EditText) findViewById(R.id.txteditregemail);
        editTextphone = (EditText) findViewById(R.id.txteditphoneno);
        bntSubmit = (Button) findViewById(R.id.bntsubmitreg);
    }

}