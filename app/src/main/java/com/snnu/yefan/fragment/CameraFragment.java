package com.snnu.yefan.fragment;

import android.animation.Animator;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.snnu.yefan.socialization.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zero on 2016/7/9.
 */
public class CameraFragment extends Fragment{

    private static CameraFragment fm = null;
    private Context mContext;
    private Camera mCamera;
    // 是否存在预览中
    private boolean isPreview = false;
    private int screenWidth, screenHeight;
    private SurfaceView mSurfaceView;

    private EditText editText;
    private View btnAdd;
    private LinearLayout danmakuContainer;
    private LayoutTransition mTransitioner;
    private GestureDetector gesture; //手势识别
    private RelativeLayout mRelativeLayout;
    private List<String> list = new ArrayList<String>();
    private final Timer timer = new Timer();
    private TimerTask task;
    private LayoutInflater mLayoutInflater = null;


    private View.OnClickListener addBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String inputStr = editText.getText().toString();
            if(TextUtils.isEmpty(inputStr)){
                showLog("请输入弹幕内容再发送！");
                return;
            }
            addContentToDanmaku(inputStr);

        }
    };




    /**
     * 发送弹幕内容到界面上
     * @param inputStr
     */
    private void addContentToDanmaku(String inputStr) {


        View contentView =  mLayoutInflater.from(mContext).inflate(R.layout.chat,null);
        TextView tv = (TextView) contentView.findViewById(R.id.chat_content);              //创建弹幕的背景及内容
        tv.setText(inputStr);
           //自定义每一条弹幕的margin值
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        danmakuContainer.addView(contentView,lp);
        contentView.postDelayed(autoRemoveSelf(contentView),2000);               //5秒后自动删除自己这条弹幕
    }

    private Runnable autoRemoveSelf(final View v) {
        return new Runnable() {
            @Override
            public void run() {
                danmakuContainer.removeView(v);
            }
        };
    }

    private void showLog(String msg) {
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
    }

    public static Fragment instance(){

        if(fm==null){
            fm = new CameraFragment();
        }

        return fm;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera,container,false);
        mContext = view.getContext();
        //根据父窗体getActivity()为fragment设置手势识别
        gesture = new GestureDetector(this.getActivity(), new MyOnGestureListener());
        //为fragment添加OnTouchListener监听器
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gesture.onTouchEvent(event);//返回手势识别触发的事件
            }
        });
        initDatas();

        init(view);
        return view;
    }

    private void initDatas() {

        for(int i = 0 ;i < 50 ;i++){
            list.add("hello"+i);
        }

    }


    private void init(View view) {

        // 获取窗口管理器
        WindowManager wm = getActivity().getWindowManager();
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        // 获取屏幕的宽和高
        display.getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;

        mSurfaceView = (SurfaceView) view.findViewById(R.id.surfaceView);
        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder arg0) {
                // 如果camera不为null，释放摄像头
                if (mCamera != null) {
                    if (isPreview)
                        mCamera.stopPreview();
                    mCamera.release();
                    mCamera = null;
                }
            }

            @Override
            public void surfaceCreated(SurfaceHolder arg0) {
                // 打开摄像头
                initCamera();

            }

            @Override
            public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2,
                                       int arg3) {
            }
        });
        mSurfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        mRelativeLayout = (RelativeLayout) view.findViewById(R.id.bottompanel);
        editText = (EditText)view.findViewById(R.id.etInput);
        btnAdd = view.findViewById(R.id.btnAdd);
        danmakuContainer = (LinearLayout)view.findViewById(R.id.danmakulayout);
        btnAdd.setOnClickListener(addBtnClickListener);

        initTransition(danmakuContainer);
        setTransition();

        //postMessage();


    }


    private void postMessage(){



        task = new TimerTask() {
            int i = 0;
            @Override
            public void run() {

                while(true){
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                }
            }
        };

        timer.schedule(task,100);


    }

    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            if(msg.what == 1){
                addContentToDanmaku("hello");
            }

            super.handleMessage(msg);
        }
    };

    DisplayMetrics metrics;
    private float getDip(int value){
        if(metrics==null){
            metrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        }
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value,metrics);
    }

    /**
     * 自定义动画效果
     */
    private void setTransition() {
        /**
         * view出现时 view自身的动画效果
         */
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 0f, 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 0f, 1f);
        ObjectAnimator multAnim = ObjectAnimator.ofPropertyValuesHolder(this, pvhX, pvhY, pvhZ)  //使用此种方法，可定义多属性同时修改的动画
                .setDuration(mTransitioner.getDuration(LayoutTransition.APPEARING));
        multAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                View view = (View) ((ObjectAnimator) animation).getTarget();  //使用动画监听器，主要是为了处理缩放的中心点修改到自己想要的位置
                view.setPivotX(0f);
                view.setPivotY(view.getHeight());
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mTransitioner.setAnimator(LayoutTransition.APPEARING, multAnim);

        /**
         * view 消失时，view自身的动画效果
         */
        pvhX = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);
        pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f, 0f);
        pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f, 0f);
        multAnim = ObjectAnimator.ofPropertyValuesHolder(this, pvhX, pvhY, pvhZ)
                .setDuration(mTransitioner.getDuration(LayoutTransition.DISAPPEARING));
        multAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                View view = (View) ((ObjectAnimator) animation).getTarget();
                view.setPivotX(0f);
                view.setPivotY(0f);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mTransitioner.setAnimator(LayoutTransition.DISAPPEARING, multAnim);

        /**
         * view出现时，导致整个布局改变的动画
         */

        /**
         * view消失，导致整个布局改变时的动画
         */
    }

    /**
     * 初始化容器动画
     */
    private void initTransition(ViewGroup layout) {
        mTransitioner = new LayoutTransition();
        layout.setLayoutTransition(mTransitioner);
    }




    private void initCamera() {

        if(!isPreview){
            mCamera = Camera.open();
            mCamera.setDisplayOrientation(90);
        }
        if(!isPreview && mCamera!=null){
            Camera.Parameters parameters = mCamera.getParameters();
            // 设置预览照片的大小
            parameters.setPreviewSize(screenWidth, screenHeight);
            // 设置预览照片时每秒显示多少帧的最小值和最大值
            parameters.setPreviewFpsRange(4, 10);
            // 设置照片的格式
            parameters.setPictureFormat(ImageFormat.JPEG);
            // 设置JPG照片的质量
            parameters.set("jpeg-quality", 85);
            // 设置照片的大小
            parameters.setPictureSize(screenWidth, screenHeight);
            // 通过SurfaceView显示取景画面
            try {
                mCamera.setPreviewDisplay(mSurfaceView.getHolder());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // 开始预览
            mCamera.startPreview();
            isPreview = true;
        }

    }



    Camera.AutoFocusCallback autoFocusCallback = new Camera.AutoFocusCallback() {

        @Override
        public void onAutoFocus(boolean arg0, Camera arg1) {
            if (arg0) {
                // takePicture()方法需要传入三个监听参数
                // 第一个监听器；当用户按下快门时激发该监听器
                // 第二个监听器；当相机获取原始照片时激发该监听器
                // 第三个监听器；当相机获取JPG照片时激发该监听器
                mCamera.takePicture(new Camera.ShutterCallback() {

                    @Override
                    public void onShutter() {
                        // 按下快门瞬间会执行此处代码
                    }
                }, new Camera.PictureCallback() {

                    @Override
                    public void onPictureTaken(byte[] arg0, Camera arg1) {
                        // 此处代码可以决定是否需要保存原始照片信息
                    }
                }, myJpegCallback);
            }

        }
    };
    Camera.PictureCallback myJpegCallback = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            // 根据拍照所得的数据创建位图
            final Bitmap bm = BitmapFactory.decodeByteArray(data, 0,
                    data.length);
            // 加载布局文件
            View saveDialog = LayoutInflater.from(mContext).inflate(R.layout.save, null);
            final EditText potoName = (EditText) saveDialog
                    .findViewById(R.id.photoNmae);
            // 获取saveDialog对话框上的ImageView组件
            ImageView show = (ImageView) saveDialog.findViewById(R.id.show);
            // 显示刚刚拍得的照片
            show.setImageBitmap(bm);
            // 使用AlertDialog组件
            new AlertDialog.Builder(mContext)
                    .setView(saveDialog)
                    .setNegativeButton("取消", null)
                    .setPositiveButton("分享",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0,
                                                    int arg1) {
                                    // 创建一个位于SD卡上的文件
                                    File file = new File(Environment
                                            .getExternalStorageDirectory()
                                            + "/"
                                            + potoName.getText().toString()
                                            + ".jpg");
                                    FileOutputStream  fileOutStream=null;
                                    try {
                                        fileOutStream=new FileOutputStream(file);
                                        //把位图输出到指定的文件中
                                        bm.compress(Bitmap.CompressFormat.JPEG, 100, fileOutStream);
                                        fileOutStream.close();
                                    } catch (IOException io) {
                                        io.printStackTrace();
                                    }

                                }
                            }).show();
            //重新浏览
            camera.stopPreview();
            camera.startPreview();
            isPreview=true;
        }
    };


    //设置手势识别监听器
    private class MyOnGestureListener extends GestureDetector.SimpleOnGestureListener
    {
        @Override//此方法必须重写且返回真，否则onFling不起效
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            mCamera.autoFocus(autoFocusCallback);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float offsetY = e1.getX()- e2.getX();
            float offsetX = e1.getX() - e2.getX();
            if((offsetY >20)&&Math.abs(velocityY)>20){
                mRelativeLayout.setVisibility(View.GONE);
                return true;
            }else if((offsetY < -20)&&Math.abs(velocityY)>20){
                mRelativeLayout.setVisibility(View.VISIBLE);
                return true;
            }
            return false;
        }
    }


}
