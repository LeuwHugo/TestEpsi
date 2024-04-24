package com.simplecity.amp_library.utils;

import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

public class LetterDrawable extends Drawable {

    String mDisplayName;
    Paint mPaint;
    String mKeyName;
    char[] mFirstChar;
    TypedArray mColors;

    public LetterDrawable(String displayName, TypedArray colors, Paint paint) {

        mDisplayName = displayName;
        mColors = colors;
        mPaint = paint;
        mKeyName = StringUtils.keyFor(displayName);
        if (displayName != null && displayName.length() != 0) {
            String key = StringUtils.keyFor(displayName);
            if (key != null && key.length() != 0) {
                mFirstChar = new char[] { Character.toUpperCase(key.charAt(0)) };
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (mFirstChar == null || mKeyName == null || mFirstChar.length == 0 || mKeyName.length() == 0) {
            return;
        }
        canvas.drawColor(pickColor(mDisplayName));
        if (mKeyName.length() > 0) {
            mPaint.setTextSize(canvas.getHeight() * 3 / 5);
            mPaint.getTextBounds(mFirstChar, 0, 1, getBounds());
            canvas.drawText(mFirstChar, 0, 1, canvas.getWidth() / 2, canvas.getHeight() / 2
                    + (getBounds().bottom - getBounds().top) / 2, mPaint);
        }
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter cf) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }

    /**
     * @param key The key used to generate the tile color
     * @return A new or previously chosen color for <code>key</code> used as the
     * tile background color
     */
    private int pickColor(String key) {
        int hash = key.hashCode();
        // Using bitwise AND with length-1 instead of modulus to avoid negative index
        // Ensure mColors.length() is a power of two for this to work correctly
        // Otherwise, use (Math.abs(hash) % mColors.length())
        final int index = (hash & 0x7FFFFFFF) % mColors.length(); // Safely wraps index within the array bounds
        return mColors.getColor(index, Color.BLACK);
    }

}
