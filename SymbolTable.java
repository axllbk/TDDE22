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
	public SymbolTable(){
		this(INIT_CAPACITY);
	}

	/**
	 * Create linear probing hash table of given capacity
	 */
	public SymbolTable(int capacity){
		N = 0;
		M = capacity;
		keys = new String[M];
		vals = new Character[M];
	}

	/**
	 * Return the number of key-value pairs in the symbol table
	 */
	public int size(){
		return N;
	}

	/**
	 * Is the symbol table empty?
	 */
	public boolean isEmpty(){
		return size() == 0;
	}

	/**
	 * Does a key-value pair with the given key exist in the symbol table?
	 */
	public boolean contains(String key){
		return get(key) != null;
	}

	/**
	 * Hash function for keys - returns value between 0 and M-1
	 */
	public int hash(String key){
		int i;
		int v = 0;
		for(i = 0; i < key.length(); i++)
			v += key.charAt(i);
		return v % M;
	}

	public String indexCheck(int index){
		return keys[index];
	}

	/**
	 * Insert the key-value pair into the symbol table
	 */
	public void put(String key, Character val){
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
		return;
	}

	/**
	 * Return the value associated with the given key, null if no such value
	 */
	public Character get(String key){
		int hash = hash(key);
    for(int i = 0; i < M; i++){
    	if(hash + i < M){
        if(keys[hash + i] != null && keys[hash + i].equals(key))
          return vals[hash + i];
      }else{
        if(keys[hash + i - M] != null && keys[hash + i - M].equals(key))
          return vals[hash + i - M];
      }
		}
    return null;
	}

	/**
	 * Delete the key (and associated value) from the symbol table
	 */
	public void delete(String key){
		int hash = hash(key);
    for(int i = 0; i < M; i++){
      if(hash + i < M){
        if(keys[hash + i] != null && keys[hash + i].equals(key)){
          remove(hash + i);
          N--;
          break;
        }
      }else{
        if(keys[hash + i - M] != null && keys[hash + i - M].equals(key)){
          remove(hash + i - M);
          N--;
          break;
        }
      }
		}
  }

	/**
	 * Assigns a key-value pair to an index.
	 */
  private void assign(int index, String key, char val){
  	keys[index] = key;
  	vals[index] = val;
  }

	/**
	 * Moves a key-value pair to a new index.
	 */
  private void move(int newIndex, int oldIndex){
  	assign(newIndex, keys[oldIndex], vals[oldIndex]); //get(keys[oldIndex]) could be used but doesn't seem intuitive when you are already accessing the keys array
  	remove(oldIndex);
  }

	/**
	 * Clears a key-value pair from an index.
	 */
  private void remove(int index){
  	keys[index] = null;
  	vals[index] = null;
    deleteCheck(index);
  }

	/**
	 * Looks for a hash-misplaced key-value pair and, if matched, moves it to
	 * its correct hash-position.
	 */
  private void deleteCheck(int hash){
    for(int i = 1; i < M; i++){ //Doesn't consider the index of the removed pair
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
  }

	public void clear(){
		for(int i = 0; i < M; i++){
			keys[i] = null;
	  	vals[i] = null;
		}
	}
	/**
	 * Print the contents of the symbol table
	 */
	public void dump(){
		String str = "";
		for(int i = 0; i < M; i++){
			str = str + i + ". " + vals[i];
			if(keys[i] != null){
				str = str + " " + keys[i] + " (";
				str = str + hash(keys[i]) + ")";
			}else
				str = str + " -";
			System.out.println(str);
			str = "";
		}
		System.out.println("Size: " + N + "\n"); //La till denna för enkelhetens skull
	}
}
