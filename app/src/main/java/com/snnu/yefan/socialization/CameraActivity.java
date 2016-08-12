package com.snnu.yefan.socialization;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.util.SparseArray;
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
import android.view.animation.AccelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.opendanmaku.DanmakuItem;
import com.opendanmaku.DanmakuView;
import com.opendanmaku.IDanmakuItem;
import com.snnu.yefan.bean.Info;
import com.snnu.yefan.custom.CustomViewPager;
import com.snnu.yefan.custom.RadarViewGroup;
import com.snnu.yefan.utils.FixedSpeedScroller;
import com.snnu.yefan.utils.LogUtil;
import com.snnu.yefan.utils.ZoomOutPageTransformer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by zero on 2016/7/9.
 */
public class CameraActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RadarViewGroup.IRadarClickListener, View.OnClickListener{

    private static CameraActivity fm = null;
    private Context mContext = this;
    private Camera mCamera;
    // 是否存在预览中
    private boolean isPreview = false;
    private int screenWidth, screenHeight;
    private SurfaceView mSurfaceView;

    private DanmakuView mDanmakuView;
    private GestureDetector gesture; //手势识别
    private ImageView imgLock;
    private ImageView imgRard;
    private ImageView imgSelect;
    private ImageView imgBarrage;
    private ImageView imgClose;
    private RelativeLayout mRelativeLayout;
    private LayoutInflater mLayoutInflater = null;
    private boolean isLock = false;
    private  boolean isRardOn = false;
    private boolean isBarrageOn = true;

    private CustomViewPager viewPager;
    private RelativeLayout ryContainer;
    private RadarViewGroup radarViewGroup;
    private int[] mImgs = {R.drawable.len, R.drawable.leo, R.drawable.lep,
            R.drawable.leq, R.drawable.ler, R.drawable.les, R.drawable.mln, R.drawable.mmz, R.drawable.mna,
            R.drawable.mnj, R.drawable.leo, R.drawable.leq, R.drawable.les, R.drawable.lep};
    private String[] mNames = {"ImmortalZ", "唐马儒", "王尼玛", "张全蛋", "蛋花", "王大锤", "叫兽", "哆啦A梦"};
    private int mPosition;
    private FixedSpeedScroller scroller;
    private SparseArray<Info> mDatas = new SparseArray<>();
//    private View.OnClickListener addBtnClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            final String inputStr = editText.getText().toString();
//            if(TextUtils.isEmpty(inputStr)){
//                showLog("请输入弹幕内容再发送！");
//                return;
//            }
//            IDanmakuItem item = new DanmakuItem(mContext, new SpannableString(inputStr), mDanmakuView.getWidth(),0,R.color.my_item_color,0,1);
////                    IDanmakuItem item = new DanmakuItem(this, input, mDanmakuView.getWidth());
////                    item.setTextColor(getResources().getColor(R.color.my_item_color));
////                    item.setTextSize(14);
////                    item.setTextColor(textColor);
//            mDanmakuView.addItemToHead(item);
//            editText.setText("");
//
//        }
//    };

    private String danmu[] = {"╮(￣▽￣\")╭ 弹幕君已阵亡","前面那个人，出来，让我们谈谈人生。","高能预警","O(∩_∩)O~~","。。。。。。","说高能的出来","注意这不是演习、","请收下我的膝盖","└(^O^)┘红红火火恍恍惚惚","看地图竟然迷路了","求搭讪","666666666666666","已备好瓜子","未完待续","今天偶遇了一个帅哥o(*￣▽￣*)o","说好的羊肉泡馍呢","本帅到此一游","第211天","欢迎来到~~","慈父痴迷网游 孝子苦劝无果","(°ー°〃)你咋不上天呢","以上赞助均已破产","只有会员才知道的世界","╭(°A°`)╮大写的懵逼","警察叔叔，就是这个人","走开，让专业的来","我和我的小伙伴门都惊呆了","人与人之间最基本的信任在哪里","说福利的先拖出去打一顿","23333333333333","大王叫我来巡山……伊尔呦，伊尔伊尔哟……","我从未见过如此厚颜无耻之人","天下有情人终成兄妹","（°Д°）Ъ大赞！","你已经来不及走了","浪费了我xx分钟的人生","( ゜- ゜)つロ 乾杯~","好像并没有什么不对","发条弹幕压压惊Σ(っ °Д °;)っ","我来增加弹幕！！！*罒▽罒*","怒刷存在感！(ง •̀_•́)ง","✿✿ヽ(ﾟ▽ﾟ)ノ✿为你撒花！","这么可爱一定是男孩纸。","（°Д°）ъ小赞！","我读的书少，你别骗我","一旦接受了这种设定，还是蛮带感的","请允许我做一个悲伤的表情","(ノ°ο°)ノ前方高能预警","好热，快被蒸熟了。","ヾ(≧O≦)〃嗷~恭喜你发现镇店之宝","(*≧▽≦)ツ┏━┓⌒ 〓▇3:) 睡你麻痹起来嗨","(￣ε(#￣)☆╰╮(ಥ_ಥ)嗨你麻痹我要睡觉","弹幕填充完毕","๑乛◡乛๑看到了不得了的东西","您已切换至2G/3G/4G网络","|二|三|四|二|三|四|二|三|四|@|东|11|伍|伍|\n" +
            "   |萬|萬|萬|萬|萬|萬|萬|萬|萬|@|风|11|萬|萬|","这个点儿了，还有人吗？？？？","阴沉的雨天，恰逢不高涨的心情，在这样的人咖啡馆，再合适不过了......","环境优美……一路走来，山水环绕，美极了，就是未上玻璃栈道一观甚是遗憾！","请收下我的膝盖！","我保证不打死你！！！","我会把你往死里打！！","这么文艺的饮品店，值得驻足。","不是不想教你,是梗太多了...","慢慢看弹幕，百度一下基本能懂的"};
    private void initData() {
        for (int i = 0; i < mImgs.length; i++) {
            Info info = new Info();
            info.setPortraitId(mImgs[i]);
            info.setAge(((int) Math.random() * 25 + 16) + "岁");
            info.setName(mNames[(int) (Math.random() * mNames.length)]);
            info.setSex(i % 3 == 0 ? false : true);
            info.setDistance(Math.round((Math.random() * 10) * 100) / 100);
            mDatas.put(i, info);
        }
    }

    private List<IDanmakuItem> initItems() {
        List<IDanmakuItem> list = new ArrayList<>();
        for (int i = 0; i < 65; i++) {
            IDanmakuItem item = new DanmakuItem(this, new SpannableString(danmu[i]), mDanmakuView.getWidth(), 0, 0, 0, 1f);
            //IDanmakuItem item = new DanmakuItem(mContext, i + " : plain text danmuku", mDanmakuView.getWidth());
            list.add(item);
        }

        String msg = " : text with image   ";
        for (int i = 0; i < 65; i++) {
            ImageSpan imageSpan = new ImageSpan(this, R.drawable.em);
            SpannableString spannableString = new SpannableString(danmu[i]);
            spannableString.setSpan(imageSpan, spannableString.length() - 2, spannableString.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            IDanmakuItem item = new DanmakuItem(this, spannableString, mDanmakuView.getWidth(), 0, 0, 0, 1.5f);
            list.add(item);
        }
        return list;
    }




    private void showLog(String msg) {
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        //根据父窗体getActivity()为fragment设置手势识别
        gesture = new GestureDetector(this, new MyOnGestureListener());
        initData();
        init();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gesture.onTouchEvent(event);//返回手势识别触发的事件
    }

    private void init() {

        // 获取窗口管理器
        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        // 获取屏幕的宽和高
        display.getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;

        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);
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



        //mRelativeLayout = (RelativeLayout) view.findViewById(R.id.bottompanel);
        mDanmakuView = (DanmakuView) findViewById(R.id.danmakuView);
        viewPager = (CustomViewPager) findViewById(R.id.vp);
        radarViewGroup = (RadarViewGroup) findViewById(R.id.radar);
        ryContainer = (RelativeLayout) findViewById(R.id.ry_container);
        imgLock = (ImageView) findViewById(R.id.img_lock);
        imgRard = (ImageView) findViewById(R.id.img_rard);
        imgSelect = (ImageView) findViewById(R.id.img_select);
        imgBarrage = (ImageView) findViewById(R.id.img_barrage);
        imgClose = (ImageView) findViewById(R.id.img_close);

        imgLock.setOnClickListener(this);
        imgRard.setOnClickListener(this);
        imgSelect.setOnClickListener(this);
        imgBarrage.setOnClickListener(this);
        imgClose.setOnClickListener(this);
        List<IDanmakuItem> list = initItems();
        Collections.shuffle(list);
        mDanmakuView.addItem(list, true);

        /**
         * 将Viewpager所在容器的事件分发交给ViewPager
         */
        ryContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return viewPager.dispatchTouchEvent(event);
            }
        });
        ViewpagerAdapter mAdapter = new ViewpagerAdapter();
        viewPager.setAdapter(mAdapter);
        //设置缓存数为展示的数目
        viewPager.setOffscreenPageLimit(mImgs.length);
        viewPager.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.viewpager_margin));
        //设置切换动画
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        viewPager.addOnPageChangeListener(this);
        setViewPagerSpeed(250);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                radarViewGroup.setDatas(mDatas);
            }
        }, 1500);
        radarViewGroup.setiRadarClickListener(this);
    }

    /**
     * 设置ViewPager切换速度
     *
     * @param duration
     */
    private void setViewPagerSpeed(int duration) {
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            scroller = new FixedSpeedScroller(this, new AccelerateInterpolator());
            field.set(viewPager, scroller);
            scroller.setmDuration(duration);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRadarItemClick(int position) {
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mPosition = position;
    }

    @Override
    public void onPageSelected(int position) {
        radarViewGroup.setCurrentShowItem(position);
        LogUtil.m("当前位置 " + mPosition);
        LogUtil.m("速度 " + viewPager.getSpeed());
        //当手指左滑速度大于2000时viewpager右滑（注意是item+2）
        if (viewPager.getSpeed() < -1800) {

            viewPager.setCurrentItem(mPosition + 2);
            LogUtil.m("位置 " + mPosition);
            viewPager.setSpeed(0);
        } else if (viewPager.getSpeed() > 1800 && mPosition > 0) {
            //当手指右滑速度大于2000时viewpager左滑（注意item-1即可）
            viewPager.setCurrentItem(mPosition - 1);
            LogUtil.m("位置 " + mPosition);
            viewPager.setSpeed(0);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_lock:
                if(isLock){
                    //TODO
                    imgLock.setImageResource(R.mipmap.img_unlock);
                    isLock = false;
                }else{
                    //TODO
                    imgLock.setImageResource(R.mipmap.img_lock);
                    isLock = true;
                }
                break;
            case R.id.img_rard:
                if(isRardOn){
                    radarViewGroup.setVisibility(View.GONE);
                    viewPager.setVisibility(View.GONE);
                    isRardOn = false;
                }else{
                    radarViewGroup.setVisibility(View.VISIBLE);
                    viewPager.setVisibility(View.VISIBLE);
                    isRardOn = true;
                }
                break;
            case R.id.img_select:
                //TODO
                selectType();
                break;
            case R.id.img_close:
                //TODO
                MainActivity.setCurrentPager(0);
                finish();
                break;
            case R.id.img_barrage:
                if(isBarrageOn){
                    mDanmakuView.setVisibility(View.GONE);
                    imgBarrage.setImageResource(R.mipmap.img_barrage_press);
                    isBarrageOn = false;
                }else{
                    mDanmakuView.setVisibility(View.VISIBLE);
                    imgBarrage.setImageResource(R.mipmap.img_barrage);
                    isBarrageOn = true;
                }
                break;
            default:
                break;
        }
    }

    private void selectType() {

        new AlertDialog.Builder(this)
                .setView(R.layout.view_select)
                .show();

    }

    class ViewpagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mImgs.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            final Info info = mDatas.get(position);
            //设置一大堆演示用的数据，麻里麻烦~~
            View view = LayoutInflater.from(mContext).inflate(R.layout.viewpager_layout, null);
            ImageView ivPortrait = (ImageView) view.findViewById(R.id.iv);
            ImageView ivSex = (ImageView) view.findViewById(R.id.iv_sex);
            TextView tvName = (TextView) view.findViewById(R.id.tv_name);
            TextView tvDistance = (TextView) view.findViewById(R.id.tv_distance);
            tvName.setText(info.getName());
            tvDistance.setText(info.getDistance() + "km");
            ivPortrait.setImageResource(info.getPortraitId());
            if (info.getSex()) {
                ivSex.setImageResource(R.drawable.girl);
            } else {
                ivSex.setImageResource(R.drawable.boy);
            }
            ivPortrait.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "这是 " + info.getName() + " >.<", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext,MessageBoardActivity.class);
                    startActivity(intent);
                }
            });
            container.addView(view);
            return view;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

        @Override
        public void onPause() {
            mDanmakuView.hide();
            super.onPause();
        }

        @Override
        public void onStop() {
            super.onStop();
            if (mCamera != null) {
                mCamera.stopPreview();
            }
        }

        @Override
        public void onResume() {
            if (mCamera != null) {
                mCamera.startPreview();
            }
            mDanmakuView.show();
            super.onResume();
        }


        @Override
        public void onDestroy() {
            mDanmakuView.clear();
            super.onDestroy();
        }

        DisplayMetrics metrics;

        private float getDip(int value) {
            if (metrics == null) {
                metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);
            }
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, metrics);
        }


        private void initCamera() {

            if (!isPreview) {
                mCamera = Camera.open();
                mCamera.setDisplayOrientation(90);
            }
            if (!isPreview && mCamera != null) {
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
                final Camera mCamera = camera;
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
                //重新浏览
                mCamera.stopPreview();
                new AlertDialog.Builder(mContext)
                        .setView(saveDialog)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mCamera.startPreview();
                                isPreview = true;
                            }
                        })
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
                                        FileOutputStream fileOutStream = null;
                                        try {
                                            fileOutStream = new FileOutputStream(file);
                                            //把位图输出到指定的文件中
                                            bm.compress(Bitmap.CompressFormat.JPEG, 100, fileOutStream);
                                            fileOutStream.close();
                                        } catch (IOException io) {
                                            io.printStackTrace();
                                        }

                                    }
                                }).show();

            }
        };



    //设置手势识别监听器
    private class MyOnGestureListener extends GestureDetector.SimpleOnGestureListener {
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
            float offsetY = e1.getX() - e2.getX();
            float offsetX = e1.getX() - e2.getX();
            if ((offsetY > 20) && Math.abs(velocityY) > 20) {
               // mRelativeLayout.setVisibility(View.GONE);
                return true;
            } else if ((offsetY < -20) && Math.abs(velocityY) > 20) {
                //mRelativeLayout.setVisibility(View.VISIBLE);
                return true;
            }
            return false;
        }
    }

}
