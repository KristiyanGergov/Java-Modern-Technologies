package bg.sofia.uni.fmi.mjt.chat;

import java.util.HashMap;

public class Test {

    static class Gosho {
        private String name;

        public Gosho(String name) {
            this.name = name;
        }

//        @Override
//        public int hashCode() {
//            return name.hashCode() & 32;
//        }
//
//        @Override
//        public boolean equals(Object obj) {
//            if (obj instanceof User)
//                return ((User) obj).getName().equals(name);
//            return false;
//        }
    }

    public static void main(String[] args) {

        HashMap<Gosho, Integer> test = new HashMap<>();

        test.put(new Gosho("gosho"), 0);
        test.put(new Gosho("gosho1"), 1);
        test.put(new Gosho("gosho2"), 2);
        test.put(new Gosho("gosho3"), 3);

        test.get("gosho");
        Integer first2 = test.get("gosho1");
        Integer first3 = test.get("gosho2");
        Integer first4 = test.get("gosho3");

    }
}
