package org.maven;

import java.io.IOException;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;

public class CMDUtils {

  public static void executeCMD(String cmd, boolean hasLog) {
      try {
        CommandLine commandLine = CommandLine.parse(cmd);
        DefaultExecutor executor = new DefaultExecutor();

        if (hasLog) {
          // Redirect output to System.out and System.err
          executor.setStreamHandler(new PumpStreamHandler(System.out, System.err));
        }

        executor.execute(commandLine);
      } catch (IOException e) {
        throw new RuntimeException("Failed to execute command: " + cmd, e);
      }
    }

  public static void executeCMD(String cmd){
      executeCMD(cmd,false);
  }
}
