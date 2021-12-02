package org.firstinspires.ftc.teamcode.util.Vision.opencv;

import android.graphics.Bitmap;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.dnn.Dnn;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgcodecs.Imgcodecs;

public class PhantomPipeline extends org.openftc.easyopencv.OpenCvPipeline {

    int i = 0;

    Mat lastMat;

    Bitmap RGBBitMap = Bitmap.createBitmap(640, 480, Bitmap.Config.ARGB_8888);

    @Override
    public Mat processFrame(Mat input) {

        Mat edgeDetect = new Mat();

        Mat blur = new Mat();

        Imgproc.medianBlur(input, blur, 11);

        Mat rgb = new Mat();

        Imgproc.cvtColor(blur, rgb, Imgproc.COLOR_BGR2RGB);

        

        try {
            Utils.matToBitmap(rgb, RGBBitMap);
        } catch (Exception e) {

        }


//        Imgcodecs.imwrite(
//                "/storage/self/primary/DCIM/image" + String.valueOf(i) + ".jpg",
//                rgb
//        );

//        if (i >= 500) {
//            i = 0;
//        }

        lastMat = rgb;

        return blur;
    }

    public Mat getLastMat() {
        return lastMat;
    }

    public Bitmap getRGBBitMap() {
        return RGBBitMap;
    }
}
