package com.nathanrjones.shiftboard.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nathanrjones.shiftboard.R;
import com.nathanrjones.shiftboard.data.model.Person;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.text.TextUtils.isEmpty;
import static com.nathanrjones.shiftboard.util.ViewUtils.dpToPx;

/**
 * Custom view
 */

public class PersonSummaryView extends RelativeLayout {

    @Bind(R.id.person_image) ImageView personImage;
    @Bind(R.id.person_name) TextView personName;
    @Bind(R.id.person_email) TextView personEmail;

    public enum Style {
        Default, Large
    }

    public PersonSummaryView(Context context) {
        this(context, null);
    }

    public PersonSummaryView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PersonSummaryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        int padding = dpToPx(16);

        LayoutInflater.from(context)
                .inflate(R.layout.component_person_summary, this, true);

        ButterKnife.bind(this);

        applySelectableBackground();

        setPaddingRelative(padding, padding, padding, padding);

    }

    public void setPerson(Person person) {
        personName.setText(formatName(person));
        personEmail.setText(person.getEmail());

        if (isEmpty(person.getImageUrl())) {
            personImage.setImageResource(R.drawable.default_avatar);
        } else {
            Glide.with(getContext())
                    .load(person.getImageUrl())
                    .into(personImage);
        }

    }

    public void setStyle(Style style) {

        int imageSize = style == Style.Large ? dpToPx(72) : dpToPx(40);

        personImage.getLayoutParams().width = imageSize;
        personImage.getLayoutParams().height = imageSize;
        personImage.requestLayout();

        personName.setTextSize(style == Style.Large ? 24 : 16);
    }

    private String formatName(Person person) {

        if (person == null) return null;

        return String.format("%s %s",
                !person.getFirstName().isEmpty() ? person.getFirstName() : "",
                !person.getLastName().isEmpty() ? person.getLastName() : ""
        );
    }

    private void applySelectableBackground() {
        TypedValue typedValue = new TypedValue();
        getContext().getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);

        setClickable(true);
        setBackgroundResource(typedValue.resourceId);
    }
}
