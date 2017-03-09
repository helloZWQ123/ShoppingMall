package com.atguigu.shoppingmall.shoppingcart.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atguigu.shoppingmall.R;

public class AddSubView extends LinearLayout {
    private ImageView iv_sub;
    private ImageView iv_add;
    private TextView tv_value;
    private int value = 1;
    private int minvalue = 1;
    private int maxvalue = 10;

    public int getMaxvalue() {
        return maxvalue;
    }

    public void setMaxvalue(int maxvalue) {
        this.maxvalue = maxvalue;
    }

    public int getMinvalue() {
        return minvalue;
    }

    public void setMinvalue(int minvalue) {
        this.minvalue = minvalue;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        tv_value.setText(value+"");
    }

    public AddSubView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.add_sub_view, AddSubView.this);
        iv_sub = (ImageView) findViewById(R.id.iv_sub);
        iv_add = (ImageView) findViewById(R.id.iv_add);
        tv_value = (TextView) findViewById(R.id.tv_value);
        iv_sub.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (value > minvalue) {
                    value--;
                }
                setValue(value);
                if (listener != null) {
                    listener.onNumberChanged(value);
                }
            }
        });
        iv_add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (value < maxvalue) {
                    value++;
                }
                setValue(value);
                if (listener != null) {
                    listener.onNumberChanged(value);
                }
            }
        });
        if (attrs != null) {
            //取出属性
            TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(context, attrs, R.styleable.AddSubView);
            int value = tintTypedArray.getInt(R.styleable.AddSubView_value, 0);
            if (value > 0) {
                setValue(value);
            }
            int minValue = tintTypedArray.getInt(R.styleable.AddSubView_minvalue, 0);
            if (value > 0) {
                setMinvalue(minValue);
            }
            int maxValue = tintTypedArray.getInt(R.styleable.AddSubView_maxvalue, 0);
            if (value > 0) {
                setMaxvalue(maxValue);
            }
            Drawable addDrawable = tintTypedArray.getDrawable(R.styleable.AddSubView_numberAddBackground);
            if (addDrawable != null) {
                iv_add.setImageDrawable(addDrawable);
            }
            Drawable subDrawable = tintTypedArray.getDrawable(R.styleable.AddSubView_numberSubBackground);
            if (subDrawable != null) {
                iv_sub.setImageDrawable(subDrawable);
            }
        }
    }

    public interface OnNumberChangedListener {
        void onNumberChanged(int value);
    }

    private OnNumberChangedListener listener;

    public void setListener(OnNumberChangedListener listener) {
        this.listener = listener;
    }


}
