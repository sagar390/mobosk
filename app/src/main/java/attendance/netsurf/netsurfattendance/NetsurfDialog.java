package attendance.netsurf.netsurfattendance;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by chetan on 15/05/15.
 */
public class NetsurfDialog extends android.app.Dialog {
    String title;
    String message;
    TextView titleTextView;
    TextView messageTextView;
    Button buttonAccept;
    Button buttonCancel;
    View.OnClickListener onAcceptButtonClickListener;
    View.OnClickListener onCancelButtonClickListener;
    Context context;
    String positiveButtonText;
    String negativeButtonText;
    ImageView titleImage;

    Drawable drawable;

    public NetsurfDialog(Context context, String title, String message, String positiveButtonText,
                         String negativeButtonText) {
        super(context, android.R.style.Theme_Translucent);
        this.title = title;
        this.context = context;
        this.message = message;
        this.positiveButtonText = positiveButtonText;
        this.negativeButtonText = negativeButtonText;
    }

    public NetsurfDialog(Context context, Drawable titleDrawable, String message,
                         String positiveButtonText,
                         String negativeButtonText) {
        super(context, android.R.style.Theme_Translucent);
        this.drawable = titleDrawable;
        this.context = context;
        this.message = message;
        this.positiveButtonText = positiveButtonText;
        this.negativeButtonText = negativeButtonText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_netsurf);
        this.titleTextView = (TextView) findViewById(R.id.title_tv);
        this.titleImage = (ImageView) findViewById(R.id.title_image);
        this.messageTextView = (TextView) findViewById(R.id.message_tv);

       /* if (title == null) {
            //titleImage.setImageDrawable(drawable);
            titleImage.setVisibility(View.VISIBLE);
            titleTextView.setVisibility(View.GONE);
        } else {


            titleTextView.setVisibility(View.VISIBLE);
            titleImage.setVisibility(View.GONE);
            setTitle(title);
        }*/

     /*   titleTextView.setVisibility(View.VISIBLE);
        titleImage.setVisibility(View.GONE);*/
        setTitle(title);

        setMessage(message);
        this.buttonAccept = (Button) findViewById(R.id.button_accept);
        this.buttonCancel = (Button) findViewById(R.id.button_cancel);

        if (TextUtils.isEmpty(positiveButtonText)) {
            this.buttonAccept.setVisibility(View.GONE);
        } else {
            this.buttonAccept.setText(positiveButtonText);
        }
        if (TextUtils.isEmpty(negativeButtonText)) {
            this.buttonCancel.setVisibility(View.GONE);
        } else {
            this.buttonCancel.setText(negativeButtonText);
        }


        buttonAccept.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //dismiss();
                if (onAcceptButtonClickListener != null) {
                    onAcceptButtonClickListener.onClick(v);
                } else {
                    dismiss();
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
                if (onCancelButtonClickListener != null)
                    onCancelButtonClickListener.onClick(v);
            }
        });
    }

    private void setMessage(String message) {
        if (TextUtils.isEmpty(message))
            messageTextView.setVisibility(View.GONE);
        else {
            messageTextView.setVisibility(View.VISIBLE);
            messageTextView.setText(message);
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        if (TextUtils.isEmpty(title))
            titleTextView.setVisibility(View.GONE);
        else {
            titleTextView.setVisibility(View.VISIBLE);
            titleTextView.setText(title);
        }
    }


    public TextView getTitleTextView() {
        return titleTextView;
    }

    public void setTitleTextView(TextView titleTextView) {
        this.titleTextView = titleTextView;
    }

    public Button getButtonAccept() {
        return buttonAccept;
    }

    public void setButtonAccept(Button buttonAccept) {
        this.buttonAccept = buttonAccept;
    }

    public Button getButtonCancel() {
        return buttonCancel;
    }

    public void setButtonCancel(Button buttonCancel) {
        this.buttonCancel = buttonCancel;
    }

    public void setOnAcceptButtonClickListener(
            View.OnClickListener onAcceptButtonClickListener) {
        this.onAcceptButtonClickListener = onAcceptButtonClickListener;
        if (buttonAccept != null)
            buttonAccept.setOnClickListener(onAcceptButtonClickListener);
    }

    public void setOnCancelButtonClickListener(
            View.OnClickListener onCancelButtonClickListener) {
        this.onCancelButtonClickListener = onCancelButtonClickListener;
        if (buttonCancel != null)
            buttonCancel.setOnClickListener(onCancelButtonClickListener);
    }


}