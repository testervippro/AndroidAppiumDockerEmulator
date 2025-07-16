const { spawn } = require('child_process');
const fs = require('fs').promises;
const path = require('path');
const os = require('os');
const videoDir = path.resolve(__dirname, 'videos');
const videoPath = path.join(videoDir, 'manual_record.mp4');
const pidPath = path.join(videoDir, 'ffmpeg.pid');

async function isRecording() {
  try {
    await fs.access(pidPath);
    return true;
  } catch {
    return false;
  }
}

async function startRecording() {
  await fs.mkdir(videoDir, { recursive: true });

  const ffmpeg = spawn('ffmpeg', [
    '-y',
    '-video_size', '1280x720',
    '-f', 'x11grab',
    '-i', ':0.0',
    '-codec:v', 'libx264',
    videoPath
  ], {
    stdio: 'ignore',
    detached: true
  });

  ffmpeg.unref(); // So it doesn't stay attached to the node process

  await fs.writeFile(pidPath, ffmpeg.pid.toString());
  console.log(`Recording started (PID ${ffmpeg.pid})`);
}

async function stopRecording() {
  try {
    const pidStr = await fs.readFile(pidPath, 'utf-8');
    const pid = parseInt(pidStr, 10);
    console.log(` Stopping ffmpeg (PID: ${pid})...`);

    process.kill(pid, 'SIGINT'); // graceful Ctrl+C

    await fs.unlink(pidPath);
    console.log(` Video saved: ${videoPath}`);
  } catch (err) {
    console.error(' Failed to stop recording:', err.message);
  }
}

async function main() {
 // Run only on Linux
  if (os.platform() !== 'linux') {
    console.log(' Skipping recording: not running on Linux');
    return;
  }

  if (await isRecording()) {
    await stopRecording();
  } else {
    await startRecording();
  }
}

main().catch(err => {
  console.error(' Unexpected error:', err);
  process.exit(1);
});
