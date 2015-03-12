package com.android.studio.custom.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.studio.custom.R;

/**
 * Created by CHENDONG on 2015-03-10.
 */
public class CustomTableLayout extends ScrollView {
    private int rowId;
    private int count;
    private TableLayout tl;

    public CustomTableLayout(Context context) {
        super(context);
    }

    public CustomTableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.custom_tablelayout);
        rowId = a.getResourceId(R.styleable.custom_tablelayout_row_layout, 0);
        if (rowId != 0) {
            tl = new TableLayout(context);
            tl.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            count = a.getInteger(R.styleable.custom_tablelayout_row_count, 0);
            if (count > 0) {
                for (int i = 0; i < count; i++) {
                    tl.addView(LayoutInflater.from(context).inflate(rowId, null));
                }
                int dataId = a.getResourceId(R.styleable.custom_tablelayout_row_text_data, 0);
                if (dataId != 0) {
                    String[] data = context.getResources().getStringArray(dataId);
                    int dataLen = data.length;
                    for (int i = 0; i < dataLen; i++) {
                        ((TextView) ((TableRow) tl.getChildAt(i)).getChildAt(0)).setText(data[i]);
                    }
                }
            }
            this.addView(tl);
        }
    }


    public View getTableRowsView(int rowNum, int rowChildNum) {
        if (tl == null) return null;
        return ((TableRow) tl.getChildAt(rowNum)).getChildAt(rowChildNum);
    }

    public void addTableRowsView(int position, View view) {
        if (position != 0 && view != null) {
            tl.addView(view, position);
            invalidate();
        }
    }
    public void addTableRowsView(@NonNull View view) {
        if (view != null) {
            tl.addView(view, tl.getChildCount());
            invalidate();
        }
    }
    public void setRowBackgroundColorScheme(@ColorRes int... colorId){
        int len = colorId.length;
            int tlSize = tl.getChildCount();
            for (int i=0;i<tlSize;i++){
                if(i>=len){
                    tl.getChildAt(i).setBackgroundResource(colorId[i % len]);
                }else {
                    tl.getChildAt(i).setBackgroundResource(colorId[i]);
                }
            }
    }
}
