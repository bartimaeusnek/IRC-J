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

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

public class DataLayer {

    private Socket socket;
    private OutputStream out;
    private InputStream in;

    public DataLayer(String host, int port) throws SocketException {
        connect(host, port);
        socket.setSoTimeout(1000);
    }

    public synchronized OutputStream getOut() {
        return out;
    }

    public synchronized InputStream getIn() {
        return in;
    }

    public boolean connect(String host, int port) {
        try {
            socket = new Socket(host, port);
            out = socket.getOutputStream();
            in = socket.getInputStream();
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public void send(String string) {
        try {
            out.write((string + "\r\n").getBytes());
        } catch (Exception ignored) {
        }
    }

    public void send(byte[] bytes) {
        try {
            out.write(bytes);
        } catch (Exception ignored) {
        }
    }

}