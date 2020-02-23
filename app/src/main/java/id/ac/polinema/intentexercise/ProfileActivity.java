package id.ac.polinema.intentexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import static id.ac.polinema.intentexercise.RegisterActivity.ABOUTYOU_KEY;
import static id.ac.polinema.intentexercise.RegisterActivity.AVATARIMAGE_KEY;
import static id.ac.polinema.intentexercise.RegisterActivity.EMAIL_KEY;
import static id.ac.polinema.intentexercise.RegisterActivity.FULLNAME_KEY;
import static id.ac.polinema.intentexercise.RegisterActivity.HOMEPAGE_KEY;

public class ProfileActivity extends AppCompatActivity {

    private ImageView avatarImage;
    private TextView fullnameText;
    private TextView emailText;
    private TextView homepageText;
    private TextView abouyouText;
    private Uri uri;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fullnameText = findViewById(R.id.label_fullname);
        emailText = findViewById(R.id.label_email);
        homepageText = findViewById(R.id.label_homepage);
        abouyouText = findViewById(R.id.label_about);
        avatarImage = findViewById(R.id.image_profile);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            fullnameText.setText(extras.getString(FULLNAME_KEY));
            emailText.setText(extras.getString(EMAIL_KEY));
            homepageText.setText((extras.getString(HOMEPAGE_KEY)));
            abouyouText.setText(extras.getString(ABOUTYOU_KEY));
            url = extras.getString(HOMEPAGE_KEY);
            uri = Uri.parse(extras.getString(AVATARIMAGE_KEY));
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            }catch (IOException e){
                e.printStackTrace();
            }
            avatarImage.setImageBitmap(bitmap);
        }
    }

    public void handlrVisit(View view) {
        Intent hompage = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+url));
        startActivity(hompage);
    }
}
