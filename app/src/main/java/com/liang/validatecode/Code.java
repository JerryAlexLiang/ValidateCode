package com.liang.validatecode;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


import java.util.Random;

/**
 * 创建日期：2017/2/8 on 11:24
 * 作者:杨亮 liangyang
 * 描述:随机验证码
 */
public class Code {

    //随机数数组
    public static final char[] CHARS = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };


    private static Code bmpCode;

    /**
     * 接口回调
     * @return
     */
    public static Code getInstance() {
        if (bmpCode == null) {
            bmpCode = new Code();
        }
        return bmpCode;
    }

    //default settings
    //验证码默认随机数的个数
    public static final int DEFAULT_CODE_LENGTH = 4;
    //默认字体个数
    public static final int DEFAULT_FONT_SIZE = 25;
    //默认线条的个数
    public static final int DEFAULT_LINE_NUMBER = 5;
    //padding的值
    public static final int BASE_PADDING_LEFT = 10, RANGE_PADDING_LEFT = 15, BASE_PADDING_TOP = 15, RANGE_PADDING_TOP = 20;
    //验证码的默认宽度
    public static final int DEFAULT_WIDTH = 100, DEFAULT_HEIGHT = 40;

    //settings decided by the layout xml

    //canvas width and height
    private int width = DEFAULT_WIDTH, height = DEFAULT_HEIGHT;

    //random word space and padding_top
    private int base_padding_left = BASE_PADDING_LEFT, range_padding_left = RANGE_PADDING_LEFT,
            base_padding_top = BASE_PADDING_TOP, range_padding_top = RANGE_PADDING_TOP;

    //number of chars,lines;font size
    private int codeLength = DEFAULT_CODE_LENGTH, line_number = DEFAULT_LINE_NUMBER, font_size = DEFAULT_FONT_SIZE;

    //variables
    private String code;
    private int padding_left, padding_top;
    private Random random = new Random();

    //验证码图片
    public Bitmap createBitmap() {
        padding_left = 0;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        //画布
        Canvas canvas = new Canvas(bitmap);
        //生成验证码
        code = createCode();
        canvas.drawColor(Color.WHITE);
        //画笔
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(font_size);

        //画验证码
        for (int i = 0; i < code.length(); i++) {
            randomTextStyle(paint);
            randomPadding();
            canvas.drawText(code.charAt(i) + "", padding_left, padding_top, paint);
        }

        //画线条
        for (int i = 0; i < line_number; i++) {
            drawLine(canvas, paint);
        }

        canvas.save(Canvas.ALL_SAVE_FLAG);//保存
        canvas.restore();
        return bitmap;
    }

    public String getCode() {
        return code;
    }

    /**
     * 生成验证码
     */
    private String createCode() {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < codeLength; i++) {
            buffer.append(CHARS[random.nextInt(CHARS.length)]);
        }
        return buffer.toString();
    }

    /**
     * 生成随机颜色
     */
    private int randomColor() {
        return randomColor(1);
    }

    private int randomColor(int rate) {
        int red = random.nextInt(256) / rate;
        int green = random.nextInt(256) / rate;
        int blue = random.nextInt(256) / rate;
        return Color.rgb(red, green, blue);
    }

    /**
     * 画干扰线
     */
    private void drawLine(Canvas canvas, Paint paint) {
        int color = randomColor();
        int startX = random.nextInt(width);
        int startY = random.nextInt(height);
        int stopX = random.nextInt(width);
        int stopY = random.nextInt(height);
        paint.setStrokeWidth(1);
        paint.setColor(color);
        canvas.drawLine(startX, startY, stopX, stopY, paint);
    }


    /**
     * 随机生成文字样式，颜色，粗细，倾斜度
     */
    private void randomTextStyle(Paint paint) {
        int color = randomColor();
        paint.setColor(color);
        paint.setFakeBoldText(random.nextBoolean());//true为粗体，false为非粗体
        float skewX = random.nextInt(11) / 10;
        skewX = random.nextBoolean() ? skewX : -skewX;
        paint.setTextSkewX(skewX);//float类型参数，负数表示右斜，整数为左斜
//        paint.setUnderlineText(true);//true为下划线，false为非下划线
//        paint.setStrikeThruText(true);//true为删除线，false为非删除线
    }

    /**
     * 随机生成padding的值
     */
    private void randomPadding() {
        padding_left += base_padding_left + random.nextInt(range_padding_left);
        padding_top = base_padding_top + random.nextInt(range_padding_top);
    }
}
