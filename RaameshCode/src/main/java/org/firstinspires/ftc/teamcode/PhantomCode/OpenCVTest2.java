package org.firstinspires.ftc.teamcode.PhantomCode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.UtilityLibs.HelperClasses.PhantomOpMode;
import org.firstinspires.ftc.teamcode.UtilityLibs.HelperClasses.chassisType;
import org.opencv.imgcodecs.Imgcodecs;

@TeleOp
public class OpenCVTest2 extends PhantomOpMode {
    @Override
    public void opModeCode() throws InterruptedException {
        setChassis(chassisType.MECANUM);
        robot.init();
        console.addLine("waiting for start...");
        waitForStart();
        robot.startWebcamStream();
        console.addData("Frame Count", robot.webcam.getFrameCount());
        console.addData("FPS", String.format("%.2f", robot.webcam.getFps()));
        console.addData("Total frame time ms", robot.webcam.getTotalFrameTimeMs());
        console.addData("Pipeline time ms", robot.webcam.getPipelineTimeMs());
        console.addData("Overhead time ms", robot.webcam.getOverheadTimeMs());
        console.addData("Theoretical max FPS", robot.webcam.getCurrentPipelineMaxFps());
        int i = 0;
        stopwatch.resetAndRestart();
        while (!isStopRequested()) {
            console.modData("Frame Count", robot.webcam.getFrameCount());
            console.modData("FPS", String.format("%.2f", robot.webcam.getFps()));
            console.modData("Total frame time ms", robot.webcam.getTotalFrameTimeMs());
            console.modData("Pipeline time ms", robot.webcam.getPipelineTimeMs());
            console.modData("Overhead time ms", robot.webcam.getOverheadTimeMs());
            console.modData("Theoretical max FPS", robot.webcam.getCurrentPipelineMaxFps());
            int majikNumber = 250;
            if (robot.openCVPipeline.getLastMat() != null) {
                Imgcodecs.imwrite(
                        "/storage/self/primary/DCIM/image" + i + ".jpg",
                        robot.openCVPipeline.getLastMat()
                );
                i+=1;
                majikNumber += 250;
            }

        }
    }
}
