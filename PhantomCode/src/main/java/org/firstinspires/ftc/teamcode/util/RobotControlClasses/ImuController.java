package org.firstinspires.ftc.teamcode.util.RobotControlClasses;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.util.RobotConfig;

public class ImuController {
    BNO055IMU imu;
    LinearOpMode linearOpMode;
    RobotConfig robot;
    Orientation angles;
    Orientation lastAngles = new Orientation();
    double lastAngle = 0;
    public double globalAngle = 0;
    public ImuController(RobotConfig robot, LinearOpMode linearOpMode) {
        this.robot = robot;
        this.imu = this.robot.imu;
        this.linearOpMode = linearOpMode;
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
    }

    //    public double getAngle() {
    //        double deltaAngle = angles.firstAngle - lastAngle;
    //
    //        if (deltaAngle < -180)
    //            deltaAngle += 360;
    //        else if (deltaAngle > 180)
    //            deltaAngle -= 360;
    //
    //        globalAngle += deltaAngle;
    //
    //        lastAngle = angles.firstAngle;
    //
    //        return globalAngle;
    //    }

    //    public void imuTurn(double degrees, double gain) {
    //        double correction = getCorrection(gain);
    //        globalAngle = degrees;
    //        while(linearOpMode.opModeIsActive() && Math.abs(globalAngle) < 4) {
    //            robot.rightDrive.setPower(correction);
    //            robot.leftDrive.setPower(correction);
    //        }
    //        robot.rightDrive.setPower(0);
    //        robot.leftDrive.setPower(0);
    //    }

    private void resetAngle()
    {
        lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        globalAngle = 0;
    }

    /**
     * Get current cumulative angle rotation from last reset.
     * @return Angle in degrees. + = left, - = right.
     */
    public double getAngle()
    {
        // We experimentally determined the Z axis is the axis we want to use for heading angle.
        // We have to process the angle because the imu works in euler angles so the Z axis is
        // returned as 0 to +180 or 0 to -180 rolling back to -179 or +179 when rotation passes
        // 180 degrees. We detect this transition and track the total cumulative angle of rotation.

        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;

        if (deltaAngle < -180)
            deltaAngle += 360;
        else if (deltaAngle > 180)
            deltaAngle -= 360;

        globalAngle += deltaAngle;

        lastAngles = angles;

        return globalAngle;
    }

    /**
     * See if we are moving in a straight line and if not return a power correction value.
     * @return Power adjustment, + is adjust left - is adjust right.
     */
    private double checkDirection()
    {
        // The gain value determines how sensitive the correction is to direction changes.
        // You will have to experiment with your robot to get small smooth direction changes
        // to stay on a straight line.
        double correction, angle, gain = .10;

        angle = getAngle();

        if (angle == 0)
            correction = 0;             // no adjustment.
        else
            correction = -angle;        // reverse sign of angle for correction.

        correction = correction * gain;

        return correction;
    }

    /**
     * Rotate left or right the number of degrees. Does not support turning more than 180 degrees.
     * @param degrees Degrees to turn, + is left - is right
     */
    public void rotate(int degrees, double power)
    {
        double  leftPower, rightPower;

        // restart imu movement tracking.
        resetAngle();

        // getAngle() returns + when rotating counter clockwise (left) and - when rotating
        // clockwise (right).

        if (degrees < 0)
        {   // turn right.
            leftPower = power;
            rightPower = -power;
        }
        else if (degrees > 0)
        {   // turn left.
            leftPower = -power;
            rightPower = power;
        }
        else return;

        // set power to rotate.
        robot.leftDrive.setPower(leftPower);
        robot.rightDrive.setPower(rightPower);

        // rotate until turn is completed.
        if (degrees < 0)
        {
            // On right turn we have to get off zero first.
            while (linearOpMode.opModeIsActive() && getAngle() == 0) {}

            while (linearOpMode.opModeIsActive() && getAngle() > degrees) {}
        }
        else    // left turn.
            while (linearOpMode.opModeIsActive() && getAngle() < degrees) {}

        // turn the motors off.
        robot.rightDrive.setPower(0);
        robot.leftDrive.setPower(0);

        // wait for rotation to stop.
        linearOpMode.sleep(1000);

        // reset angle tracking on new heading.
        resetAngle();
    }
}