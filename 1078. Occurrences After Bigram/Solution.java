import java.util.ArrayList;
import java.util.List;

/**
 * 二元文法 Bi-Gram 的简单算法实现
 *
 * @author leonwong
 */
class Solution {

    public String[] findOcurrences(String text, String first, String second) {
        String[] textList = text.split(" ");
        List<String> result = new ArrayList<>(3);
        while (textList.length > 2) {
            if (first.equalsIgnoreCase(textList[0]) && second.equalsIgnoreCase(textList[1])) {
                result.add(textList[2]);
            }
            String[] tmp = new String[textList.length - 1];
            System.arraycopy(textList, 1, tmp, 0, textList.length - 1);
            textList = tmp;
        }
        return result.toArray(new String[0]);
    }
}