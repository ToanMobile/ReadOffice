package com.app.office.fc.poifs.nio;

import com.app.office.fc.util.IOUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class FileBackedDataSource extends DataSource {
    private FileChannel channel;

    public FileBackedDataSource(File file) throws FileNotFoundException {
        if (file.exists()) {
            this.channel = new RandomAccessFile(file, "r").getChannel();
            return;
        }
        throw new FileNotFoundException(file.toString());
    }

    public FileBackedDataSource(FileChannel fileChannel) {
        this.channel = fileChannel;
    }

    public ByteBuffer read(int i, long j) throws IOException {
        if (j < size()) {
            this.channel.position(j);
            ByteBuffer allocate = ByteBuffer.allocate(i);
            if (IOUtils.readFully((ReadableByteChannel) this.channel, allocate) != -1) {
                allocate.position(0);
                return allocate;
            }
            throw new IllegalArgumentException("Position " + j + " past the end of the file");
        }
        throw new IllegalArgumentException("Position " + j + " past the end of the file");
    }

    public void write(ByteBuffer byteBuffer, long j) throws IOException {
        this.channel.write(byteBuffer, j);
    }

    public void copyTo(OutputStream outputStream) throws IOException {
        WritableByteChannel newChannel = Channels.newChannel(outputStream);
        FileChannel fileChannel = this.channel;
        fileChannel.transferTo(0, fileChannel.size(), newChannel);
    }

    public long size() throws IOException {
        return this.channel.size();
    }

    public void close() throws IOException {
        this.channel.close();
    }
}
