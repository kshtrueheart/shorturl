package com.github.ququzone.shorturl;

import java.util.Stack;

/**
 * code transfer.
 *
 * @author Yang XuePing
 */
public class CodeTransfer {
    private static final char[] array = {
            'P', 'm', 'A', 'b', 'I', 'c', 'J', 'z', 'H', 'd',
            'a', '6', 'p', 'W', 'O', 'e', 'Q', 'f', '7', 'K',
            'h', 'C', 'i', '5', 'j', 'R', 'g', 'D', 'k', 'Z',
            'M', '8', 'n', '4', 'F', 'L', 'E', 'o', '9', 'X',
            'r', 'T', 's', 'B', '3', 't', 'V', 'G', 'u', 'Y',
            'v', '2', 'w', 'x', '1', 'y', 'U', '0', 'N', 'l',
            'S', 'q'
    };

    public static String decode(long number) {
        Long rest = number;
        Stack<Character> stack = new Stack<>();
        StringBuilder result = new StringBuilder(6);
        while (rest != 0) {
            stack.add(array[new Long((rest - (rest / 62) * 62)).intValue()]);
            rest = rest / 62;
        }
        for (; !stack.isEmpty(); ) {
            result.append(stack.pop());
        }
        return result.toString();

    }

}
