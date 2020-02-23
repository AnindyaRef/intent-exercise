package id.ac.polinema.intentexercise;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getCanonicalName();
    private static final int GALLERY_REQUEST_CODE = 1;


    public static final String AVATARIMAGE_KEY = "image";
    public static final String FULLNAME_KEY = "fullname";
    public static final String EMAIL_KEY = "email";
    public static final String PASSWORD_KEY = "password";
    public static final String CONFIRMPAS_KEY = "confirmpass";
    public static final String HOMEPAGE_KEY = "homepage";
    public static final String ABOUTYOU_KEY = "about";

    private ImageView avatarImage;
    private EditText fullnameInput;
    private EditText emailInput;
    private EditText passwordInput;
    private EditText confirmpassInput;
    private EditText homepageInput;
    private EditText aboutyouInput;

    private Uri imageUri=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        avatarImage = findViewById(R.id.image_profile);
        fullnameInput = findViewById(R.id.text_fullname);
        emailInput = findViewById(R.id.text_email);
        passwordInput = findViewById(R.id.text_password);
        confirmpassInput = findViewById(R.id.text_confirm_password);
        homepageInput = findViewById(R.id.text_homepage);
        aboutyouInput = findViewById(R.id.text_about);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_CANCELED){
            return;
        }

        if (requestCode == GALLERY_REQUEST_CODE) {
            if (data != null) {
                try {
                    imageUri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    avatarImage.setImageBitmap(bitmap);
                } catch (IOException e) {
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }

    public void handleChageAvatar(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    public void handlesubmit(View view) {
        String fullname = fullnameInput.getText().toString();
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        String confirmpass = confirmpassInput.getText().toString();
        String homepage = homepageInput.getText().toString();
        String aboutyou = aboutyouInput.getText().toString();

        Intent intent = new Intent(this, ProfileActivity.class);
        int countError = 5;


        if(Cfullname(fullname)){
            intent.putExtra(FULLNAME_KEY,fullname);
            countError--;
        }

        if(Cemail(email)){
            intent.putExtra(EMAIL_KEY,email);
            countError--;
        }

        if(Cpassword(password, confirmpass)){
            countError--;
        }

        if (Chomepage(homepage)){
            intent.putExtra(HOMEPAGE_KEY, homepage);
            countError--;
        }

        if (Caboutyou(aboutyou)){
            intent.putExtra(ABOUTYOU_KEY, aboutyou);
            countError--;
        }

        if (countError==0){
            if (imageUri!=null){
                intent.putExtra(AVATARIMAGE_KEY, imageUri.toString());
                try{
                    startActivity(intent);
                }catch (Exception ex){
                    intent.putExtra(AVATARIMAGE_KEY,"");
                    Toast.makeText(this, "Your image to big", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            }else {
                Toast.makeText(this,"Please insert your image", Toast.LENGTH_SHORT).show();
                handleChageAvatar(view);
            }
        }
    }

    private boolean Caboutyou(String aboutyou) {
        if (aboutyouInput.getText().toString().length()==0){
            aboutyouInput.setError("Input AboutYou");
            return false;
        }else {
            return true;
        }
    }

    private boolean Chomepage(String homepage) {
        if (homepageInput.getText().toString().length()==0){
            homepageInput.setError("Input HomePage!");
            return false;
        }else{
            return  true;
        }
    }

    private boolean Cpassword(String password, String confirmpass) {
        if ((password.isEmpty() && confirmpass.isEmpty())) {
            passwordInput.setError("Input Password");
            confirmpassInput.setError("Confirm your Password");
            return false;
        }else  if(password.isEmpty()){
            passwordInput.setError("Input Password");
            return  false;
        }else if (confirmpass.isEmpty()){
            confirmpassInput.setError("Confirm your password");
            return false;
        }else if (password.equals(confirmpass)){
            return true;
        }else {
            passwordInput.setError("Password doesn't match");
            confirmpassInput.setError("Password does't match");
            return false;
        }
    }

    private boolean Cemail(String email) {
        if (emailInput.getText().toString().length()==0){
            emailInput.setError("Input Email!");
            return  false;
        }else{
            return  true;
        }
    }

    private boolean Cfullname(String fullname) {
        if(fullnameInput.getText().toString().length()==0){
            fullnameInput.setError("Input Fullname!");
            return false;
        }else {
            return  true;
        }
    }


}
