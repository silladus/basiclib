package silladus.basic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

public class ActivityDialog extends AppCompatActivity {
    private static final String EXTRA_DELEGATE = "ActivityDialogDelegate";

    private ActivityDialogDelegate dialogDelegate;

    public static void show(final Context context, final ActivityDialogDelegate delegate) {
        Intent intent = new Intent(context, ActivityDialog.class);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        intent.putExtra(EXTRA_DELEGATE, delegate);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Serializable delegate = getIntent().getSerializableExtra(EXTRA_DELEGATE);
        if (!(delegate instanceof ActivityDialogDelegate)) {
            finish();
            return;
        }

        dialogDelegate = (ActivityDialogDelegate) delegate;
        dialogDelegate.onCreate(savedInstanceState, this);
    }

    @Override
    protected void onDestroy() {
        if (dialogDelegate != null) {
            dialogDelegate.onDestroy();
        }
        dialogDelegate = null;
        super.onDestroy();
    }

    public interface ActivityDialogDelegate extends Serializable {
        void onCreate(@Nullable Bundle savedInstanceState, @NonNull Activity activity);

        default void onDestroy() {
        }

        default void show(Context context) {
            ActivityDialog.show(context, this);
        }
    }
}
