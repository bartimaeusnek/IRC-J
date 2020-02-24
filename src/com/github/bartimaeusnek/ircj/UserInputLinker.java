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

import java.util.Scanner;

import static com.github.bartimaeusnek.ircj.CommandBuilder.*;

public class UserInputLinker {
    DataLayer dataLayer;


    public UserInputLinker(DataLayer dataLayer) {
        this.dataLayer = dataLayer;
    }

    public String readInUserMessage() {
        Scanner scanner = new Scanner(System.in);
        return scanner.hasNext() ? scanner.nextLine() : null;
    }

    public Thread createDaemon(NetworkTracker networkTracker, String channel) {
        return new Thread(() -> {
            while (true) {
                String input = this.readInUserMessage();
                if (input == null || input.isEmpty())
                    continue;
                if (!input.startsWith("!"))
                    networkTracker.layer.send(msg(channel, input));
                else if (input.equals("!q")) {
                    networkTracker.layer.send(quit("Quitting"));
                    System.exit(0);
                } else if (input.startsWith("!n"))
                    networkTracker.layer.send(nick(input.substring(2)));
            }
        });
    }

}
