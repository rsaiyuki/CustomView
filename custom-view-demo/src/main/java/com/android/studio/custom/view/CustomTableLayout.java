package com.android.studio.custom.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
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
    private int dataId;
    private Context context;
    private TypedArray typedArray;

    public CustomTableLayout(Context context) {
        super(context);
        this.context = context;
    }

    public CustomTableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.custom_tablelayout);
        tl = new TableLayout(context);
        tl.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initTableLayout();
    }

    private void initTableLayout(){
        tl.removeAllViews();
        if(rowId==0){
            rowId = typedArray.getResourceId(R.styleable.custom_tablelayout_row_layout, 0);
        }
        if(count==0){
            count = typedArray.getInteger(R.styleable.custom_tablelayout_row_count, 0);
        }
        if(dataId==0){
            dataId = typedArray.getResourceId(R.styleable.custom_tablelayout_row_text_data, 0);
        }
        if (rowId != 0) {
            if (count > 0) {
                for (int i = 0; i < count; i++) {
                    tl.addView(LayoutInflater.from(context).inflate(rowId, null));
                }
                if (dataId != 0) {
                    String[] data = context.getResources().getStringArray(dataId);
                    int dataLen = data.length;
                    for (int i = 0; i < count; i++) {
                        if(dataLen>i){
                            ((TextView) ((TableRow) tl.getChildAt(i)).getChildAt(0)).setText(data[i]);
                        }
                    }
                }
            }
            if(this.getChildCount()<=0){
                this.addView(tl);
            }
        }
    }
    public void setTableRowCount(int count) {
        this.count = count;
        initTableLayout();
    }
    public void setTableRow(@LayoutRes int rowId) {
        this.rowId = rowId;
        initTableLayout();
    }
    public View getTableRow(int rowNum) {
        if (tl == null) return null;
        if(tl.getChildCount()<rowNum)return null;
        return tl.getChildAt(rowNum);
    }
    public View getTableRowsChild(int rowNum, int rowChildNum) {
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
