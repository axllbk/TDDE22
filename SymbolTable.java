public class SymbolTable {
	private static final int INIT_CAPACITY = 7;

	/* Number of key-value pairs in the symbol table */
	private int N;
	/* Size of linear probing table */
	private int M;
	/* The keys */
	private String[] keys;
	/* The values */
	private Character[] vals;

	/**
	 * Create an empty hash table - use 7 as default size
	 */
	public SymbolTable() {
		this(INIT_CAPACITY);
	}

	/**
	 * Create linear probing hash table of given capacity
	 */
	public SymbolTable(int capacity) {
		N = 0;
		M = capacity;
		keys = new String[M];
		vals = new Character[M];
	}

	/**
	 * Return the number of key-value pairs in the symbol table
	 */
	public int size() {
		return N;
	}

	/**
	 * Is the symbol table empty?
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Does a key-value pair with the given key exist in the symbol table?
	 */
	public boolean contains(String key) {
		return get(key) != null;
	}

	/**
	 * Hash function for keys - returns value between 0 and M-1
	 */
	public int hash(String key) {
		int i;
		int v = 0;

		for (i = 0; i < key.length(); i++) {
			v += key.charAt(i);
		}
		return v % M;
	}

	/**
	 * Insert the key-value pair into the symbol table
	 */
	public void put(String key, Character val) {
		int hash = hash(key);
        for(int i = 0; i < M; i++){
            if(hash + i < M){
                if(keys[hash + i] == null || keys[hash + i].equals(key)){
                    assign(hash + i, key, val);
                    N++;
                    break;
                }
            }else{
                if(keys[hash + i - M] == null || keys[hash + i - M].equals(key)){
                    assign(hash + i - M, key, val);
                    N++;
                    break;
                }
            }
        }
	} // dummy code

	/**
	 * Return the value associated with the given key, null if no such value
	 */
	public Character get(String key) {
		int hash = hash(key);
        for(int i = 0; i < M; i++){
          if(hash + i < M){
            if(keys[hash + i] != null)
              continue;
            if(keys[hash + i].equals(key))
              return vals[hash + i];
            if(keys[hash + i - M] != null)
              continue;
            if(keys[hash + i - M].equals(key))
              return vals[hash + i - M];
            }
        }
        return null;
	}

	/**
	 * Delete the key (and associated value) from the symbol table
	 */
	public void delete(String key) {
        int hash = hash(key);
        for(int i = 0; i < M; i++){
            if(hash + i < M){
                if(keys[hash + i].equals(key)){
                    clear(hash + i);
                    N--;
                    break;
                }
            }else{
                if(keys[hash + i - M].equals(key)){
                    clear(hash + i - M);
                    N--;
                    break;
                }
            }
        }
        return;
    } // dummy code

    private void deleteCheck(int hash){
    	Integer[] test = new Integer[0];
    	int index = 0;
        for(int i = 1; i < M; i++){
        		if(hash + i < M){
        			if(keys[hash + i] == null) continue;
        			if(hash == hash(keys[hash + i])){
        				move(hash, hash + i);
        				break;
        			}
        		}else{
        			if(keys[hash + i - M] == null) continue;
        			if(hash == hash(keys[hash + i - M])){
        				move(hash, hash + i - M);
        				break;
        			}
        		}
        	}
        return;
    }

    /**
  	 * Assignes a key-value pair to an index.
  	 */
  	private void assign(int index, String key, char val){
  		keys[index] = key;
  		vals[index] = val;
  		return;
  	}

  	/**
  	 * Clears a key-value pair from an index.
  	 */
  	private void clear(int index){
  		keys[index] = null;
  		vals[index] = null;
      deleteCheck(index);
  		return;
  	}

  	/**
  	 * Moves the given key-value pair to the new index.
  	 */
  	private void move(int newIndex, int oldIndex){
  		assign(newIndex, keys[oldIndex], vals[oldIndex]);
  		clear(oldIndex);
  		return;
  	}

	/**
	 * Print the contents of the symbol table
	 */
	public void dump() {
		String str = "";

		for (int i = 0; i < M; i++) {
			str = str + i + ". " + vals[i];
			if (keys[i] != null) {
				str = str + " " + keys[i] + " (";
				str = str + hash(keys[i]) + ")";
			} else {
				str = str + " -";
			}
			System.out.println(str);
			str = "";
		}
	}
}
