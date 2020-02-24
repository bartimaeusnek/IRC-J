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

public class CommandBuilder {

    private CommandBuilder() {
    }

    public static String join(String channel) {
        return "JOIN " + channel;
    }

    public static String part(String channel) {
        return "PART " + channel;
    }

    public static String msg(String to, String text) {
        return "PRIVMSG " + to + " :" + text;
    }

    public static String pong(String server) {
        return "PONG " + server;
    }

    public static String quit(String reason) {
        return "QUIT :Quit: " + reason;
    }

    public static String nick(String nickname) {
        return "NICK " + nickname;
    }

    public static String user(String username, String hostname, String servername, String realname) {
        return "USER " + username + " " + hostname + " " + servername + " :" + realname;
    }

}
