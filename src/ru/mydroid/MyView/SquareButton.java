package ru.mydroid.MyView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import ru.mydroid.R;

public class SquareButton extends Button {

	public SquareButton(Context context) {
		super(context);
	}

	public SquareButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

    // This is used to make square buttons.
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
	}
}