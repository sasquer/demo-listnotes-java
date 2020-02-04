package com.sasquer.listnotes.utils;

import android.text.Html;
import android.view.View;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import static android.view.View.VISIBLE;

public class BindingUtils {

    public BindingUtils() {

    }

    @BindingAdapter("bind:visibility")
    public static void onVisibility(View view, boolean visibility) {
        view.setVisibility(visibility ? VISIBLE : View.GONE);
    }

    @BindingAdapter("bind:textHtml")
    public static void onTextHtmlShow(TextView view, String textHtml) {
        view.setText(Html.fromHtml(textHtml));
    }
}
