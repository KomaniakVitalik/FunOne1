package com.www.funone.view;

/**
 * Created by vitaliy.komaniak on 6/25/16.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import com.www.funone.R;

/**
 * TextView to support custom font and other attrs
 */
public class TextViewFont extends TextView {

    @SuppressWarnings("unused")
    private static final String TAG = TextViewFont.class.getSimpleName();

    /**
     * To add new font put font file in asset folder and place
     * in this array it's name and in attr file, also don't forget to increment
     * it's value there.
     */
    private static final String[] FONTS = new String[]{
            "Roboto-Regular",
            "Roboto-Medium",
            "Roboto-MediumItalic",
            "Roboto-Italic",
            "Roboto-Thin",
            "Roboto-ThinItalic",
            "Roboto-Bold",
            "Roboto-BoldItalic",
            "Roboto-Light",
            "Roboto-LightItalic",
            "Roboto-Black",
            "Roboto-BlackItalic",
            "RobotoCondensed-Regular",
            "RobotoCondensed-Italic",
            "RobotoCondensed-Light",
            "RobotoCondensed-LightItalic",
            "RobotoCondensed-Bold",
            "RobotoCondensed-BoldItalic",
            "SFNS-Display",
            "OpenSansHebrew-Regular",
            "OpenSansHebrew-Italic",
            "OpenSansHebrew-Light",
            "OpenSansHebrew-LightItalic",
            "OpenSansHebrew-Bold",
            "OpenSansHebrew-BoldItalic",
            "OpenSansHebrew-ExtraBold",
            "OpenSansHebrew-ExtraBoldItalic",
            "OpenSansHebrewCondensed-Regular",
            "OpenSansHebrewCondensed-Italic",
            "OpenSansHebrewCondensed-Light",
            "OpenSansHebrewCondensed-LightItalic",
            "OpenSansHebrewCondensed-Bold",
            "OpenSansHebrewCondensed-BoldItalic",
            "OpenSansHebrewCondensed-ExtraBold",
            "OpenSansHebrewCondensed-ExtraBoldItalic"
    };

    private final String FONT_DIR = "fonts/";
    private final String FILE_FORMAT = ".ttf";

    /**
     * By def we don't set any font, use system one
     */
    private int mFontPos = -1;

    public TextViewFont(Context context) {
        super(context);
    }

    public TextViewFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        readXMLAttrs(context, attrs);
    }

    public TextViewFont(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setEllipsize(null);
        readXMLAttrs(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TextViewFont(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setEllipsize(null);
        readXMLAttrs(context, attrs);
    }

    /**
     * Read custom attributes from view declaration
     * we search for value of "custom_font" attribute
     *
     * @param context
     * @param attrs
     */
    protected void readXMLAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.TextViewFont,
                0, 0);

        try {
            mFontPos = a.getInteger(R.styleable.TextViewFont_custom_font, 0);
            setCustomFont(FONTS[mFontPos]);
        } finally {
            a.recycle();
        }
    }

    /**
     * Set view to use other font
     *
     * @param fontName - name of font file in font folder
     */
    protected void setCustomFont(String fontName) {
        try {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), fontPath(fontName));
            setTypeface(tf, 0);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Generate full path to font
     *
     * @param font - name of font file
     * @return full path with it's format and folder
     * where it's sit
     */
    private String fontPath(String font) {
        return FONT_DIR + font + FILE_FORMAT;
    }
}
