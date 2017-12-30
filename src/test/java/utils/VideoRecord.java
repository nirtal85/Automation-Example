package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;

import com.automation.remarks.video.recorder.VideoRecorder;

import io.qameta.allure.Attachment;

public class VideoRecord {

	@Attachment(value = "video", type = "video/mp4")
	public byte[] attachment() throws InterruptedException {
		try {
			File video = VideoRecorder.getLastRecording();
			Awaitility.await().atMost(5, TimeUnit.SECONDS).pollDelay(1, TimeUnit.SECONDS).ignoreExceptions()
					.until(() -> video != null);
			return Files.readAllBytes(Paths.get(video.getAbsolutePath()));
		} catch (IOException e) {
			return new byte[0];
		}
	}
}
