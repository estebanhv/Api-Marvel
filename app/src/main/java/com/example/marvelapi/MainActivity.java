package com.example.marvelapi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marvelapi.data.remote.MarvelApi;
import com.example.marvelapi.domain.model.Results;
import com.example.marvelapi.domain.model.Root;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    private MarvelApi marvelAPI = new MarvelApi();
    private Button butSearch = null;
    private EditText txtName = null;
    private ImageView imgCharacter = null;

    //private CardView cardView = null;
    private TextView lblInformation = null;

    private TextView txNameT = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initEvents();
    }

    public void initViews(){
       txtName = findViewById(R.id.lnSeek);
        butSearch = findViewById(R.id.btSearch);
        imgCharacter = findViewById(R.id.chac);
        lblInformation = findViewById(R.id.txDescription);
        txNameT = findViewById(R.id.txNameT);

    }

    public void initEvents(){
        butSearch.setOnClickListener(view -> {
            String name = txtName.getText().toString();
            lblInformation.setText("");
            txNameT.setText("");
            imgCharacter.setImageBitmap(null);
            butSearch.setEnabled(false);

            if (name.isEmpty()){
                imgCharacter.setImageBitmap(null);
                lblInformation.setText("");
                butSearch.setEnabled(true);
                txtName.setEnabled(true);

            }else {
                requestCharacterInfo(txtName.getText().toString());
            }
        });
    }

    public void showCharacterInfo(Results result, Bitmap bitmap){
        lblInformation.setText(result.getDescription());
        System.out.println(lblInformation.getText());
        txNameT.setText(result.getName());
        imgCharacter.setImageBitmap(bitmap);



    }

    public void showMessageError(int errorCode){

    }

    public void requestCharacterInfo(String name){
        butSearch.setEnabled(false);
        txtName.setEnabled(false);

        marvelAPI.requestCharacterInfo(name, (errorCode1, text) -> {
            if (errorCode1 == 200){
                if (text != null){
                    Root root = new Gson().fromJson(text, Root.class);
                    System.out.println(root.getStatus() + " " + root.getCopyright() + " " + root.getData());
                    if (root.getData().getResults().size() > 0){
                        //System.out.println(root.getData().getResults());
                        var result = root.getData().getResults().get(0);
                        var thumbnail = result.getThumbnail();
                        var imgFilename = thumbnail.getPath() + "." + thumbnail.getExtension();
                        marvelAPI.requestImage(imgFilename, (errorCode2, bitmap) -> {
                            if (errorCode2 == 200){
                                if (bitmap != null){
                                    runOnUiThread(() -> {
                                        showCharacterInfo(result, bitmap);
                                        butSearch.setEnabled(true);
                                        txtName.setEnabled(true);
                                    });
                                }
                            }
                        });

                    }else {
                        runOnUiThread(() -> {
                            butSearch.setEnabled(true);
                            lblInformation.setText("Not found");
                            txtName.setEnabled(true);
                        });
                    }
                }
            }
        });
    }
}