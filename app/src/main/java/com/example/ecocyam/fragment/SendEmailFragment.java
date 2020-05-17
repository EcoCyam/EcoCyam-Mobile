package com.example.ecocyam.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ecocyam.R;

public class SendEmailFragment extends Fragment {

    /* default */View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.contact_us_fragment,container,false);

       //String emailUser = getArguments().getString("email");

        final EditText subjectEmail = view.findViewById(R.id.subject_email);
        final EditText bodyEmail = view.findViewById(R.id.message_email);
        Button buttonSend = view.findViewById(R.id.button_send_email);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               sendMail(subjectEmail.getText().toString(),bodyEmail.getText().toString());
            }
        });
       return view;
    }

    public void sendMail(String subjectEmail,String bodyEmail) {
        //Pas la meilleure manière de faire -> faudra envoyer un mail sans intent avec direct : from = email user to = notre adresse
        String recipientList = "ecocyam@gmail.com";
        String[] recipients = recipientList.split(","); //au cas où on aurait plusieurs destinataire de mail

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT,subjectEmail);
        intent.putExtra(Intent.EXTRA_TEXT,bodyEmail);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent,"choose a mailer"));
    }

}
