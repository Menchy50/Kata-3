package main.java.software.ulpgc.kata3;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.zip.GZIPInputStream;

public class RemoteMovieDesealizer implements MovieLoader {

  private final String url;
  private final Function<String, Movie> deserialize;

  public RemoteMovieDesealizer(String url, Function<String, Movie> deserialize) {
    this.deserialize = deserialize;
    this.url = url;
  }

  @Override
  public List<Movie> loadAll() {
    try {
      return loadFrom(new URL(url));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private List<Movie> loadFrom(URL url) throws IOException {
    return loadFrom(url.openConnection());
  }

  private List<Movie> loadFrom(URLConnection urlConnection) throws IOException {
    try (InputStream inputStream = unzip(urlConnection.getInputStream())) {
      return loadFrom(toReader(inputStream));
    }
  }

  private List<Movie> loadFrom(BufferedReader reader) throws IOException {
    List<Movie> list = new ArrayList<>();
    reader.readLine();
    while (true) {
      String line = reader.readLine();
      if (line == null) break;
      list.add(deserialize.apply(line));
    }
    return list;
  }

  private BufferedReader toReader(InputStream inputStream) {
    return new BufferedReader(new InputStreamReader(inputStream));
  }

  private InputStream unzip(InputStream inputStream) throws IOException {
    return new GZIPInputStream(new BufferedInputStream(inputStream));
  }

}
