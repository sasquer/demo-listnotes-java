package com.sasquer.listnotes.utils;

import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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

    @BindingAdapter("bind:textChangedListener")
    public static void bindTextWatcher(EditText editText, TextWatcher textWatcher) {
        editText.addTextChangedListener(textWatcher);
        editText.setSelection(editText.getText().toString().length());
    }

    @BindingAdapter("bind:onKeyListener")
    public static void bindKeyListener(EditText editText, View.OnKeyListener keyListner) {
        editText.setOnKeyListener(keyListner);
    }

    @BindingAdapter("bind:onCheckBoxClickListener")
    public static void bindCheckBoxListener(CheckBox checkBox, CompoundButton.OnCheckedChangeListener listener) {
        checkBox.setOnCheckedChangeListener(listener);
    }
}
