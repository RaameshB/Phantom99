package org.firstinspires.ftc.teamcode.PhantomCode

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.UtilityLibs.HelperClasses.PhantomOpMode
import org.firstinspires.ftc.teamcode.UtilityLibs.HelperClasses.chassisType
import kotlin.Throws

@Autonomous(name = "MecanumTestClass2")
class MecanumTestClass2 : PhantomOpMode() {
    @Throws(InterruptedException::class)
    override fun opModeCode() {
        setChassis(chassisType.MECANUM)
        robot.init()
        waitForStart()
        chassis.driveWithImu(90.0, 0.5)
        sleep(5000)
        chassis.stopMotors()
    }
}
