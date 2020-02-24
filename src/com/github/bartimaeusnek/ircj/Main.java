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

import java.net.SocketException;

public class Main {

    public static void main(String[] args) throws SocketException {
        if (args.length != 4) {
            System.out.println("Please start this program with the following arguments:\n" +
                    "1. IP/Hostname of the server\n" +
                    "2. Port\n" +
                    "3. Channel\n" +
                    "4. Username");
            System.exit(2);
        }
        //Set up Username & Channel
        String channel = args[2];
        String greeting = "me @ " + channel + " :";
        String username = args[3];

        //set up DataLayer and Input listener
        NetworkTracker networkTracker = new NetworkTracker(new DataLayer(args[0], Integer.parseInt(args[1])));
        UserInputLinker userInputLinker = new UserInputLinker(networkTracker.layer);

        //actual connect
        networkTracker.warmUp(username, channel);

        //starting input backend
        userInputLinker.createDaemon(networkTracker, channel).start();

        //noinspection InfiniteLoopStatement
        while (true) {
            networkTracker.run();
            while (!networkTracker.incomingMessageBuffer.isEmpty()) {
                String msg = networkTracker.incomingMessageBuffer.poll();
                if (msg.contains(greeting))
                    System.out.println(msg.substring(greeting.length()).replaceAll("\\s", "\r\n"));
                System.out.println(MessageFormatter.formatText(msg));
                networkTracker.handleErrors(msg);
                networkTracker.keepAlive(msg);
            }
        }
    }
}
