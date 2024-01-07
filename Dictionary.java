public class Dictionary implements Map<String, Integer> {

    private final static int INITIAL_CAPACITY = 10;
    private final static int INCREMENT = 5;
    private int count;

    private Pair[] elems;

    public int getCount() {
      return count;
    }

    public int getCapacity() {
      return elems.length;
    }

    public Dictionary() {
        elems = new Pair[INITIAL_CAPACITY];
        count = 0;
    }

    @Override
    public void put(String key, Integer value) {

        if (count == elems.length) {
            increaseCapacity();
        }

        elems[count] = new Pair(key, value);
        count++;
    }

    private void increaseCapacity() {
        Pair nArr[] = new Pair[elems.length + INCREMENT];
        for (int i = 0; i < count; i ++ ) {
            nArr[i] = elems[i];
        }
       elems = nArr;
    }

    @Override
    public boolean contains(String key) {
        for (int i = count - 1; i >= 0; i--) {
            if (elems[i].getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Integer get(String key) {
        for (int i = count - 1; i >= 0; i--) {
            if (elems[i].getKey().equals(key)) {
                return elems[i].getValue();
            }
        }
        return null;
    }

    @Override
    public void replace(String key, Integer value) {
        for (int i = count - 1; i >= 0; i--) {
            if (elems[i].getKey().equals(key)) {
                elems[i].setValue(value);
            }
            break;
        }
    }

    @Override
    public Integer remove(String key) {
        for (int i = count - 1; i >= 0; i--) {
            if (elems[i].getKey().equals(key)) {
                Integer value = elems[i].getValue();
                
                for (int j = i; j < count - 1; j++) {
                    elems[j] = elems[j + 1];
                }
                count--;
                return value;
            }   
        }
        return null;
    }

    @Override
    public String toString() {
      String res;
      res = "Dictionary: {elems = [";
      for (int i = count-1; i >= 0 ; i--) {
          res += elems[i];
          if(i > 0) {
              res += ", ";
          }
      }
      return res +"]}";
    }

}