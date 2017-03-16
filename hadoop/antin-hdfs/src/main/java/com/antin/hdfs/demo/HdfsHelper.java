package com.antin.hdfs.demo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.URI;

/**
 * Created by Administrator on 2017/3/16.
 */
public class HdfsHelper {
    private static Logger log = Logger.getLogger(HdfsHelper.class);
    private static FileSystem fileSystem;
    private static Configuration conf;

    private static String HDFS_PATH = "hdfs://192.168.2.88:9000/";
    //private static String HDFS_PATH = "hdfs://cluster1/";

    static {
        conf = new Configuration();
//        conf.set("mapreduce.framework.name", "yarn");
//        conf.set("dfs.nameservices", "cluster1");
//        conf.set("dfs.ha.namenodes.cluster1", "zoe01,zoe02");
//        conf.set("dfs.namenode.rpc-address.cluster1.zoe01", "zoe01:9000");
//        conf.set("dfs.namenode.rpc-address.cluster1.zoe02", "zoe02:9000");
//        conf.set("fs.client.failover.proxy.provider.cluster1", "org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
        try {
            getFileSystem();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {
        // 创建目录
        //mkdir("/hdfs_test_path");

        // 向文件中写内容，如果文件不存在会创建
        //write("/hdfs_test_path/testWrite", "write success!", false);

        // 读取文件内容
        // log.info(read("/hdfs_test_path/testWrite"));

        //列出所有文件
        listFile("/");

        // 删除文件
        //delete("/hdfs_test_path/testWrite");
        //
        // 删除目录
        //rmdir("/hdfs_test_path");
    }

    /**
     * /列出所有文件
     *
     * @param path
     * @throws Exception
     */

    public static void listFile(String path) throws Exception {
        FileStatus[] fileStatus = fileSystem.listStatus(new Path(path));
        for (FileStatus fs : fileStatus) {
            log.info((fs.isDirectory() ? "目录" : "文件") + " " + fs.getOwner() + " " + fs.getPermission() + " size:" + fs.getLen() + " " + fs.getPath());
            if (fs.isDirectory())
                listFile(fs.getPath().toString());

        }

    }

    /**
     * 获取fileSystem对象
     */
    public static void getFileSystem() throws Exception {
        //fileSystem = FileSystem.get(conf);
        fileSystem = FileSystem.get(new URI(HDFS_PATH), conf, "root");
    }

    /**
     * 删除目录
     *
     * @param path
     * @throws IOException
     */
    public static void rmdir(String path) throws IOException {
        Path destPath = new Path(path);
        if (!deletePath(destPath)) {
            System.err.println("failed to delete destPath " + path);
            return;
        }
        System.out.println("success to delete path " + path);

    }

    /**
     * 创建目录
     *
     * @param path 目录名
     * @throws IOException
     */
    public static void mkdir(String path) throws IOException {
        Path destPath = new Path(path);
        if (!createPath(destPath)) {
            log.info("failed to create destPath " + path);
            return;
        }
        log.info("success to create path " + path);
    }


    /**
     * 向文件中写内容
     *
     * @param fileName 文件名，包含文件绝对路径
     * @param content  文件内容
     * @param append   是否追加
     * @throws IOException
     * @throws ParameterException
     */
    public static void write(String fileName, String content, boolean append) throws IOException, ParameterException {
        InputStream in = new ByteArrayInputStream(content.getBytes());
        try {
            HdfsWriter writer = new HdfsWriter(fileSystem, fileName);
            if (append)
                writer.doAppend(in);
            else
                writer.doWrite(in);
            log.info("success to write.");
        } finally {
            // make sure the stream is closed finally.
            close(in);
        }
    }

    /**
     * 读取文件
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public static String read(String fileName) throws IOException {
        Path path = new Path(fileName);
        FSDataInputStream in = null;
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();

        try {
            in = fileSystem.open(path);
            reader = new BufferedReader(new InputStreamReader(in));
            String sTempOneLine;

            // write file
            while ((sTempOneLine = reader.readLine()) != null) {
                sb.append(sTempOneLine);
            }

            log.info("success to read.");
            return sb.toString();
        } finally {
            // make sure the streams are closed finally.
            close(reader);
            close(in);
        }
    }

    /**
     * 删除文件
     *
     * @param fileName
     * @throws IOException
     */
    public static void delete(String fileName) throws IOException {
        if (fileSystem.delete(new Path(fileName), true))
            System.out.println("success to delete the file " + fileName);
        else
            System.err.println("failed to delete the file " + fileName);

    }

    /**
     * close stream
     *
     * @param stream
     * @throws IOException
     */
    public static void close(Closeable stream) throws IOException {
        stream.close();
    }

    /**
     * 创建目录
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static boolean createPath(final Path filePath) throws IOException {
        if (!fileSystem.exists(filePath)) {
            fileSystem.mkdirs(filePath);
        }
        return true;
    }

    /**
     * 删除目录
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static boolean deletePath(final Path filePath) throws IOException {
        if (!fileSystem.exists(filePath)) {
            return false;
        }
        // fSystem.delete(filePath, true);
        return fileSystem.delete(filePath, true);
    }
}
