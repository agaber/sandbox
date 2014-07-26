package com.acme.sandbox;

import static com.google.common.base.Preconditions.checkState;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Throwables;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;

/**
 * For some reason iTunes creates multiple copies of my imported songs.
 * This code cleans it up while also experimenting with Java 7's new I/O lib
 * and (new!) Java 8 streams and lambdas.
 *
 * <p>I'm pretty sure I've done this before by the way...
 *
 * <p>Usage:
 * <pre>
   mvn exec:java -Dexec.mainClass="com.acme.sandbox.MusicCleanup" \
     -Dexec.args="--dir \"$HOME/Music/iTunes/iTunes Media/Music/\" --dry_run"
 * </pre>
 */
class MusicCleanup {
  private static final Logger LOG = LoggerFactory.getLogger(MusicCleanup.class);

  public static void main(String... args) throws Exception {
    Injector injector = Guice.createInjector(new MusicCleanupModule(args));
    injector.getInstance(MusicCleanup.class).cleanup();
  }

  private final boolean dryRun;
  private final Path path;

  @Inject
  MusicCleanup(Path path, boolean dryRun) {
    this.dryRun = dryRun;
    this.path = path;
  }

  void cleanup() throws IOException {
    Files.walk(path)
        .filter(file -> !Files.isDirectory(file))
        .collect(Collectors.groupingBy(MusicCleanup::uniqueName))
        .forEach((String upath, List<Path> files) -> {
          files.stream().skip(1).forEach(file -> delete(file));
        });
  }

  private void delete(Path file) {
    try {
      if (!dryRun) {
        Files.deleteIfExists(file);
      }
      LOG.info("{}rm {}", dryRun ? "Would have executed " : "", file);
    } catch (IOException e) {
      throw Throwables.propagate(e);
    }
  }

  private static String uniqueName(Path path) {
    return path.toAbsolutePath().toString()
        .replaceAll("\\.mp3$", "")
        .replaceAll(" \\d+$", "");
  }
}

class MusicCleanupModule extends AbstractModule {
  @Option(name = "-d", aliases = "--dir", usage = "iTunes music dir.")
  private String dir = Paths.get(
      System.getProperty("user.home"),
      "Music/iTunes/iTunes Media/Music/")
      .toString();

  @Option(name = "--dry_run", usage = "If true this app will not delete anything.")
  private boolean dryRun = false;

  MusicCleanupModule(String[] args) throws CmdLineException {
    CmdLineParser parser = new CmdLineParser(this);
    parser.parseArgument(args);
  }

  @Override
  protected void configure() {
    bind(Boolean.class).toInstance(dryRun);
  }

  @Provides @Singleton
  FileSystem fileSystem() {
    return FileSystems.getDefault();
  }

  @Provides @Singleton
  Path path(FileSystem fileSystem) {
    Path path = fileSystem.getPath(dir).toAbsolutePath();
    checkState(Files.isDirectory(path), "%s is not a valid directory!", path);
    return path;
  }
}