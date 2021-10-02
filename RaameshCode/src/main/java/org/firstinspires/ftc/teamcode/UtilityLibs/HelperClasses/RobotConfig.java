package org.firstinspires.ftc.teamcode.UtilityLibs.HelperClasses;

import android.os.Environment;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.teamcode.UtilityLibs.Vision.opencv.PhantomPipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

import java.io.File;


public class RobotConfig {

    boolean isInit = false;

    HardwareMap hwMap;
    LinearOpMode ln;
    TelemetryHelper telemetry;
    public chassisType robotType;
    boolean chassisMoving = false;

    public PhantomPipeline openCVPipeline = new PhantomPipeline();

    public RobotConfig (HardwareMap ahwMap, LinearOpMode lnOpMode, TelemetryHelper tel, chassisType bot) {
        hwMap = ahwMap;
        ln = lnOpMode;
        telemetry = tel;
        robotType = bot;
    }

    public RobotConfig (HardwareMap ahwMap, LinearOpMode lnOpMode, chassisType bot) {
        hwMap = ahwMap;
        ln = lnOpMode;
        robotType = bot;
    }

    public RobotConfig () {

    }

    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;
    public DcMotor leftMotor;
    public DcMotor rightMotor;
    public OpenCvWebcam webcam;

    public VuforiaLocalizer vuforia;

    public BNO055IMU imu;

    public void initREEEE(HardwareMap hwMap) {
        frontRight = hwMap.get(DcMotor.class, "front_right");
        frontLeft = hwMap.get(DcMotor.class, "front_left");
        backRight = hwMap.get(DcMotor.class, "back_right");
        backLeft = hwMap.get(DcMotor.class, "back_left");

        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        DcMotor.ZeroPowerBehavior behaviour = DcMotor.ZeroPowerBehavior.BRAKE;

        frontRight.setZeroPowerBehavior(behaviour);
        frontLeft.setZeroPowerBehavior(behaviour);
        backRight.setZeroPowerBehavior(behaviour);
        frontRight.setZeroPowerBehavior(behaviour);
    }

    public void init() {

        autoMotorFinder();

        setMotorDirection(DcMotorSimple.Direction.FORWARD);

        setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior.BRAKE);

        stopMotors();

//        startChassisMovementWatcher();


        calibrate();

        webCamConfig();

        isInit = true;
    }

    public void initNoCal() {

        autoMotorFinder();

        setMotorDirection(DcMotorSimple.Direction.FORWARD);

        setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior.BRAKE);

        stopMotors();

//        startChassisMovementWatcher();


//        calibrate();

        webCamConfig();

        isInit = true;
    }

    public void webCamConfig() {
        int cameraMonitorViewId = hwMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hwMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hwMap.get(WebcamName.class, "webcam"), cameraMonitorViewId);
        webcam.setPipeline(openCVPipeline);

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "Abu5/av/////AAABmTNaRGEbdERoqGEl9H8dx7Yh+NdUtAjxh7O7tARchl5uqVaO2JQAhzPrAJcMssG7JhrtmXnKunIN3Bnow8rPl9KE61z9p4Tmwf7pVYEoMhvlDdlkOvxJUuH+O9yY6Zo7jv8QTR08I3JAQnMcRFNyoQpjPkfN8z8rPs0jgwgox5OwUlIHJYCYwCh1xPpzeGKAW58KZ1+xksxTwaK61anH7ThhxVbr/nlUAHiXTwjb1FzeJvpnPXkJRSg7GmQpYcKE+FthyUS6du+LEHgd1V3qF31w1wqvafz1nMMVUyn57mh9ZFNGrAx21pF5eHJXqHczxU4lLPLUk6gGl8in3zLpeIONz1PvXoF47pMiyPweZzhk";

//        parameters.cameraName = hwMap.get(WebcamName.class, "webcam");
//        vuforia = ClassFactory.getInstance().createVuforia(parameters);
//        vuforia.enableConvertFrameToBitmap();
//        File dir = new File("/storage/self/primary/DCIM/image") ;
//        AppUtil.getInstance().ensureDirectoryExists(dir);

    }

    public void startWebcamStream() {
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
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
                webcam.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {

            }
        });
    }

    void startChassisMovementWatcher() {
        Threader thread = new Threader();

        thread.start();
    }

    public boolean isChassisMoving() {
        return chassisMoving;
    }

    void autoMotorFinder() {
        if (robotType == chassisType.PUSHBOT) {
            findMotors("RM", "LM");
        } else {
            findMotors("front_right","front_left","back_right","back_left");
        }
    }

    public void findMotors(String frontRightName, String frontLeftName, String backRightName, String backLeftName) {
        frontRight = hwMap.get(DcMotor.class, frontRightName);
        frontLeft = hwMap.get(DcMotor.class, frontLeftName);
        backRight = hwMap.get(DcMotor.class, backRightName);
        backLeft = hwMap.get(DcMotor.class, backLeftName);

    }
    public void findMotors(String rightName, String leftName) {
        rightMotor = hwMap.get(DcMotor.class, rightName);
        leftMotor = hwMap.get(DcMotor.class, leftName);
    }

    public void stopMotors() {
        switch (robotType) {
            case PUSHBOT:
                rightMotor.setPower(0);
                leftMotor.setPower(0);
                return;
        }
        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
        chassisMoving = false;
    }

    public void setMotorDirection(DcMotorSimple.Direction direction) {
        switch (robotType) {
            case PUSHBOT:
                switch (direction) {
                    case FORWARD:
                        rightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
                        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
                    case REVERSE:
                        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
                        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
                }
                return;
        }
        switch (direction) {
            default:
                throw new IllegalArgumentException();
            case FORWARD:
                frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
                frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
                backRight.setDirection(DcMotorSimple.Direction.FORWARD);
                backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
            case REVERSE:
                frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
                frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
                backLeft.setDirection(DcMotorSimple.Direction.FORWARD);
                backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        }
    }

    public void setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior behaviour) {
        switch (robotType) {
            case PUSHBOT:
                rightMotor.setZeroPowerBehavior(behaviour);
                leftMotor.setZeroPowerBehavior(behaviour);
        }
        frontRight.setZeroPowerBehavior(behaviour);
        frontLeft.setZeroPowerBehavior(behaviour);
        backRight.setZeroPowerBehavior(behaviour);
        frontRight.setZeroPowerBehavior(behaviour);
    }

    public Orientation angles;

    boolean stopper = false;
    boolean stopped = true;

    double degX;
    double degY;
    double degZ;

    protected class Helpering extends Thread {
        @Override
        public void run() {
            stopped = false;
            while (!ln.isStopRequested() && !stopper) {
                telemetry.modData("Z: ", angles.firstAngle);
                telemetry.modData("Y: ", angles.secondAngle);
                telemetry.modData("X: ", angles.thirdAngle);
                degZ = angles.firstAngle;
                degY = angles.secondAngle;
                degX = angles.thirdAngle;
            }
            stopped = true;
        }
    }

    Helpering thread = new Helpering();

    public void calibrate() {
        imu = hwMap.get(BNO055IMU.class, "imu");


        BNO055IMU.Parameters para = new BNO055IMU.Parameters();
        para.angleUnit = BNO055IMU.AngleUnit.DEGREES;


        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);


        imu.initialize(para);

        telemetry.addLine("Gyro Calibrating...");


        while (!imu.isGyroCalibrated() && !imu.isAccelerometerCalibrated() && !ln.isStopRequested()) {
            ln.idle();
            ln.sleep(50);
        }


        telemetry.modLine("Gyro Calibrating...", "Gyro Calibration Complete!");

    }

    public Orientation             lastAngles = new Orientation();
    public double globalAngle;



    public double getAngle() {
        // We experimentally determined the Y axis is the axis we want to use for heading angle.
        // We have to process the angle because the imu works in euler angles so the Z axis is
        // returned as 0 to +180 or 0 to -180 rolling back to -179 or +179 when rotation passes
        // 180 degrees. We detect this transition and track the total cumulative angle of rotation.

        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;

        if (deltaAngle < -180) {
            deltaAngle += 360;
        }
        else if (deltaAngle > 180) {
            deltaAngle -= 360;
        }
        globalAngle += deltaAngle;

        lastAngles = angles;

        return globalAngle;
    }

    public void getGravity() {

    }

    public void enableImuTelemetry() {
        telemetry.addLine("Angles:");
        telemetry.addData("X: ", angles.firstAngle);
        telemetry.addData("Y: ", angles.secondAngle);
        telemetry.addData("Z: ", angles.thirdAngle);
        stopper = false;
        thread.start();
    }

    public void disableTelemetry() {
        stopper = true;
        while (!stopped);
        telemetry.removeLine("Angles:");
        telemetry.removeData("X: ");
        telemetry.removeData("Y: ");
        telemetry.removeData("Z: ");
    }

    boolean threadLoopBreak = false;

    class Threader extends Thread {
        @Override
        public void run() {
            switch (robotType) {

                case MECANUM:
                    while (!ln.isStopRequested()) {
                        if (frontLeft.getPower() != 0 || frontRight.getPower() != 0 || backLeft.getPower() != 0 || backRight.getPower() != 0) {
                            chassisMoving = true;
                            while (!threadLoopBreak) {
                                if (frontLeft.getPower() == 0 && frontRight.getPower() == 0 && backLeft.getPower() == 0 && backRight.getPower() == 0) {
                                    chassisMoving = false;
                                    threadLoopBreak = true;
                                }
                                yield();
                            }
                            threadLoopBreak = false;
                        }
                        yield();
                    }
                case PUSHBOT:
                    while (!ln.isStopRequested()) {
                        if (leftMotor.getPower() != 0 || rightMotor.getPower() != 0) {
                            chassisMoving = true;
                            while (!threadLoopBreak) {
                                if (frontLeft.getPower() == 0 && frontRight.getPower() == 0) {
                                    chassisMoving = false;
                                    threadLoopBreak = true;
                                }
                                yield();
                            }
                            threadLoopBreak = false;
                        }
                        yield();
                    }
                default:
                    break;
            }
        }
    }


}
