package main.java.software.ulpgc.kata3;

import java.util.List;

public class Main {

  public static final String url = "https://datasets.imdbws.com/title.basics.tsv.gz";

  public static void main(String[] args) {
    List<Movie> movies = new RemoteMovieDesealizer(url, MovieDeserialize::fromTsv).loadAll();
    display(new HistogramBuilder(Movie::year).build(movies));
    display(new HistogramBuilder(m -> m.duration() / 60).build(movies));
  }

  private static void display(Histogram histogram) {
    for (int key : histogram) {
      System.out.println(key + " " + histogram.count(key));
    }
  }
}
