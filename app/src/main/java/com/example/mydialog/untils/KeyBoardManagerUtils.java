package com.example.mydialog.untils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.Selection;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.ref.WeakReference;

public class KeyBoardManagerUtils {

    public KeyBoardManagerUtils() {

    }

    public static void openSoftKeyboard(Context context, final EditText editText) {
        final WeakReference<Context> mContext = new WeakReference<>(context);
        if (mContext.get() == null) {
            return;
        }
        if (editText == null) {
            return;
        }
        editText.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) mContext.get().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(editText, 1);
        setEditTextSelectionToEnd(editText);
    }


    public static void closeSoftKeyboard(Activity activity) {
        final WeakReference<Activity> mActivity = new WeakReference<>(activity);
        if (mActivity == null)
            return;
        View view = mActivity.get().getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) mActivity.get().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputmanger != null) {
                inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }


    public static void openSoftKeyboardWithHandler(Context context, final EditText editText, int time) {
        final WeakReference<Context> mContext = new WeakReference<>(context);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mContext.get() == null) {
                    return;
                }
                if (editText == null) {
                    return;
                }
                editText.requestFocus();
                InputMethodManager inputMethodManager = (InputMethodManager) mContext.get().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(editText, 1);
                setEditTextSelectionToEnd(editText);
            }
        }, time);

    }

    public static void closeSoftKeyboardWithHandler(Activity activity, int time) {
        final WeakReference<Activity> mActivity = new WeakReference<>(activity);
        if (mActivity == null)
            return;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                View view = mActivity.get().getWindow().peekDecorView();
                if (view != null) {
                    InputMethodManager inputmanger = (InputMethodManager) mActivity.get().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (inputmanger != null) {
                        inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
            }
        }, time);
    }

    public static void toggleSoftKeyboardState(Context context) {
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(1, 2);
    }

    public boolean keyBoxIsShow(Activity mActivity) {
        if (mActivity.getWindow().getAttributes().softInputMode == 0) {
            mActivity.getWindow().setSoftInputMode(2);
            return true;
        } else {
            return false;
        }
    }

    public static final void setEditTextSelectionToEnd(EditText editText) {
        Editable editable = editText.getEditableText();
        Selection.setSelection(editable, editable.toString().length());
    }


}
