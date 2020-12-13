package com.springboot.cs.common.utils;

import com.alibaba.fastjson.JSON;

import java.util.LinkedList;
import java.util.List;

public class IdGenUtil {

    private long workerId;
    private long datacenterId;
    private long sequence = 0L;
    private long twepoch = 1288834974657L; //Thu, 04 Nov 2010 01:42:54 GMT
    private long workerIdBits = 5L; //节点ID长度
    private long datacenterIdBits = 5L; //数据中心ID长度
    private long maxWorkerId = -1L ^ (-1L << workerIdBits); //最大支持机器节点数0~31，一共32个
    private long maxDatacenterId = -1L ^ (-1L << datacenterIdBits); //最大支持数据中心节点数0~31，一共32个
    private long sequenceBits = 12L; //序列号12位
    private long workerIdShift = sequenceBits; //机器节点左移12位
    private long datacenterIdShift = sequenceBits + workerIdBits; //数据中心节点左移17位
    private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits; //时间毫秒数左移22位
    private long sequenceMask = -1L ^ (-1L << sequenceBits); //4095
    private long lastTimestamp = -1L;

    public static class IdGenHolder {
        private static final IdGenUtil instance = new IdGenUtil();
    }

    public static IdGenUtil get() {
        return IdGenHolder.instance;
    }

    //无参构造
    public IdGenUtil() {
        this(0L,0L);
    }

    //有参构造
    public IdGenUtil(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("workerId不能大于%d也不能小于0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenterId不能大于%d也不能小于0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    //获取下一个时间
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    //获取当前时间
    protected long timeGen() {
        return System.currentTimeMillis();
    }

    //获取下一个id
    public synchronized String nextId() {

        //获取当前毫秒数
        Long timestamp = timeGen();

        //如果服务器时间有问题(时钟后退) 报错。
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("服务器时间回退，报错，%d", lastTimestamp - timestamp));
        }

        //如果上次生成时间和当前时间相同,在同一毫秒内
        if(lastTimestamp == timestamp) {
            //sequence自增，因为sequence只有12bit，所以和sequenceMask相与一下，去掉高位
            sequence = (sequence + 1) & sequenceMask;

            //判断是否溢出,也就是每毫秒内超过4095，当为4096时，与sequenceMask相与，sequence就等于0
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp); //自旋等待到下一毫秒
            }

        }else {
            sequence = 0L; //如果和上次生成时间不同,重置sequence，就是下一毫秒开始，sequence计数重新从0开始累加
        }

        //赋值最后时间
        lastTimestamp = timestamp;

        // 最后按照规则拼出ID。
        // 000000000000000000000000000000000000000000  00000            00000       000000000000
        // time                                         datacenterId   workerId    sequence
        return String.valueOf(((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift) | sequence);
    }


    public List<String> getIdList(Integer size) {

        List<String> list = new LinkedList<String>();
        if(size < 0) {
            return list;
        }

        for(int i=0;i<size;i++) {
            String id = nextId();
            list.add(id);
        }

        return list;
    }

    public static void main(String[] args) {
        IdGenUtil idGen = IdGenUtil.get();
        String id = idGen.nextId();
        System.out.println(id);

        List<String> list = idGen.getIdList(5);
        System.out.println(JSON.toJSON(list));
    }

}