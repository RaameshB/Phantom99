package org.firstinspires.ftc.teamcode.util.RobotControlClasses;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.util.RobotConfig;
import org.firstinspires.ftc.teamcode.util.enums.SliderEnum;

public class SliderController {

    RobotConfig robot;
    LinearOpMode linearOpMode;
    private ElapsedTime runtime = new ElapsedTime();

    //TODO: Edit this
    private final int BOT_POSITION = 0;
    private final int MID_POSITION = 50;
    private final int TOP_POSITION = 100;
    
    public SliderController(RobotConfig robot, LinearOpMode linearOpMode) {
        this.robot = robot;
        robot.slider.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.slider.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    static final double COUNTS_PER_REVOLUTION = 288;
    //    static final double SPROCKET_REDUCTION = 1.5;
    //    static final double GEAR_REDUCTION = ;
    static final double SPOOL_DIAMETER_INCHES = 1.5;
    static final double COUNTS_PER_INCH = (COUNTS_PER_REVOLUTION) / (SPOOL_DIAMETER_INCHES * Math.PI);
    
    public void goToPosition(double speed, SliderEnum pos, double timeoutS) {
        int target;
        switch (pos) {
            case TOP:
                target = TOP_POSITION;
                break;
            case MID:
                target = MID_POSITION;
                break;
            case BOT:
                target = BOT_POSITION;
                break;
            default:
                target = BOT_POSITION;
        }
        robot.slider.setTargetPosition(target);
        robot.slider.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.slider.setPower(Math.abs(speed));
        
        while (linearOpMode.opModeIsActive() &&
              (runtime.seconds() < timeoutS) &&
              (robot.slider.isBusy())) {

                // Display it for the driver.
                linearOpMode.telemetry.addData("Path1",  "Running to %7d", target);
                linearOpMode.telemetry.addData("Path2",  "Running at %7d",
                        robot.slider.getCurrentPosition()
//                        ,robot.rightDrive.motor1.getCurrentPosition());
                );
                linearOpMode.telemetry.update();
            }

            // Stop all motion;
            robot.slider.setPower(0);

//             robot.slider.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            robot.leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            // Turn off RUN_TO_POSITION
            robot.slider.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                              
    }
    
                              
    // Don't use, potentionally dangerous
    @Deprecated
    public void encoderSlider(double speed,
                              double inches,
                              double timeoutS) {
        int newTarget;
        //        int newRightTarget;

        // Ensure that the opmode is still active
        if (linearOpMode.opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newTarget = robot.slider.getCurrentPosition() + (int)(inches * COUNTS_PER_INCH);
            //            newRightTarget = robot.rightDrive.motor1.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            robot.slider.setTargetPosition(newTarget);
            //            robot.rightDrive.setTargetPosition(newRightTarget);


            // Turn On RUN_TO_POSITION
            robot.slider.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            //            robot.rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.slider.setPower(Math.abs(speed));
            //            robot.rightDrive.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (linearOpMode.opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.slider.isBusy())) {

                // Display it for the driver.
                linearOpMode.telemetry.addData("Path1",  "Running to %7d", newTarget);
                linearOpMode.telemetry.addData("Path2",  "Running at %7d",
                        robot.slider.getCurrentPosition()
                        //                        ,robot.rightDrive.motor1.getCurrentPosition());
                );
                linearOpMode.telemetry.update();
            }

            // Stop all motion;
            robot.slider.setPower(0);

            robot.slider.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //            robot.leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            // Turn off RUN_TO_POSITION
            robot.slider.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            //            robot.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }

}

