public class SymbolTableTest{
  public static void main(String[] args){
    SymbolTable st = new SymbolTable();
    //Givet att get funkar
    test(!st.isEmpty(), "initiering funkar INTE, dictionary är inte tom");

    System.out.println("Testar put");
    st.put("apelsin", 'a'); //hash 6
    test(st.get("apelsin") == null, "put funkar INTE, här ska det stå ett 'a'");

    System.out.println("Testar delete");
    st.delete("ab");
    test(st.contains("ab"), "delete funkar INTE, ab ska vara borta");

    System.out.println("Testar deleteCheck");
    st.put("apelsin", 'a'); //hash 6
    st.put("banan", 'b'); //hash 1
    st.put("äpple", 'c'); //hash 3
    st.put("päron", 'd'); //hash 3
    st.put("potatis", 'e'); //hash 2
    st.put("broccoli", 'f'); //hash 5
    st.put("zucchini", 'g'); //hash 0
    st.delete("äpple");
    test(st.indexCheck(3) != "päron" && st.indexCheck(4) == null, "deleteCheck funkar INTE, nyckeln \"päron\" ska ligga på index 3");

    System.out.println("Testar flera deleteCheck");
    st.clear();
    st.put("zucchini", 'a'); //0
    st.put("melon", 'f'); //0
    st.put("banan", 'b'); //1
    st.put("äpple", 'c'); //3
    st.put("potatis", 'd'); //2
    st.put("broccoli", 'e'); //5
    st.put("apelsin", 'g'); //6
    st.delete("zucchini");
    test(st.indexCheck(0) != "melon", "deleteCheck funkar INTE, nyckeln \"melon\" ska ligga på index 0");
    test(st.indexCheck(4) == null, "deleteCheck funkar INTE, index 4 ska vara tomt");

    test(st.indexCheck(1) != "banan", "deleteCheck funkar INTE, värdet 'b' ska ligga på index 1");

    System.out.println("yeee :^)");
  }

  private static void test(boolean c, String s){
    if(c){
      System.out.println(s);
      System.exit(1);
    }
  }
}
