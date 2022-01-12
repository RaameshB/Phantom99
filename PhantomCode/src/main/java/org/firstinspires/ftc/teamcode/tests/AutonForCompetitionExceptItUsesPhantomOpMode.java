package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.util.PhantomOpMode2;

@Autonomous
public class AutonForCompetitionExceptItUsesPhantomOpMode extends PhantomOpMode2 {
    @Override
    public void opModeCode() throws InterruptedException {
        robotControl.driveUtils.encoderDrive(0.5, -8, -8, 2);
        robotControl.imuUtils.rotate(-35, 0.5);

        robotControl.driveUtils.encoderDrive(0.4, 11.7, 11.7, 5);
        robotControl.driveUtils.encoderDrive(0.2, 2, 2, 3);
        sleep(1000);

        hardware.carousel.setPower(1);
        sleep(2500);
        hardware.carousel.setPower(0);
        sleep(500);

        robotControl.driveUtils.encoderDrive(0.5, -38.3, -38.3, 5);

        hardware.bucket.setPosition(1.0);
        sleep(2000);

        robotControl.driveUtils.encoderDrive(0.5, 10, 10, 5);

        robotControl.imuUtils.rotate(-30, 0.5);

        robotControl.driveUtils.encoderDrive(0.75, -75, -75, 10);

        hardware.bucket.setPosition(0.5);
    }
}

