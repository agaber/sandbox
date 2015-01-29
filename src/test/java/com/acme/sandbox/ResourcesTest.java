package com.acme.sandbox;

import static com.google.common.truth.Truth.assertThat;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.io.Resources;

public class ResourcesTest {

	@Test
	public void listFilesInResource() throws Exception {
		assertThat(list("resources/")).containsExactly(
		    "file1.txt",
		    "file2.txt",
		    "subdir");
	}

	 @Test
	  public void walkFilesInResource() throws Exception {
	    assertThat(walk("resources/")).containsExactly(
	        "file1.txt",
	        "file2.txt",
	        "file3.txt");
	  }

  private ImmutableList<String> list(String dir) throws URISyntaxException {
    ImmutableList.Builder<String> files = ImmutableList.builder();
    Path path = Paths.get(Resources.getResource(getClass(), dir).toURI());
    for (String file : path.toFile().list()) {
		  files.add(file);
		}
    return files.build();
  }

  private ImmutableList<String> walk(String dir) throws IOException, URISyntaxException {
    return ImmutableList.copyOf(Files
        .walk(Paths.get(Resources.getResource(getClass(), dir).toURI()))
        .filter(path -> !Files.isDirectory(path))
        .map(path -> path.getFileName().toString())
        .collect(toList()));
  }
}
