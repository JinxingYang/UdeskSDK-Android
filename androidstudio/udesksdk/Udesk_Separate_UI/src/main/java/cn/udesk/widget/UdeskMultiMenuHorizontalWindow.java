package cn.udesk.widget;import android.content.Context;import android.graphics.drawable.ColorDrawable;import android.view.Gravity;import android.view.LayoutInflater;import android.view.View;import android.view.View.MeasureSpec;import android.view.ViewGroup;import android.widget.PopupWindow;import android.widget.TextView;import cn.udesk.R;public class UdeskMultiMenuHorizontalWindow extends PopupWindow implements View.OnClickListener {    public UdeskMultiMenuHorizontalWindow(Context context) {        super(context );                setFocusable(true);         setOutsideTouchable(true);         setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));            }        public void show(Context content,View locationView,String[] menuLabelArray,OnPopMultiMenuClick onPopMultiMenuClick){        //check         if(menuLabelArray==null || menuLabelArray.length<1){            throw new RuntimeException(" menu label can not be empty ");        }                // ready         LayoutInflater layoutInflater=LayoutInflater.from(content);        mOnPopMultiMenuClick = onPopMultiMenuClick;                // 如果只有一个，则把divider删除掉        ViewGroup popupView =(ViewGroup) layoutInflater.inflate(R.layout.udesk_multi_horizontal_popmenu, null);                // 把菜单都添加进去        addChildView(popupView,layoutInflater,menuLabelArray);        setContentView(popupView);               // 测绘并定位        popupView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);        int popupWidth = popupView.getMeasuredWidth();        int popupHeight =  popupView.getMeasuredHeight();        setWindowLayoutMode(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);        setHeight(popupHeight);                //显示出来        int[] location = new int[2];        locationView.getLocationOnScreen(location);                showAtLocation(locationView, Gravity.NO_GRAVITY , /*location[0]*/(location[0]+locationView.getWidth()/2)-popupWidth/2,                location[1]-popupHeight/*location[1]- getHeight()*/);    }        private void addChildView(ViewGroup mainView,LayoutInflater layoutInflater,String[] menuLabelArray){        int indexTemp =0;        //先把第一个加进来        TextView firstView = (TextView)mainView.findViewById(R.id.udesk_multi_horizontal_item);        firstView.setText( menuLabelArray[indexTemp]);        firstView.setTag(indexTemp);        firstView.setOnClickListener(this);        // 有可能只有一个选项        if(menuLabelArray.length<=1){            return;        }                //添加其他选项        for (  indexTemp = 1; indexTemp < menuLabelArray.length; indexTemp++) {            TextView chidView  = (TextView)layoutInflater.inflate(R.layout.udesk_multi_horizontal_menu_item, mainView, false);            chidView.setText(menuLabelArray[indexTemp]);            chidView.setTag(indexTemp);            chidView.setOnClickListener(this);            mainView.addView(chidView);        }    }        @Override    public void onClick(View childView) {        if(mOnPopMultiMenuClick!=null){            mOnPopMultiMenuClick.onMultiClick((Integer)childView.getTag());        }                dismiss();    }            private OnPopMultiMenuClick mOnPopMultiMenuClick;            public interface OnPopMultiMenuClick {        public void onMultiClick(int MenuIndex);    }}