/*
 * Copyright 2020 bartimaeusnek
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT  OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.bartimaeusnek.ircj;

public class MessageFormatter {

    public static String formatText(String message) {
        if (message.contains(" JOIN :"))
            return formatJoin(message);
        else if (message.contains(" QUIT :"))
            return formatQuit(message);
        else if (message.contains(" PRIVMSG "))
            return formatMessage(message);
        else
            return message;
    }

    private static String formatJoin(String message) {
        return getUsername(message) + " has joined!";
    }

    private static String formatQuit(String message) {
        return getUsername(message) + " has left!";
    }

    private static String getUsername(String message) {
        return message.substring(1).split("!")[0];
    }

    private static String formatMessage(String message) {
        return getUsername(message) + ": " + message.substring(1).substring(message.substring(1).indexOf(':') + 1);
    }
}
