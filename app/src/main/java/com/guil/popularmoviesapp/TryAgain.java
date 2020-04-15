package com.guil.popularmoviesapp;

import android.app.Activity;
import android.app.Dialog;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import java.lang.ref.WeakReference;

public class TryAgain extends AppCompatDialogFragment {

    public void displayToast(final Context context, final Button button) {

        button.setVisibility(View.VISIBLE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Activity main = (Activity) context;
                main.recreate();
            }
        });

        WeakReference<Context> contextRef = new WeakReference<>(context);
        Toast.makeText(contextRef.get(), "Sorry something went wrong! Check your Connection and try again", Toast.LENGTH_LONG).show();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Connection Issue!")
                .setMessage("Please check you internet connection and try again.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }
}
