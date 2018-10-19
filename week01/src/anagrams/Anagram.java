package anagrams;

import java.util.Arrays;

public class Anagram {
    public boolean isAnagram(String input) {
        int resFirst[] = new int[26];
        int resSecond[] = new int[26];

        String first = input.split(" ")[0].toLowerCase();
        String second = input.split(" ")[1].toLowerCase();

        for (int i = 0; i < first.length(); i++) {
            int index = 'z' - first.charAt(i);
            if (index < 0 || index > 25) {
                continue;
            }
            resFirst[index]++;
        }

        for (int i = 0; i < second.length(); i++) {
            int index = 'z' - second.charAt(i);
            if (index < 0 || index > 25) {
                continue;
            }
            resSecond[index]++;
        }

        return Arrays.equals(resFirst, resSecond);
    }
}
