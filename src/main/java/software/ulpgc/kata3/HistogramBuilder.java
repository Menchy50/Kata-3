package main.java.software.ulpgc.kata3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public class HistogramBuilder {

  private final Function<Movie, Integer> axis;

  public HistogramBuilder(Function<Movie, Integer> axis) {
    this.axis = axis;
  }

  public Histogram build(List<Movie> movies) {
    Histogram histogram = new Histogram();
    for (Movie movie : movies) {
      histogram.add((int) axis.apply(movie));
    }
    return histogram;
  }
}
