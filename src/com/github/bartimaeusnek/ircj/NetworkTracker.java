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

import java.io.IOException;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

import static com.github.bartimaeusnek.ircj.CommandBuilder.*;

public class NetworkTracker implements Runnable {

    Queue<String> incomingMessageBuffer = new PriorityQueue<>();

    DataLayer layer;

    public NetworkTracker(DataLayer layer) {
        this.layer = layer;
    }

    public void warmUp(String username, String channel, String... additional) {
        if (additional == null || additional.length != 3)
            additional = new String[]{"null", "null", "null"};
        this.layer.send(nick(username));
        this.layer.send(user(username, additional[0], additional[1], additional[2]));
        this.layer.send(join(channel));
    }

    public void keepAlive(String msg) {
        if (msg.startsWith("PING :"))
            this.layer.send(pong(msg.split(":")[1]));
    }

    public void handleErrors(String msg) {
        if (msg.startsWith("ERROR :"))
            System.exit(-1);
    }

    @Override
    public void run() {
        while (true) {
            byte[] buff = new byte[2048];
            try {
                if (layer.getIn().read(buff) == -1)
                    break;
                incomingMessageBuffer.addAll(Arrays.asList(new String(buff).split("\\r\\n")));
            } catch (IOException ignored) {
                break;
            }
        }
    }
}
