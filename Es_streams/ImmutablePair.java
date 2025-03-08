class ImmutablePair<T1,T2> {
  private T1 x;
  private T2 y;

  public ImmutablePair(T1 x, T2 y) {
    this.x = x;
    this.y = y;
  }

  public T1 getX() {
    return x;
  }

  public T2 getY() {
    return y;
  }
}