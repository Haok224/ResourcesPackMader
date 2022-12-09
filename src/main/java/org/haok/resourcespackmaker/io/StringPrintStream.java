package org.haok.resourcespackmaker.io;

import org.haok.resourcespackmaker.log.Logger;

import java.io.PrintStream;

public class StringPrintStream extends PrintStream {
    private final StringBuffer str = new StringBuffer();

    public StringPrintStream() {
        super(new Logger.LogOutputStream());
    }

    @Override
    public void println() {
        super.println();
        str.append('\n');
    }

    @Override
    public void println(boolean x) {
        super.println(x);
        str.append(x).append('\n');
    }

    @Override
    public void println(char x) {
        super.println(x);
        str.append(x).append('\n');
    }

    @Override
    public void println(int x) {
        super.println(x);
        str.append(x).append('\n');
    }

    @Override
    public void println(long x) {
        super.println(x);
        str.append(x).append('\n');
    }

    @Override
    public void println(float x) {
        super.println(x);
        str.append(x).append('\n');
    }

    @Override
    public void println(double x) {
        super.println(x);
        str.append(x).append('\n');
    }

    @Override
    public void println(char[] x) {
        super.println(x);
        str.append(x).append('\n');
    }

    @Override
    public void println(String x) {
        super.println(x);
        str.append(x).append('\n');
    }

    @Override
    public void println(Object x) {
        super.println(x);
        str.append(x).append('\n');
    }

    @Override
    public String toString() {
        return str.toString();
    }
}
