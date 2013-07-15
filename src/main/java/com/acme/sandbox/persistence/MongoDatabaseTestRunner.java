package com.acme.sandbox.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;

/**
 * Launches a single mongod process for all tests that use this Runner on port
 * 10000. The mongod process will be shared for all tests so it is up to the
 * tester not to rely on this shared state. Don't rely on a clean database
 * unless your test explicitly cleans it up.
 *
 * <p>Important: mongod must be installed on the computer this test is running
 * on for tests with this runner to work; it doesn't include it's own binary
 * with the source code.
 */
public class MongoDatabaseTestRunner extends BlockJUnit4ClassRunner {
  private static final Logger LOG = LoggerFactory.getLogger(MongoDatabaseTestRunner.class);
  private static final int PORT = 20000;
  private static Process mongod;

  public MongoDatabaseTestRunner(Class<?> clazz) throws InitializationError {
    super(clazz);
  }

  @Override
  public void run(final RunNotifier notifier) {
    mongod();
    super.run(notifier);
  }

  private String[] command(File mongoDir) {
    // TODO: Support windows.
    String command = String.format(
        "mongod --dbpath=%s --port=%d",
        mongoDir.getAbsolutePath(),
        PORT);
    return new String[] { "/bin/sh", "-c", command, };
  }

  private File mongoDir() {
    String tempDir = System.getProperty("java.io.tmpdir");
    File mongoDir = new File(tempDir, "mongodb-" + System.currentTimeMillis());
    mongoDir.mkdir();
    mongoDir.deleteOnExit();
    return mongoDir;
  }

  /**
   * Start the local mongo database server if it hasn't already been started.
   */
  private synchronized void mongod() {
    if (mongod != null) {
      return;
    }

    String[] command = command(mongoDir());
    LOG.info(String.format(
        "Launching mongod with command:%n%s",
        Joiner.on(" ").join(command)));
    ProcessBuilder pb = new ProcessBuilder(command);
    pb.redirectErrorStream();

    try {
      mongod = pb.start();
      waitForMongod(mongod);
    } catch (Exception e) {
      throw new AssertionError(
          "Failure to start the mongod process. Is mongod installed?",
          e);
    }

    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        if (mongod != null) {
          LOG.info("Shutting down mongod.");
          mongod.destroy();
          mongod = null;
        }
      }
    });
  }

  private void waitForMongod(Process mongod) throws IOException, AssertionError {
    InputStream stdout = mongod.getInputStream();
    BufferedReader reader = new BufferedReader (new InputStreamReader(stdout));

    String line = null;
    while ((line = reader.readLine()) != null) {
      System.out.println(line);
      if (line.contains("waiting for connections on port " + PORT)) {
        return;
      }
    }

    if (mongod.exitValue() != 0) {
      throw new AssertionError(String.format(
          "Unable to start the mongod process. mongod exit value = %d.",
          mongod.exitValue()));
    }
  }
}
