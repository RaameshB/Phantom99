package org.firstinspires.ftc.teamcode.util.RobotControlClasses;

import org.firstinspires.ftc.teamcode.util.PhantomSecretStuff.vision.easyopencv.javaPipelines.PipelineMkTwo;
import org.firstinspires.ftc.teamcode.util.RobotConfig;
import org.firstinspires.ftc.teamcode.util.enums.Positions;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

public class CvWebcamController {
    OpenCvWebcam webcam;
    RobotConfig hardware;
    PipelineMkTwo pipeline = new PipelineMkTwo();
    public boolean hasStreamStarted = false;

    public CvWebcamController (RobotConfig robot) {
        hardware = robot;
        webcam = hardware.cvWebcam;
        webcam.setPipeline(pipeline);
    }


    public void startAsyncStream() {
        if (!hardware.isInit) {
            throw new IllegalStateException("Robot may not be properly init (likely a programming issue)");
        }
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                /*
                 * Tell the webcam to start streaming images to us! Note that you must make sure
                 * the resolution you specify is supported by the camera. If it is not, an exception
                 * will be thrown.
                 *
                 * Keep in mind that the SDK's UVC driver (what OpenCvWebcam uses under the hood) only
                 * supports streaming from the webcam in the uncompressed YUV image format. This means
                 * that the maximum resolution you can stream at and still get up to 30FPS is 480p (640x480).
                 * Streaming at e.g. 720p will limit you to up to 10FPS and so on and so forth.
                 *
                 * Also, we specify the rotation that the webcam is used in. This is so that the image
                 * from the camera sensor can be rotated such that it is always displayed with the image upright.
                 * For a front facing camera, rotation is defined assuming the user is looking at the screen.
                 * For a rear facing camera or a webcam, rotation is defined assuming the camera is facing
                 * away from the user.
                 */
                webcam.startStreaming(1920, 720, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {
                /*
                 * This will be called if the camera could not be opened
                 */
            }
        });

        hasStreamStarted = true;
    }

    public Positions getPos() {

        if (!hasStreamStarted) {
            throw new IllegalStateException("The webcam has seemingly isn't on... (likely a programming issue)");
        }

        return pipeline.getPos();
    }

}
