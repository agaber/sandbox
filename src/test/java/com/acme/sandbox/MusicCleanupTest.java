package com.acme.sandbox;

import static org.junit.Assert.*;

import java.nio.file.FileSystem;
import java.nio.file.Files;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.jimfs.Configuration;
import com.google.jimfs.Jimfs;

public class MusicCleanupTest {
  FileSystem fs;

  @Before
  public void beforeEach() throws Exception {
    fs = Jimfs.newFileSystem(Configuration.unix());
  }

  @After
  public void afterEach() throws Exception {
    fs.close();
  }

  @Test
  public void cleanup() throws Exception {
    Files.createDirectories(fs.getPath("/itunes/music/album1"));
    Files.createDirectories(fs.getPath("/itunes/music/album2"));
    Files.createDirectories(fs.getPath("/itunes/music/album3/subdir"));
    Files.createFile(fs.getPath("/itunes/music/album1/song1.mp3"));
    Files.createFile(fs.getPath("/itunes/music/album1/song1 1.mp3"));
    Files.createFile(fs.getPath("/itunes/music/album1/song1 2.mp3"));
    Files.createFile(fs.getPath("/itunes/music/album1/song2.mp3"));
    Files.createFile(fs.getPath("/itunes/music/album1/song3 1.mp3"));
    Files.createFile(fs.getPath("/itunes/music/album1/song3 3.mp3"));
    Files.createFile(fs.getPath("/itunes/music/album2/song1.mp3"));
    Files.createFile(fs.getPath("/itunes/music/album2/song2.mp3"));
    Files.createFile(fs.getPath("/itunes/music/album2/song22.mp3"));
    Files.createFile(fs.getPath("/itunes/music/album3/subdir/song a.mp3"));
    Files.createFile(fs.getPath("/itunes/music/album3/subdir/song a 1.mp3"));
    Files.createFile(fs.getPath("/itunes/music/album3/subdir/song11.mp3"));

    // Pre-validate to make sure the files are there before we try to delete.
    assertTrue(Files.exists(fs.getPath("/itunes/music/album1/song1.mp3")));
    assertTrue(Files.exists(fs.getPath("/itunes/music/album1/song1 1.mp3")));
    assertTrue(Files.exists(fs.getPath("/itunes/music/album1/song1 2.mp3")));
    assertTrue(Files.exists(fs.getPath("/itunes/music/album1/song2.mp3")));
    assertTrue(Files.exists(fs.getPath("/itunes/music/album1/song3 1.mp3")));
    assertTrue(Files.exists(fs.getPath("/itunes/music/album1/song3 3.mp3")));
    assertTrue(Files.exists(fs.getPath("/itunes/music/album2/song1.mp3")));
    assertTrue(Files.exists(fs.getPath("/itunes/music/album2/song2.mp3")));
    assertTrue(Files.exists(fs.getPath("/itunes/music/album2/song22.mp3")));
    assertTrue(Files.exists(fs.getPath("/itunes/music/album3/subdir/song a.mp3")));
    assertTrue(Files.exists(fs.getPath("/itunes/music/album3/subdir/song a 1.mp3")));
    assertTrue(Files.exists(fs.getPath("/itunes/music/album3/subdir/song11.mp3")));

    // Execute.
    new MusicCleanup(fs.getPath("/itunes/music"), false).cleanup();

    // Verify.
    assertTrue(Files.exists(fs.getPath("/itunes/music/album1/song1 1.mp3")));
    assertTrue(Files.exists(fs.getPath("/itunes/music/album1/song2.mp3")));
    assertTrue(Files.exists(fs.getPath("/itunes/music/album1/song3 1.mp3")));
    assertTrue(Files.exists(fs.getPath("/itunes/music/album2/song1.mp3")));
    assertTrue(Files.exists(fs.getPath("/itunes/music/album2/song2.mp3")));
    assertTrue(Files.exists(fs.getPath("/itunes/music/album2/song22.mp3")));
    assertTrue(Files.exists(fs.getPath("/itunes/music/album3/subdir/song a 1.mp3")));
    assertTrue(Files.exists(fs.getPath("/itunes/music/album3/subdir/song11.mp3")));

    // This stuff should be deleted.
    // TODO: Improve the predictability of which files get deleted.
    assertFalse(Files.exists(fs.getPath("/itunes/music/album1/song1.mp3")));
    assertFalse(Files.exists(fs.getPath("/itunes/music/album1/song1 2.mp3")));
    assertFalse(Files.exists(fs.getPath("/itunes/music/album1/song3 3.mp3")));
    assertFalse(Files.exists(fs.getPath("/itunes/music/album3/subdir/song a.mp3")));
  }

  @Test
  public void cleanupWithDryRun() throws Exception {
    Files.createDirectories(fs.getPath("/itunes/music/album1"));
    Files.createFile(fs.getPath("/itunes/music/album1/song1.mp3"));
    Files.createFile(fs.getPath("/itunes/music/album1/song1 1.mp3"));

    // Pre-validate to make sure the files are there before we try to delete.
    assertTrue(Files.exists(fs.getPath("/itunes/music/album1/song1.mp3")));
    assertTrue(Files.exists(fs.getPath("/itunes/music/album1/song1 1.mp3")));

    // Execute.
    new MusicCleanup(fs.getPath("/itunes/music"), true).cleanup();

    // Verify.
    assertTrue(Files.exists(fs.getPath("/itunes/music/album1/song1.mp3")));
    assertTrue(Files.exists(fs.getPath("/itunes/music/album1/song1 1.mp3")));
  }
}
